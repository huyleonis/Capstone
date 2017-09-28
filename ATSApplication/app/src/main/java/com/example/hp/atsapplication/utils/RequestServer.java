package com.example.hp.atsapplication.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.http.HTTP;

public class RequestServer extends AsyncTask<Object, Void, String> {
    private static final String DEFAULT_URL = "http://172.20.10.3:2896/ATS/";
	public RequestResult delegate = null;

    @Override
    protected String doInBackground(Object... params) {

    	Map<String, String> q = (Map<String, String>) params[0];
    	
    	String path = (String) params[1];
    	
        Log.d("Request Server: ", path);
        return postData(q, path);
    }
        

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Request Server: ", s);
        delegate.processFinish(s);
    }

    private String postData(Map<String, String> params, String path) {
        try {
            //Create URL
            URL url = new URL(DEFAULT_URL + path);

            //Create Connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Config the Connection
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setDoOutput(true);
            connection.setReadTimeout(5000);

            //Set params for the request
            boolean first = true;
            StringBuilder s = new StringBuilder();
            if (params != null && !params.isEmpty()) {
                for (Entry<String, String> param: params.entrySet()) {
                    if (first) {
                        first = false;
                    } else {
                        s.append("&");
                    }

                    s.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                    s.append("=");
                    s.append(URLEncoder.encode(param.getValue(),"UTF-8"));
                }
            }

            //Begin connect to send request
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            bw.write(s.toString());
            bw.flush();
            bw.close();
            connection.connect();

            //Process Response Data
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            rd.close();
            return content;

        } catch (Exception e) {
            Log.e("Request Server: ", e.getMessage());
            return "Exception: " + e.getClass() + " - " + e.getMessage();
        }



    }

    public interface RequestResult {    	
        void processFinish(String result);
    }

}
