package com.mob.weathercollection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.mob.weathercollection.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setWeather(mainViewModel.getKmaWeather());

        binding.layoutKma.btnItemmainRefresh.setOnClickListener(view -> binding.setWeather(mainViewModel.getKmaWeather()));
//        LinearLayout llItemMain = binding.layoutKma.llItammainTemperatureperhour;
//        View itemTemp = getLayoutInflater().inflate(R.layout.item_tempperhours, llItemMain, false);
//        llItemMain.addView(itemTemp);
    }
}