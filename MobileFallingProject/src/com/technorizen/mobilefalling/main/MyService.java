package com.technorizen.mobilefalling.main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.technorizen.mobilefalling.main.constant.AppsContants;

public class MyService extends Service implements SensorEventListener {//service for getting sensor gravity
	 private SensorManager sensorManager;
	  private long lastUpdate;
	  String accelatorString="";
	private boolean min;
	private boolean max;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
	
	}

	@Override
	public void onDestroy() {
		 sensorManager.unregisterListener(this);

		 
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {//sensor register here
		try{
			   sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
			    lastUpdate = System.currentTimeMillis();
			    sensorManager.registerListener(this,
			            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			            SensorManager.SENSOR_STATUS_ACCURACY_HIGH);//MobileFallingProject
			   /* sensorManager.registerListener(this,
			            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			            SensorManager.SENSOR_DELAY_NORMAL);*/
			   
			}catch(Exception e)
			{
			   Log.e("", "service error= "+e);	
			}
		return START_STICKY;
		
	}
	
	
	public  void showdialogmethod()//for showing dialog box sceen
	{
		Intent dialogIntent = new Intent(getApplicationContext(), ShowDialogAndMediyaPlayer.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(dialogIntent);
		 
	}

	  private void getAccelerometer(SensorEvent event) {	//get accelerometer gravity
		 final float alpha = 0.8f;
		    float[] values = event.values;
		    // Movement
		    float x = values[0];
		    float y = values[1];
		    float z = values[2];

		    float accelationSquareRoot = (x * x + y * y + z * z)
		        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		    long actualTime = event.timestamp;
		    
		    if (accelationSquareRoot >= 2)//2
		    {	    	
		      
		    	if (accelationSquareRoot<9)//actualTime - lastUpdate <200
		    	  {
				        return;
				      }
		      lastUpdate = actualTime;
		      if(!AppsContants.checkDialogFlag)
		      {
		    	  AppsContants.checkDialogFlag=true;
		    	  accelatorString=accelationSquareRoot+"";
		    	  saveDataOnCsvFile();
		          showdialogmethod();		         
		      }
		     
		    }
		
		    
		  }

		  @Override
		  public void onAccuracyChanged(Sensor sensor, int accuracy) {

		  }

		@Override
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
			{
			  getAccelerometer(event);	
			  
			  
			}
			
		}
	public void saveDataOnCsvFile()
		{
		try
		{
		  File folder = new File(Environment.getExternalStorageDirectory()
                  + "/MobileFalling");
          boolean var = false;
          if (!folder.exists())
              var = folder.mkdir();
          System.out.println("" + var);
              
          final String sFileName = folder.toString() + "/" + "Mobile_Falling.csv";
          FileWriter writer = new FileWriter(sFileName,true);	 
		    writer.append("accelatorvalue");
		    writer.append(" "+accelatorString);
		    writer.append('\n');	
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
		
		}
		}
