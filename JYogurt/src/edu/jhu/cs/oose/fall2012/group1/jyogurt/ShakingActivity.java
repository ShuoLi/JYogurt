package edu.jhu.cs.oose.fall2012.group1.jyogurt;

import java.util.Random;
import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * The activity for shake function.
 * @author Zaoxing Liu (Alan)
 *
 */

public class ShakingActivity extends Activity implements SensorEventListener {
	private TextView tv;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private String jhed;
	private String category;

	@SuppressWarnings("unused")
	private ImageButton imgShake;
	 private static final int DIALOG_ALERT = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shaking);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		jhed = bundle.getString("user");
		//category = bundle.getString("category");

		// iv_shake = (ImageView) findViewById(R.id.iv_shake);
		tv = (TextView) findViewById(R.id.showsensor);
		imgShake = (ImageButton) findViewById(R.id.imageShake);
		tv.setText("Please shake your phone to get a lucky post");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
	}

	@Override
	protected void onPause() {

		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do nothing.
	}

	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
					.abs(values[2]) > 17)) {
				Random ran = new Random();
				int k = 3+ran.nextInt(10);
				String Id = Integer.toString(k);
				category = "BuyingPostList";
				Intent intent = new Intent(this, PostDetail.class);
				Bundle bundle = new Bundle();
				bundle.putString("POSTID", Id);
				bundle.putString("category", category);
				bundle.putString("user", jhed);
				intent.putExtras(bundle);
				vibrator.vibrate(500);
				startActivity(intent);
				//showDialog(DIALOG_ALERT);
				
			}
		}
	}
	
	/**
	 * The function for creating the dialog
	 */
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_ALERT:
	      // Create out AlterDialog
	      Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage("This will end the activity");
	      builder.setCancelable(true);
	      builder.setPositiveButton("I agree", new OkOnClickListener());
	      builder.setNegativeButton("No, no", new CancelOnClickListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	    }
	    return super.onCreateDialog(id);
	  }
	
	/**
	 * The class for cancellation listener
	 * @author Alan
	 *
	 */
	  private final class CancelOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      Toast.makeText(getApplicationContext(), "Activity will continue",
	          Toast.LENGTH_LONG).show();
	    }
	  }
	  /**
	   * The class for Onclick listener
	   * @author Alan
	   *
	   */
	  private final class OkOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      ShakingActivity.this.finish();
	    }
	  }
	  
	  
	  
}