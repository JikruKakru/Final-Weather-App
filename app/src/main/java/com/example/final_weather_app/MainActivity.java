package com.example.final_weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private String weather, cityName, imageCode, imageUrl;
    private double temperature = 0;
    private double tempMin = 0;
    private double tempMax = 0;
    private double humidity = 0;
    private double windSpeed = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        fetchWeatherData(null);
        displayDate();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CITYNAME", cityName);
        outState.putString("WEATHER", weather);
        outState.putDouble("TEMPERATURE", temperature);
        outState.putDouble("TEMPMIN", tempMin);
        outState.putDouble("TEMPMAX", tempMax);
        outState.putDouble("HUMIDITY", humidity);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cityName = savedInstanceState.getString("CITY");
        weather = savedInstanceState.getString("WEATHER");
        temperature = savedInstanceState.getDouble("TEMPERATURE", 0);
        tempMin = savedInstanceState.getDouble("TEMPMIN", 0);
        tempMax = savedInstanceState.getDouble("TEMPMAX", 0);
        humidity = savedInstanceState.getDouble("HUMIDITY", 0);
        windSpeed = savedInstanceState.getDouble("WINDSPEED", 0);

        TextView City = findViewById(R.id.City);
        City.setText(cityName);
        TextView Weather = findViewById(R.id.Weather);
        Weather.setText(weather);
        TextView Temperature = findViewById(R.id.Temperature);
        Temperature.setText("" + temperature + " °C");
    }


    public void fetchWeatherData(View view) {
                                                            //Change city  V
        String url = "https://api.openweathermap.org/data/2.5/weather?q=tampere&units=metric&appid=6c433438776b5be4ac86001dc88de74d";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("WEATHER-APP", response);
                    parseJson(response);
                }, error -> {
            Log.d("WEATHER-APP", error.toString());
        });
        queue.add(stringRequest);
    }

    private void parseJson(String response) {
        try{
            JSONObject Response = new JSONObject(response);

            cityName = Response.getString("name");
            weather = Response.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = Response.getJSONObject("main").getDouble("temp");
            tempMin = Response.getJSONObject("main").getDouble("temp_min");
            tempMax = Response.getJSONObject("main").getDouble("temp_max");
            humidity = Response.getJSONObject("main").getDouble("humidity");
            windSpeed = Response.getJSONObject("wind").getDouble("speed");

            imageCode = Response.getJSONArray("weather").getJSONObject(0).getString("icon");
            imageUrl = "https://openweathermap.org/img/wn/" + imageCode +"@2x.png";

            TextView City = findViewById(R.id.City);
            City.setText(cityName);
            TextView Weather = findViewById(R.id.Weather);
            Weather.setText(weather);
            TextView Temperature = findViewById(R.id.Temperature);
            Temperature.setText("" + temperature + " °C");

            imageView = findViewById(R.id.weatherIcon);
            Glide.with(MainActivity.this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayDate() {
        TextView textView = findViewById(R.id.Date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateAndTime = sdf.format(new Date());
        textView.setText(currentDateAndTime);
    }

    public void openAdvancedDetails (View view) {
        Intent openAdvanced = new Intent(this, AdvancedDetails.class);
        openAdvanced.putExtra("TEMPERATURE", temperature);
        openAdvanced.putExtra("TEMPMIN", tempMin);
        openAdvanced.putExtra("TEMPMAX", tempMax);
        openAdvanced.putExtra("HUMIDITY", humidity);
        openAdvanced.putExtra("WINDSPEED", windSpeed);
        startActivity(openAdvanced);
    }
}