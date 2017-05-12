package edu.pitt.cs.cs1635.nam99.notetakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.app.Activity;




public class settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void setColor(View view){
        String actual_color = "";
        Intent resultIntent = new Intent();

        switch(view.getId()){
            case R.id.aquamarineButton:
                actual_color = "#7fffd4";
                break;
            case R.id.pinkButton:
                actual_color = "#ff69b4";
                break;
            case R.id.blackButton:
                actual_color = "#000000";
                break;
            case R.id.blueButton:
                actual_color = "#FF33B5E5";
                break;
            case R.id.purpleButton:
                actual_color = "#FFAA66CC";
                break;
            case R.id.greenButton:
                actual_color = "#FF99CC00";
                break;
            case R.id.orangeButton:
                actual_color = "#FFFFBB33";
                break;
            case R.id.redButton:
                actual_color = "#FFFF4444";
                break;
            case R.id.yellowButton:
                actual_color = "#ffff00";
                break;


        }

        System.out.println(actual_color);
        resultIntent.putExtra("setColor", actual_color);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
