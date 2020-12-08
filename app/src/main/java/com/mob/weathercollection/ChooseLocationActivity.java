package com.mob.weathercollection;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.weathercollection.databinding.ActivityChooseLocationBinding;

public class ChooseLocationActivity extends AppCompatActivity implements ChooseLocationDialog.NoticeDialogListener {

    private ActivityChooseLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.llChooselocationCity.setOnClickListener(view -> {
            ChooseLocationDialog dialog = new ChooseLocationDialog(view, "kma_location");
            dialog.show(getSupportFragmentManager(), "Choose Location Dialog");
        });
    }

    @Override
    public void onDialogClick(View requestView, String selectedItem) {
        switch (requestView.getId()) {
            case R.id.ll_chooselocation_city: {
                binding.tvChooselocationCity.setText(selectedItem);
            }
            case R.id.ll_chooselocation_county: {
                binding.tvChooselocationCounty.setText(selectedItem);
            }
            case R.id.ll_chooselocation_town: {
                binding.tvChooselocationTown.setText(selectedItem);
            }
        }
    }
}