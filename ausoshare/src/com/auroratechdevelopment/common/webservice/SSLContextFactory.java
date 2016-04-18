package com.auroratechdevelopment.common.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
*
* @author Edward Liu
*/
public class SSLContextFactory {

    private static SSLContextFactory theInstance = null;
    private SSLContext sslContext;
    
    private SSLContextFactory() {
    }

    public static SSLContextFactory getInstance() {
        if(theInstance == null) {
            theInstance = new SSLContextFactory();
        }
        return theInstance;
    }
    
    /**
     * Creates an SSLContext with the client and server certificates
     */
    public SSLContext makeContext(InputStream fileStream, String clientCertPassword) throws Exception {
    	if(sslContext != null) {
    		return sslContext;
    	}
    	
        sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = setTrustManagers();
        
        if (fileStream == null ) {
        	sslContext.init(null, trustAllCerts, new java.security.SecureRandom());	
        	return sslContext;
        }
        
    	final KeyStore keyStore = loadPKCS12KeyStore(fileStream, clientCertPassword);
    	
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, clientCertPassword.toCharArray());
        KeyManager[] keyManagers = kmf.getKeyManagers();

        sslContext.init(keyManagers, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }

    private TrustManager[] setTrustManagers() {
		// Create a trust manager that does not validate certificate chains 
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[] {};
                } 
 
                public void checkClientTrusted(X509Certificate[] chain,
                                String authType) throws CertificateException {
                } 
 
                public void checkServerTrusted(X509Certificate[] chain,
                                String authType) throws CertificateException {
                } 
        } };
		return trustAllCerts;
	}
    
    /**
     * Produces a KeyStore from a PKCS12 (.p12) certificate file, typically the client certificate
     */
    private KeyStore loadPKCS12KeyStore(InputStream fileStream, String clientCertPassword) throws Exception {

    	KeyStore keyStore = null;
        try {

        	keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileStream, clientCertPassword.toCharArray());
          
        } finally {
            try {
                if(fileStream != null) {
                	fileStream.close();
                }
            } catch(IOException ex) {
                // ignore
            }
        }
        return keyStore;
    }
    
}
