package fpt.capstone.ats.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RequestServer extends AsyncTask<Object, Void, String> {
    public static String DEFAULT_URL = "";
	public RequestResult delegate = null;

    @Override
    protected String doInBackground(Object... params) {

    	Object q = params[0];
    	
    	String controller = (String) params[1];
        String method = (String) params[2];
        String requestMethod = (String) params[3];
    	
        Log.w("Request Server: ", controller + " - " + method);
        return postData(q, controller, method, requestMethod);
    }
        

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("Request Server: ", s);
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

                        paramQuery.append(param.getKey());
                        paramQuery.append("=");
                        paramQuery.append(param.getValue());
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
            if (requestMethod.equals("POST")) {
                byte[] postData       = paramQuery.toString().getBytes(StandardCharsets.UTF_8);
                int    postDataLength = postData.length;

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Charset", "utf-8");
                connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                connection.setUseCaches( false );
                try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    wr.write(postData);
                }
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
            Log.e("Request Server Error: ", e.getMessage());
            return "Exception: " + e.getClass() + " - " + e.getMessage();
        }


    }

    public interface RequestResult {    	
        void processFinish(String result);
    }

}
