package com.auroratechdevelopment.ausoshare.ui.profile;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.webservice.models.ForwardedADItem;
import com.auroratechdevelopment.common.webservice.models.ForwardedADItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward Liu on 2015/11/1.
 */
public class ForwardedAdHistoryAdapter extends BaseAdapter{
    public interface GetItemSelected{
        public void onItemDetails(ForwardedADItem item);
    }

    private Context context;
    private List<ForwardedADItem> list;
    private GetItemSelected listener;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public void setList(List<ForwardedADItem> list){
        if (this.list != null) {
            this.list.addAll(list);
        }
    }
    public List<ForwardedADItem> getList(){
        return list;
    }

    public void setListener(GetItemSelected listener){
        this.listener = listener;
    }

    public void clearList(){
        this.list.clear();
    }

    public ForwardedAdHistoryAdapter(Context context, List<ForwardedADItem> ads){
        if(ads == null){
            ads = new ArrayList<ForwardedADItem>();
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
        synchronized (list){
            final ForwardedADItem item = list.get(position);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.fragment_home_valid_ad_list_item, null);
                holder = new ViewHolder();
                holder.thumb = (ImageView)convertView.findViewById(R.id.ad_image);
                holder.adSimpleDesTv = (TextView)convertView.findViewById(R.id.ad_simple_des_tv);
                holder.adTotalADFundTv = (TextView)convertView.findViewById(R.id.ad_total_fund_tv);
                holder.adCompletedProgressTv = (TextView)convertView.findViewById(R.id.ad_completed_progress_tv);

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
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(holder.thumb);
                }

                //ad description
                holder.adSimpleDesTv.setText(item.description);

                //clicked times
                holder.adTotalADFundTv.setText(context.getResources().getString(R.string.profile_reading_times) + item.click);

                //earned funds
                holder.adCompletedProgressTv.setText(context.getResources().getString(R.string.profile_earned_funds)+ item.amount);



                convertView.setOnClickListener(onItemClicked);
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
