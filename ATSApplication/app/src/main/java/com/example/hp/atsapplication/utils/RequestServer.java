package com.example.hp.atsapplication.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    private static final String DEFAULT_URL = "http://127.20.10.3:8080/";
	public RequestResult delegate = null;

    @Override
    protected String doInBackground(Object... params) {

    	Object q = params[0];
    	
    	String controller = (String) params[1];
        String method = (String) params[2];
        String requestMethod = (String) params[3];
    	
        Log.d("Request Server: ", controller + " - " + method);
        return postData(q, controller, method, requestMethod);
    }
        

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Request Server: ", s);
        delegate.processFinish(s);
    }

    private String postData(Object params, String path, String method, String requestMethod) {
        try {
            StringBuilder urlString = new StringBuilder(DEFAULT_URL + path + "/" + method + "/");
            StringBuilder paramQuery = new StringBuilder();

            if (requestMethod == "POST") {
                boolean first = true;
                paramQuery = new StringBuilder();
                Map<String, String> paramsMap = (Map<String, String>) params;
                if (paramsMap != null && !paramsMap.isEmpty()) {
                    for (Entry<String, String> param: paramsMap.entrySet()) {
                        if (first) {
                            first = false;
                        } else {
                            paramQuery.append("&");
                        }

                        paramQuery.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                        paramQuery.append("=");
                        paramQuery.append(URLEncoder.encode(param.getValue(),"UTF-8"));
                    }
                }
            } else if (requestMethod == "GET") {
                List<String> paramsList = (List<String>) params;
                for (String param : paramsList) {
                    urlString.append(param);
                    urlString.append("/");
                }
            }

            //Create URL
            URL url = new URL(urlString.toString());

            //Create Connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Config the Connection
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(false);
            connection.setDoInput(true);

            //Set params for the request
            boolean first = true;
            StringBuilder s = new StringBuilder();
            if (method == "POST") {
                connection.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(paramQuery.toString());
                bw.flush();
                bw.close();
            }


            //Begin connect to send request
            connection.connect();

            //Process Response Data
            int status = connection.getResponseCode();
            InputStream is;
            if (status == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String content = "";
            String line;
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            rd.close();
            is.close();
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
