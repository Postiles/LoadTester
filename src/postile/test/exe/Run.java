/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.exe;

import java.util.ArrayList;
import java.util.List;
import postile.test.controller.Tester;

/**
 *
 * @author gary_li
 */
public class Run {
	static List<Tester> testers = new ArrayList<Tester>();
	public static void main(String args[]) {
		testers.add(new Tester("litao91", "asdfghjkl"));
		testers.add(new Tester("billy", "asdfghjkl"));
		testers.add(new Tester("fei", "asdfghjkl"));
		testers.add(new Tester("sally", "asdfghjkl"));
		testers.add(new Tester("kmxz", "asdfghjkl"));
		testers.add(new Tester("guanlun", "asdfghjkl"));
		for(Tester tester: testers) {
			Thread worker = new Thread(new TesterAdapter(tester));
			worker.start();
		}
	}
}

class TesterAdapter implements Runnable {
	Tester tster;
	TesterAdapter(Tester tster) {
		this.tster = tster;
	}
	@Override
	public void run() {
		tster.run();
	}
}
