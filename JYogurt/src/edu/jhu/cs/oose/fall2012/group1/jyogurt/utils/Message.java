package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

/**
 * The class for message info
 * @author Group 1
 *
 */
public class Message {
	private String id;
	private String usersByToJhed;
	private String usersByFromJhed;
	private String title;
	private String description;
	private String date;
	private String type;
	/**
	 * @return the id
	 */
	public String getMessageId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setMessageId(String id) {
		this.id = id;
	}
	/**
	 * @return the usersByToJhed
	 */
	public String getUsersByToJhed() {
		return usersByToJhed;
	}
	/**
	 * @param usersByToJhed the usersByToJhed to set
	 */
	public void setUsersByToJhed(String usersByToJhed) {
		this.usersByToJhed = usersByToJhed;
	}
	/**
	 * @return the usersByFromJhed
	 */
	public String getUsersByFromJhed() {
		return usersByFromJhed;
	}
	/**
	 * @param usersByFromJhed the usersByFromJhed to set
	 */
	public void setUsersByFromJhed(String usersByFromJhed) {
		this.usersByFromJhed = usersByFromJhed;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
