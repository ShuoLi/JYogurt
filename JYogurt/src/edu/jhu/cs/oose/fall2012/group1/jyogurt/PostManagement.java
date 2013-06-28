package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.ListAdapter;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * The page for managing the posts by UserID
 * 
 * @author Zaoxing Liu
 * 
 */
public class PostManagement extends ListActivity {

	/**
	 * 
	 */
	public final String CATE_PATH = "user";
	public final String SPEC_PATH = "get_posts.jsp";
	public final String[] pathes = { CATE_PATH, SPEC_PATH };
	private HashMap<String, String> paras = new HashMap<String, String>();

	private List<Post> postList;
	private String category;
	private String jhed;

	/**
	 * Show the display components in the beginning (All the posts in the list)
	 */
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();

		jhed = bundle.getString("user");
		category = bundle.getString("category");
		paras.put("jhed", jhed);
		String path = SettingUtil.append(pathes, paras).toString();
		postList = getListbyID(path);

		ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this,
				R.layout.list_row, R.id.txtTitle, postList);
		setListAdapter(adapter);

		setListAdapter(new ListAdapter(this, postList));
	}

	/**
	 * Select an option item
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.delete:

			return true;

		case R.id.menu_settings:
			Toast.makeText(PostManagement.this, "Logged out!",
					Toast.LENGTH_SHORT).show();
			PostManagement.this.finish();
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * The actions when click a list item
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(PostManagement.this, "Post Detail showed",
				Toast.LENGTH_LONG).show();

		// postList.get(position).getPostId();
		Intent intent = new Intent(this, PostDetail.class);
		String postId = postList.get(position).getPostId();
		Bundle bundle = new Bundle();
		bundle.putString("POSTID", postId);
		bundle.putString("category", category);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * The function to create an options menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_post_management, menu);
		return true;
	}

	/**
	 * The function for getting a user's post list
	 * 
	 * @param path
	 * @return The post list
	 */
	@SuppressWarnings("unchecked")
	private List<Post> getListbyID(String path) {
		List<Post> data = null;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(path));
		data = (List<Post>) new HttpConn().fetchDataFromServer(PostManagement.this,
				"List", client, request);

		return data;
	}
}
