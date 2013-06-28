package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.URI;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;

import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLWriter;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.provider.MediaStore;

/**
 * The class for creating a new post and upload an image from android side
 * 
 * @author Hang
 * 
 */
@SuppressWarnings("unused")
public class NewPost extends Activity {

	private EditText title;
	private EditText address;
	private EditText description;
	private Button btnSubmit;
	private Button btnClear;
	private Button btnCancel;
	private ImageButton loadImage;
	private ImageButton deleteImage;
	private Spinner spinPostType;
	private Spinner spinloadedImages;
	private String postType;
	private static ArrayList<String> images = new ArrayList<String>();
	private Post post;
	private String jhed;

	public final String CATE_BUY_PATH = "buying";
	public final String CATE_SALE_PATH = "selling";
	public final String CATE_HOUSING_PATH = "housing";
	private HashMap<String, String> paras = new HashMap<String, String>();
	public final String SPEC_PATH = "create.jsp";
	public final String[] BUY_PATH = { CATE_BUY_PATH, SPEC_PATH };
	public final String[] SALE_PATH = { CATE_SALE_PATH, SPEC_PATH };
	public final String[] HOUSING_PATH = { CATE_HOUSING_PATH, SPEC_PATH };

	private final static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);
		// Show the Up button in the action bar.
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		jhed = bundle.getString("user");

		title = (EditText) this.findViewById(R.id.title);
		address = (EditText) this.findViewById(R.id.address);
		description = (EditText) this.findViewById(R.id.description);
		btnSubmit = (Button) this.findViewById(R.id.btnSubmit);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnClear = (Button) this.findViewById(R.id.btnClear);
		spinPostType = (Spinner) this.findViewById(R.id.spinPostType);
		loadImage = (ImageButton) this.findViewById(R.id.loadImage);
		deleteImage = (ImageButton) this.findViewById(R.id.deleteImage);
		spinloadedImages = (Spinner) this.findViewById(R.id.spinloadedImages);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.post_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinPostType.setAdapter(adapter);

		loadImage.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
				/*
				 * Intent intent2 = new Intent(); intent2.setType("image/*");
				 * intent2.setAction(Intent.ACTION_GET_CONTENT);
				 * startActivityForResult(intent2, 1); //
				 */
			}
		});

		deleteImage.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				String deleteImage = spinloadedImages.getSelectedItem()
						.toString();
				String deleteImagePath = null;
				for (String imagePath : images) {
					String[] ss = imagePath.split("/");
					if (ss[ss.length - 1].equals(deleteImage)) {
						deleteImagePath = imagePath;
						break;
					}
				}
				if (deleteImagePath != null) {
					images.remove(deleteImagePath);
					renewLoadedImageSpinner();
				}
			}
		});

		btnSubmit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				post = new Post();
				post.setAddress(title.getText().toString());
				post.setTitle(address.getText().toString());
				post.setDescription(description.getText().toString());

				post.setJhed(jhed);// */
				/*
				 * HashMap<String, byte[]> ims = new HashMap<String, byte[]>();
				 * for (String imagePath : images) { String[] ss =
				 * imagePath.split("/"); String imageName = ss[ss.length - 1];
				 * ims.put(imageName, convertBitmapToByteArray(BitmapFactory
				 * .decodeFile(imagePath))); }
				 */

				postType = spinPostType.getSelectedItem().toString();
				if (postType.equals("buying post")) {
					post.setCategory("Buying");
					sendPost(SettingUtil.append(BUY_PATH).toString(),
							"BuyingPost", post, null);
				} else if (postType.equals("housing post")) {
					post.setCategory("Housing");
					sendPost(SettingUtil.append(HOUSING_PATH).toString(),
							"HousingPost", post, null);
				} else if (postType.equals("selling post")) {
					post.setCategory("Selling");
					sendPost(SettingUtil.append(SALE_PATH).toString(),
							"SellingPost", post, null);
				}

				for (String imagePath : images) {
					String[] pathes = { "servlet", "Upload" };
					HashMap<String, String> paras = new HashMap<String, String>();
					String[] temp = imagePath.split("/");
					String temp2 = temp[temp.length - 1].substring(0,
							temp[temp.length - 1].lastIndexOf("."));
					System.out.println(temp2);
					paras.put("name", temp2);
					paras.put("postname", post.getTitle());
					paras.put("posttype", post.getCategory());
					Uri uri = SettingUtil.append(pathes, paras);
					uploadFile(temp2, imagePath, uri.toString());
				}
			}

		});

		btnClear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				title.setText("");
				address.setText("");
				description.setText("");
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				NewPost.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_post, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& data != null) {
			Uri selectedImage = (Uri) data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			// ContentResolver cr = this.getContentResolver();
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			images.add(picturePath);
			cursor.close();
			renewLoadedImageSpinner();

			/*
			 * try { Bitmap bitmap =
			 * BitmapFactory.decodeStream(cr.openInputStream(uri));
			 * images.put(this.convertBitmapToByteArray(bitmap)); String[] items
			 * = new String[images.size()]; for(int i = 0 ; i < items.length ;
			 * i++){ items[i] = } ArrayAdapter<CharSequence> adapter = new
			 * ArrayAdapter<CharSequence>(this,
			 * android.R.layout.simple_spinner_item, items);
			 * adapter.setDropDownViewResource
			 * (android.R.layout.simple_spinner_dropdown_item);
			 * spinloadedImages.setAdapter(adapter);
			 * 
			 * 
			 * } catch (FileNotFoundException e) { Log.e("Exception",
			 * e.getMessage(),e); } //
			 */

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void renewLoadedImageSpinner() {
		if (images.size() > 0) {
			int count = 0;
			String[] items = new String[images.size()];
			for (String s : images) {
				items[count++] = s;
			}
			// String[] items = images.toArray(new String[images.size()]);

			for (int i = 0; i < items.length; i++) {
				String[] ss = items[i].split("/");
				items[i] = ss[ss.length - 1];

			}

			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
					this, android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinloadedImages.setAdapter(adapter);
		} else {
			String[] items = new String[1];
			items[0] = "";
			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
					this, android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinloadedImages.setAdapter(adapter);
		}
	}

	/**
	 * The function for sending a new post to the server
	 * 
	 * @param path
	 * @param category
	 * @param post
	 * @param images
	 * @return
	 */
	private boolean sendPost(String path, String category, Post post,
			HashMap<String, byte[]> images) {

		XMLWriter wr = new XMLWriter();
		String xml = wr.writeXmlCreatePost(post, category, images);
		HttpPost request = new HttpPost(URI.create(path));

		return new HttpConn().SendRequestWithXML(NewPost.this, xml, request);
	}

	@SuppressWarnings("deprecation")
	private void uploadFile(String imageName, String filepath, String urlpath) {

		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(urlpath);
		File file = new File(filepath);

		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(file, "image/jpeg");
		mpEntity.addPart("userfile", cbFile);

		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			// System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println(EntityUtils.toString(resEntity));
			}
			if (resEntity != null) {
				resEntity.consumeContent();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();

	}

	private void showDialog(String mess) {
		new AlertDialog.Builder(NewPost.this)
				.setTitle("Message")
				.setMessage(mess)
				.setNeutralButton("comfirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}

	private byte[] convertBitmapToByteArray(Bitmap bitmap) {
		int size = bitmap.getRowBytes() * bitmap.getHeight();
		ByteBuffer b = ByteBuffer.allocate(size);
		bitmap.copyPixelsToBuffer(b);
		byte[] bytes = new byte[size];
		try {
			b.get(bytes, 0, bytes.length);
		} catch (BufferUnderflowException e) {
		}
		return bytes;
	}
}
