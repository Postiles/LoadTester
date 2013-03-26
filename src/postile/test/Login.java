/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test;

import java.net.URLEncoder;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class Login {
	public static String PATH="/user/login";
	public static void login(String userName, String password) {
		String url=Tester.URL + PATH;
		String query = null;
		try {
			query = String.format("username=%s&password=%s", 
					URLEncoder.encode(userName, Tester.CHARSET),
					URLEncoder.encode(password, Tester.CHARSET));
		} catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rVal = HttpRequester.executePost(url, query);
		System.out.printf("%s\n", rVal);

	}
	
}
