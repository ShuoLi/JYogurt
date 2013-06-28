package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;

/**
 * The XML handler is used for parsing the post list XML architecture
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@SuppressWarnings("unused")
public class XMLHandlerNearbyList extends JYogurtHandler {

	private boolean in_post = false;
	private boolean in_title = false;
	private boolean in_author = false;
	private boolean in_date = false;
	private boolean in_latitude = false;
	private boolean in_longitude = false;

	private boolean in_mainTitle = false;
	private List<Post> li = new ArrayList<Post>();
	private Post post;
	private String item_name = "";
	private StringBuffer buf = new StringBuffer();

	public Object getParsedData() {
		return li;
	}

	public String getItemName() {
		return item_name;
	}

	public void startDocument() throws SAXException {
		li = new ArrayList<Post>();
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("BuyingPost")) {
			this.in_post = true;
			post = new Post();
			try {
				post.setPostId(CryptoBASE64.decrypt(sharedkey, new String(atts.getValue("id"))));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (localName.equals("title")) {
			if (this.in_post) {
				this.in_title = true;
			} else {
				this.in_mainTitle = true;
			}
		}

		else if (localName.equals("author")) {
			if (this.in_post) {
				this.in_author = true;
			}
		}
		else if (localName.equals("date")) {
			if (this.in_post) {
				this.in_date = true;
			}
		}
		else if (localName.equals("latitude")) {
			if (this.in_post) {
				this.in_latitude = true;
			}
		} else if (localName.equals("longitude")) {
			if (this.in_post) {
				this.in_longitude = true;
			}
		}

	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("BuyingPost")) {
			this.in_post = false;
			li.add(post);

		}

		else if (localName.equals("title")) {
			if (this.in_post) {
				try {
					post.setTitle(CryptoBASE64.decrypt(sharedkey, buf.toString().trim()));
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_title = false;
			}

		} else if (localName.equals("author")) {
			if (this.in_post) {
				try {
					post.setAuthor(CryptoBASE64.decrypt(sharedkey, buf.toString().trim()));
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_author = false;
			}
			
		} else if (localName.equals("date")) {
			if (this.in_post) {
				try {
					post.setDate(CryptoBASE64.decrypt(sharedkey, buf.toString().trim()));
				} catch (Exception e) {
			
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_date = false;
			}
			
		} 
		else if (localName.equals("latitude")) {
			if (in_post) {

				try {
					post.setLatitude(CryptoBASE64.decrypt(sharedkey, buf.toString().trim()));
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_latitude = false;
			}
		} else if (localName.equals("longitude")) {
			if (in_post) {
				try {
					post.setLongitude(CryptoBASE64.decrypt(sharedkey, buf.toString().trim()));
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_longitude = false;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (this.in_post || this.in_mainTitle) {

			buf.append(ch, start, length);
		}
	}

}
