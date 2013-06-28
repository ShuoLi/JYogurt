package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;

/**
 * The XML Handler is used for parsing the login XML and get the return value
 * 
 * 
 * @author Zaoxing Liu (Alan) Hang Ou
 * 
 */
@SuppressWarnings("unused")
public class XMLHandlerMessageList extends JYogurtHandler {
	private boolean in_message = false;
	private boolean in_title = false;
	private boolean in_from = false;
	private boolean in_to = false;
	private boolean in_date = false;
	private boolean in_type = false;
	private boolean in_description = false;

	private List<Message> li;
	private Message message;
	private StringBuffer buf = new StringBuffer();

	public Object getParsedData() {
		return li;
	}

	public void startDocument() throws SAXException {
		li = new ArrayList<Message>();
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("Message")) {
			this.in_message = true;
			message = new Message();
			message.setMessageId(new String(atts.getValue("id")));

		} else if (localName.equals("title")) {
			if (this.in_message) {
				this.in_title = true;
			}
		} else if (localName.equals("from")) {
			if (this.in_message) {
				this.in_from = true;
			}
		} else if (localName.equals("to")) {
			if (this.in_message) {
				this.in_to = true;
			}
		} else if (localName.equals("date")) {
			if (this.in_message) {
				this.in_date = true;
			}
		} else if (localName.equals("type")) {
			if (this.in_message) {
				this.in_type = true;
			}
		} else if (localName.equals("desp")) {
			if (this.in_message) {
				this.in_description = true;
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("Message")) {
			this.in_message = false;
			li.add(message);
			// items.setCategory(buf.toString().trim());
			// buf.setLength(0);

		} else if (localName.equals("title")) {
			if (this.in_message) {
				try {
					message.setTitle(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_title = false;
			}

		} else if (localName.equals("from")) {
			if (this.in_message) {
				try {
					message.setUsersByFromJhed(CryptoBASE64.decrypt(sharedkey,
							buf.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_from = false;
			}
		} else if (localName.equals("to")) {
			if (this.in_message) {
				try {
					message.setUsersByToJhed(CryptoBASE64.decrypt(sharedkey,
							buf.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_to = false;
			}
		} else if (localName.equals("date")) {
			if (this.in_message) {
				try {
					message.setDate(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_date = false;
			}
		} else if (localName.equals("type")) {
			if (this.in_message) {
				try {
					message.setType(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_type = false;
			}
		} else if (localName.equals("desp")) {
			if (this.in_message) {
				try {
					message.setDescription(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_description = false;
			}
		}

	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (this.in_message) {
			buf.append(ch, start, length);
		}
	}

}
