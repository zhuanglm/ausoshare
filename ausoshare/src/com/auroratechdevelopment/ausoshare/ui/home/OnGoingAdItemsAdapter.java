package com.auroratechdevelopment.ausoshare.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.util.ImageService;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.OnGoingAdItem;
import com.auroratechdevelopment.ausoshare.ui.ext.LineProgress;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edward Liu on 2015/11/1.
 */
public class OnGoingAdItemsAdapter extends BaseAdapter{
    public interface GetItemSelected{
        public void onItemDetails(AdDataItem item);
    }

    private Context context;
    private List<AdDataItem> list;
    private GetItemSelected listener;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public void setList(List<AdDataItem> list){
        if (this.list != null) {
            this.list.addAll(list);
        }
    }
    public List<AdDataItem> getList(){
        return list;
    }

    public void setListener(GetItemSelected listener){
        this.listener = listener;
    }

    public void clearList(){
        this.list.clear();
    }

    public OnGoingAdItemsAdapter(Context context, List<AdDataItem> ads){
        if(ads == null){
            ads = new ArrayList<AdDataItem>();
        }

        this.list = ads;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }
    

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
    	if(list != null){
    	    synchronized (list){
    	    	
    	    	Log.e("Edward", "list size is: " + list.size());
    	    	Log.e("Edward", "position is: " + position);
    	    	
	            final AdDataItem item = list.get(position);
	
	            if(convertView == null){
	                convertView = inflater.inflate(R.layout.fragment_home_valid_ad_list_item_v2, null);
	                holder = new ViewHolder();
	                holder.thumb = (ImageView)convertView.findViewById(R.id.ad_image);
	                holder.adSimpleDesTv = (TextView)convertView.findViewById(R.id.ad_simple_des_tv);
	                //holder.adTotalADFundTv = (TextView)convertView.findViewById(R.id.ad_total_fund_tv);
	                holder.adCompletedProgressTv = (TextView)convertView.findViewById(R.id.ad_completed_progress_tv);
	                holder.click_ad_Progress = (LineProgress)convertView.findViewById(R.id.ad_progress);
	                holder.click_ad_Progress.setRoundEdge(true);
	                holder.click_ad_Progress.setShadow(true);
	                
	                
	
	                convertView.setTag(holder);
	            }else{
	                holder = (ViewHolder) convertView.getTag();
	            }
	
	            if(item != null){
	                //ad thumb
	                if (TextUtils.isEmpty(item.thumb)) {
	                    holder.thumb.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
	                } else {
	                    Picasso.with(context).load(item.thumb)
	                    		.resize(100, 100).centerCrop()
	                    		.config(Bitmap.Config.RGB_565)
	                            .placeholder(R.drawable.placeholder)
	                            .error(R.drawable.placeholder)
	                            .into(holder.thumb);
	                }
	
	                //ad description
	                holder.adSimpleDesTv.setText(item.description);
	                
	                //holder.click_ad_Progress.setProgress(Integer.parseInt(item.completedPercentage) );
	                holder.click_ad_Progress.setProgress(new Random().nextInt(100));
	
	                //ad total Ad Funds
	                //holder.adTotalADFundTv.setText(context.getResources().getString(R.string.ongoing_total_ad_funds) + item.totalAdFunds);
	
	                //ad completed progress
	                //holder.adCompletedProgressTv.setText(context.getResources().getString(R.string.ongoing_ad_completed_percentage)+
	                        //item.completedPercentage + context.getResources().getString(R.string.ongoing_ad_completed_percentage_suffix));
	
	                CustomApplication.getInstance().setSharedAdUrl(item.shareURL);
//	                CustomApplication.getInstance().setSharedAdThumb(item.thumb);
	                CustomApplication.getInstance().setSharedAdReviewURL(item.reviewURL);
	
	                CustomApplication.getInstance().setSharedAdDescription(item.description);
	
	                holder.iTag = position;
	                convertView.setOnClickListener(onItemClicked);
	            }
	        }
        }
        return convertView;
    }

    protected View.OnClickListener onItemClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v == null){
                return;
            }
            final int position = ((ViewHolder) v.getTag()).iTag;
            if(listener != null){
                listener.onItemDetails(list.get(position));
            }
        }
    };

    static class ViewHolder{
        int iTag;
        ImageView thumb;
        TextView adSimpleDesTv;
        //TextView adTotalADFundTv;
        TextView adCompletedProgressTv;
        LineProgress click_ad_Progress;
    }
}
