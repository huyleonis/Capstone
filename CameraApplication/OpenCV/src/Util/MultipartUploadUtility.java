package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 *
 * @author www.codejava.net
 *
 */
public class MultipartUploadUtility implements Runnable {

    private final static String BASE_URL = "http://localhost:8080/";
    
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;

    /**
     * This constructor initializes a new HTTP POST request with content type is
     * set to multipart/form-data.
     *
     * @param requestURL
     * @param charset
     * @throws IOException
     */
    public MultipartUploadUtility(String requestURL, String charset)
            throws IOException {

        // creates a unique boundary based on time stamp
        boundary = "" + System.currentTimeMillis() + "";

        URL url = new URL(BASE_URL + requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }

    /**
     * Add a upload file section to the request
     *
     * @param fieldName name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, String fileName)
            throws IOException {

        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
    }

    /**
     * Write an array of bytes to the request's output stream.
     */
    public void writeFileBytes(byte[] bytes, int offset, int length)
            throws IOException {
        outputStream.write(bytes, offset, length);
    }

    /**
     * Complete the request and receives response from the server.
     *
     * @return 
     * @throws IOException if any network error occurred or the server returns
     * non-HTTP_OK status code.
     */
    public String finish() throws IOException {
        outputStream.flush();
        writer.append(LINE_FEED);
        writer.flush();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // check server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String s = "";
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                s += tmp + "\n";
            }            
            reader.close();
            httpConn.disconnect();
            
            return s;
        } else {
            throw new IOException("Server returned non-OK status: " + status);            
        }
    }

    @Override
    public void run() {
        try {
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}
