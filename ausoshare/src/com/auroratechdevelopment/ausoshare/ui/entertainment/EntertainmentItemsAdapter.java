package com.auroratechdevelopment.ausoshare.ui.entertainment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward Liu on 2015/11/1.
 * Updated by Raymond Zhuang May 5 2016
 */
public class EntertainmentItemsAdapter extends BaseAdapter{
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

    public EntertainmentItemsAdapter(Context context, List<AdDataItem> ads){
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
	            final AdDataItem item = list.get(position);
	
	            if(convertView == null){
	                convertView = inflater.inflate(R.layout.fragment_home_valid_ad_list_item, null);
	                holder = new ViewHolder();
	                holder.thumb = (ImageView)convertView.findViewById(R.id.ad_image);
	                holder.adSimpleDesTv = (TextView)convertView.findViewById(R.id.ad_simple_des_tv);
	                holder.adTotalADFundTv = (TextView)convertView.findViewById(R.id.ad_total_fund_tv);
	                holder.adCompletedProgressTv = (TextView)convertView.findViewById(R.id.ad_completed_progress_tv);
	                
	                holder.adTotalADFundTv.setVisibility(View.GONE);
	                holder.adCompletedProgressTv.setVisibility(View.GONE);
	
	                convertView.setTag(holder);
	            }else{
	                holder = (ViewHolder) convertView.getTag();
	            }
	
	            if(item != null){
	                //ad thumb
	                if (TextUtils.isEmpty(item.thumb)) {
	                    holder.thumb.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
	                } else {
	                    /*Picasso.with(context).load(item.thumb)
			                    .resize(100, 100).centerCrop()
		                		.config(Bitmap.Config.RGB_565)
	                            .placeholder(R.drawable.placeholder)
	                            .error(R.drawable.placeholder)
	                            .into(holder.thumb);*/
	                	Picasso.with(context).load(item.thumb)
	                    .fit().centerCrop()
                		.config(Bitmap.Config.RGB_565)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(holder.thumb);
	                }
	
	                //ad description
	                holder.adSimpleDesTv.setText(item.description);
	
	                CustomApplication.getInstance().setSharedAdUrl(item.shareURL);
	                CustomApplication.getInstance().setSharedAdThumb(item.thumb);
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
        TextView adTotalADFundTv;
        TextView adCompletedProgressTv;
    }
}
