package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Message;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.MessageListAdapter;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;

/**
 * The class for the management of message
 * 
 * @author Hang Ou, Zaoxing Liu
 * 
 */
public class MessageManagement extends ListActivity {

	public final String CATE_PATH = "message";
	public final String SPEC_PATH = "get_by_user.jsp";
	public final String[] pathes = { CATE_PATH, SPEC_PATH };
	private List<Message> messageList = new ArrayList<Message>();
	private HashMap<String, String> paras = new HashMap<String, String>();
	private String sharedKey = SettingUtil.getKey();
	
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String fromid = bundle.getString("user");
		String toid = bundle.getString("touser");
		try {
			paras.put("from", CryptoBASE64.encrypt(sharedKey, fromid));
			paras.put("to", CryptoBASE64.encrypt(sharedKey, toid));
		} catch (Exception e) {
			
			Toast.makeText(MessageManagement.this, "Encryption Error!",
					Toast.LENGTH_LONG).show();
			
			e.printStackTrace();
		}
		

		messageList = getListbyID();

		ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this,
				R.layout.list_row, R.id.txtTitle, messageList);
		setListAdapter(adapter);

		setListAdapter(new MessageListAdapter(this, messageList));
	}

	/**
	 * Create an options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_message_management, menu);
		return true;
	}

	/**
	 * The function when an item in the list is clicked
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(MessageManagement.this, "Message Detail showed",
				Toast.LENGTH_LONG).show();

		messageList.get(position).getMessageId();
		Intent intent = new Intent(this, PostDetail.class);
		String messageId = messageList.get(position).getMessageId();
		Bundle bundle = new Bundle();
		bundle.putString("MESSAGEID", messageId);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * The function for getting a user's post list
	 * 
	 * @param path
	 * @return The post list
	 */
	@SuppressWarnings("unchecked")
	private List<Message> getListbyID() {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(SettingUtil.append(pathes,
				paras).toString()));
		List<Message> data = (List<Message>) new HttpConn().fetchDataFromServer(
				MessageManagement.this, "MessageList", client, request);
		return data;
	}

}
