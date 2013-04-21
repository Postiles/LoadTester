/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.loadtest.netutil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author gary_li
 */
public class HttpRequester {

	public static String executePost(String targetURL, String data, String contentType) {
		URL url;
		HttpURLConnection connection = null;
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			if (data != null) {
				connection.setRequestProperty("Content-Length", ""
						+ Integer.toString(data.getBytes().length));
			} else {
				connection.setRequestProperty("Content-Length", ""
						+ 0);
			}
			if (contentType != null) {
				connection.setRequestProperty("Content-Type", contentType);
			}

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			if (data != null) {
				wr.writeBytes(data);
			}
			wr.flush();
			wr.close();

			//Get Response	
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String executePostAjax(String targetURL, String urlParameters) {
		return executePost(targetURL, urlParameters, null);
	}

	public static String executePostFaye(String targetURL, String json) {
		return executePost(targetURL, json, "application/json");
	}
}
