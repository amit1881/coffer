package com.asmobisoft.coffer.webservices;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientGet {

	private Context ctx;
	URL URLurl;
	public NetClientGet(Context context) {
			this.ctx = context;

	}
	String outputStr="";
	public String getDataClientData(String urlStr){
		String output;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}else{
				System.out.println("conn.getResponseCode()0"+conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));


			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("RESSDSDSDSDSD :"+output);
				outputStr = output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return outputStr;
	}

}