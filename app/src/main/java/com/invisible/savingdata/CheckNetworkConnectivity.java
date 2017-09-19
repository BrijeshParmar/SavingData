package com.invisible.savingdata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckNetworkConnectivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_network_connectivity);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConn()) {
                    new LoadData().execute();
                } else {
                    System.out.println("Not Connected");
                    Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean checkInternetConn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                if (cm.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    void setData(String... arr) {
        System.out.println("arr = " + arr[0]);
    }

    class NameClass extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {

                String link =
                        "http://appsnurture.com/admin/api/BusinessCategories";
                URL url = new URL(link);

                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    String webPage = "", data = "";

                    while ((data = reader.readLine()) != null) {
                        webPage += data + "\n";
                    }
                    result = webPage;
                    System.out.println("CheckNetworkConnectivity.onCreate=="
                            + webPage);
                } else {
                    result = "Response Code = " + conn.getResponseCode();
                }


            } catch (Exception e) {
                e.printStackTrace();
                result = "" + e.getMessage();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("s = " + s);

            try {
                JSONArray ja = new JSONArray(s);
                if (ja != null) {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject job = ja.getJSONObject(i);

                        String id = job.get("id").toString();
                        String name = job.get("name").toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
