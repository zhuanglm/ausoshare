package com.auroratechdevelopment.common.webservice;

import android.content.Context;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.common.DebugLogUtil;
import com.auroratechdevelopment.common.webservice.request.RequestBase;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.ResponseErrorNumber;
import com.tencent.mm.sdk.platformtools.Log;

//import org.apache.http.client.HttpResponseException;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/**
 * The central class for web service communication
 *
 */
public class WebService {
    static private int g_Identifier = 0;

    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /***
     * The callback to invoke when an async web service returns.
     *
     * @param <T>
     *            The type of ResponseBase to return to the callback.
     */
    public interface WebServiceCallback<T extends ResponseBase> {
        void ResponseReady(int id, int tag, T response);
    };

    /***
     * Helper item that holds information about an async request
     *
     * @param <X>
     *            The type of webservice to invoke.
     * @param <Y>
     *            The type of response object to get back from the webservice.
     */
    static private class QueueItem<X extends RequestBase<Y>, Y extends ResponseBase> {
        public WebServiceCallback<Y> m_Callback;
        public RequestBase<Y> m_Request;
        public Thread m_Thread;
        public int m_Id;
        public int m_tag;

        private Runnable worker() {
            return new Runnable() {
                public void run() {
                    Y response = WebService.sendRequest(m_Request);
                    m_Callback.ResponseReady(m_Id, m_tag, response);
                }
            };
        }
    };

    /* start debug */
    static private OutputStream getOutputStream(final OutputStream stream) {
        if (!WebServiceConstants.DoDebug)
            return stream;

        return new OutputStream() {
            StringBuilder sb = new StringBuilder();

            @Override
            public void write(int arg0) throws IOException {
                sb.append((char) arg0);
                stream.write(arg0);
            }

            @Override
            public void write(byte[] buffer) throws IOException {
                sb.append(new String(buffer));
                stream.write(buffer);
            }

            @Override
            public void write(byte[] buffer, int offset, int count)
                    throws IOException {
                sb.append(new String(buffer, offset, count));
                stream.write(buffer, offset, count);
            }

            @Override
            public void flush() throws IOException {
                stream.flush();
            }

            @Override
            public void close() throws IOException {
                DebugLogUtil.LogD("DGSWebService: Request");
                DebugLogUtil.LogD(sb.toString());
                stream.close();
            }
        };
    }

    static private InputStream getInputStream(final InputStream stream) {
        if (!WebServiceConstants.DoDebug)
            return stream;

        return new InputStream() {
            StringBuilder sb = new StringBuilder();

            @Override
            public int read() throws IOException {
                int val = stream.read();
                sb.append((char) val);
                return val;
            }

            @Override
            public int read(byte[] b) throws IOException {
                int val = stream.read(b);
                if (val > 0)
                    sb.append(new String(b, 0, val));
                return val;
            }

            @Override
            public int read(byte[] b, int offset, int length)
                    throws IOException {
                int val = stream.read(b, offset, length);
                if (val > 0)
                    sb.append(new String(b, offset, val));
                return val;
            }

            @Override
            public int available() throws IOException {
                return stream.available();
            }

            @Override
            public void mark(int readlimit) {
                stream.mark(readlimit);
            }

            @Override
            public boolean markSupported() {
                return stream.markSupported();
            }

            @Override
            public synchronized void reset() throws IOException {
                stream.reset();
            }

            @Override
            public long skip(long n) throws IOException {
                return stream.skip(n);
            }

            @Override
            public void close() throws IOException {
                DebugLogUtil.LogD("DGSWebService: Response");
                DebugLogUtil.LogD(sb.toString());
                super.close();
                stream.close();
            }

        };
    }

	/* end debug */

    /***
     * Begins an asynchronous web service call.
     *
     * @param <X>
     *            The type of webservice to invoke.
     * @param <Y>
     *            The type of response object to get back from the webservice.
     * @param request
     *            The request object to send.
     * @param callback
     *            The object to be invoked when the web service responds.
     * @return The unique identifier for the webservice that is invoked
     */
    static public <X extends RequestBase<Y>, Y extends ResponseBase> int sendRequestAsync(
            int tag, X request, WebServiceCallback<Y> callback) {
        QueueItem<X, Y> item = new QueueItem<X, Y>();
        item.m_Request = request;
        item.m_Callback = callback;
        item.m_Thread = new Thread(item.worker(), String.format(
                "Worker thread %d", item.m_Id));
        item.m_Id = g_Identifier++;
        item.m_tag = tag;
        item.m_Thread.start();


        return item.m_Id;
    }

    static public <X extends RequestBase<Y>, Y extends ResponseBase> int sendRequestAsync(
            X request, WebServiceCallback<Y> callback) {

        return sendRequestAsync(0, request, callback);

    }

