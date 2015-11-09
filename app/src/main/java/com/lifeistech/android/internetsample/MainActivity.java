package com.lifeistech.android.internetsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class MainActivity extends AppCompatActivity {
    // 天気予報API
    private String REQUEST_URL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    private void accessOnHttpClient() {
        new AsyncTask<String, String, String>() {
            // 処理の前に呼ばれるメソッド
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            // 処理を行うメソッド
            @Override
            protected String doInBackground(String... params) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(REQUEST_URL);
                HttpResponse httpResponse = null;
                try {
                    httpResponse = httpClient.execute(httpGet);
                    return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            // 処理がすべて終わったら呼ばれるメソッド
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // 通信失敗として処理
                if (TextUtils.isEmpty(s)) return;
                Log.d("result", s);
                // 通信結果を表示
                textView.setText(s);
            }
        }.execute();
    }

    private void accessOnVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("result", response.toString());
                textView.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void accessOnOkHttp() {
        // リクエストオブジェクトを作って
        Request request = new Request.Builder()
                // URLを生成
                .url(REQUEST_URL)
                .get()
                .build();
        // クライアントオブジェクトを作成する
        OkHttpClient client = new OkHttpClient();
        // 新しいリクエストを行う
        client.newCall(request).enqueue(new Callback() {
            // 通信が失敗した時
            @Override
            public void onFailure(Request request, IOException e) {

            }

            // 通信が成功した時
            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                // 通信結果をログに出力する
                Log.d("result", response.toString());
                textView.setText(response.toString());
            }
        });
    }

    private void accessOnRetrofit() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://weather.livedoor.com")
                .build();
        WeatherService service = restAdapter.create(WeatherService.class);
        service.getWeather(130010, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                Log.d("result", response.toString());
                textView.setText(response.toString());
            }
        });
    }

    private interface WeatherService {
        @GET("/forecast/webservice/json/v1")
        public void getWeather(@Query("city") int city, Callback callback);
    }

}
