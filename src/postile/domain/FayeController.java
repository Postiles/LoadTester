/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.domain;

import postile.test.controller.Tester;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class FayeController {
	int clientId;
	public static String HSHAKE_PATH="/faye/meta/handshake";
	public static String FayeURL="http://www.postiles.com:9292";
	public void handshake() {
		String url = FayeURL+HSHAKE_PATH;
		String rJson = HttpRequester.executePostFaye(url,"{\"channel\":\"/meta/handshake\",\"supportedConnectionTypes\":[\"long-polling\"],\"version\":\"1.0\"}");
		System.out.printf("%s\n", rJson);
	}
	
}
