package com.auroratechdevelopment.ausoshare.ui.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.ausoshare.validation.EmailValidator;
import com.auroratechdevelopment.ausoshare.validation.PasswordValidator;
import com.auroratechdevelopment.ausoshare.validation.UsernameValidator;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by happy pan on 2015/11/6.
 */
public class RegisterActivity extends ActivityBase implements View.OnClickListener {
    private EditText txtUserName,txtCode;
    private EditText txtEmail, txtPassWord, txtPasswordConfirm;
    private TextView textBirthday, textGender;
    private Button btnRegister;

    private StringBuilder str = new StringBuilder("");

    private int mYear, mMonth, mDay;
    private final int DATE_DIALOG_ID = 0;

    private static final String[] Genders = new String[]{"Male", "Female"};

    private RadioOnClick radioOnClick = new RadioOnClick(1);

    private String email,promotion_code;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_user_register);

        txtUserName = (EditText) findViewById(R.id.text_username);
        txtPassWord = (EditText) findViewById(R.id.text_update_password);
        txtEmail = (EditText) findViewById(R.id.text_register_email);
        txtPasswordConfirm = (EditText) findViewById(R.id.text_password_confirm);
        textBirthday = (TextView) findViewById(R.id.text_birthday);
        textGender = (TextView) findViewById(R.id.text_gender);
        txtCode = (EditText) findViewById(R.id.promotion_code);
        btnRegister = (Button) findViewById(R.id.btn_register);

   //     btnRegister.setOnClickListener(this);
        textBirthday.setOnClickListener(this);
        textGender.setOnClickListener(this);
    }

    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_gender:
                AlertDialog ad = new AlertDialog.Builder(RegisterActivity.this).setTitle(getResources().getString(R.string.gender_text))
                        .setSingleChoiceItems(Genders, radioOnClick.getIndex(), radioOnClick).create();
                ad.show();
                break;
            case R.id.text_birthday:
                showDialog(DATE_DIALOG_ID);
//                showDate();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
    	final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.CONTACT_PAGE);
		ViewUtils.startPageWithClearStack(bundle, RegisterActivity.this, HomeActivity.class);
		hideSoftBoard();
        super.onBackPressed();
    }

    public void showDate() {
        Calendar c = Calendar.getInstance();
        Dialog dateDialog = new DatePickerDialog(RegisterActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year,
                                          int month, int dayOfMonth) {
                        str.append(year + "-" + (month + 1) + "-"
                                + dayOfMonth + " ");
                        Calendar time = Calendar.getInstance();
                        Dialog timeDialog = new TimePickerDialog(
                                RegisterActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(
                                            TimePicker tp,
                                            int hourOfDay, int minute) {
                                        str.append(hourOfDay + ":"
                                                + minute);
                                        TextView show = (TextView) findViewById(R.id.text_birthday);
                                        show.setText(str);
                                    }
                                }
                                , time.get(Calendar.HOUR_OF_DAY), time
                                .get(Calendar.MINUTE)
                                , true);
                        timeDialog.setTitle("请选择日期");
                        timeDialog.show();


                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH));
        dateDialog.setTitle("请选择日期");
        dateDialog.show();
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mYear);
        }
        return null;
    }
    
    private void hideSoftBoard(){
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    	imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(txtPassWord.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(txtPasswordConfirm.getWindowToken(), 0);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    private void updateDisplay() {
        textBirthday.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth).append("-")
                        .append(mDay)
        );
    }

    class RadioOnClick implements DialogInterface.OnClickListener {
        private int index;

        public RadioOnClick(int index) {
            this.index = index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            setIndex(whichButton);
            textGender.setText(Genders[index]);
            dialog.dismiss();
        }

    }

    @Override
    protected void viewInitialized() {
        //  menu.setVisibility(View.INVISIBLE);
    }

    public void onRegisterButtonClicked(View v) {
        username = txtUserName.getText().toString().trim();
        password = txtPassWord.getText().toString().trim();
        String passwordConfirm = txtPasswordConfirm.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        promotion_code = txtCode.getText().toString().trim();

//        if (!runValidation(UsernameValidator.class, username)) {
//            return;
//        }

        if (!runValidation(EmailValidator.class, email)) {
            return;
        }

        if (!runValidation(PasswordValidator.class, password, passwordConfirm)) {
            return;
        }

        showWaiting();

        WebServiceHelper.getInstance().register(username, email, password,
                Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID),promotion_code);
//        Toast.makeText(this, "Register information sent out", Toast.LENGTH_LONG).show();
    }


    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        dismissWaiting();
        super.ResponseFailedCallBack(tag, response);
    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {
        dismissWaiting();

        if (response instanceof RegisterResponse) {
        	final RegisterResponse registerResponse = (RegisterResponse) response;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CustomApplication.getInstance().setUsername(username);
                    
                    Log.e("Edward", "RegisterActivity: Token is: " + registerResponse.token);
                    
                    CustomApplication.getInstance().setEmail(email);
                    CustomApplication.getInstance().setUserToken(registerResponse.token);
                    
                    hideSoftBoard();
                    
                    final Bundle bundle = new Bundle(); 
    		        bundle.putString(Constants.LAST_PAGE, Constants.CONTACT_PAGE);
                    ViewUtils.startPageWithClearStack(bundle, RegisterActivity.this, HomeActivity.class);
                }
            });
        }

        super.ResponseSuccessCallBack(tag, response);
    }
    
    
    @Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}


	public  boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
