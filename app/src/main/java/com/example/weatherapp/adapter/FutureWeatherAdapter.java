package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.bean.DayWeatherBean;

import java.util.List;

public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {

    private Context mContext;
    private List<DayWeatherBean> mWeatherBeans;

    public FutureWeatherAdapter(Context context, List<DayWeatherBean> weatherBeans) {
        mContext = context;
        this.mWeatherBeans = weatherBeans;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item_layout, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        DayWeatherBean weatherBean = mWeatherBeans.get(position);

        holder.tv_weather.setText(weatherBean.getWea());
        holder.tv_tem.setText(weatherBean.getTem());
        holder.tv_date.setText(weatherBean.getDate());
        holder.tv_tem_low_high.setText(weatherBean.getTem2() + "~" + weatherBean.getTem1());
        holder.tv_win.setText(weatherBean.getWin()[0] + weatherBean.getWinSpeed());
        holder.tv_air.setText("空氣:" + weatherBean.getAir() + weatherBean.getAirLevel());
        holder.iv_weather.setImageResource(getImgResOfWeather(weatherBean.getWeaImg()));

    }

    @Override
    public int getItemCount() {
        return (mWeatherBeans == null) ? 0 : mWeatherBeans.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        TextView tv_tem,tv_weather,tv_tem_low_high,tv_win,tv_air,tv_date;
        ImageView iv_weather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tem = itemView.findViewById(R.id.tv_tem);
            tv_weather = itemView.findViewById(R.id.tv_weather);
            tv_tem_low_high = itemView.findViewById(R.id.tv_tem_low_high);
            tv_win = itemView.findViewById(R.id.tv_win);
            tv_air = itemView.findViewById(R.id.tv_air);
            iv_weather = itemView.findViewById(R.id.iv_weather);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

    private int getImgResOfWeather(String weaStr){
        //xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
        int result = 0;
        switch (weaStr){
            case "qing":
                result = R.drawable.sun;
                break;
            case "yin":
                result = R.drawable.partly_cloudy;
                break;
            case "yu":
                result = R.drawable.rain;
                break;
            case "yun":
                result = R.drawable.clouds;
                break;
            case "bingbao":
                result = R.drawable.hail64;
                break;
            case "wu":
                result = R.drawable.fog;
                break;
            case "shachen":
                result = R.drawable.dust;
                break;
            case "lei":
                result = R.drawable.storm;
                break;
            case "xue":
                result = R.drawable.snow;
                break;
            default:
                result = R.drawable.sun;
                break;

        }

        return result;
    }
}
