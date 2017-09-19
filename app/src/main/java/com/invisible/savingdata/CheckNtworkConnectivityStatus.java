package com.invisible.savingdata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckNtworkConnectivityStatus extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ntwork_connectivity_status);

        button = (Button) findViewById(R.id.button);
        button.setText("Load Data");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CheckNtworkConnectivityStatus.onClick==" + checkConnectivity());
            }
        });
    }

    void loadDataFromAPI() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://appsnurture.com/admin/testEnv/api/BusinessCategories";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("CheckNtworkConnectivityStatus.onResponse==" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                button.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    void postDataToServer() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://appsnurture.com/admin/testEnv/api/addBusiness";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response = " + response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("CheckNtworkConnectivityStatus.onErrorResponse==" + error.getLocalizedMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", "1");
                return params;
            }

        };

        queue.add(jsonObjReq);
    }

    boolean checkConnectivity() {
        ConnectivityManager coMan = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        if (coMan != null) {
            NetworkInfo networkInfo =
                    coMan.getActiveNetworkInfo();

            if ((networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    ||
                    (networkInfo.getType() ==
                            ConnectivityManager.TYPE_MOBILE)) {
                if ((networkInfo.getState() == NetworkInfo.State.CONNECTED) ||
                        (networkInfo.getState() == NetworkInfo.State.CONNECTING)) {
                    return true;
                }
            }

        }
        return false;
    }

}
