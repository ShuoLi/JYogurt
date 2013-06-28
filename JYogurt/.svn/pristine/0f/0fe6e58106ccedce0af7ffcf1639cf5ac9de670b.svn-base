package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;

/**
 * The XML Handler is used for parsing the login XML and get the return value
 * 
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@SuppressWarnings("unused")
public class XMLHandlerMessage extends JYogurtHandler {

	private boolean in_message = false;
	private boolean in_id = false;
	private boolean in_title = false;
	private boolean in_from = false;
	private boolean in_to = false;
	private boolean in_date = false;
	private boolean in_type = false;
	private boolean in_description = false;

	private Message item;
	private StringBuffer buf = new StringBuffer();

	public Object getParsedData() {
		return item;
	}

	public void startDocument() throws SAXException {
		item = new Message();
	}

	public void endDocument() throws SAXException {

	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("Message")) {
			this.in_message = true;
			// item=new Post();
			// item.setPostId(new String(atts.getValue("category")));

		} else if (localName.equals("id")) {
			if (this.in_message) {
				this.in_id = true;
			}
		}

		else if (localName.equals("title")) {
			if (this.in_message) {
				this.in_title = true;
			}
		}

		else if (localName.equals("from")) {
			if (this.in_message) {
				this.in_from = true;
			}
		}

		else if (localName.equals("to")) {
			if (this.in_message) {
				this.in_to = true;
			}
		}

		else if (localName.equals("date")) {
			if (this.in_message) {
				this.in_date = true;
			}
		}

		else if (localName.equals("type")) {
			if (this.in_message) {
				this.in_type = true;
			}
		}

		else if (localName.equals("description")) {
			if (this.in_message) {
				this.in_description = true;
			}
		}

	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("Message")) {
			this.in_message = false;
			// items.setCategory(buf.toString().trim());
			// buf.setLength(0);

		}

		else if (localName.equals("id")) {
			if (this.in_id) {
				try {
					item.setMessageId(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_id = false;
			}

		} else if (localName.equals("title")) {
			if (this.in_message) {
				try {
					item.setTitle(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_title = false;
			}
		} else if (localName.equals("from")) {
			if (in_message) {

				try {
					item.setUsersByFromJhed(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_from = false;
			}
		} else if (localName.equals("to")) {
			if (in_message) {

				try {
					item.setUsersByToJhed(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_to = false;
			}
		} else if (localName.equals("date")) {
			if (in_date) {
				try {
					item.setDate(CryptoBASE64.decrypt(sharedkey, buf.toString()
							.trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_date = false;
			}
		} else if (localName.equals("type")) {
			if (in_message) {
				try {
					item.setType(CryptoBASE64.decrypt(sharedkey, buf.toString()
							.trim()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_type = false;
			}
		} else if (localName.equals("description")) {
			if (in_message) {
				try {
					item.setDescription(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_description = false;
			}
		}
	}

	public void characters(char ch[], int start, int length) {
		if (this.in_message) {
			buf.append(ch, start, length);
		}
	}

}
