package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.net.URI;
import org.apache.http.client.methods.HttpPost;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.HttpConn;
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
import android.widget.TextView;
import android.widget.Toast;
/**
 * The activity for changing the user's password
 * @author Zaoxing Liu
 *
 */
public class ChangePwdActivity extends Activity {

	EditText editPwd;
	EditText editNewPwd;
	EditText editNewPwdConfirm;
	Button btnSubmit;
	Button btnClear;
	TextView txtPwd;
	TextView txtNewPwd;
	TextView txtNewPwdConfirm;

	private String pwd;
	private String newPwd;
	private String newPwdConfirm;
	private String jhed;

	public final String CATE_PATH = "user";
	public final String UPDATE_PATH = "update_password.jsp";
	public final String[] pathes = { CATE_PATH, UPDATE_PATH };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);

		Intent intentThis = this.getIntent();
		Bundle bund = intentThis.getExtras();
		jhed = bund.getString("user");

		editPwd = (EditText) this.findViewById(R.id.editCurrentPwd);
		editNewPwd = (EditText) this.findViewById(R.id.editNewPwd);
		editNewPwdConfirm = (EditText) this.findViewById(R.id.editNewPwd2);
		btnSubmit = (Button) this.findViewById(R.id.btnSubmit);
		btnClear = (Button) this.findViewById(R.id.btnClear);
		txtPwd = (TextView) this.findViewById(R.id.txtPwd);
		txtNewPwd = (TextView) this.findViewById(R.id.txtNewPwd);
		txtNewPwdConfirm = (TextView) this.findViewById(R.id.txtNewPwd2);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pwd = editPwd.getText().toString().trim();
				newPwd = editNewPwd.getText().toString().trim();
				newPwdConfirm = editNewPwdConfirm.getText().toString().trim();
				if (newPwd.equals(newPwdConfirm)) {
					if (changePwd(jhed, pwd, newPwd)) {
						Toast.makeText(ChangePwdActivity.this,
								"The password has been changed",
								Toast.LENGTH_LONG).show();

					} else {
						Toast.makeText(ChangePwdActivity.this, "Current Password is not correct or other errors!",
								Toast.LENGTH_LONG).show();

					}
				} else {
					Toast.makeText(ChangePwdActivity.this,
							"Password not match, please try again!",
							Toast.LENGTH_LONG).show();
				}

			}

		});

		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				editPwd.setText("");
				editNewPwd.setText("");
				editNewPwdConfirm.setText("");
			}

		});

	}

	private boolean changePwd(String jhed, String oldPwd, String newPwd) {

		XMLWriter wr = new XMLWriter();
		String xml = wr.writeXmlResetPwd(jhed, oldPwd, newPwd);
		HttpPost request = new HttpPost(URI.create(SettingUtil.append(pathes)
				.toString()));

		return new HttpConn()
				.SendRequestWithXML(ChangePwdActivity.this, xml, request);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_change_pwd, menu);
		return true;
	}

}
