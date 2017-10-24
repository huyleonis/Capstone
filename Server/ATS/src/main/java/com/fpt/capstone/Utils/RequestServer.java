/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * @author hp
 */
public class RequestServer {

    private final static String POST_METHOD = "POST";
    private final static String GET_METHOD = "GET";

    private static HttpURLConnection getConnection(String sUrl, String method) throws MalformedURLException, IOException {
        URL url = new URL(sUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");

        return connection;
    }

    private static String modifyParams(Map<String, String> params) {
        StringBuilder s = new StringBuilder();

        boolean first = true;
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                s.append('&');
            }
            s.append(param.getKey());
            s.append('=');
            s.append(param.getValue());
        }

        return s.toString();
    }

    private static String processResponse(InputStream is) throws IOException {
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        return response.toString();
    }

    public static String sendPostRequest(String url, Map<String, String> params) throws IOException {
        String urlParameters = modifyParams(params);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        HttpURLConnection con = getConnection(url, POST_METHOD);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        con.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
            wr.flush();
        }

        String response = processResponse(con.getInputStream());
        return response;
    }

    public static String sendGetRequest(String url, Map<String, String> params) throws IOException {
        String urlParameters = modifyParams(params);
        HttpURLConnection con = getConnection(url + "?" + urlParameters, GET_METHOD);

        String response = processResponse(con.getInputStream());
        return response;
    }
}
