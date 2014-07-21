package br.com.brokenbits.mvn.versions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
import org.xml.sax.SAXException;

public class POMFile {

	private File file;
	
	private Document dom;
	
	
	public POMFile(File f) throws IOException {
		loadFromFile(f);
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
		
	}
	
	
	private Document loadXML(File f) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory .setValidating(false); // Disable validation in order		
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		return documentBuilder.parse(f);
	}
}
