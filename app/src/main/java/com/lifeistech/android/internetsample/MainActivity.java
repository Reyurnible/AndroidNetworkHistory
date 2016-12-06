package com.lifeistech.android.internetsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifeistech.android.internetsample.entities.Weather;
import com.lifeistech.android.internetsample.repository.WeatherRepository;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplHttpClient;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplOkHttp2;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplRetrofit1;
import com.lifeistech.android.internetsample.repository.WeatherRepositoryImplVolley;

public class MainActivity extends AppCompatActivity implements WeatherRepository.RequestCallback {
    private static final String CLIENT_HTTPCLIENT = "HttpClient";
    private static final String CLIENT_VOLLEY = "Volley";
    private static final String CLIENT_OKHTTP = "OkHttp2";
    private static final String CLIENT_RETROFIT = "Retrofit1";

    private TextView textView;
    private Spinner spinner;
    private Button button;
    private long startTimeMills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        new String[]{
                                CLIENT_HTTPCLIENT,
                                CLIENT_VOLLEY,
                                CLIENT_OKHTTP,
                                CLIENT_RETROFIT
                        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WeatherRepository repository = provideRepository();
                assert repository != null;
                startTimeMills = System.currentTimeMillis();
                Log.d("request", "start: (" + startTimeMills + ")");
                repository.getWeather(MainActivity.this);
                button.setEnabled(false);
            }
        });
    }

    @Override
    public void success(Weather weather) {
        long endTimeMillis = System.currentTimeMillis();
        Log.d("request", "success: (" + endTimeMillis + ")");
        Log.d("request", "time: (" + (endTimeMillis - startTimeMills) + ")");
        String message = "success: ";
        message += weather.toString();
        textView.setText(message);
        button.setEnabled(true);
    }

    @Override
    public void error(Throwable throwable) {
        Log.d("request", "error: (" + System.currentTimeMillis() + ")");
        String message = "error: ";
        message += throwable.getMessage();
        textView.setText(message);
        button.setEnabled(true);
    }

    @Nullable
    private WeatherRepository provideRepository() {
        final String client = (String) spinner.getSelectedItem();
        if (CLIENT_HTTPCLIENT.equals(client)) {
            return new WeatherRepositoryImplHttpClient();
        } else if (CLIENT_VOLLEY.equals(client)) {
            return new WeatherRepositoryImplVolley(this);
        } else if (CLIENT_OKHTTP.equals(client)) {
            return new WeatherRepositoryImplOkHttp2();
        } else if (CLIENT_RETROFIT.equals(client)) {
            return new WeatherRepositoryImplRetrofit1();
        }
        return null;
    }

}
