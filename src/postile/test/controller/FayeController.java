/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class FayeController {
	public static String FayeURL="http://www.postiles.com:9292/faye";
	public static final String FayeQueryFormat = "{\"channel\":\"%s\",\"%s\"}";
	public static final String HandShakeChannel = "/meta/handshake";
	public static final String FayeHandShakeQuery="supportedConnectionTypes\":"
			+ "[\"long-polling\"],\"version\":\"1.0\"";
	HandShakeResponse fayeClient;
	boolean isSubscribed;
	public void handshake(){
		String query = String.format(FayeQueryFormat,HandShakeChannel,FayeHandShakeQuery);
		String rJson = HttpRequester.executePostFaye(FayeURL,query);
		System.out.printf("%s\n", rJson);
		Gson gson = new Gson();
		List<HandShakeResponse> responses = gson.fromJson(rJson, 
				new TypeToken<List<HandShakeResponse>>(){}.getType());
		fayeClient = responses.get(0);
		System.out.printf("Hand Shake success: Client ID:%s\n", 
				fayeClient.clientId);
	}
	public void subscribe() {
		SubscribeBase queryObj = new SubscribeBase();
		queryObj.channel = "/meta/subscribe";
		queryObj.subscription = "/"+Tester.boardId;
		queryObj.clientId = fayeClient.clientId;
		Gson gson  = new Gson();
		String query = gson.toJson(queryObj);
		System.out.printf("%s\n", query);
		String rJson = HttpRequester.executePostFaye(FayeURL, query);
		System.out.printf("Subscribe Response:%s\n", rJson);
		List<SubscribeResponse> subscribeResponse = 
				gson.fromJson(rJson, new TypeToken<List<SubscribeResponse>>(){}.getType());
		isSubscribed = subscribeResponse.get(0).successful;
	}
}
class FayeBase {
	public String channel;
	public String clientId;
}

class SubscribeBase extends FayeBase {
	public String subscription;
}
class SubscribeResponse extends SubscribeBase{
	public boolean successful;
}


class HandShakeResponse extends FayeBase{
	public boolean successful;
	public String version;
	public List<String> supportedConnectionTypes;
	public FayeAdvice advice;
}

class FayeAdvice {
	public String reconnect;
	public int interval;
	public int timeout;
}
