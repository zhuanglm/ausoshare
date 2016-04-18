package com.auroratechdevelopment.common.util;

import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.android.gms.maps.model.LatLng;
import com.auroratechdevelopment.common.DebugLogUtil;

import java.io.IOException;

/**
 * Created by Edward on 2015-05-17.
 */
public class MediaUtils {

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static LatLng getGPSFromImage(Context context, Uri contentUri) {

        return getGPSFromImage(context, getRealPathFromURI(context, contentUri));

    }


    public static LatLng getGPSFromImage(Context context, String path) {
        float[] ll = {0, 0};
        try {
            ExifInterface exif = new ExifInterface(path);

            exif.getLatLong(ll);

            if (ll != null && ll.length>1) {
                DebugLogUtil.LogD(String.format("exif lat: %s lon: %s",
                        Float.toString(ll[0]), Float.toString(ll[1])));
            }
        } catch (IOException e) {
            DebugLogUtil.Log(e, "MediaUtils getGPSFromImage");
        }
        return new LatLng(ll[0], ll[1]);
    }
}
