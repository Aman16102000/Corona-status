package com.example.coronaupdateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    String url = "https://api.covid19api.com/summary";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        URL url1=null;
//
//        String json1 = null;
//        {
//            try {
//                url1 = new URL(url);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            json1 = makere(url1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONObject root = null;
//        try {
//            root = new JSONObject(json1);
//            JSONArray arr = root.getJSONArray("Countries");
//            JSONObject firstcou= arr.getJSONObject(0);
//            String country12=firstcou.getString("Country");
//            String num =firstcou.getString("TotalConfirmed");
//            TextView t1 =(TextView) findViewById(R.id.country);
//            t1.setText(country12);
//            TextView t2 =(TextView) findViewById(R.id.cases);
//            t2.setText(num);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//
//
//
//    private String makere(URL url) throws IOException {
//        String json = "";
//        if (url == null) {
//            return json;
//        }
//        HttpURLConnection http = null;
//        InputStream input = null;
//        try {
//            http = (HttpURLConnection) url.openConnection();
//            http.setRequestMethod("GET");
//            http.setReadTimeout(10000);
//            http.setConnectTimeout(15000);
//            http.connect();
//            if (http.getResponseCode() == 200) {
//                input = http.getInputStream();
//                json = read(input);
//            }
//        } catch (IOException e) {
//
//        }
//        return json;
//    }
//
//    private String read(InputStream input) throws IOException {
//        StringBuilder output = new StringBuilder();
//        if (input != null) {
//            InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
//            BufferedReader buff = new BufferedReader(reader);
//            String line = buff.readLine();
//            while (line != null) {
//                output.append(line);
//                line = buff.readLine();
//            }
//        }
//        return output.toString();
        TsunamiAsyncTask task = new TsunamiAsyncTask();
        task.execute();
    }
    private void updateUi(Event earthquake) {
        // Display the earthquake title in the UI
//        TextView titleTextView = (TextView) findViewById(R.id.title);
//        titleTextView.setText(earthquake.title);

        // Display the earthquake date in the UI
        TextView dateTextView = (TextView) findViewById(R.id.country);
        dateTextView.setText(earthquake.country);

        // Display whether or not there was a tsunami alert in the UI
        TextView tsunamiTextView = (TextView) findViewById(R.id.cases);
        tsunamiTextView.setText(earthquake.cases);
    }
//    private String getDateString(long timeInMilliseconds) {
//        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
//        return formatter.format(timeInMilliseconds);
//    }
//    private String getTsunamiAlertString(int tsunamiAlert) {
//        switch (tsunamiAlert) {
//            case 0:
//                return getString(R.string.alert_no);
//            case 1:
//                return getString(R.string.alert_yes);
//            default:
//                return getString(R.string.alert_not_available);
//        }
//    }

    private class TsunamiAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            // Create URL object
            URL url1 = createUrl(url);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url1);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Event earthquake = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return earthquake;
        }

        @Override
        protected void onPostExecute(Event earthquake) {
            if (earthquake == null) {
                return;
            }

            updateUi(earthquake);
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private Event extractFeatureFromJson(String earthquakeJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
                JSONArray featureArray = baseJsonResponse.getJSONArray("Countries");

                // If there are results in the features array
//                if (featureArray.length() > 0) {
//                    // Extract out the first feature (which is an earthquake)
//                    JSONObject firstFeature = featureArray.getJSONObject(0);
//                    JSONObject properties = firstFeature.getJSONObject("properties");
//
//                    // Extract out the title, time, and tsunami values
//                    String title = properties.getString("title");
//                    long time = properties.getLong("time");
//                    int tsunamiAlert = properties.getInt("tsunami");
                if(featureArray.length()>0){
                JSONObject firstcou= featureArray.getJSONObject(0);
            String country12=firstcou.getString("Country");
            String num =firstcou.getString("TotalConfirmed");

                    // Create a new {@link Event} object
                    return new Event(country12,num);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }

    }