/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class RequestServer implements Runnable {

    private static final String baseUrl = "http://172.20.10.2:8080/";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private Map<String, String> params;
    private String controller;
    private String method;
    private String requestMethod;
    private RequestResult delegate;

    public RequestServer(Map<String, String> params, String controller, String method,
            String requestMethod, RequestResult delegate) {
        this.params = params;
        this.controller = controller;
        this.method = method;
        this.requestMethod = requestMethod;
        this.delegate = delegate;
    }

    private String sendGetRequest() throws MalformedURLException, IOException {
        StringBuilder sUrl = new StringBuilder(baseUrl);

        sUrl.append(controller).append("/");
        sUrl.append(method).append("/");

        int i = 1;
        while (params.get("" + i) != null) {
            sUrl.append(params.get("" + i)).append("/");
            i++;
        }

        URL url = new URL(sUrl.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);

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
    }

    private String sendPostRequest() throws MalformedURLException, IOException {
        StringBuilder sUrl = new StringBuilder(baseUrl);

        sUrl.append(controller).append("/");
        sUrl.append(method).append("/");

        //Build url string
        URL url = new URL(sUrl.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);

        //add params to body of request
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        
        StringBuilder sParam = new StringBuilder("");
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) {
                sParam.append("&");
            } else {
                first = false;
            }
            sParam.append(entry.getKey());
            sParam.append("=");
            sParam.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String encodedData = sParam.toString();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))) {
            bw.write(encodedData);            
            bw.flush();
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
    }

    @Override
    public void run() {
        try {
            String result = null;
            if (requestMethod.equals(METHOD_GET)) {
                result = this.sendGetRequest();
            } else if (requestMethod.equals(METHOD_POST)) {
                result = this.sendPostRequest();
            }

            if (delegate != null) {
                delegate.processResult(result);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public interface RequestResult {

        public void processResult(String result);
    }
}
