package com.example.coronaupdateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    String url = "https://api.covid19api.com/summary";

    private String makere(URL url) throws IOException {
        String json = "";
        if (url == null) {
            return json;
        }
        HttpURLConnection http = null;
        InputStream input = null;
        try {
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.connect();
            if (http.getResponseCode() == 200) {
                input = http.getInputStream();
                json = read(input);
            }
        } catch (IOException e) {

        }
        return json;
    }

    private String read(InputStream input) throws IOException {
        StringBuilder output = new StringBuilder();
        if (input != null) {
            InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader buff = new BufferedReader(reader);
            String line = buff.readLine();
            while (line != null) {
                output.append(line);
                line = buff.readLine();
            }
        }
        return output.toString();
    }
}