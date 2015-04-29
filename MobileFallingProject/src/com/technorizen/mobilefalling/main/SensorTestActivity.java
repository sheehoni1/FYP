package com.technorizen.mobilefalling.main;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SensorTestActivity extends Activity {

private ImageView ivServiceStart;
private boolean checkResource=false;
Context context;
private TextView tvStartStopService;
private TextView tvSetting;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_senser_test);
    context=this;
  //find all views id 
    ivServiceStart=(ImageView) findViewById(R.id.iv_service_start);
    tvSetting=(TextView) findViewById(R.id.name_setting); 
    tvStartStopService=(TextView) findViewById(R.id.tv_set_start_or_stop_service);
    
       ivServiceStart.setOnClickListener(new OnClickListener() {//get listener of service button click
		@Override
		public void onClick(View v) {
			if(checkResource==false)//start
			 {
				 checkResource=true;
				 ivServiceStart.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_off));
				 startService(new Intent(SensorTestActivity.this, MyService.class));//start service
				 tvStartStopService.setText("Stop Service");
			 }
			 else//stop
			 {
				 
				 ivServiceStart.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_on));
				 checkResource=false;
				 stopService(new Intent(SensorTestActivity.this, MyService.class)); //stop service
				 tvStartStopService.setText("Start Service");
			 }
		}
	});
       
    tvSetting.setOnClickListener(new OnClickListener() {//get listener of setting button click
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(SensorTestActivity.this,SettingScreenActivity.class));//navigate setting screen
			
		}
	});
    
  }

 private boolean isMyServiceRunning(Class<?> serviceClass) {//find service is playing or not
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
  @Override
  protected void onResume() {//after pauseing this app start from here,set text and service start/stop
    super.onResume();
    if(isMyServiceRunning(MyService.class))//for service running then
    {
 	   checkResource=true;
 	   ivServiceStart.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_off));//set button background image
 	   tvStartStopService.setText("Stop Service");//set text
    }
   else
    {
 	   checkResource=false;
 	   tvStartStopService.setText("Start Service");//set text
 	   ivServiceStart.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_on));//set button background image
    }
   
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
	public void onBackPressed() {//on press back button then call
		exitAlertBox();
//		finish();
	}
	
	public  void exitAlertBox() {//show dialog after press back button
		final Dialog dialog = new Dialog(SensorTestActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.exit_alert_dialog);
		Button yes = (Button) dialog.findViewById(R.id.yes_button);
		yes.setOnClickListener(new OnClickListener() {//press yes then call
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		Button no = (Button) dialog.findViewById(R.id.no_button);
		no.setOnClickListener(new OnClickListener() {//press no then call
			@Override
			public void onClick(View v) {
				dialog.dismiss();//close dialog box
			}
		});

		dialog.show();//show dialog box
	}
 
} 

