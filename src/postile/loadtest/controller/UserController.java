/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.loadtest.controller;

import com.google.gson.Gson;
import postile.loadtest.domain.LoginMsg;
import postile.loadtest.domain.User;
import java.net.URLEncoder;
import postile.loadtest.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class UserController {

	public static String PATH = "/user/login";

	public static User login(String email, String password) {
		String url = Tester.URL + PATH;
		String query = null;
		try {
			query = String.format("username=%s&password=%s",
					URLEncoder.encode(email, Tester.CHARSET),
					URLEncoder.encode(password, Tester.CHARSET));
		} catch (Exception e) {
			System.err.println("Error encoding paramters");
			return null;
		}
		String rJson = HttpRequester.executePostAjax(url, query);
		System.out.printf("Login: Received Json:\n%s\n", rJson);
		Gson gson = new Gson();
		LoginMsg msg = gson.fromJson(rJson, LoginMsg.class);
		System.out.printf("Login: status:%s\n", msg.status);
		return msg.getUser();
	}

}
