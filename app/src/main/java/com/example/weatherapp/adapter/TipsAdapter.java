package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.bean.OtherTipsBean;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    private Context mContext;
    private List<OtherTipsBean> mTipsBeans;

    public TipsAdapter(Context context, List<OtherTipsBean> tipsBeans) {
        this.mContext = context;
        this.mTipsBeans = tipsBeans;
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tips_item_layout, parent, false);


        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {

        OtherTipsBean otherTipsBean = mTipsBeans.get(position);
        holder.tv_title.setText(otherTipsBean.getTitle());
        holder.tv_desc.setText(otherTipsBean.getDesc());
        holder.tv_level.setText(otherTipsBean.getLevel());

    }

    @Override
    public int getItemCount() {
        return (mTipsBeans == null) ? 0 : mTipsBeans.size();
    }

    class TipsViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_desc,tv_level;

        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_level = itemView.findViewById(R.id.tv_level);
        }
    }
}
