/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.controller;

import com.google.gson.Gson;
import postile.domain.User;
import java.net.URLEncoder;
import java.util.Random;
import postile.domain.Post;
import postile.domain.Profile;
import postile.domain.ResponseStatus;
import postile.test.netutil.HttpRequester;

/**
 *
 * @author gary_li
 */
public class PostController {

	public static String PATH_CREATE = "/post/new";
	public static String PATH_START_EDIT = "/post/start_edit";
	public static String PATH_DELETE = "/post/delete";
	public static int MAX_POST_SIZE = 10;
	User user;
	Integer boardId;
	Post post;
	boolean isOK;

	public PostController(User user, int boardId) {
		this.user = user;
		this.boardId = boardId;
	}

	public void newPost(Integer pos_x, Integer pos_y, Integer span_x, Integer span_y) {
		String url = Tester.URL + PATH_CREATE;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s"
					+ "&pos_x=%s&pos_y=%s&span_x=%s&span_y=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET),
					URLEncoder.encode(pos_x.toString(), Tester.CHARSET),
					URLEncoder.encode(pos_y.toString(), Tester.CHARSET),
					URLEncoder.encode(span_x.toString(), Tester.CHARSET),
					URLEncoder.encode(span_y.toString(), Tester.CHARSET));
		} catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("/post/new: Received Json:\n%s\n", rJson);

		Gson gson = new Gson();
		String status = gson.fromJson(rJson, ResponseStatus.class).status;
		System.out.printf("/post/new status: %s\n", status);
		isOK = status.equals("ok");
		if (isOK) {
			post = gson.fromJson(rJson, ResponseData.class).message.post;
			System.out.printf("Post created with id %s\n", post.id);
		} else {
			System.out.printf("Post Create: Err\n");
		}
	}

	public void newPostRan() {
		Random rgen = new Random();
		int pos_x = rgen.nextInt(BoardController.MAXRANGE) - BoardController.MAXRANGE / 2;
		int pos_y = rgen.nextInt(BoardController.MAXRANGE) - BoardController.MAXRANGE / 2;
		int span_x = rgen.nextInt(MAX_POST_SIZE);
		int span_y = rgen.nextInt(MAX_POST_SIZE);
		newPost(pos_x, pos_y, span_x, span_y);
	}

	public void startEdit() {
		if (!isOK) {
			return;
		}
		String url = Tester.URL + PATH_START_EDIT;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s"
					+ "&post_id=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET),
					URLEncoder.encode(post.id, Tester.CHARSET));
		}catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("/post/start_edit: Received Json:\n%s\n", rJson);
	}

	public void submit_change() {
		if (!isOK) {
			return;
		}
		String url = Tester.URL + PATH_DELETE;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s"
					+ "&post_id=%s&title=%s&content=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET),
					URLEncoder.encode(post.id, Tester.CHARSET),
					URLEncoder.encode("loadTest", Tester.CHARSET),
					URLEncoder.encode("loadTest", Tester.CHARSET));
		}catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("/post/delete: Received Json:\n%s\n", rJson);
	}

	public void delete() {
		if (!isOK) {
			return;
		}
		String url = Tester.URL + PATH_DELETE;
		String query = null;
		try {
			query = String.format("user_id=%s&session_key=%s&board_id=%s"
					+ "&post_id=%s",
					URLEncoder.encode(user.id, Tester.CHARSET),
					URLEncoder.encode(user.session_key, Tester.CHARSET),
					URLEncoder.encode(boardId.toString(), Tester.CHARSET),
					URLEncoder.encode(post.id, Tester.CHARSET));
		}catch (Exception e) {
			System.err.println("Error encoding paramters");
			return;
		}
		String rJson = HttpRequester.executePost(url, query);
		System.out.printf("/post/delete: Received Json:\n%s\n", rJson);
	}

	class ResponseData {

		String status;
		MsgAdapter message;
	}

	class MsgAdapter {

		public Post post;
		public User creator;
		public Profile profile;
	}
}
