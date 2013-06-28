package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.R.id;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.dev.util.CryptoBASE64;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.AsyncImageLoader;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.CallbackImpl;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Class for displaying the post detail
 * 
 * @author Zaoxing Liu
 * 
 */
@TargetApi(14)
public class PostDetail extends Activity {

	private String postId;
	private TextView title;
	private TextView author;
	private TextView email;
	private TextView phonenumber;
	private TextView description;
	private TextView address;
	private ImageView viewImage;
	private ImageView viewImage2;
	private ImageView viewImage3;
	private Post post;
	private String jhed;
	private String authorId;
	private String category;
	private AsyncImageLoader loader;

	public final String CATE_BUY_PATH = "buying";
	public final String CATE_SALE_PATH = "selling";
	public final String CATE_HOUSING_PATH = "housing";
	private HashMap<String, String> paras = new HashMap<String, String>();
	public final String SPEC_PATH = "get_post.jsp";
	public final String[] BUY_PATH = { CATE_BUY_PATH, SPEC_PATH };
	public final String[] SALE_PATH = { CATE_SALE_PATH, SPEC_PATH };
	public final String[] HOUSING_PATH = { CATE_HOUSING_PATH, SPEC_PATH };

	/**
	 * Showing the display components in the beginning
	 */
	public void onCreate(Bundle savedInstanceState) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		postId = bundle.getString("POSTID");
		jhed = bundle.getString("user");
		category = bundle.getString("category");
		setContentView(R.layout.activity_post_detail);
		try {
			paras.put("id", CryptoBASE64.encrypt(SettingUtil.getKey(), postId));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String path = "";
		if (category.equals("HousingPostList")) {
			path = SettingUtil.append(HOUSING_PATH, paras).toString();
			System.out.println(path);
		} else if (category.equals("BuyingPostList")) {
			path = SettingUtil.append(BUY_PATH, paras).toString();
		} else if (category.equals("SellingPostList")) {
			path = SettingUtil.append(SALE_PATH, paras).toString();
		}
		
		loader = new AsyncImageLoader();
		post = getPost(path);

		title = (TextView) this.findViewById(R.id.title);
		author = (TextView) this.findViewById(R.id.author);
		email = (TextView) this.findViewById(R.id.email);
		phonenumber = (TextView) this.findViewById(R.id.phonenumber);
		description = (TextView) this.findViewById(R.id.description);
		address = (TextView) this.findViewById(R.id.address);
		viewImage = (ImageView) this.findViewById(R.id.viewImage);
		viewImage2 = (ImageView) this.findViewById(id.viewImage2);
		viewImage3 = (ImageView) this.findViewById(id.viewImage3);

		title.setText(post.getTitle());
		author.setText("Author: " + post.getAuthor());
		email.setText("Email: " + post.getEmail());
		phonenumber.setText("Phone Number: " + post.getPhoneNumber());
		description.setText(post.getDescription());
		address.setText("Address: " + post.getAddress());
		authorId = post.getJhed();
		//View.INVISIBLE
		//viewImage.setVisibility(View.INVISIBLE);
		//viewImage2.setVisibility(View.INVISIBLE);
		//viewImage3.setVisibility(View.INVISIBLE);
		/**
		 * Notice, here because the gallery is not supported, we just add three ImageView in the layout
		 * And we don't know why when we add arraylist in Post, the XMLHandlerPost couldn't store the image
		 * url into arraylist and the only thing it can store is String[] 
		 */
		//List<String> imageurl = post.getImageUrl();
		String[] images = post.getImages();
		
		
		if(images != null && images.length != 0){
			//System.out.println("*******************"+imageurl.get(0));
			
			CallbackImpl callbackImpl=new CallbackImpl(viewImage);
			loader.loadDrawable(images[0], callbackImpl);
			Drawable Imagecache=loader.loadDrawable(images[0], callbackImpl);
		    if(Imagecache!=null){
	            viewImage.setImageDrawable(Imagecache);
	            viewImage.setVisibility(View.VISIBLE);
	        }
			
			if(images.length > 1){
				callbackImpl = new CallbackImpl(viewImage2);
				loader.loadDrawable(images[1], callbackImpl);
				Imagecache = loader.loadDrawable(images[1], callbackImpl);
			    if(Imagecache!=null){
		            viewImage2.setImageDrawable(Imagecache);
		            viewImage2.setVisibility(View.VISIBLE);
		        }
			}
			
			if(images.length > 2){
				callbackImpl=new CallbackImpl(viewImage3);
				loader.loadDrawable(images[2], callbackImpl);
				Imagecache=loader.loadDrawable(images[2], callbackImpl);
			    if(Imagecache!=null){
		            viewImage3.setImageDrawable(Imagecache);
		            viewImage3.setVisibility(View.VISIBLE);
		        }
			}
		}

		/*try {
			viewImage.setImageBitmap(loadImageFromUrl(SettingUtil.getServer()
					+ "/images/housing/" + post.getPostId() + ".jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//*/
		
		/*try {
			if( post.getImage() != null) {
				CallbackImpl callbackImpl=new CallbackImpl(viewImage);
				loader.loadDrawable(post.getImage(), callbackImpl);
				Drawable Imagecache=loader.loadDrawable(imageurl.get(0), callbackImpl);
				//viewImage.setImageBitmap(loadImageFromUrl(post.getImage()));
				viewImage.setVisibility(View.VISIBLE);
				if(Imagecache!=null){
		            viewImage.setImageDrawable(Imagecache);
				}
			}//*/
		/*} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//*/
	}

