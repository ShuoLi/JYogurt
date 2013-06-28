package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.HashMap;
import android.net.Uri;

public final class SettingUtil {
	private final static String SERVER = "http://128.220.70.106:8080/JYogurtMyServer";
	private static final String SHAREDKEY = "!@345678()";

	public static String getServer() {
		return SERVER;
	}
	public static String getKey(){
		return SHAREDKEY;
	}

	public static Uri append(String[] path,
			HashMap<String, String> paras) {
		Uri.Builder builder = Uri.parse(SERVER).buildUpon();

		for (String s : path) {
			builder.appendPath(s);
		}

		for (String s : paras.keySet()) {
			builder.appendQueryParameter(s, paras.get(s));
		}
		return builder.build();
	}
	
	public static Uri append(String[] path) {
		Uri.Builder builder = Uri.parse(SERVER).buildUpon();

		for (String s : path) {
			builder.appendPath(s);
		}
		return builder.build();
	}
}
