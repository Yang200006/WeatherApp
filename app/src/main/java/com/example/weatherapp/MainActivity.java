package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.adapter.FutureWeatherAdapter;
import com.example.weatherapp.bean.DayWeatherBean;
import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.util.NetUtil;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner sp_city;
    private ArrayAdapter<String> mSpAdapter;
    private String[] mCities;
    private TextView tv_tem;
    private TextView tv_weather;
    private TextView tv_tem_low_high;
    private TextView tv_win;
    private TextView tv_air;
    private ImageView iv_weather;
    private RecyclerView rv_future_weather;
    private FutureWeatherAdapter mWeatherAdapter;
    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                String weather = (String) msg.obj;
                Log.d("fan","--主線程收到了天氣數據-weather--" + weather);

                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                Log.d("fan","--解析後的數據-weather--" + weatherBean.toString());

                updateUiOfWeather(weatherBean);

                Toast.makeText(MainActivity.this, "更新天氣~", Toast.LENGTH_SHORT).show();

            }
        }
    };
    private DayWeatherBean todayWeather;

    private void updateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null){
            return;
        }

        List<DayWeatherBean> dayWeathers = weatherBean.getDayWeathers();
        todayWeather = dayWeathers.get(0);
        if (todayWeather == null){
            return;
        }

        tv_tem.setText(todayWeather.getTem());
        tv_weather.setText(todayWeather.getWea() + "(" + todayWeather.getDate() + todayWeather.getWeek() + ")");
        tv_tem_low_high.setText(todayWeather.getTem2() + "~" + todayWeather.getTem1());
        tv_win.setText(todayWeather.getWin()[0] + todayWeather.getWinSpeed());
        tv_air.setText("空氣:" + todayWeather.getAir() + todayWeather.getAirLevel() +"\n" + todayWeather.getAirTips());
        iv_weather.setImageResource(getImgResOfWeather(todayWeather.getWeaImg()));

        //去掉當天的天氣
        dayWeathers.remove(0);
        //未來天氣的展示
        mWeatherAdapter = new FutureWeatherAdapter(this,dayWeathers);
        rv_future_weather.setAdapter(mWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv_future_weather.setLayoutManager(layoutManager);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        initView();

    }



    private void initView() {
        sp_city = findViewById(R.id.sp_city);
        mCities = getResources().getStringArray(R.array.cities);
        mSpAdapter = new ArrayAdapter<>(this, R.layout.sp_item_layout,mCities);
        sp_city.setAdapter(mSpAdapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedCity = mCities[position];

                getWeatherOfCity(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv_tem = findViewById(R.id.tv_tem);
        tv_weather = findViewById(R.id.tv_weather);
        tv_tem_low_high = findViewById(R.id.tv_tem_low_high);
        tv_win = findViewById(R.id.tv_win);
        tv_air = findViewById(R.id.tv_air);
        iv_weather = findViewById(R.id.iv_weather);
        rv_future_weather = findViewById(R.id.rv_future_weather);

        tv_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MainActivity.this, TipsActivity.class);
                //將數據傳遞給TipsActivity
                it.putExtra("tips",todayWeather);
                startActivity(it);

            }
        });


    }

    private void getWeatherOfCity(String selectedCity) {
        //開起子線程，請求網路
        new Thread(new Runnable() {
            @Override
            public void run() {
                //請求網路
                String weatherOfCity = NetUtil.getWeatherOfCity(selectedCity);
                //使用handler將數據傳遞給主線程
                Message message = Message.obtain();
                message.what = 0;
                message.obj = weatherOfCity;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}