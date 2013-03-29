/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import postile.test.controller.Tester;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class FayeController {
	int clientId;
	public static String FayeURL="http://www.postiles.com:9292/faye";
	public static final String FayeQueryFormat = "{\"channel\":\"%s\",\"%s\"}";
	public static final String HandShakeChannel = "/meta/handshake";
	public static final String FayeHandShakeQuery="supportedConnectionTypes\":"
			+ "[\"long-polling\"],\"version\":\"1.0\"";
	HandShakeResponse fayeClient;
	public void handshake(){
		String url = FayeURL;
		String query = String.format(FayeQueryFormat,HandShakeChannel,FayeHandShakeQuery);
		String rJson = HttpRequester.executePostFaye(url,query);
		System.out.printf("%s\n", rJson);
		Gson gson = new Gson();
		List<HandShakeResponse> responses = gson.fromJson(rJson, 
				new TypeToken<List<HandShakeResponse>>(){}.getType());
		fayeClient = responses.get(0);
		System.out.printf("Hand Shake success: Client ID:%s\n", 
				fayeClient.clientId);
	}
	public void subscribe() {
	}
}


class HandShakeResponse {
	public String channel;
	public boolean successful;
	public String version;
	public List<String> supportedConnectionTypes;
	public String clientId;
	public FayeAdvice advice;
}

class FayeAdvice {
	public String reconnect;
	public int interval;
	public int timeout;
}
