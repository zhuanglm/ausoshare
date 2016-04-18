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
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward Liu on 2015/11/1.
 */
public class Top10IncomeAdapter extends BaseAdapter{
    public interface GetItemSelected{
        public void onItemDetails(Top10Item item);
    }

    private Context context;
    private List<Top10Item> list;
    private GetItemSelected listener;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public void setList(List<Top10Item> list){
        if (this.list != null) {
            this.list.addAll(list);
        }
    }
    public List<Top10Item> getList(){
        return list;
    }

    public void setListener(GetItemSelected listener){
        this.listener = listener;
    }

    public void clearList(){
        this.list.clear();
    }

    public Top10IncomeAdapter(Context context, List<Top10Item> ads){
        if(ads == null){
            ads = new ArrayList<Top10Item>();
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
            final Top10Item item = list.get(position);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.fragment_current_income_list_item, null);
                holder = new ViewHolder();
                holder.top10Name = (TextView)convertView.findViewById(R.id.top10_name_txt);
                holder.top10Income = (TextView)convertView.findViewById(R.id.top10_income_txt);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            if(item != null){

                //top 10 name
                holder.top10Name.setText(item.name);

                //top 10 income
                holder.top10Income.setText("CAD "+item.totalEarning);


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
        TextView top10Name;
        TextView top10Income;
    }
}
