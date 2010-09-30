package net.java.dev.moskito.webcontrol.feed;

import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpGetter implements FeedGetter {

	private static Logger log = Logger.getLogger(HttpGetter.class);

	@Override
	public Document retreive(SourceConfiguration source) {
		System.out.println(source);
		try {
			URL url = new URL(source.getUrl());
			URLConnection connection = url.openConnection();
            authenticateConnection(source, connection);
            return buildDocument(connection);
		} catch (MalformedURLException e) {
			log.error("Malformed URL in source configuration : " + source.getUrl());
		} catch (IOException e) {
			log.error("Error retreiving content for source : " + source.getUrl());
		} catch (SAXException e) {
			log.error("Error parsing XML for source configuration : " + source.getUrl(), e);
		} catch (ParserConfigurationException e) {
			log.error("Error parsing XML for source configuration : " + source.getUrl(), e);
		}
		return null;
	}

    private void authenticateConnection(SourceConfiguration source, URLConnection connection) {
        if(source.needAuth()) {
            StringBuilder credentials = new StringBuilder(source.getUsername() + ":" + source.getPassword());
            String encoding = new BASE64Encoder().encode(credentials.toString().getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encoding);
        }
    }

    private Document buildDocument(URLConnection connection) throws ParserConfigurationException, IOException, SAXException {
        if(connection.getContentType() == null) throw new IOException();
        if(connection.getContentType().startsWith("text/xml")) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            return dbf.newDocumentBuilder().parse((InputStream) connection.getContent());
        }
        return null;
    }

}
