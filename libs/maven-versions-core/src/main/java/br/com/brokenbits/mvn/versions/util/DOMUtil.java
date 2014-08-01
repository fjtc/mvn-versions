package br.com.brokenbits.mvn.versions.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtil {


	public static Node getFirstNodeByName(Node root, String tagName) {
		NodeList nodes;
		
		nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeName().equals(tagName)) {
				return n;
			}
		}
		return null;		
	}
}
