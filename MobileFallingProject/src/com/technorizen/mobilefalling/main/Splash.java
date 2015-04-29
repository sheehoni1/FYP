package com.technorizen.mobilefalling.main;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.technorizen.mobilefalling.main.constant.AppsContants;
public class Splash extends Activity {
	
//	String LoginValue;
private ImageLoader imageLoader;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	      StrictMode.setThreadPolicy(policy);
	  imageLoader = ImageLoader.getInstance();
	  
	  ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
	    getApplicationContext()).threadPoolSize(4).build();
	  imageLoader.init(config);
	  
//	 create object for getting data from sharedpreference 
//	  AppsContants.userDataEntry =getApplicationContext().getSharedPreferences(AppsContants.USERREGISTER, AppsContants.Mode);
//		LoginValue = AppsContants.userDataEntry.getString(AppsContants.LoginType,  "0");//get value from sharedpreference
		
		new Thread(new Runnable() {
			@Override
			public void run() {//after 3secend call login screen/home screen
				try {
					Thread.sleep(3000);
					try {
					
						{
							Intent i = new Intent(getApplicationContext(),
									SensorTestActivity.class);
								startActivity(i);
								finish();
						}
						
					} catch (Exception e) {
						e.printStackTrace();

					}
					finish();
				} catch (Exception e) {
					System.out.println("$$$Exception=" + e);
				}
			}
		}).start();
	}

	

}
