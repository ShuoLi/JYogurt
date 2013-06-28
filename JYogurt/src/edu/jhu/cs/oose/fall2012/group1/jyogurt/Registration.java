package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.SettingUtil;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.User;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.XMLWriter;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The page to register a new user account
 * 
 * @author Zaoxing Liu (Alan)
 * 
 */
@TargetApi(14)
public class Registration extends Activity {

	private EditText lname;
	private EditText fname;
	private EditText password;
	private EditText address;
	private EditText email;
	private EditText phone;
	private EditText jhed;
	private EditText hopkinsid;

	private Button btnSubmit;
	private Button btnCancel;

	private User user;

	public final String CATE_PATH = "user";
	public final String SPEC_PATH = "user_register.jsp";
	public final String[] pathes= {CATE_PATH,SPEC_PATH};

	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		lname = (EditText) this.findViewById(R.id.editLname);
		fname = (EditText) this.findViewById(R.id.editFname);
		password = (EditText) this.findViewById(R.id.editPwd);
		address = (EditText) this.findViewById(R.id.editAddress);
		email = (EditText) this.findViewById(R.id.editEmail);
		phone = (EditText) this.findViewById(R.id.editPhone);
		jhed = (EditText) this.findViewById(R.id.editJhed);
		hopkinsid = (EditText) this.findViewById(R.id.editHopkins);

		btnSubmit = (Button) this.findViewById(R.id.btnSubmit);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);

		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(Registration.this, "Please wait......",
						Toast.LENGTH_LONG).show();
				user = new User();
				String lnameTmp = lname.getText().toString();
				String fnameTmp = fname.getText().toString();
				String addressTmp = address.getText().toString();
				String jhedTmp = jhed.getText().toString();
				String hopkinsTmp = hopkinsid.getText().toString();
				String emailTmp = email.getText().toString();
				String phoneTmp = phone.getText().toString();
				String pwdTmp = password.getText().toString();

				user.setLname(lnameTmp);
				user.setFname(fnameTmp);
				user.setAddress(addressTmp);
				user.setJhed(jhedTmp);
				user.setHopkinsId(hopkinsTmp);
				user.setEmail(emailTmp);
				user.setPhoneNumber(phoneTmp);
				user.setPassword(pwdTmp);

				if (sendReg(user)) {
					Toast.makeText(Registration.this,
							"The registration info has been sent!",
							Toast.LENGTH_LONG).show();
					Registration.this.finish();
				} else{
					Toast.makeText(Registration.this,
							"Registration failed!!!!!",
							Toast.LENGTH_LONG).show();
					
				}

			}

		});

		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Registration.this.finish();
			}

		});
	}

	/**
	 * The function for creating an option menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_registration, menu);
		return true;
	}

	/**
	 * Send the registration info to the server
	 * 
	 * @return succeed or fail
	 */
	private boolean sendReg(User user) {

		XMLWriter wr = new XMLWriter();
		String xml = wr.writeXmlUser(user);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(URI.create(SettingUtil.append(pathes).toString()));
		try {
			StringEntity se = new StringEntity(xml, "UTF-8");
			request.setEntity(se);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return true;
			}else{
				return false;
			}

		} catch (NullPointerException e) {
			Toast.makeText(Registration.this, "Null Pointer Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			Toast.makeText(Registration.this, "Client Protocol Error!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(Registration.this, "Illegal State!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(Registration.this, "IO Error!", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

		return false;
	}

}
