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
import com.mob.weathercollection.model.weather.Data;

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

        mainViewModel.getKmaWeather().observe(this, weather -> addTempItems(weather.channel.item.description.body.data));
    }

    private void addTempItems(List<Data> temps) {
        LinearLayout itemLayout = binding.layoutKma.llItammainTemperatureperhour;
        itemLayout.removeAllViews();
        for (Data temp : temps) {
            @NonNull ItemTempperhoursBinding itemBinding = ItemTempperhoursBinding.inflate(getLayoutInflater(), itemLayout, false);
            Log.d("Add kma item: data", temp.temp + " " + temp.hour);
            itemBinding.tvItemtempperhoursTemp.setText(temp.temp);
            itemBinding.tvItemtempperhoursHour.setText("" + temp.hour);
            Log.d("Add kma item: layout", itemBinding.tvItemtempperhoursTemp.getText().toString() + " " + itemBinding.tvItemtempperhoursHour.getText().toString());
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
            mainViewModel.loadWeather(locationCode);
        }
    }
}