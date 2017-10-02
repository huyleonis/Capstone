/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.connection;

import ats.dtos.VehiclePayment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.Timer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chi Hieu
 */
public class RequestServer extends TimerTask {

    public static final String urlName = "http://192.168.88.191:8080/price/findPrice/B9407F30-F5F8-466E-AFF9-25556B57FE6D:36857:31381/tuan";

    //private final String USER_AGENT = "Mozilla/5.0";
    // HTTP GET request
    
    public VehiclePayment sendGet() throws Exception {
        JSONParser parser = new JSONParser();
        VehiclePayment vehiclePayment = new VehiclePayment();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject)parser.parse(inputLine);
                // Loop through each item
//                for (Object o : a) {
//                    JSONObject tutorials = (JSONObject) o;
                String licensePlate = (String) payment.get("nameStation");
                vehiclePayment.setLicensePlate(licensePlate);

                String typeName = (String) payment.get("nameStation");
                vehiclePayment.setTypeName(typeName);
//                System.out.println("Post ID : " + typeName);

                Double price = (Double) payment.get("price");
                vehiclePayment.setFee(price);
//                System.out.println("Post Title : " + price);

                String status = (String) payment.get("nameStation");
                vehiclePayment.setStatus(status);
//                System.out.println("\n");
//                

            }
            //in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiclePayment;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
//    
    
// HTTP POST request
//	private void sendPost() throws Exception {
//
//		String url = "https://selfsolve.apple.com/wcResults.do";
//		URL obj = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//
//		//add reuqest header
//		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//
//		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//
//		// Send post request
//		con.setDoOutput(true);
//		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		wr.writeBytes(urlParameters);
//		wr.flush();
//		wr.close();
//
//		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
//
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();
//
//		//print result
//		System.out.println(response.toString());
//
//	}
