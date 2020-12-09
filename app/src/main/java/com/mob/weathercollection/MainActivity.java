package com.mob.weathercollection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.mob.weathercollection.databinding.ActivityMainBinding;
import com.mob.weathercollection.databinding.ItemTempperhoursBinding;
import com.mob.weathercollection.model.weather.TempPerHour;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int CHOOSE_LOCATION_REQUEST = 5001;
    MainViewModel mainViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setVm(mainViewModel);

        binding.layoutKma.btnItemmainChooselocation.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChooseLocationActivity.class);
            startActivityForResult(intent, CHOOSE_LOCATION_REQUEST);
        });

        mainViewModel.getKmaWeather().observe(this, weather -> addTempItems(binding.layoutKma.llItammainTemperatureperhour, weather.getTempList()));
        mainViewModel.getNaverWeather().observe(this, weather -> addTempItems(binding.layoutNaver.llItammainTemperatureperhour, weather.getTempList()));
    }

    private void addTempItems(LinearLayout itemLayout, List<TempPerHour> tempList) {
        itemLayout.removeAllViews();
        for (TempPerHour temp : tempList) {
            @NonNull ItemTempperhoursBinding itemBinding = ItemTempperhoursBinding.inflate(getLayoutInflater(), itemLayout, false);
            itemBinding.tvItemtempperhoursTemp.setText(temp.getTemp());
            itemBinding.tvItemtempperhoursHour.setText(temp.getHour());
            itemLayout.addView(itemBinding.getRoot());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(this.getClass().getSimpleName(), "onActivityResult");
        if (resultCode == RESULT_OK && requestCode == CHOOSE_LOCATION_REQUEST) {
            String locationCode = data.getStringExtra("locationCode");
            Log.d(this.getClass().getSimpleName(), "onActivityResult: " + locationCode);
            mainViewModel.loadWeatherFromKma(locationCode);
        }
    }
}