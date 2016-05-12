package com.auroratechdevelopment.ausoshare.ui.contact;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeFragmentBase;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebService;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.request.GetURLRequest;
import com.auroratechdevelopment.common.webservice.response.ForgotPasswordResponse;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.LoginResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

import org.w3c.dom.Text;

/**
 * Created by Edward Liu on 2015/11/10.
 * Updated by Raymond Zhuang 2016/4/26
 */
public class ContactFragment extends HomeFragmentBase implements View.OnClickListener {
    //private TextView contactTCText, contactIncomeRuleText, contactWithdrawRuleText, contactCompanyIntroductionText, contactCustomerText, contactQuestionsAnswersText;
    //private ImageButton  contactIncomeRuleImage, contactCompanyIntroductionImage, contactQuestionsAnswersImage;
    //private ImageButton contactTCImage, contactWithdrawRuleImage, contactCustomerImage;
	private TextView contactTCText, contactIncomeRuleText, contactWithdrawRuleText, contactCompanyIntroductionText, contactCustomerText, contactQuestionsAnswersText;
    private ImageView contactTCImage, contactIncomeRuleImage, contactWithdrawRuleImage, contactCompanyIntroductionImage, contactCustomerImage, contactQuestionsAnswersImage;
    protected HomeActivity homeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*View rootView = inflater.inflate(R.layout.fragment_contact_v2, container,false);
        
        contactTCText = (TextView) rootView.findViewById(R.id.contact_t_c_tv);
        contactTCImage = (ImageButton) rootView.findViewById(R.id.contact_t_c_image);
        contactIncomeRuleText = (TextView) rootView.findViewById(R.id.contact_income_rule_tv);
        contactIncomeRuleImage = (ImageButton) rootView.findViewById(R.id.contact_income_rule_image);
        contactWithdrawRuleText = (TextView) rootView.findViewById(R.id.contact_withdraw_rule_tv);
        contactWithdrawRuleImage = (ImageButton) rootView.findViewById(R.id.contact_withdraw_rule_image);
        contactCompanyIntroductionText = (TextView) rootView.findViewById(R.id.contact_company_introduction_tv);
        contactCompanyIntroductionImage = (ImageButton) rootView.findViewById(R.id.contact_company_introduction_image);
        contactQuestionsAnswersText = (TextView)rootView.findViewById(R.id.contact_questions_answers_tv);
        contactQuestionsAnswersImage = (ImageButton)rootView.findViewById(R.id.contact_questions_answers_image);
        contactCustomerText = (TextView) rootView.findViewById(R.id.contact_customer_tv);
        contactCustomerImage = (ImageButton) rootView.findViewById(R.id.contact_customer_image);

        contactTCText.setOnClickListener(this);
        contactTCImage.setOnClickListener(this);
        contactIncomeRuleText.setOnClickListener(this);
        contactIncomeRuleImage.setOnClickListener(this);
        contactWithdrawRuleText.setOnClickListener(this);
        contactWithdrawRuleImage.setOnClickListener(this);
        contactCompanyIntroductionText.setOnClickListener(this);
        contactCompanyIntroductionImage.setOnClickListener(this);
        contactQuestionsAnswersText.setOnClickListener(this);
        contactQuestionsAnswersImage.setOnClickListener(this);
        contactCustomerText.setOnClickListener(this);
        contactCustomerImage.setOnClickListener(this);*/
    	
    	View rootView = inflater.inflate(R.layout.fragment_contact, container,
                false);
        
        contactTCText = (TextView) rootView.findViewById(R.id.contact_t_c_tv);
        contactTCImage = (ImageView) rootView.findViewById(R.id.contact_t_c_image);
        contactIncomeRuleText = (TextView) rootView.findViewById(R.id.contact_income_rule_tv);
        contactIncomeRuleImage = (ImageView) rootView.findViewById(R.id.contact_income_rule_image);
        contactWithdrawRuleText = (TextView) rootView.findViewById(R.id.contact_withdraw_rule_tv);
        contactWithdrawRuleImage = (ImageView) rootView.findViewById(R.id.contact_withdraw_rule_image);
        contactCompanyIntroductionText = (TextView) rootView.findViewById(R.id.contact_company_introduction_tv);
        contactCompanyIntroductionImage = (ImageView) rootView.findViewById(R.id.contact_company_introduction_image);
        contactQuestionsAnswersText = (TextView)rootView.findViewById(R.id.contact_questions_answers_tv);
        contactQuestionsAnswersImage = (ImageView)rootView.findViewById(R.id.contact_questions_answers_image);
        contactCustomerText = (TextView) rootView.findViewById(R.id.contact_customer_tv);
        contactCustomerImage = (ImageView) rootView.findViewById(R.id.contact_customer_image);

        contactTCText.setOnClickListener(this);
        contactTCImage.setOnClickListener(this);
        contactIncomeRuleText.setOnClickListener(this);
        contactIncomeRuleImage.setOnClickListener(this);
        contactWithdrawRuleText.setOnClickListener(this);
        contactWithdrawRuleImage.setOnClickListener(this);
        contactCompanyIntroductionText.setOnClickListener(this);
        contactCompanyIntroductionImage.setOnClickListener(this);
        contactQuestionsAnswersText.setOnClickListener(this);
        contactQuestionsAnswersImage.setOnClickListener(this);
        contactCustomerText.setOnClickListener(this);
        contactCustomerImage.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact_t_c_tv:
            case R.id.contact_t_c_image:
                getContentFromWeb(String.format(WebServiceConstants.companyTermsConditions));
                break;
            case R.id.contact_income_rule_tv:
            case R.id.contact_income_rule_image:
                getContentFromWeb(String.format(WebServiceConstants.companyIncomeRule));
                break;
            case R.id.contact_withdraw_rule_tv:
            case R.id.contact_withdraw_rule_image:
                getContentFromWeb(String.format(WebServiceConstants.companyWithdrawRule));
                break;
            case R.id.contact_company_introduction_tv:
            case R.id.contact_company_introduction_image:
                getContentFromWeb(String.format(WebServiceConstants.companyIntroduction));
                break;
            case R.id.contact_customer_tv:
            case R.id.contact_customer_image:
                sendEmail();
                break;
            case R.id.contact_questions_answers_tv:
            case R.id.contact_questions_answers_image:
            	getContentFromWeb(String.format(WebServiceConstants.companyQuestionsAnswers));
                break;
            default:
                break;
        }
    }

    public void getContentFromWeb(String urlPart) {
        Intent urlIntent = new Intent();
        urlIntent.putExtra(Constants.CONTACT_URL,urlPart);
        urlIntent.setClass(getActivity(),ContactURLActivity.class);

        getActivity().startActivity(urlIntent);
        getActivity().finish();
//        WebServiceHelper.getInstance().getContactURL(CustomApplication.getInstance().getAndroidID(),
//                CustomApplication.getInstance().getEmail()
//                ,urlPart);
    }



    private void displayContactURL(String url){

        Intent urlIntent = new Intent();
        urlIntent.putExtra(Constants.CONTACT_URL,url);
        urlIntent.setClass(getActivity(),ContactURLActivity.class);

        getActivity().startActivity(urlIntent);
        getActivity().finish();
    }

    public void sendEmail() {
        Intent data=new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse(getResources().getString(R.string.customer_support_email)));
        data.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_title));
        data.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.email_content));
        getActivity().startActivity(data);
//        getActivity().finish();
    }

}
