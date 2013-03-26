/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exe;

import domain.User;
import postile.test.Login;

/**
 *
 * @author gary_li
 */
public class Run {
	public static void main(String args[]) {
		User user = Login.login("litao91", "asdfghjkl");
		System.out.printf("get user session_key: %s\n", user.session_key);
	}
	
}
