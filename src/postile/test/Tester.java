/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test;

import domain.User;

/**
 *
 * @author gary_li
 */
public class Tester {

	public static String URL="http://www.postiles.com:300";
	public static String CHARSET = "UTF-8";
	private User curUser;
	private String username;
	private String password;
	private int board_id = 1;
	public Tester(String uname, String pwd) {
		username = uname;
		password = pwd;
	}
	
	/**
	 * @param args the command line arguments
	 */

	public void run() {
		// TODO code application logic here
		curUser = Login.login(username, password);
		EnterBoard.enterBoard( curUser,board_id);
		MoveTo.moveTo(curUser, board_id);
	}
}
