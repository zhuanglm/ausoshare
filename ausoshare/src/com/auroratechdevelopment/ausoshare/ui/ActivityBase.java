package com.auroratechdevelopment.ausoshare.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.ext.MyProgressDialog;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.CheckNetworkStatus;
import com.auroratechdevelopment.common.DebugLogUtil;
import com.auroratechdevelopment.common.ThemesSetting;
import com.auroratechdevelopment.common.persistent.SharedPreferenceManager;
import com.auroratechdevelopment.common.validation.ValidatorBase;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.WebServiceHelper.WebServiceListener;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;


/**
 * @author Edward
 * Updated by Raymond Zhuang 2016/5/3
ActionBarActivity
 */
public abstract class ActivityBase extends ActionBarActivity implements
        WebServiceListener {

    public static final String ActionTag_SlideMenuClicked = "com.auroratechdevelopment.ausomedia.SlideMenuClicked";


    public interface OnSlidingMenuListener {
        public void OnSlidingMenuSelect(int item);
    }

    BroadcastReceiver receiver;
    protected Button btnBack;
    protected RelativeLayout btnBackLayout;
    protected Button btnInfo;
    protected TextView txtTitle;
    protected TextView txtCashValue;
    protected Button btnBonus, btnPoints;
    protected MyProgressDialog waitingDialog;

    protected TextView txtInput;

    protected CustomAlertDialog customAlertDialog;
 
    protected TextView txtInfo;
    protected static boolean isShowedPinLock = false;

    private EditText txtAPIKey;

    private boolean isForeGround;

    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;
    public static boolean isBackPressed = false;

    protected boolean isLoginView = false;

    public CheckNetworkStatus checkNetworkStatus;

    //    protected SlidingMenu menu;
    private OnSlidingMenuListener slidingMenuListener;

    public void setSlidingMenuListener(OnSlidingMenuListener listener) {
        this.slidingMenuListener = listener;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        
      }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemesSetting.setActivityTheme();
        ThemesSetting.onActivityCreateSetTheme(this);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        checkNetworkStatus = new CheckNetworkStatus(this);

        if (!checkNetworkStatus.getNetworkStatus()) {
            Toast.makeText(this, getResources().getString(R.string.network_unavailable_info), Toast.LENGTH_LONG).show();
        }

        hidActionBar();

        setView();
        setCommonView();
        viewInitialized();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals(ActivityBase.ActionTag_SlideMenuClicked)) {
                    if (context instanceof HomeActivity) {
                    } else {
                        finish();
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(ActivityBase.ActionTag_SlideMenuClicked));

    }


    public void BitmapRecycle(Bitmap recycleBitmap){
    	//recycle image
		if(recycleBitmap != null && !recycleBitmap.isRecycled()){
			recycleBitmap.recycle();
			recycleBitmap = null;
		}
		System.gc();
    }

