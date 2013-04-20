/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.controller;

import java.util.Random;
import postile.domain.User;

/**
 *
 * @author gary_li
 */
public class Tester {

	public static String URL = "http://www.postiles.com:300";
	public static String CHARSET = "UTF-8";
	private int boardId = 15;
	private User curUser;
	private FayeController faye;
	private String email;
	private String password;
	private int repeat = 10;

	public Tester(String uname, String pwd) {
		email = uname;
		password = pwd;
	}

	/**
	 * @param args the command line arguments
	 */
	public void run() {
		Random rg = new Random();
		try {
			// TODO code application logic here
			long startTime = System.currentTimeMillis();
			curUser = Login.login(email, password);
			System.out.printf("User %s: Login Response Time: %d\n", curUser.id,
					System.currentTimeMillis() - startTime);
			startTime = System.currentTimeMillis();
			BoardController.enterBoard(curUser, boardId);
			System.out.printf("User %s EnterBoardTime: %d\n", curUser.id,
					System.currentTimeMillis() - startTime);
			//subscribe to board after enter
			faye = new FayeController("" + boardId);
			faye.handshake();
			faye.subscribe();
			faye.longPolling();

			Thread.sleep(rg.nextInt(3000));

			startTime = System.currentTimeMillis();
			System.out.println("Starting Move to Test\n");
			for (int i = 0; i < repeat; i++) {
				BoardController.moveTo(curUser, boardId);
				Thread.sleep(rg.nextInt(3000));
			}
			System.out.printf("User %s: 10 Move To: %d\n", curUser.id,
					System.currentTimeMillis() - startTime - 500 * repeat);
			PostController post = new PostController(curUser, boardId);
			System.out.println("Starting Posting test set\n");
			for (int i = 0; i < repeat; i++) {
				Thread.sleep(rg.nextInt(3000));
				startTime = System.currentTimeMillis();
				post.newPostRan();
				Thread.sleep(rg.nextInt(3000));
				post.startEdit();
				Thread.sleep(rg.nextInt(3000));
				post.delete();
				System.out.printf("Post Test Set:%s, compeleted in %d ms\n",
						i, System.currentTimeMillis() - startTime);
			}
			System.out.printf("Done for usr: %s\n", curUser.id);
			//wait for faye
			Thread.sleep(10000);
			stopFaye();
		} catch (Exception e) {
			System.err.printf("Error Thread sleeping");
		}
	}

	public void subscribeTest() {
		FayeController faye = new FayeController("" + boardId);
		faye.handshake();
		faye.subscribe();
	}

	public void stopFaye() {
		faye.stop();
	}
}
