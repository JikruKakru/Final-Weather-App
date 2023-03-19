package com.example.final_weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdvancedDetails extends AppCompatActivity {

    private double temperature = 0;
    private double temperatureMin = 0;
    private double temperatureMax = 0;
    private double windSpeed = 0;
    private double humidity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_details);

        Intent intent = getIntent();
        temperature = intent.getDoubleExtra("TEMPERATURE", 0);
        temperatureMin = intent.getDoubleExtra("TEMPMIN", 0);
        temperatureMax = intent.getDoubleExtra("TEMPMAX", 0);
        windSpeed = intent.getDoubleExtra("WINDSPEED", 0);
        humidity = intent.getDoubleExtra("HUMIDITY", 0);
        showAdvancedInfo();
    }

    public void showAdvancedInfo () {
        TextView Temp = findViewById(R.id.displayTemp);
        Temp.setText(temperature + " °C");
        TextView TempMin = findViewById(R.id.displayMinTemp);
        TempMin.setText(temperatureMin + " °C");
        TextView TempMax = findViewById(R.id.displayMaxTemp);
        TempMax.setText(temperatureMax + " °C");
        TextView Wind = findViewById(R.id.displayWind);
        Wind.setText(windSpeed + " m/s");
        TextView Humidity = findViewById(R.id.displayHumidity);
        Humidity.setText(humidity + "%");
    }

    public void openMainActivity (View view) {
        Intent openMainActivity = new Intent(this, MainActivity.class);
        startActivity(openMainActivity);
    }
}

