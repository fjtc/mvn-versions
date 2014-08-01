package br.com.brokenbits.mvn.versions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.brokenbits.mvn.versions.util.DOMUtil;

public class POMFile {

	private File file;
	
	private Document dom;
	
	public POMFile(String file) throws IOException {
		this(new File(file));
	}
	
	public POMFile(File f) throws IOException {
		loadFromFile(f);
	}
	
	public File getFile(){
		return this.file;
	}

	private void loadFromFile(File f) throws IOException {
		
		try {
			this.dom = loadXML(f);
		} catch (SAXException e) {
			throw new  IOException("Invalid POM file.", e);
		} catch (ParserConfigurationException e) {
			throw new  IOException("Unable to load the POM file.", e);
		}
		
		// Perform a small validation
		validatePOM();
		
		this.file = f;
	}
	
	public void save() throws IOException {
		this.save(this.file);
	}	

	public void save(File f) throws IOException {

		try {
			writeXML(f);
		} catch (TransformerConfigurationException e) {
			throw new IOException("Unable to create the XML writer.", e);
		} catch (TransformerException e) {
			throw new IOException("Unable to write the XML file.", e);
		}
	}
	
	private void writeXML(File f) throws IOException, TransformerConfigurationException, TransformerException {
		OutputStream out;
		
		out = new FileOutputStream(f);
		try {
			writeXML(out);
		} finally {
			out.close();
		}
	}
	

	private void writeXML(OutputStream f) throws IOException, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory;
		Transformer transformer;
		
		transformerFactory = TransformerFactory.newInstance();
		transformer = transformerFactory.newTransformer();
		transformer.transform(new DOMSource(this.dom), 
				new StreamResult(f));		
	}

	private void validatePOM() throws IOException {
		Element root;
		
		root = dom.getDocumentElement();
		if ((root.getNamespaceURI() != null) && (!root.getNamespaceURI().equals("http://maven.apache.org/POM/4.0.0"))) {
			throw new InvalidPOMException(String.format("Invalid POM namespace %1$s.", root.getNamespaceURI()));
		}
		if (!root.getNodeName().equals("project")) {
			throw new InvalidPOMException("Invalid POM document root.");
		}
	}
	
	
	private Document loadXML(File f) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false); // Disable validation in order to skip the schema validation
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		return documentBuilder.parse(f);
	}

	public Artifact getArtifact() throws InvalidPOMException {
		try {
			return new Artifact(dom.getDocumentElement());
		} catch (IllegalArgumentException e) {
			throw new InvalidPOMException("Unable to get the artifact information.", e);
		}
	}
	
	public void setVersion(Version v) throws InvalidPOMException {
		
		setVersion(dom.getDocumentElement(), v);
	}
	
	private void setVersion(Node root, Version v) throws InvalidPOMException {
		Node n;
		
		n = DOMUtil.getFirstNodeByName(root, "version");
		if (n == null) {
			n = dom.createElement("version");
			dom.appendChild(n);
		}
		n.setTextContent(v.toString());		
	}	
	
	public List<Artifact> listDependencies() throws InvalidPOMException {
		Node depsNode;
		NodeList nodeList;
		ArrayList<Artifact> list = new ArrayList<Artifact>(); 
		
		depsNode = DOMUtil.getFirstNodeByName(dom.getDocumentElement(), "dependencies");
		if (depsNode != null) {
			nodeList = depsNode.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node depNode = nodeList.item(i);
				if (depNode.getNodeName().equals("dependency")) {
					try {
						list.add(new Artifact(depNode));
					} catch (IllegalArgumentException e) {
						throw new InvalidPOMException("Invalid dependency list.", e);
					}
				}
			}
		}
		return list;
	}
	
	public void updateDependencies(Map<String, Artifact> depMap) throws InvalidPOMException {
		Node depsNode;
		NodeList nodeList;
		
		depsNode = DOMUtil.getFirstNodeByName(dom.getDocumentElement(), "dependencies");
		if (depsNode != null) {
			nodeList = depsNode.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node depNode = nodeList.item(i);
				if (depNode.getNodeName().equals("dependency")) {
					Artifact dep;
					try {
						dep = new Artifact(depNode);
					} catch (IllegalArgumentException e) {
						throw new InvalidPOMException("Invalid dependency list.", e);
					}
					Artifact newDep = depMap.get(dep.getGroupArtifact());
					if (newDep != null) {
						setVersion(depNode, newDep.getVersion());
					}
				}
			}
		}
	}	
}
