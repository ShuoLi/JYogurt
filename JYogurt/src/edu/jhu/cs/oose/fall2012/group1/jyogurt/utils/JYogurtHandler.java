package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import org.xml.sax.helpers.DefaultHandler;
/**
 * 
 * JYogurt Handler uniform abstract class
 *
 */
public abstract class JYogurtHandler extends DefaultHandler{
	public abstract Object getParsedData();
	protected static String sharedkey = SettingUtil.getKey();
}
