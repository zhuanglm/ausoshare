package com.auroratechdevelopment.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Edward on 2015-08-09.
 */
public class IntentUtils {

    public static Intent newDialIntent(Context context, String phoneUrl) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneUrl));
        return intent;
    }

    public static Intent newDirectionIntent(double lat, double lon) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format("http://maps.google.com/maps?saddr=&daddr=%s,%s",
                        Double.toString(lat),
                        Double.toString(lon))));

        return intent;
    }

    public static Intent newLocationIntent(double lat, double lon) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format("http://maps.google.com/maps?q=loc:%s,%s",
                        Double.toString(lat),
                        Double.toString(lon))));

        return intent;
    }
}
