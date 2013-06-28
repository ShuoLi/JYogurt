package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;

/**
 * The XML handler is used for parsing the post detail XML architecture
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@SuppressWarnings("unused")
public class XMLHandlerPost extends JYogurtHandler {

	private boolean in_post = false;
	private boolean in_id = false;
	private boolean in_title = false;
	private boolean in_author = false;
	private boolean in_jhed = false;
	private boolean in_address = false;
	private boolean in_latitude = false;
	private boolean in_longitude = false;
	private boolean in_date = false;
	private boolean in_description = false;
	private boolean in_email = false;
	private boolean in_phonenumber = false;
	private boolean in_images = false;
	private boolean in_image = false;
	private ArrayList<String> tempList = new ArrayList<String>();

	private boolean in_mainTitle = false;
	private Post item;
	private String item_name = "";
	private StringBuffer buf = new StringBuffer();

	public Object getParsedData() {
		return item;
	}

	public String getItemName() {
		return item_name;
	}

	public void startDocument() throws SAXException {
		item = new Post();
	}

	public void endDocument() throws SAXException {

	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("BuyingPost") || localName.equals("HousingPost")
				|| localName.equals("SellingPost")) {
			this.in_post = true;
			// item=new Post();
			// item.setPostId(new String(atts.getValue("category")));

		} else if (localName.equals("id")) {
			if (this.in_post) {
				this.in_id = true;
			} else {
				this.in_mainTitle = true;
			}
		} else if (localName.equals("author")) {
			if (this.in_post) {
				this.in_author = true;
			}
		}

		else if (localName.equals("authorjhed")) {
			if (this.in_post) {
				this.in_jhed = true;
			}
		} else if (localName.equals("address")) {
			if (this.in_post) {
				this.in_address = true;
			}
		} else if (localName.equals("latitude")) {
			if (this.in_post) {
				this.in_latitude = true;
			}
		} else if (localName.equals("longitude")) {
			if (this.in_post) {
				this.in_longitude = true;
			}
		} else if (localName.equals("date")) {
			if (this.in_post) {
				this.in_date = true;
			}
		} else if (localName.equals("description")) {
			if (this.in_post) {
				this.in_description = true;
			}
		} else if (localName.equals("email")) {
			if (this.in_post) {
				this.in_email = true;
			}
		} else if (localName.equals("phonenumber")) {
			if (this.in_post) {
				this.in_phonenumber = true;
			}
		} else if (localName.equals("postImages")) {
			if (this.in_post) {
				this.in_images = true;
			}
		} else if (localName.equals("postImage")) {
			if(this.in_post) {
				this.in_image = true;
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("BuyingPost") || localName.equals("HousingPost")
				|| localName.equals("SellingPost")) {
			this.in_post = false;
			// items.setCategory(buf.toString().trim());
			// buf.setLength(0);

		}

		else if (localName.equals("id")) {
			if (this.in_post) {
				try {
					item.setPostId(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_id = false;
			}

		} else if (localName.equals("title")) {
			if (this.in_post) {
				try {
					item.setTitle(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_title = false;
			}
		} else if (localName.equals("author")) {
			if (in_post) {

				try {
					item.setAuthor(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_author = false;
			}
		} else if (localName.equals("authorjhed")) {
			if (in_post) {

				try {
					item.setJhed(CryptoBASE64.decrypt(sharedkey, buf.toString()
							.trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_jhed = false;
			}
		} else if (localName.equals("address")) {
			if (in_post) {
				try {
					item.setAddress(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_address = false;
			}
		} else if (localName.equals("latitude")) {
			if (in_post) {
				try {
					item.setLatitude(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_latitude = false;
			}
		} else if (localName.equals("longitude")) {
			if (in_post) {
				try {
					item.setLongitude(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_longitude = false;
			}
		} else if (localName.equals("date")) {
			if (in_post) {
				try {
					item.setDate(CryptoBASE64.decrypt(sharedkey, buf.toString()
							.trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_date = false;
			}
		} else if (localName.equals("description")) {
			if (in_post) {
				try {
					item.setDescription(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_description = false;
			}
		} else if (localName.equals("email")) {
			if (in_post) {
				try {
					item.setEmail(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {

					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_email = false;
			}
		}

		else if (localName.equals("phonenumber")) {
			if (in_post) {
				try {
					item.setPhoneNumber(CryptoBASE64.decrypt(sharedkey, buf
							.toString().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_phonenumber = false;
			}
		}

		else if (localName.equals("postImages")) {
			if (in_post) {
				try {
					//System.out.println("&&&&&&&&&&&&^^^^^^^^^^^^^^^^^");
					item.setImageUrl(new ArrayList<String>());
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_images = false;
			}
		}
		else if (localName.equals("postImage")) {
			if (in_post) {
				try {//CryptoBASE64.decrypt(sharedkey, buf
					//.toString().trim())
					//System.out.println("&&&&&&&&&&&&&&&&&&&&&"+buf.toString());
					item.addImage(SettingUtil.getServer()+buf.toString().trim());
					tempList.add(SettingUtil.getServer()+buf.toString().trim());
					item.setImages(tempList.toArray(new String[tempList.size()]));
					item.setImage(SettingUtil.getServer()+buf.toString().trim());
				} catch (Exception e) {
					e.printStackTrace();
				}
				buf.setLength(0);
				this.in_image = false;
			}
		}
	}

	public void characters(char ch[], int start, int length) {
		if (this.in_post || this.in_mainTitle) {
			buf.append(ch, start, length);
		}
	}

}