    /***
     * Executes a web service and returns the result as an ResponseBase object.
     *
     * @param <X>
     *            The type of webservice to invoke.
     * @param <Y>
     *            The type of response object to get back from the webservice.
     * @param request
     *            The request object to send.
     * @return The parsed web service as a response object
     */
    static public <X extends RequestBase<Y>, Y extends ResponseBase> Y sendRequest(
            X request) {

        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        Exception e = null;
        ResponseErrorNumber errNumber = ResponseErrorNumber.Success;

        Y response = request.getResponse();

        try {
            String requestMethod = request.getRequestMethodType();

            URL url = new URL(request.getUri());
            if (url.getProtocol().toLowerCase(CustomApplication.appLocale)
                    .equals("https")) {

                SSLContext sc = null;
                Context context = CustomApplication.getContext();

                if (WebServiceConstants.ClientCertResId != -1) {
                    InputStream inputStream = context.getResources()
                            .openRawResource(
                                    WebServiceConstants.ClientCertResId);
                    // Using a client certificate
                    sc = SSLContextFactory.getInstance()
                            .makeContext(inputStream,
                                    WebServiceConstants.ClientCertPassword);

                    HttpsURLConnection.setDefaultSSLSocketFactory(sc
                            .getSocketFactory());
                }

                HttpsURLConnection https = (HttpsURLConnection) url
                        .openConnection();

                // https.setHostnameVerifier(DO_NOT_VERIFY);
                connection = https;
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
            connection
                    .setConnectTimeout((int) (1000F * WebServiceConstants.WebServiceConnectionTimeout));
            connection
                    .setReadTimeout((int) (1000F * WebServiceConstants.WebServiceReadTimeout));
            connection.setDoOutput(true);

            // set the connection properties needed for the web service
            connection.setRequestProperty("Accept", "Application/JSON");
            connection.setRequestProperty("Content-Type", "Application/JSON");
            connection.setRequestMethod(requestMethod);

            if (requestMethod.equalsIgnoreCase("GET")){
                connection.setDoOutput(false);

            } else {
                connection.setDoOutput(true);
                // write the request
                os = getOutputStream(connection.getOutputStream());
                request.writeRequest(os);
                os.flush();
            }

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpsURLConnection.HTTP_OK) {
                if (statusCode == HttpsURLConnection.HTTP_FORBIDDEN
                        || statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {

                    errNumber = ResponseErrorNumber.ClientNotAuthorized;

                } else {
                    errNumber = ResponseErrorNumber.ServerResponseError;
                }

                response.setSuccess(false);
                response.setErrorNumber(errNumber);
                response.setMessage("HTTPS URL Connection ERROR: " + statusCode);
                return response;
            }

            // get and parse the reply
            is = getInputStream(connection.getInputStream());
            response = response.Parse(is);

            if (response != null) {
            	
            	Log.e("Edward", "response.reasonCode = " + response.reasonCode);
                response.parseServerCode(response.reasonCode);
            }
        } catch (UnknownHostException unknownHostEx) { // no internet connection
            e = unknownHostEx;
            errNumber = ResponseErrorNumber.NoInternetConnection;
        } catch (SocketException socketEx) { // socket failure
            e = socketEx;
            errNumber = ResponseErrorNumber.NoInternetConnection;
        } catch (SocketTimeoutException timeoutEx) { // a timeout
            e = timeoutEx;
            errNumber = ResponseErrorNumber.Timeout;
        } catch (MalformedURLException malEx) { // invalid url
            e = malEx;
            errNumber = ResponseErrorNumber.InvalidURL;
        }
//        catch (HttpResponseException responseEx) {
//            e = responseEx;
//            errNumber = ResponseErrorNumber.ServerResponseError;
//        }
        catch (SAXException saxEx) { // invalid xml
            e = saxEx;
            errNumber = ResponseErrorNumber.InvalidResponse;
        } catch (XmlPullParserException pullEx) { // an error occured while
            // parsing the result
            e = pullEx;
            errNumber = ResponseErrorNumber.UnknownCommunicationError;
        } catch (FileNotFoundException fileEx) {
            e = fileEx;
            errNumber = ResponseErrorNumber.BlockedByFirewall;
        } catch (IOException ioEx) { // something happened to either the request
            // or response stream
            e = ioEx;
            errNumber = ResponseErrorNumber.IOError;
        } catch (Exception ex) {
            e = ex;
            errNumber = ResponseErrorNumber.UnknownCommunicationError;
        } finally {
            if (connection != null)
                connection.disconnect();

            if (os != null)
                try {
                    os.close();
                } catch (Exception ex) {
                    // ex.printStackTrace();
                }
            if (is != null)
                try {
                    is.close();
                } catch (Exception ex) {
                    // ex.printStackTrace();
                }
        }

        if (e != null) {
            response.setSuccess(false);
            response.setErrorNumber(errNumber);
            response.setMessage(e.getMessage());
            DebugLogUtil.Log(e, "Web service get response");
        }

        return response;
    }
}
