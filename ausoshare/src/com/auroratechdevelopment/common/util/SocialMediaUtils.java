package com.auroratechdevelopment.common.util;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import java.net.URL;


/**
 * Created by Raymond Zhuang on 2016-06-01.
 */
public  class SocialMediaUtils {
    /*public static void TwitterOnShared(Context context,AdDataItem adDataItem){

        String TWITTER_KEY = "L0MfjIUlqgMGjGsQiLktfSPD6";
        String TWITTER_SECRET = "wbih4mNfjZR7uOXm7hq5EXqyxyrbAGLeZ3MfcBWwfIwnobKI2I";

        URL adUrl = null;
        try{
            adUrl = new URL(adDataItem.reviewURL);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig));

        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                .text(adDataItem.description_long)
                .url(adUrl)
                .image(Uri.parse(adDataItem.thumb));
        builder.show();


    }*/

    public static void FacebookOnShared(ShareDialog shareDialog,AdDataItem adDataItem){

        try {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(adDataItem.description)
                        .setContentDescription(adDataItem.description_long)
                        .setContentUrl(Uri.parse(adDataItem.reviewURL))
                        .setImageUrl(Uri.parse(adDataItem.thumb))
                        .build();
                shareDialog.show(linkContent);
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Error Sharing with Facebook.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
