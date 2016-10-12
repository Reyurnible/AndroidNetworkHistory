package com.lifeistech.android.internetsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifeistech.android.internetsample.entities.Weather;
import com.lifeistech.android.internetsample.repository.WeatherRepository;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplHttpClient;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplOkHttp;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplRetrofit;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplVolley;

public class MainActivity extends AppCompatActivity implements WeatherRepository.RequestCallback {
    private static final String[] CLIENTS = {
            "HttpClient",
            "Volley",
            "OkHttp",
            "Retrofit"
    };

    private TextView textView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CLIENTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("request", "start: (" + System.currentTimeMillis() + ")");
                provideRepository().getWeather(MainActivity.this);
            }
        });
    }

    private WeatherRepository provideRepository() {
        final String client = (String) spinner.getSelectedItem();
        if (CLIENTS[0].equals(client)) {
            return new WeatherRepositoryImplHttpClient();
        } else if (CLIENTS[1].equals(client)) {
            return new WeatherRepositoryImplVolley(this);
        } else if (CLIENTS[2].equals(client)) {
            return new WeatherRepositoryImplOkHttp();
        } else {
            return new WeatherRepositoryImplRetrofit();
        }
    }

    @Override
    public void success(Weather weather) {
        Log.d("request", "end (" + System.currentTimeMillis() + ")");
        String message = "success: ";
        message += weather.toString();
        textView.setText(message);
    }

    @Override
    public void error(Throwable throwable) {
        Log.d("request", "end (" + System.currentTimeMillis() + ")");
        String message = "error: ";
        message += throwable.getMessage();
        textView.setText(message);
    }
}
