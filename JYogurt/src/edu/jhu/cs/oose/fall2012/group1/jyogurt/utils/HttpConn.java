package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.widget.Toast;

/**
 * The class to handle the Http connection between android side and server side.
 * 
 */
public class HttpConn {

	public boolean SendRequestWithXML(Activity object, String xml,
			HttpPost request) {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			StringEntity se = new StringEntity(xml, "UTF-8");
			// se.setContentType("text/xml");
			request.setEntity(se);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(object, "success", Toast.LENGTH_LONG).show();
				object.finish();
				return true;
			} else if (response.getStatusLine().getStatusCode() == 500) {
				Toast.makeText(object, "Internal Server Error!",
						Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(object, "Unknown Error!", Toast.LENGTH_LONG)
						.show();
				object.finish();
			}

		} catch (IllegalArgumentException e) {
			Toast.makeText(object, "Illegal Argument Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		} catch (NullPointerException e) {
			Toast.makeText(object, "Null Pointer Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
			return false;
		} catch (ClientProtocolException e) {
			Toast.makeText(object, "Client Protocol Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			Toast.makeText(object, "Illegal State!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Toast.makeText(object, "IO Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		} catch (RuntimeException e) {
			Toast.makeText(object, "Runtime Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*
	 * The first parameter is the activity itself. The className is which XML
	 * handler it want to invoke [List, Login, Message, MessageList NearbyList,
	 * Post]
	 */
	public Object fetchDataFromServer(Activity object, String className,
			DefaultHttpClient client, HttpGet request) {
		Object data = null;
		try {
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();

				XMLReader xr = sp.getXMLReader();
				JYogurtHandler myHandler = (JYogurtHandler) Class.forName(
						"edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLHandler"
								+ className).newInstance();
				xr.setContentHandler(myHandler);
				xr.parse(new InputSource(response.getEntity().getContent()));
				data = myHandler.getParsedData();
			} else {
				Toast.makeText(object, "Network Connection Error!",
						Toast.LENGTH_SHORT).show();
			}

		} catch (ClientProtocolException e) {
			Toast.makeText(object, "Client Protocol Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(object, "Illegal State!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(object, "IO Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (SAXException e) {
			Toast.makeText(object, "XML SAX Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Toast.makeText(object, "XML Parser Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (InstantiationException e) {
			Toast.makeText(object, "XML Instance Create Fail Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Toast.makeText(object, "XML Instance Create Fail Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Toast.makeText(object, "Class Not Found Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (RuntimeException e) {
			Toast.makeText(object, "Runtime Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return data;
	}

	public Object fetchDataFromServer(String xml, Activity object,
			String className, DefaultHttpClient client, HttpPost request) {
		Object data = null;
		try {
			StringEntity se = new StringEntity(xml, "UTF-8");
			request.setEntity(se);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();

				XMLReader xr = sp.getXMLReader();
				JYogurtHandler myHandler = (JYogurtHandler) Class.forName(
						"edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLHandler"
								+ className).newInstance();
				xr.setContentHandler(myHandler);
				xr.parse(new InputSource(response.getEntity().getContent()));
				data = myHandler.getParsedData();
			} else {
				Toast.makeText(object, "Network Connection Error!",
						Toast.LENGTH_SHORT).show();
			}

		} catch (ClientProtocolException e) {
			Toast.makeText(object, "Client Protocol Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(object, "Illegal State!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(object, "IO Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (SAXException e) {
			Toast.makeText(object, "XML SAX Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Toast.makeText(object, "XML Parser Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (InstantiationException e) {
			Toast.makeText(object, "XML Instance Create Fail Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Toast.makeText(object, "XML Instance Create Fail Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Toast.makeText(object, "Class Not Found Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (NullPointerException e) {
			Toast.makeText(object, "Null Pointer Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (RuntimeException e) {
			Toast.makeText(object, "Runtime Error!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return data;
	}

}
