package com.mob.weathercollection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        mainViewModel.setLocation(getLocation());

        binding.setLifecycleOwner(this);
        binding.setVm(mainViewModel);

        mainViewModel.getKmaWeather().observe(this,
                weather -> addTempItems(binding.layoutKma.llItammainTemperatureperhour,
                        weather.getTempList()));
        mainViewModel.getNaverWeather().observe(this,
                weather -> addTempItems(binding.layoutNaver.llItammainTemperatureperhour,
                        weather.getTempList()));
        mainViewModel.getOpenWeather().observe(this,
                weather -> addTempItems(binding.layoutOpenweather.llItammainTemperatureperhour,
                        weather.getTempList()));
    }

    private String getLocation() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String location = sharedPref.getString("location", "부산광역시_2600000000");
        return location;
    }

    private void addTempItems(LinearLayout itemLayout, List<TempPerHour> tempList) {
        itemLayout.removeAllViews();
        for (TempPerHour temp : tempList) {
            @NonNull ItemTempperhoursBinding itemBinding =
                    ItemTempperhoursBinding.inflate(getLayoutInflater(), itemLayout, false);
            itemBinding.tvItemtempperhoursTemp.setText(temp.getTemp());
            itemBinding.tvItemtempperhoursHour.setText(temp.getHour());
            itemLayout.addView(itemBinding.getRoot());
        }
    }

    public void onChooseLocationClick(View view) {
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        startActivityForResult(intent, CHOOSE_LOCATION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(this.getClass().getSimpleName(), "onActivityResult");
        if (resultCode == RESULT_OK && requestCode == CHOOSE_LOCATION_REQUEST) {
            String location = data.getStringExtra("location");
            Log.d(this.getClass().getSimpleName(), "onActivityResult: " + location);
            mainViewModel.setLocation(location);
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("location", location);
            editor.apply();
            mainViewModel.refreshAllWeather();
        }
    }
}