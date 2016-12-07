package com.reyurnible.android.networkhistory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.reyurnible.android.networkhistory.entities.Weather;
import com.reyurnible.android.networkhistory.repository.RepositoryProvider;
import com.reyurnible.android.networkhistory.repository.WeatherRepository;

public class MainActivity extends AppCompatActivity implements WeatherRepository.RequestCallback {

    private TextView textView;
    private Spinner spinner;
    private Button button;
    // リクエスト開始時間
    private long startTimeMills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        final ArrayAdapter<RepositoryProvider.Client> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RepositoryProvider.Client.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Requesting...");
                final RepositoryProvider.Client client = (RepositoryProvider.Client) spinner.getSelectedItem();
                final WeatherRepository repository = RepositoryProvider.provideWeatherRepository(client);
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

}
