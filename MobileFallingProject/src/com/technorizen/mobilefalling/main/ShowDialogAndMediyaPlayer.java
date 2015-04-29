package com.technorizen.mobilefalling.main;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.technorizen.mobilefalling.main.constant.AppsContants;
import com.technorizen.mobilefalling.main.constant.Progress_Dialog;

public class ShowDialogAndMediyaPlayer extends Activity implements
		LocationListener {
	private MediaPlayer mPlayer;
	private Button yes, no;
	private TextView tvRemainingTime;
	private santimer timer;
	private final long startTime = 50000;
	private final long interval = 1000;
	private String strRecieverName;
	private String strRecieverPhoneNo;
	private boolean smsflag, callflag, emailflag;
	private double latitude;
	private double longitude;
	// for location
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	private Location location;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
	protected LocationManager locationManager;
	private Context mContext;
	private String locationUrl = "";
//	private String strSenderId;
	private String strRecieverEmail;
	private Progress_Dialog progress_Dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//for full screen 
		setContentView(R.layout.show_dialog_and_player);//for layout set on this activity 
		mContext = this;
		getLocation();
		//get data from shared preference
		AppsContants.userDataEntry = getApplicationContext()
				.getSharedPreferences(AppsContants.USERREGISTER,
						AppsContants.Mode);
//		strSenderId = AppsContants.userDataEntry.getString(
//				AppsContants.USERREGISTER, "null");
		strRecieverEmail = AppsContants.userDataEntry.getString(
				AppsContants.RECIEVER_EMAIL, "null");
		strRecieverName = AppsContants.userDataEntry.getString(
				AppsContants.RECIEVER_NAME, "null");
		strRecieverPhoneNo = AppsContants.userDataEntry.getString(
				AppsContants.RECIEVER_MOBILE_NO, "0");
		smsflag = AppsContants.userDataEntry.getBoolean(AppsContants.SMS_FLAG,
				false);
		callflag = AppsContants.userDataEntry.getBoolean(
				AppsContants.CALL_FLAG, false);
		emailflag = AppsContants.userDataEntry.getBoolean(
				AppsContants.EMAIL_FLAG, false);
		if (isMyServiceRunning(MyService.class)) {
			stopService(new Intent(ShowDialogAndMediyaPlayer.this,
					MyService.class));
		}

		yes = (Button) findViewById(R.id.yes_button_player);//find dialog box yes button id
		no = (Button) findViewById(R.id.no_button_player);//find dialog box no button id
		tvRemainingTime = (TextView) findViewById(R.id.tv_remaining_time);
		timer = new santimer(startTime, interval);//create object
		timer.start();//start time
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPlayer.stop();
				mPlayer.release();
				finish();
				AppsContants.checkDialogFlag = false;
				startService(new Intent(ShowDialogAndMediyaPlayer.this,
						MyService.class));

			}
		});

		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mPlayer.isPlaying())
				{
					 mPlayer.reset();
				mPlayer.stop();
				
				mPlayer.release();
				}
//				if(timer.notify())
				timer.cancel();
