package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLHandlerPost;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLWriter;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Modify the published post
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@TargetApi(14)
public class PostEdit extends Activity {

	private String postId;
	private String category;
	private Post post;
	private EditText address;
	private EditText description;
	private EditText title;
	private TextView txtAddress;
	private TextView txtDesc;
	private TextView txtTitle;
	private Button btnSubmit;
	private Button btnCancel;
	private Button btnClear;

	public final String CATE_BUY_PATH = "buying";
	public final String CATE_SALE_PATH = "selling";
	public final String CATE_HOUSING_PATH = "housing";
	public final String SPEC_GET_PATH = "get_post.jsp";
	public final String SPEC_SEND_PATH = "update.jsp";
	private HashMap<String,String> paras = new HashMap<String,String>(); 
	

	/**
	 * Show the display components from the beginning
	 */
	public void onCreate(Bundle savedInstanceState) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_edit);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		postId = bundle.getString("POSTID");
		category = bundle.getString("category");
		
		String[] pathGet = null;
		String[] pathSend = null;
		
		if (category.equals("HousingPostList")) {
			pathGet = new String[]{CATE_HOUSING_PATH, SPEC_GET_PATH};
			pathSend = new String[]{CATE_HOUSING_PATH, SPEC_SEND_PATH};
		} else if (category.equals("BuyingPostList")) {
			pathGet = new String[]{CATE_BUY_PATH, SPEC_GET_PATH};
			pathSend = new String[]{CATE_BUY_PATH, SPEC_SEND_PATH};
		}else if(category.equals("SellingPostList")){
			pathGet = new String[]{CATE_SALE_PATH, SPEC_GET_PATH};
			pathSend = new String[]{CATE_SALE_PATH, SPEC_SEND_PATH};
		}

		try {
			paras.put("id", CryptoBASE64.encrypt(SettingUtil.getKey(), postId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String getPath = SettingUtil.append(pathGet,paras).toString();
		final String sendPath = SettingUtil.append(pathSend).toString();
		post = getPost(getPath, postId);

		title = (EditText) this.findViewById(R.id.title);
		address = (EditText) this.findViewById(R.id.address);
		description = (EditText) this.findViewById(R.id.description);
		txtAddress = (TextView) this.findViewById(R.id.txtAddress);
		txtTitle = (TextView) this.findViewById(R.id.txtTitle);
		txtDesc = (TextView) this.findViewById(R.id.txtDesc);
		btnSubmit = (Button) this.findViewById(R.id.btnSubmit);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnClear = (Button) this.findViewById(R.id.btnClear);

		txtAddress.setText("Address");
		txtTitle.setText("Title");
		txtDesc.setText("Description");
		title.setText(post.getTitle());
		address.setText(post.getAddress());
		description.setText(post.getDescription());

		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				post.setAddress(address.getText().toString());
				post.setTitle(title.getText().toString());
				post.setDescription(description.getText().toString());
				
				sendPost(sendPath, post);

			}

		});

		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				PostEdit.this.finish();
			}

		});

		btnClear.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				address.setText("");
				title.setText("");
				description.setText("");
			}

		});

	}

	/**
	 * Create the options menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_post_edit, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent;
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Toast.makeText(PostEdit.this, "Logged out!", Toast.LENGTH_SHORT)
					.show();
			PostEdit.this.finish();
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Get the current post detail from the server
	 * 
	 * @param path
	 * @param postID
	 * @return the post from the server
	 */
	private Post getPost(String path, String postID) {

		Post data = new Post();
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(path));

		try {
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();

				XMLReader xr = sp.getXMLReader();
				XMLHandlerPost myHandler = new XMLHandlerPost();
				xr.setContentHandler(myHandler);

				xr.parse(new InputSource(response.getEntity().getContent()));

				data = (Post) myHandler.getParsedData();
			} else {
				Toast.makeText(PostEdit.this, "Network Connection Error!",
						Toast.LENGTH_SHORT).show();
			}

		} catch (ClientProtocolException e) {
			Toast.makeText(PostEdit.this, "Client Protocol Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(PostEdit.this, "Illegal State!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(PostEdit.this, "IO Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (SAXException e) {
			Toast.makeText(PostEdit.this, "XML SAX Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Toast.makeText(PostEdit.this, "XML Parser Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return data;

	}

	/**
	 * Send the modified post detail to the server
	 * 
	 * @param path
	 * @param post
	 */
	public void sendPost(String path, Post post) {

		XMLWriter wr = new XMLWriter();
		String xml = wr.writeXmlPost(post, null);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(URI.create(path));

		try {
			StringEntity se = new StringEntity(xml, "UTF-8");
			// se.setContentType("text/xml");
			request.setEntity(se);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Toast.makeText(PostEdit.this, "The post has been updated",
						Toast.LENGTH_LONG).show();
				PostEdit.this.finish();

			} else {
				Toast.makeText(PostEdit.this, "Network Conection Error!",
						Toast.LENGTH_LONG).show();
			}

		} catch (ClientProtocolException e) {
			Toast.makeText(PostEdit.this, "Client Protocol Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(PostEdit.this, "Illegal State!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(PostEdit.this, "IO Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (NullPointerException e) {
			Toast.makeText(PostEdit.this, "Null Pointer Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();

		}

	}

}