	/**
	 * Create the options menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_post_detail, menu);
		return true;
	}

	/**
	 * Click an options
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		@SuppressWarnings("unused")
		Intent intent;
		Bundle bundle;
		switch (item.getItemId()) {

		case R.id.callPhone:
			// Single menu item is selected do something
			Toast.makeText(PostDetail.this, "Calling......", Toast.LENGTH_SHORT)
					.show();
			intent = new Intent(this, PostEdit.class);
			bundle = new Bundle();

			String uri = "tel:" + post.getPhoneNumber().trim();
			Intent intentCall = new Intent(Intent.ACTION_CALL);
			intentCall.setData(Uri.parse(uri));
			startActivity(intentCall);

			return true;

		case R.id.messageManagement:
			Toast.makeText(PostDetail.this, "Message Management",
					Toast.LENGTH_SHORT).show();
			intentCall = new Intent(this, MessageManagement.class);
			bundle = new Bundle();
			bundle.putString("user", jhed);
			bundle.putString("touser", authorId);
			intentCall.putExtras(bundle);
			startActivity(intentCall);

			return true;

		case R.id.message:
			// Single menu item is selected do something
			Toast.makeText(PostDetail.this, "Messaging is preparing...",
					Toast.LENGTH_SHORT).show();
			intentCall = new Intent(this, NewMessage.class);
			bundle = new Bundle();
			// bundle.putString("POSTID", postId);
			bundle.putString("user", jhed);
			bundle.putString("touser", authorId);
			intentCall.putExtras(bundle);
			startActivity(intentCall);

			return true;

		case R.id.edit:
			// Single menu item is selected do something
			if (jhed.equals(post.getJhed())) {

				Toast.makeText(PostDetail.this, "Edit function started",
						Toast.LENGTH_SHORT).show();
				intentCall = new Intent(this, PostEdit.class);
				bundle = new Bundle();
				bundle.putString("POSTID", postId);
				bundle.putString("user", jhed);
				bundle.putString("category", category);

				intentCall.putExtras(bundle);
				startActivity(intentCall);
			} else {
				Toast.makeText(PostDetail.this, "This is not your post!",
						Toast.LENGTH_SHORT).show();

			}

			return true;

		case R.id.menu_settings:
			Toast.makeText(PostDetail.this, "Logged out!", Toast.LENGTH_SHORT)
					.show();
			PostDetail.this.finish();
			intentCall = new Intent(this, LoginActivity.class);
			startActivity(intentCall);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Use XML handler(XMLHandlerPost) to parse the XML file
	 * 
	 * @param path
	 * @param postID
	 * @return
	 */
	private Post getPost(String path) {
		//Post data = ne;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URI.create(path));

		Post data = (Post) new HttpConn().fetchDataFromServer(PostDetail.this,
				"Post", client, request);

		return data;

	}

	/**
	 * Read the inputstream and return the string value
	 * 
	 * @param is
	 *            InputStream
	 * @return string value
	 */
	@SuppressWarnings("unused")
	private String readStream(InputStream is) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = is.read();
			while (i != -1) {
				bo.write(i);
				i = is.read();
			}
			return bo.toString();
		} catch (IOException e) {
			return "";
		}
	}

	@SuppressWarnings("unused")
	private Bitmap loadImageFromUrl(String url) throws Exception {
		final DefaultHttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		HttpResponse response = client.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			Toast.makeText(PostDetail.this, "Request URL failed, error code =" + statusCode,
					Toast.LENGTH_SHORT).show();
		}

		HttpEntity entity = response.getEntity();
		if (entity == null) {
			Toast.makeText(PostDetail.this, "Http Entity is NULL!",
					Toast.LENGTH_SHORT).show();;
		}
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int readBytes = -1;
			while ((readBytes = is.read(buf)) != -1) {
				baos.write(buf, 0, readBytes);
			}
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		byte[] imageArray = baos.toByteArray();
		return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
	}
}