//				timer.notify();
				// data send here
				AppsContants.checkDialogFlag = false;

				if (smsflag) {
					sendSMS();//for sending sms
				}
				if (callflag) {
					sendCall();//for calling
				}
				if (emailflag) {
//					sendEmail();	//for sending email				
				}
				finish();
			}
		});
	}

	public void sendSMS() {//send sms with location
		// https://www.google.co.in/maps/place/23.1689986,75.7972204
		locationUrl = "https://www.google.co.in/maps/place/" + latitude + ","
				+ longitude;
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(strRecieverPhoneNo, null,
					"Has suffered a fall and is at this location " + locationUrl, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
					Toast.LENGTH_LONG).show();
			// emptyAllFields();
			finish();

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}

	public void sendCall() {//for calling

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + strRecieverPhoneNo));
		try {
			startActivity(callIntent);
			Log.i("Finished making a call...", "");
			Toast.makeText(ShowDialogAndMediyaPlayer.this, "making a call...",
					Toast.LENGTH_SHORT).show();

			finish();
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(ShowDialogAndMediyaPlayer.this,
					"Call faild, please try again later.", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/*public void sendEmail() {//for sending email
		if (NetConnection.isConnected(mContext)) {
			if (strRecieverEmail != null) {
//				new SendEmailAsynctask().execute();//create new thread for sending email
				finish();
			} else {
				Toast.makeText(mContext,
						"Please Set Reciever Email", 2000).show();
			}
		} else {
			Toast.makeText(mContext,
					"Please Check Your NetConnection", 2000).show();
		}
	}*/

	/*public class SendEmailAsynctask extends AsyncTask<Void, Void, Void> {//sending email

		String result = "", web_response = "";
		boolean isError = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress_Dialog = new Progress_Dialog(mContext);
			progress_Dialog.dialog_show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				locationUrl = "https://www.google.co.in/maps/place/" + latitude
						+ "," + longitude;
				HttpPost post = new HttpPost(HttpUrlPath.urlPath
						+ "action=send_mail");
				// http://www.technorizen.com/WORKSPACE3/MOBILE/process/process.php?action=send_mail&user_id=12&message=sdfsdf&email=rakeshdongre9@gmail.com
				HttpClient client = new DefaultHttpClient();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						4);
				nameValuePairs.add(new BasicNameValuePair("user_id",
						strSenderId));
				nameValuePairs.add(new BasicNameValuePair("message",
						locationUrl));
				nameValuePairs.add(new BasicNameValuePair("email",
						strRecieverEmail));
//add all parameters for sending email
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = client.execute(post);
				web_response = EntityUtils.toString(response.getEntity());
				Log.e("", "web_response send mail= " + web_response);
				if (!web_response.contains("data not found.")) {
					
				}
			} catch (Exception e) {

				Log.e("", "in asynctask error= " + e);
				isError = true;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!isError) {				
				Toast.makeText(mContext, "sent Mail Successfully", 2000).show();
			} else {
				Toast.makeText(mContext, "please send again!", 2000).show();

			}
			try{
			progress_Dialog.dialog_dismiss();//progress dialog close 
			}catch(Exception e)
			{e.printStackTrace();}
			
                finish();
		}
	}*/

	private boolean isMyServiceRunning(Class<?> serviceClass) {//find service is active or not
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public void stopPlayer() {//for player stop
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.stop();
		}
	}

	private class santimer extends CountDownTimer {//time create here

		public santimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);

		}

		@Override
		public void onFinish() {
			tvRemainingTime.setText("done!");
			// if(mPlayer.isPlaying())

			AppsContants.checkDialogFlag = false;
			timer.cancel();
			try {
				mPlayer.stop();
				mPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (smsflag) {
				sendSMS();
			}
			if (callflag) {
				sendCall();

			}
			if (emailflag) {
//				sendEmail();
			}
//			finish();
			// Toast.makeText(getApplicationContext(), "data sending here",
			// 2000).show();
			// data send here
		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvRemainingTime
					.setText("Time remain:" + millisUntilFinished / 1000);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();//after paush call this method and set all settings
		if (isMyServiceRunning(MyService.class)) {
			stopService(new Intent(ShowDialogAndMediyaPlayer.this,
					MyService.class));
		}
		mPlayer = MediaPlayer.create(this, R.raw.test);
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setLooping(true);
		if (mPlayer.isPlaying()) {

			mPlayer.stop();
			mPlayer.release();
		}

		mPlayer.start();

	}

	@Override
	protected void onPause() {//if going to background activity then call
		super.onPause();
		try {
			 if(mPlayer.isPlaying())
			{
				 mPlayer.reset();
				mPlayer.stop();
				mPlayer.release();
			}
				AppsContants.checkDialogFlag = false;
				startService(new Intent(ShowDialogAndMediyaPlayer.this,
						MyService.class));
				finish();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {//for back button press of device then call
		super.onBackPressed();
		try {
			// if(mPlayer.isPlaying())
			{
				mPlayer.stop();
				mPlayer.release();
			}
			AppsContants.checkDialogFlag = false;
			finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onLocationChanged(Location location) {//my current position change then call
		latitude = location.getLatitude();
		longitude = location.getLongitude();

	}

	public Location getLocation() {//get my current location via gps
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
				/*Log.e("", "latitude= " + latitude + " longitude= " + longitude);
				Toast.makeText(getApplicationContext(),
						"latitude= " + latitude + " longitude= " + longitude,
						2000).show();*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {// unimplemented methods
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {// unimplemented methods
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {// unimplemented methods
		// TODO Auto-generated method stub

	}

}
