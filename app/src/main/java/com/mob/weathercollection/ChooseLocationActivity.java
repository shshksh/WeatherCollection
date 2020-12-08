package com.mob.weathercollection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.weathercollection.databinding.ActivityChooseLocationBinding;

public class ChooseLocationActivity extends AppCompatActivity implements ChooseLocationDialog.NoticeDialogListener {

    private ActivityChooseLocationBinding binding;
    private String[] locationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationData = new String[3];

        binding.llChooselocationCity.setOnClickListener(view -> {
            showChooseDialog(view, "kma_location");
        });
        binding.llChooselocationCounty.setOnClickListener(view -> {
            if (!locationData[0].isEmpty()) {
                showChooseDialog(view, locationData[0]);
            }
        });
        binding.llChooselocationTown.setOnClickListener(view -> {
            if (!locationData[1].isEmpty()) {
                showChooseDialog(view, locationData[1]);
            }
        });
        binding.btnChooselocationClose.setOnClickListener(view -> onBackPressed());
        binding.btnChooselocationComplete.setOnClickListener(view -> {
            if (!locationData[2].isEmpty()) {
                Intent result = new Intent();
                result.putExtra("locationCode", locationData[2].split("_")[1]);
                Log.d(this.getClass().getSimpleName(), "onCompleteClick: " + locationData[2]);
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(this, "지역을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showChooseDialog(View view, String key) {
        ChooseLocationDialog dialog = new ChooseLocationDialog(view, key);
        dialog.show(getSupportFragmentManager(), "Choose Location Dialog: " + key);
    }

    @Override
    public void onDialogClick(View requestView, String selectedItem) {
        Object tag = requestView.getTag();
        if ("city".equals(tag)) {
            locationData[0] = selectedItem;
            binding.tvChooselocationCity.setText(selectedItem.split("_")[0]);
        } else if ("county".equals(tag)) {
            locationData[1] = selectedItem;
            binding.tvChooselocationCounty.setText(selectedItem.split("_")[0]);
        } else if ("town".equals(tag)) {
            locationData[2] = selectedItem;
            binding.tvChooselocationTown.setText(selectedItem.split("_")[0]);
        }
    }
}