package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Message;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLWriter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The class for sending a new message
 * 
 * @author Hangou
 * 
 */
public class NewMessage extends Activity {

	private EditText description;
	private EditText title;
	private EditText to;
	private Button btnSend;
	private Button btnClear;
	private Button btnCancel;
	private Message message;
	private String user;
	private String toUser;

	public final String MESSAGE_PATH = "message";
	public final String SPEC_SEND_PATH = "create.jsp";
	public final String[] pathes = {MESSAGE_PATH,SPEC_SEND_PATH};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		user = bundle.getString("user");
		toUser = bundle.getString("touser");

		title = (EditText) this.findViewById(R.id.title);
		description = (EditText) this.findViewById(R.id.description);
		// to = (EditText) this.findViewById(R.id.to);
		btnSend = (Button) this.findViewById(R.id.btnSend);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnClear = (Button) this.findViewById(R.id.btnClear);

		btnSend.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				message = new Message();
				message.setTitle(title.getText().toString());
				message.setDescription(description.getText().toString());
				message.setUsersByFromJhed(user);
				message.setUsersByToJhed(toUser);
				String MESSAGE_REQUEST_PREFIX_SEND = SettingUtil.append(pathes).toString();
				sendMessage(MESSAGE_REQUEST_PREFIX_SEND, message);
				// sendPost(REQUEST_PREFIX_SEND, post);
			}

		});

		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				NewMessage.this.finish();
			}

		});

		btnClear.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				to.setText("");
				title.setText("");
				description.setText("");
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_message, menu);
		return true;
	}

	public void sendMessage(String path, Message message) {

		XMLWriter wr = new XMLWriter();
		String xml = wr.writeXmlMessage(message);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(URI.create(path));

		try {
			StringEntity se = new StringEntity(xml, "UTF-8");
			request.setEntity(se);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(NewMessage.this, "The message has been saved",
						Toast.LENGTH_SHORT).show();
				NewMessage.this.finish();

			} else {
				Toast.makeText(NewMessage.this, "Network Connection Error!",
						Toast.LENGTH_SHORT).show();
			}
		} catch (IllegalArgumentException e) {
			Toast.makeText(NewMessage.this, "Illegal Argument Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (NullPointerException e) {
			Toast.makeText(NewMessage.this, "Null Pointer Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Toast.makeText(NewMessage.this, "Client Protocol Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(NewMessage.this, "Illegal State!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(NewMessage.this, "IO Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}
}
