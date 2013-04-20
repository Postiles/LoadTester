/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postile.test.exe;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import postile.test.controller.FayeController;
import postile.test.controller.Tester;

/**
 *
 * @author gary_li
 */
public class Run {

	static List<Tester> testers = new ArrayList<Tester>();

	public static void main(String args[]) {
		Document dom = parseXMLFile(args[0]);
		prepareTester(dom);
		for (Tester tester : testers) {
			Thread worker = new Thread(new TesterAdapter(tester));
			worker.start();
		}
	}

	private static void prepareTester(Document dom) {
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("User");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				String email = getTextValue(el, "email");
				String password = getTextValue(el, "password");
				System.out.printf("Adding tester: \n\tEmail:%s\n\tpassword:%s\n",
						email, password);
				testers.add(new Tester(email, password));
			}
		}
	}

	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private static Document parseXMLFile(String fileName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(fileName);
		} catch (Exception pce) {
			System.err.printf("Error parsing file\n");
		}
		return dom;

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
