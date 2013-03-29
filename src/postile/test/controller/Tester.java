/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.controller;

import postile.domain.User;

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
	private int boardId = 2;
	private int repeat = 1;
	public Tester(String uname, String pwd) {
		username = uname;
		password = pwd;
	}
	
	/**
	 * @param args the command line arguments
	 */

	public void run() {
		// TODO code application logic here
		long startTime = System.currentTimeMillis();
		curUser = Login.login(username, password);
		System.out.printf("User %s: Login Response Time: %d\n", curUser.id, 
				System.currentTimeMillis() - startTime);
		startTime = System.currentTimeMillis();
		BoardController.enterBoard( curUser,boardId);
		System.out.printf("User %s: EnterBoardTime: %d", curUser.id,
				System.currentTimeMillis()-startTime);
		startTime = System.currentTimeMillis();
		System.out.println("Starting Move to Test");
		for(int i = 0; i< repeat; i++) {
			BoardController.moveTo(curUser, boardId);
		}
		System.out.printf("User %s: 10 Move To: %d\n", curUser.id,
				System.currentTimeMillis() - startTime);
		PostController post = new PostController(curUser, boardId);
		System.out.println("Starting Posting test set");
		for(int i=0; i< repeat; i++) {
			startTime = System.currentTimeMillis();
			post.newPostRan();
			post.startEdit();
			post.delete();
			System.out.printf("Post Test Set:%s, compeleted in %d ms\n", 
					i, System.currentTimeMillis()-startTime);
		}
		System.out.printf("Done for usr: %s\n", curUser.id);
	}
}
