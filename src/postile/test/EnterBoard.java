/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test;

import domain.User;
import java.net.URLEncoder;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class EnterBoard {
	public static String PATH = "/board/enter_board";
	public static void enterBoard(User user, Integer boardId) {
		String url = Tester.URL + PATH;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET));
		} catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("EnterBoard: Received Json:\n%s\n", rJson);
	}
	
}
