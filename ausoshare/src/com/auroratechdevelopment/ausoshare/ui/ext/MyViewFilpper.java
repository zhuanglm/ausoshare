package com.auroratechdevelopment.ausoshare.ui.ext;

import android.widget.ViewFlipper;

//Created by Raymond Zhuang May 1 2016

public class MyViewFilpper extends ViewFlipper {
	private OnDisplayChagnedListener mListener;

    public MyViewFilpper(android.content.Context context) {
        super(context);
    }

    public MyViewFilpper(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnDisplayChagnedListener(OnDisplayChagnedListener listener) {
        if (mListener != listener) {
            this.mListener = listener;
        }
    }

    @Override
    public void showNext() {
        super.showNext();

        if(mListener != null){
            mListener.OnDisplayChildChanging(this, super.getDisplayedChild());
        }
    }

    @Override
    public void showPrevious() {
        super.showPrevious();

        if(mListener != null){
            mListener.OnDisplayChildChanging(this, super.getDisplayedChild());
        }
    }

    public interface OnDisplayChagnedListener {
        void OnDisplayChildChanging(ViewFlipper view, int index);
    }

}
