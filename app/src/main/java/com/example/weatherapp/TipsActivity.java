package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.weatherapp.adapter.TipsAdapter;
import com.example.weatherapp.bean.DayWeatherBean;

public class TipsActivity extends AppCompatActivity {

    private RecyclerView rv_tips;
    private TipsAdapter mTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        rv_tips = findViewById(R.id.rv_tips);

        Intent it = getIntent();
        DayWeatherBean weatherBean = (DayWeatherBean) it.getSerializableExtra("tips");
        if (weatherBean == null){
            return;
        }

        mTipsAdapter = new TipsAdapter(this, weatherBean.getmTipsBean());
        rv_tips.setAdapter(mTipsAdapter);
        rv_tips.setLayoutManager(new LinearLayoutManager(this));

    }
}