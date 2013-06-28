package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.net.URI;
import java.util.HashMap;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Message;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The class for viewing a message
 * 
 * @author Hang
 * 
 */

public class MessageView extends Activity {

	private EditText description;
	private EditText title;
	private EditText from;
	private Message message;
	private String messageId;

	public final String CATE_PATH = "message";
	public final String SPEC_PATH = "get_by_id.jsp";
	private HashMap<String, String> paras = new HashMap<String, String>();
	public final String[] PATH = { CATE_PATH, SPEC_PATH };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_view);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		messageId = bundle.getString("MESSAGEID");

		title = (EditText) this.findViewById(R.id.title);
		description = (EditText) this.findViewById(R.id.description);
		from = (EditText) this.findViewById(R.id.from);

		try {
			paras.put("id", CryptoBASE64.encrypt(SettingUtil.getKey(), messageId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = SettingUtil.append(PATH, paras).toString();
		message = getMessage(path);

		title.setText(message.getTitle());
		from.setText(message.getUsersByFromJhed());
		description
				.setText(message.getDate() + "\n" + message.getDescription());
	}

	/**
	 * The function for creating an options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_message_view, menu);
		return true;
	}

	/**
	 * The function when an option item is selected
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.edit:
			// Single menu item is selected do something
			Toast.makeText(MessageView.this, "Message View function started",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, PostEdit.class);
			Bundle bundle = new Bundle();
			bundle.putString("MESSAGEID", messageId);
			intent.putExtras(bundle);
			startActivity(intent);

			return true;

		case R.id.menu_settings:
			Toast.makeText(MessageView.this, "Under Construction",
					Toast.LENGTH_SHORT).show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * The function to get the messsge
	 * 
	 * @param path
	 * @return
	 */
	public Message getMessage(String path) {

		Message data = new Message();
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(path));
		// request.addHeader(ID, postID);

		data = (Message) new HttpConn().fetchDataFromServer(MessageView.this,
				"Message", client, request);

		return data;

	}
}
