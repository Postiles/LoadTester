/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test;

import domain.User;
import java.net.URLEncoder;
import java.util.Random;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class MoveTo {
	private static int MAXRANGE = 20;
	private static String PATH= "/board/enter_board";
	public static void moveTo( User user, Integer boardId){
		Random rGen = new Random();
		Integer left = -rGen.nextInt(MAXRANGE);
		Integer top = -rGen.nextInt(MAXRANGE);
		Integer bottom = rGen.nextInt(MAXRANGE);
		Integer right = rGen.nextInt(MAXRANGE);
		String url = Tester.URL + PATH;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s"
					+ "&left=%s&top=%s&right=%s&bottom=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET),
					URLEncoder.encode(left.toString(),Tester.CHARSET),
					URLEncoder.encode(top.toString(), Tester.CHARSET),
					URLEncoder.encode(right.toString(), Tester.CHARSET),
					URLEncoder.encode(bottom.toString(), Tester.CHARSET));
		} catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("MoveTo: Received Json:\n%s\n", rJson);
	}

	
}
