package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.net.URI;
import java.util.List;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.ListAdapter;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;

/**
 * View all the posts in the list
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@TargetApi(14)
public class ViewAll extends ListActivity {

	private List<Post> postList;
	private String category;
	private String path;
	private String jhed;

	public final String CATE_BUY_PATH = "buying";
	public final String CATE_SALE_PATH = "selling";
	public final String CATE_HOUSING_PATH = "housing";
	public final String SPEC_PATH = "get_post_list.jsp";
	public final String[] BUY_PATH = { CATE_BUY_PATH, SPEC_PATH };
	public final String[] SALE_PATH = { CATE_SALE_PATH, SPEC_PATH };
	public final String[] HOUSING_PATH = { CATE_HOUSING_PATH, SPEC_PATH };

	/**
	 * Show the display components in the beginning (All the posts in the list)
	 */
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all);
		((PullToRefreshListView) getListView())
				.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh() {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
				});

		Intent intentThis = this.getIntent();
		Bundle bund = intentThis.getExtras();
		jhed = bund.getString("user");
		category = bund.getString("category");
		if (category.equals("HousingPostList")) {
			path = SettingUtil.append(HOUSING_PATH).toString();
		} else if (category.equals("BuyingPostList")) {
			path = SettingUtil.append(BUY_PATH).toString();
		} else if (category.equals("SellingPostList")) {
			path = SettingUtil.append(SALE_PATH).toString();
		}
		postList = getList(category, path);

		ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this,
				R.layout.list_row, R.id.txtTitle, postList);
		setListAdapter(adapter);

		setListAdapter(new ListAdapter(this, postList));
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(ViewAll.this, "Post Detail showed", Toast.LENGTH_LONG)
				.show();

		Intent intent = new Intent(this, PostDetail.class);
		String postId = postList.get(position - 1).getPostId();
		Bundle bundle = new Bundle();
		bundle.putString("POSTID", postId);
		bundle.putString("user", jhed);
		bundle.putString("category", category);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * Show the option menu in the beginning
	 */

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_all, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent;
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Toast.makeText(ViewAll.this, "Logged out!", Toast.LENGTH_SHORT)
					.show();
			ViewAll.this.finish();
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Get the post list from the Server
	 * 
	 * @param Post
	 *            Category (Housing or Trading)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Post> getList(String Category, String path) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(path));

		 HttpConn hc = new HttpConn();
		List<Post> data = (List<Post>) hc.fetchDataFromServer(ViewAll.this, "List",
				client, request);

		return data;
	}

	/**
	 * Get the sync data from the server
	 * 
	 * @author Alan
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, List<Post>> {

		@Override
		protected List<Post> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				;
			}
			// postList = getList(category, path);
			return postList;
		}

		/**
		 * Execute the post
		 */
		@Override
		protected void onPostExecute(List<Post> result) {
			postList = getList(category, path);

			// Call onRefreshComplete when the list has been refreshed.
			((PullToRefreshListView) getListView()).onRefreshComplete();

			super.onPostExecute(result);
		}
	}

}
