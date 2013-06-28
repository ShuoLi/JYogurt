package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;


import java.io.StringWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.xmlpull.v1.XmlSerializer;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Xml;

/**
 * The class of XML writer for different purposes.
 * @author Zaoxing Liu (Alan) Hang Ou
 *
 */
public class XMLWriter {
	
	private static String sharedKey = SettingUtil.getKey();
	/**
	 * XML writer for Post
	 * @return xml string
	 */
	public String writeXmlPost(Post post, HashMap<String, byte[]> images) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "BuyingPost");

			serializer.startTag("", "id");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getPostId()));
			serializer.endTag("", "id");
			serializer.startTag("", "title");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getTitle()));
			serializer.endTag("", "title");
			serializer.startTag("", "author");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getAuthor()));
			serializer.endTag("", "author");
			serializer.startTag("", "authorjhed");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getJhed()));
			serializer.endTag("", "authorjhed");
			serializer.startTag("", "address");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getAddress()));
			serializer.endTag("", "address");
			serializer.startTag("", "latitude");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getLatitude()));
			serializer.endTag("", "latitude");
			serializer.startTag("", "longitude");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getLongitude()));
			serializer.endTag("", "longitude");
			serializer.startTag("", "date");
			serializer.text(CryptoBASE64.encrypt(sharedKey, getNowDate()));
			serializer.endTag("", "date");
			serializer.startTag("", "description");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getDescription()));
			serializer.endTag("", "description");
			serializer.startTag("", "email");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getEmail()));
			serializer.endTag("", "email");
			serializer.startTag("", "phonenumber");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getPhoneNumber()));
			serializer.endTag("", "phonenumber");
			
			if(images != null && images.size() > 0){
				serializer.startTag("", "images");
				for(String image : images.keySet()){
					serializer.startTag("", "image");
					serializer.text(new String(Base64.encode(images.get(image), Base64.NO_WRAP)));
					serializer.endTag("", "image");
				}
				serializer.endTag("", "images");
			}
			
			
			serializer.endTag("", "BuyingPost");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * XML writer for User
	 * @return XML string
	 */
	public String writeXmlUser(User user) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "User");

			serializer.startTag("", "jhed");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getJhed()));
			serializer.endTag("", "jhed");
			serializer.startTag("", "password");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getPassword()));
			serializer.endTag("", "password");
			serializer.startTag("", "hopkinsid");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getHopkinsId()));
			serializer.endTag("", "hopkinsid");
			serializer.startTag("", "email");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getEmail()));
			serializer.endTag("", "email");
			serializer.startTag("", "fname");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getFname()));
			serializer.endTag("", "fname");
			serializer.startTag("", "lname");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getLname()));
			serializer.endTag("", "lname");
			serializer.startTag("", "phone_number");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getPhoneNumber()));
			serializer.endTag("", "phone_number");
			serializer.startTag("", "address");
			serializer.text(CryptoBASE64.encrypt(sharedKey, user.getAddress()));
			serializer.endTag("", "address");
			serializer.startTag("", "longitude");
			serializer.text("");
			serializer.endTag("", "longitude");
			serializer.startTag("", "latitude");
			serializer.text("");
			serializer.endTag("", "latitude");

			serializer.endTag("", "User");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * XML writer for getting nearby posts
	 * @return
	 */
	public String writeXmlNearby(String lat, String lon, String radius) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "location");

			serializer.startTag("", "latitude");
			serializer.text(CryptoBASE64.encrypt(sharedKey, lat));
			serializer.endTag("", "latitude");
			serializer.startTag("", "longitude");
			serializer.text(CryptoBASE64.encrypt(sharedKey,lon));
			serializer.endTag("", "longitude");
			serializer.startTag("", "radius");
			serializer.text(CryptoBASE64.encrypt(sharedKey,radius));
			serializer.endTag("", "radius");

			serializer.endTag("", "location");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public String writeXmlCreatePost(Post post , String category, HashMap<String, byte[]> images){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", category.trim());//BuyingPost; HousingPost;

			serializer.startTag("", "title");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getTitle()));
			serializer.endTag("", "title");
			serializer.startTag("", "authorjhed");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getJhed()));
			serializer.endTag("", "authorjhed");
			serializer.startTag("", "address");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getAddress()));
			serializer.endTag("", "address");
			serializer.startTag("", "date");
			serializer.text(CryptoBASE64.encrypt(sharedKey, getNowDate()));
			serializer.endTag("", "date");
			serializer.startTag("", "description");
			serializer.text(CryptoBASE64.encrypt(sharedKey, post.getDescription()));
			serializer.endTag("", "description");
			
			if(images != null && images.size() > 0){
				serializer.startTag("", "images");
				for(String image : images.keySet()){
					serializer.startTag("", "image");
					serializer.text(new String(Base64.encode(images.get(image), Base64.NO_WRAP)));
					serializer.endTag("", "image");
				}
				serializer.endTag("", "images");
			}
			
			
			serializer.endTag("", category.trim());
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public String writeXmlMessage(Message message) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "Message");
			
			serializer.startTag("", "title");
			serializer.text(CryptoBASE64.encrypt(sharedKey, message.getTitle()));
			serializer.endTag("", "title");
			
			serializer.startTag("", "from");
			serializer.text(CryptoBASE64.encrypt(sharedKey, message.getUsersByFromJhed()));
			serializer.endTag("", "from");
			
			serializer.startTag("", "to");
			serializer.text(CryptoBASE64.encrypt(sharedKey, message.getUsersByToJhed()));
			serializer.endTag("", "to");
			
			serializer.startTag("", "date");
			serializer.text(CryptoBASE64.encrypt(sharedKey, this.getNowDate()));
			serializer.endTag("", "date");
			
			serializer.startTag("", "type");
			serializer.text("");
			serializer.endTag("", "type");
			
			serializer.startTag("", "description");
			serializer.text(CryptoBASE64.encrypt(sharedKey, message.getDescription()));
			serializer.endTag("", "description");
			
			
			
			serializer.endTag("", "Message");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String writeXmlResetPwd(String jhed, String oldPwd, String newPwd) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "UpdatePassword");
			
			serializer.startTag("", "jhed");
			serializer.text(CryptoBASE64.encrypt(sharedKey, jhed));
			serializer.endTag("", "jhed");
			
			serializer.startTag("", "oldpassword");
			serializer.text(CryptoBASE64.encrypt(sharedKey, oldPwd));
			serializer.endTag("", "oldpassword");
			
			serializer.startTag("", "newpassword");
			serializer.text(CryptoBASE64.encrypt(sharedKey, newPwd));
			serializer.endTag("", "newpassword");		
			
			serializer.endTag("", "UpdatePassword");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getNowDate() {
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		   //ParsePosition pos = new ParsePosition(8);
		   //Date currentTime_2 = formatter.parse(dateString, pos);
		  // return currentTime_2;
		   return dateString;
		}
}