//    private void viewPendingOrders() {
//        ViewUtils.startPage(null, ActivityBase.this, PendingOrdersActivity.class);
//    }
//
//    private void viewCart() {
//        ViewUtils.startPage(null, ActivityBase.this, CartActivity.class);
//    }
//
//    private void logout() {
////        SharedPreferenceManager.clearAll();
//        ViewUtils.startPageWithClearStack(null, ActivityBase.this, LoginActivity.class);
//    }
//
//    private void viewAbout() {
//        ViewUtils.startPage(null, ActivityBase.this, AboutActivity.class);
//    }
//
//    private void PendingUsers(){
//        ViewUtils.startPage(null, ActivityBase.this, PendingUserActivity.class);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
//            case android.R.id.home:
//                menu.toggle();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void setView();

    protected abstract void viewInitialized();

    private void setCommonView() {

        btnBack = (Button) findViewById(R.id.backButton);
        btnBackLayout = (RelativeLayout) findViewById(R.id.backButton_layout);
        
        if (btnBack != null) {
            btnBack.setOnClickListener(clickedCommon);
        }
        
        if(btnBackLayout != null){
        	btnBackLayout.setOnClickListener(clickedCommon);
        }

        txtTitle = (TextView) findViewById(R.id.text_title);

//        setupSlidingMenu();
    }

    protected void sendAppBroadCast(String actionTag) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(actionTag);
        sendBroadcast(broadcastIntent);
    }


    public boolean isAdminUser() {
        return !TextUtils.isEmpty(CustomApplication.getInstance().getUsername())
                && CustomApplication.getInstance().getUsername().equals("admin");
    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isForeGround = true;

        CustomApplication.getInstance().setCurrentActivity(this);
        WebServiceHelper.getInstance().setListener(this);
    }


    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
        isForeGround = false;

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        clearReferences();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        CustomApplication.activityCount++;
        applicationWillEnterForeground();
        super.onStart();
    }

    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        CustomApplication.activityCount--;

        if (this instanceof HomeActivity) {
            if (!isBackPressed) {
                applicationDidEnterBackground();
            }

        } else {
            applicationDidEnterBackground();
        }

    }

    public void applicationDidEnterBackground() {
        if (!isWindowFocused) {
            if (!isShowedPinLock) {
                if (!isLoginView && CustomApplication.activityCount == 0) {
                }
            }
            isAppWentToBg = true;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        isWindowFocused = hasFocus;

        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }


    private OnClickListener clickedCommon = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_info:
                    showInfo();
                    break;
                case R.id.backButton_layout:
                case R.id.backButton:
                    ActivityBase.this.onBackPressed();
                    break;
                default:
                    break;
            }
        }
    };
    
    private void clearReferences() {
        Activity currActivity = CustomApplication.getInstance()
                .getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            CustomApplication.getInstance().setCurrentActivity(null);

    }

    protected void showInfo() {


    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {

    }

    @Override
    public void ResponseFailedCallBack(int tag, ResponseBase response) {
        dismissWaiting();
        showAlert(this,
                getResources().getString(R.string.error_title),
                response.responseMessage);
    }


    @Override
    public void ResponseConnectionError(int tag, ResponseBase response) {
        dismissWaiting();
        showAlert(this,
                getResources().getString(R.string.error_title),
                response.responseMessage);
    }

    public void dismissWaiting() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    waitingDialog.dismiss();
                }
            }
        });
    }

    public void showWaiting() {
        dismissWaiting();

        runOnUiThread(new Runnable() {
            public void run() {
                waitingDialog = MyProgressDialog.show(ActivityBase.this, "",
                        "", false, true);
            }
        });
    }


    public void showAlert(final Context context, String title, String message) {
        showCenterScreenAlert(context, title, message,
                getString(R.string.button_ok), null, null, true);
    }
    
    public void showAlertOkCancel(final Context context, String title, String message) {
    	
    	Log.e("Edward", "showAlertOkCancel");
        showCenterScreenAlert(context, title, message,
                getString(R.string.button_ok), getString(R.string.button_cancel), null, true);
    }
    
    public void showCenterScreenAlert(final Context context,
                                      final CharSequence alertTitle, final String msg,
                                      final String okButtonName, final String cancelButtonName,
                                      final CustomAlertDialog.AlertCallback callback, final boolean isCancelable) {
    	
    	Log.e("Edward", "showCenterScreenAlert");
    	
        this.runOnUiThread(new Runnable() {
            public void run() {
                dismissCustomAlertDialog();
                ((ActivityBase) context).customAlertDialog = new CustomAlertDialog(
                        context, R.style.alertStyle);
                customAlertDialog.setContentView(R.layout.alert_center_screen);
                customAlertDialog.setMessage(msg, R.id.alert_message);
                customAlertDialog.setTitle(alertTitle, R.id.alert_title);
                customAlertDialog
                        .setOnCancelListener(alertCancelClicked(callback));
                customAlertDialog.setOnDismissListener(alertDismissClicked());
                if (okButtonName != null && okButtonName.length() > 0) {
                    customAlertDialog.setButton(okButtonName,
                            alertButton0Clicked(callback));
                }

                if (cancelButtonName != null && cancelButtonName.length() > 0) {
                    customAlertDialog.setButton2(cancelButtonName,
                            alertButton1Clicked(callback));
                }

                customAlertDialog.setCancelable(isCancelable);
                customAlertDialog.show();
            }
        });
    }

    public void dismissCustomAlertDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (customAlertDialog != null && customAlertDialog.isShowing()) {
                    customAlertDialog.dismiss();
                }
            }
        });
    }

    protected DialogInterface.OnClickListener alertButton0Clicked(
            final CustomAlertDialog.AlertCallback callback) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.GetAlertButton(which);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                return;
            }
        };
    }

    protected DialogInterface.OnClickListener alertButton1Clicked(
            final CustomAlertDialog.AlertCallback callback) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.GetAlertButton(which);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                return;
            }
        };
    }

    protected DialogInterface.OnCancelListener alertCancelClicked(final CustomAlertDialog.AlertCallback callback) {
        return new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (callback != null)
                    callback.AlertCancelled();
            }
        };
    }

    protected DialogInterface.OnDismissListener alertDismissClicked() {
        return new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                return;
            }
        };
    }


    protected void setBackButton(String text) {
        if (btnBack == null)
            return;
        if (TextUtils.isEmpty(text)) {
            btnBack.setVisibility(View.INVISIBLE);
        } else {
            btnBack.setText(text);
        }
    }

    protected String cleanupInputTextViewString(String input) {

        if (input.indexOf("$") > -1)
            input = input.substring(1);

        if (input.indexOf(",") > -1) {
            input = input.replaceAll(",", "");
        }
        if (input.indexOf(":") > -1) {
            input = input.substring(input.indexOf(":") + 1);
        }
        input = input.trim();
        return input;
    }


    protected <T extends ValidatorBase> boolean runValidation(Class<T> cls,
                                                              String value, String... args) {
        try {
            T validator = cls.newInstance();
            validator.setContext(this);
            ValidatorBase.ValidationResult result = validator.ValidateValue(
                    value, args);

            if (result.isValid()) {
                return true;
            }
            showAlert(CustomApplication.getInstance().getCurrentActivity(),
                    getString(R.string.title_validation_failed),
                    result.getError());
            return false;
        } catch (Exception ex) {
            DebugLogUtil.Log(ex, "validation error");
        }
        return true;
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showBackButton(boolean show) {
        if (btnBack != null) {
            if (show) {
                btnBack.setVisibility(View.VISIBLE);
            } else {
                btnBack.setVisibility(View.GONE);
            }
        }
    }

    protected void setTopBarTitle(String text) {
        setTitle(text);
    }

    public boolean isInForeGround() {
        return this.isForeGround;
    }

    protected void hidActionBar() {
        getSupportActionBar().hide();
    }

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}


}
