package com.invisible.savingdata;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SavedIntstanceStateTestActivity extends AppCompatActivity {

    String KEY1 = "key1", KEY2 = "key2", VALUE1 = "", VALUE2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            VALUE1 = savedInstanceState.getString(KEY1, "");
            VALUE2 = savedInstanceState.getString(KEY2, "");
            System.out.println("VALUE1 = " + VALUE1);
            System.out.println("VALUE2 = " + VALUE2);
        }

        //Save File
        String filename = "FirstFile";
        String string = "SOME DATA";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save File
        File file = new File(getFilesDir(), filename);
        try {
            String data = "String Data";
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Read File
        try {
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println("Saved Data==" + sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String strData = "Hello friends, i am External File Data.";
        File mydir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                File.separator + getString(R.string.app_name));
        mydir = new File(Environment.getExternalStorageDirectory() +
                File.separator + getString(R.string.app_name));
        // Save to External Storage
        try {
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            File fileWithinMyDir = new File(mydir, "HTML Data.txt"); //Getting a file within the dir.
            FileOutputStream out = new FileOutputStream(fileWithinMyDir); //Use the stream as usual to write into the file
            out.write(strData.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read External
        BufferedReader reader = null;
        try {
            File fileWithinMyDir = new File(mydir, "HTML Data.txt"); //Getting a file within the dir.
            reader = new BufferedReader(new FileReader(fileWithinMyDir));
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line);
                textBuilder.append("\n");

            }
            System.out.println("SavedIntstanceStateTestActivity.onCreate==" + textBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(KEY1, "value1");
        outState.putString(KEY2, "value2");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        VALUE1 = savedInstanceState.getString(KEY1, "");
        VALUE2 = savedInstanceState.getString(KEY2, "");
        System.out.println("VALUE1 == " + VALUE1);
        System.out.println("VALUE2 == " + VALUE2);
    }
}