package com.auroratechdevelopment.common.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Edward on 2015-09-13.
 */
public class WebViewUtils {


    public static String getHtmlString(Context context, int rawResId) {
        String html = "";
        int size;
        try {
            InputStream input = context.getResources().openRawResource(rawResId);
            size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            html = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }


}
