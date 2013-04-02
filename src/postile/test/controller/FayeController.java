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
	private String channel;
	private boolean startPolling;
	public FayeController(String channel) {
		this.channel = channel;

	}
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
		queryObj.subscription = "/faye/"+channel;
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
	public void requestUpdate() {
		Polling polling = new Polling();
		polling.channel = "/meta/connect";
		polling.connectionType = "long-polling";
		polling.clientId = fayeClient.clientId;
		Gson gson = new Gson();
		String query = gson.toJson(polling);
		String rJson = HttpRequester.executePostFaye(FayeURL, query);
		System.out.printf("=====Faye=========\n Response:%s\n", rJson);
	}
	public void longPolling() {
		if(!startPolling){
			startPolling = true;
			Thread worker = new Thread(new PollingRunner());
			worker.start();
		}
	}

	private void longPollingRun() {
		while(startPolling) {
			System.out.println("=====Polling\n");
			try{
				Thread.sleep(100);
			}catch(Exception e){
				System.err.printf("Polling Error\n");
			}
			requestUpdate();
		}
	}
	public void stop() {
		startPolling = false;
	}
	class PollingRunner implements Runnable {
		@Override 
		public void run() {
			longPollingRun();
		}
		
	}
}

class FayeBase {
	public String channel;
	public String clientId;
}
class Polling extends FayeBase {
	public String connectionType;
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
