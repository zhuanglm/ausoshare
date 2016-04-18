package com.auroratechdevelopment.common;

import android.app.Activity;

public class ThemesSetting
{
	private static int sTheme;

	public final static int THEME_DEFAULT = 0;
	public final static int THEME_WHITE = 1;
	public final static int THEME_BLUE = 2;
	public final static int THEME_BLUE_WHITE = 3;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	private static void changeToTheme(int theme)
	{
		sTheme = theme;
//		
//		activity.finish();
//
//		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/** Set the theme of the activity, according to the configuration. */
	public static void onActivityCreateSetTheme(Activity activity) {
		switch (sTheme)	{
			case THEME_DEFAULT:			
			default:
				//activity.setTheme(R.style.Theme_Commons);
				break;
		}
	}
	
	public static int getActivityTheme() {
		return sTheme;
	}
	
	public static void setActivityTheme() {
		changeToTheme(ThemesSetting.THEME_DEFAULT);
	}

}