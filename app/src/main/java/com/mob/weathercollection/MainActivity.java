package com.mob.weathercollection;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout llMain = findViewById(R.id.ll_main);
        View itemMain = getLayoutInflater().inflate(R.layout.item_main, llMain, false);
        llMain.addView(itemMain);

        LinearLayout llItemMain = itemMain.findViewById(R.id.ll_itammain_temperatureperhour);
        View itemTemp = getLayoutInflater().inflate(R.layout.item_tempperhours, llItemMain, false);
        llItemMain.addView(itemTemp);
    }
}