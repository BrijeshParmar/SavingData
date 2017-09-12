package com.invisible.savingdata;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WorkWithSharedPrefs extends AppCompatActivity {

    Button btnSave, btnRetrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_retrive_spdata);

        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        sp.edit().clear().apply();

        btnSave = (Button) findViewById(R.id.btnSave);
        btnRetrive = (Button) findViewById(R.id.btnRetrive);

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchJson();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWithJSON();
            }
        });

    }

    void saveWithJSON() {
        String name = "test", email = "a@b.com", phone = "123";
        JSONArray ja = new JSONArray();

        JSONObject job = new JSONObject();
        try {
            job.put("name", name);
            job.put("phone", phone);
            job.put("email", email);

            ja.put(job);

            SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
            sp.edit().putString(phone, ja.toString()).apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void fetchJson() {
        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        HashMap<String, String> map = (HashMap<String, String>) sp.getAll();
        System.out.println("WorkWithSharedPrefs.fetchMultipleVal==" + map);

        for (String key :
                map.keySet()) {
            System.out.println(key + "==" + map.get(key));

            try {

                JSONArray jsonArray = new JSONArray(map.get(key).toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = (JSONObject) jsonArray.get(i);
                    System.out.println("name== " + (job.has("name") ? job.get("name") : ""));
                    System.out.println("email= " + (job.has("email") ? job.get("email") : ""));
                    System.out.println("phone= " + (job.has("phone") ? job.get("phone") : ""));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void saveSingleVal() {
        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        sp.edit().putString("FN", "Brijesh").apply();
    }

    void saveMultipleVal() {

        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        sp.edit().putString("123", "Brijesh==123456==a@g.com==M").apply();

        SharedPreferences sp1 = getSharedPreferences("SP1", MODE_PRIVATE);
        sp1.edit().putString("1234", "John==123456==a@g.com==M").apply();
    }


    void getchVal() {
        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        String fn = sp.getString("FN", "");
        System.out.println("WorkWithSharedPrefs.getchVal=" + fn);
    }

    void fetchMultipleVal() {
        SharedPreferences sp = getSharedPreferences("SP1", MODE_PRIVATE);
        HashMap<String, String> map = (HashMap<String, String>) sp.getAll();
        System.out.println("WorkWithSharedPrefs.fetchMultipleVal==" + map);

        for (String key :
                map.keySet()) {
            System.out.println(key + "==" + map.get(key));
        }
    }

}
