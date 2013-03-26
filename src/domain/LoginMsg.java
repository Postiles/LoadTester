/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author gary_li
 */
public class LoginMsg {
	public String status;
	public Msg message;
	public User getUser() {
		return message.user;
	}
}

class Msg {
	public User user;
}
