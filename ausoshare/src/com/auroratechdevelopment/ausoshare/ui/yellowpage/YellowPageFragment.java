package com.auroratechdevelopment.ausoshare.ui.yellowpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.home.HomeFragmentBase;

/**
 * Created by happy pan on 2015/11/3.
 */
public class YellowPageFragment  extends HomeFragmentBase implements View.OnClickListener {
    private RelativeLayout ypLifeServiceFirstLayout;
    private RelativeLayout ypLifeServiceSecondLayout;
    private boolean lifeServiceSwitch = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_yellow_page, container,
                false);

        ypLifeServiceFirstLayout = (RelativeLayout)rootView.findViewById(R.id.yp_life_service_first_layout);
        ypLifeServiceSecondLayout = (RelativeLayout)rootView.findViewById(R.id.yp_life_service_second_layout);
        ypLifeServiceFirstLayout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yp_life_service_first_layout:
                if (!lifeServiceSwitch) {
                    ypLifeServiceSecondLayout.setVisibility(View.VISIBLE);
                    lifeServiceSwitch = true;
                }
                else{
                    ypLifeServiceSecondLayout.setVisibility(View.GONE);
                    lifeServiceSwitch = false;
                }
                break;

            default:
                break;
        }
    }


}
