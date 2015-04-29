package com.technorizen.mobilefalling.main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technorizen.mobilefalling.main.constant.AppsContants;
import com.technorizen.mobilefalling.main.constant.Progress_Dialog;

public class SettingScreenActivity extends Activity implements OnClickListener{
	private LinearLayout lyFindContact,lySetRington;

	
	private EditText etRecieverName,etRecieverContact,etRecieverEmail;
	private RelativeLayout rlSMScheckBox,rlCallcheckBox,rlEmailcheckBox;
	private ImageView ivSmsCheckBox,ivCallCheckBox,ivEmailCheckBox;
	boolean callFlag=false,smsFlag=false;//,emailFlag=false;
private	Button btnSubmitdata;
	Progress_Dialog progress_Dialog;
	Context context;
	private static final int SELECT_AUDIO = 5;
	String selectedAudioPath = "";
	String base64AudioFilePath;
	private ArrayList<SelectFriendListBean> alOnlyContacts;
	private ListView lvSelectedFriend;


	private String strRecieverName;
	private String strRecieverPhoneNo;
	private String strRecieverSms;
	private String beforeRecieverName;
	private String beforeRecieverPhoneNo;
	private boolean beforeSmsflag;
	private boolean beforecCallflag;
	private boolean beforeEmailflag;
	private String beforRecieverEmail;
	private LinearLayout lyRecieverEmail;
	private String strRecieverEmail;
	
	 public void onCreate(Bundle savedInstanceState) {
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_new_setting_screen);
		    initComponents();
		    context=this;
		    
		    AppsContants.userDataEntry =getApplicationContext().getSharedPreferences(AppsContants.USERREGISTER, AppsContants.Mode);
		  //get all views id
		    beforeRecieverName = AppsContants.userDataEntry.getString(AppsContants.RECIEVER_NAME,  "null");
		    beforeRecieverPhoneNo = AppsContants.userDataEntry.getString(AppsContants.RECIEVER_MOBILE_NO,  "null"); 
		    beforeSmsflag= AppsContants.userDataEntry.getBoolean(AppsContants.SMS_FLAG, false);//get data from sharedpreferences 
		    beforecCallflag= AppsContants.userDataEntry.getBoolean(AppsContants.CALL_FLAG, false);//get data from sharedpreferences
		    beforeEmailflag= AppsContants.userDataEntry.getBoolean(AppsContants.EMAIL_FLAG, false);//get data from sharedpreferences
		    beforRecieverEmail= AppsContants.userDataEntry.getString(AppsContants.RECIEVER_EMAIL,  "null"); //get data from sharedpreferences
		    if(!beforeRecieverName.equals("null"))
		    {
		    	etRecieverName.setText(beforeRecieverName);//set reviever name on  a reviever textview
			
		    }
		    if(!beforeRecieverPhoneNo.equals("null"))
		    {
		    	etRecieverContact.setText(beforeRecieverPhoneNo);//set reviever phone no on  a reviever phone textview
		    }
		    if(beforeSmsflag)
		    {
		    	smsFlag=true;
		    	ivSmsCheckBox.setBackgroundResource(R.drawable.check_box_checked);//set image on imageview is sms checked or not
		    }
		    if(beforecCallflag)
		    {
		    	callFlag=true;
		    	ivCallCheckBox.setBackgroundResource(R.drawable.check_box_checked);//set image on imageview is call checked or not
		    }

		    
		    lyFindContact.setOnClickListener(this);//for get find contact listener
		      lySetRington.setOnClickListener(this);//for get set ringtone listener
		    rlSMScheckBox.setOnClickListener(this);//for get sms check box listener
		    rlCallcheckBox.setOnClickListener(this);//for get call check box listener
		    rlEmailcheckBox.setOnClickListener(this);//for get email check box listener
		    btnSubmitdata.setOnClickListener(this);//for get submit data button listener
		    lyRecieverEmail.setVisibility(View.GONE);//for reviever email layout visibility gone
	 }
	 public void initComponents()//for find all view id
	 {
		 lyFindContact=(LinearLayout) findViewById(R.id.ly_find_contact_setting);
		 etRecieverName=(EditText) findViewById(R.id.et_revicer_name_setting);		 
		 etRecieverContact=(EditText) findViewById(R.id.et_revicer_contact_setting);
		 lySetRington=(LinearLayout) findViewById(R.id.linearly_set_ringtone_setting);		 
		 rlSMScheckBox=(RelativeLayout) findViewById(R.id.rl_check_sms_setting);
		 rlCallcheckBox=(RelativeLayout) findViewById(R.id.rl_check_call_setting);
		 rlEmailcheckBox=(RelativeLayout) findViewById(R.id.rl_check_email_setting);
		 ivSmsCheckBox=(ImageView)findViewById(R.id.iv_checkbox_sms_setting);
		 ivCallCheckBox=(ImageView)findViewById(R.id.iv_checkbox_call_setting);
		 ivEmailCheckBox=(ImageView)findViewById(R.id.iv_checkbox_email_setting);
		 etRecieverEmail=(EditText) findViewById(R.id.et_revicer_email_setting);		 
		 lyRecieverEmail=(LinearLayout) findViewById(R.id.ly_reviever_email_setting);	
		 
		 btnSubmitdata=(Button) findViewById(R.id.btn_submit_data);

	 }
	@Override
	public void onClick(View v) {//for click any view then call  
		switch (v.getId()) {
		case R.id.ly_find_contact_setting:
			selectFriendsList();
			break;
		case R.id.linearly_set_ringtone_setting:
			
			break;
		case R.id.rl_check_sms_setting:
			if(!smsFlag)
			{
				smsFlag=true;
				ivSmsCheckBox.setBackgroundResource(R.drawable.check_box_checked);//set checked image for sms 
			}
			else
			{
				smsFlag=false;
				ivSmsCheckBox.setBackgroundResource(R.drawable.check_box);//set unchecked image for sms
			}
			break;
		case R.id.rl_check_call_setting:
			if(!callFlag)
			{
				callFlag=true;
				ivCallCheckBox.setBackgroundResource(R.drawable.check_box_checked);//set checked image for call
			}
			else
			{
				callFlag=false;
				ivCallCheckBox.setBackgroundResource(R.drawable.check_box);//set unchecked image for call
			}
			break;
	
		case R.id.btn_submit_data:
			if(checkFields())
			{
				Toast.makeText(context, "save your data", 2000).show();

				   try{//save all data in sharedpreference
						Editor edit = AppsContants.userDataEntry.edit();
						edit.putString(AppsContants.RECIEVER_NAME, strRecieverName);
						edit.putString(AppsContants.RECIEVER_MOBILE_NO, strRecieverPhoneNo);
						edit.putBoolean(AppsContants.SMS_FLAG, smsFlag);
						edit.putBoolean(AppsContants.CALL_FLAG, callFlag);
//						edit.putBoolean(AppsContants.EMAIL_FLAG, emailFlag);
						edit.putString(AppsContants.RECIEVER_EMAIL, strRecieverEmail);
						edit.commit();
					
						}catch(Exception e)
						{
							Log.e("", "login screen error= "+e);
						}
				   finish();//this activity finish
			}
          
			break;	
			
			
			
		default:
			break;
		}
		
	}
	public boolean checkFields()//check all field fill or not 
	{
		strRecieverName=etRecieverName.getText().toString().trim();
		strRecieverPhoneNo = etRecieverContact.getText().toString().trim();
		strRecieverSms = "I m in Problem";
		strRecieverEmail=etRecieverEmail.getText().toString().trim();
		boolean check=true;
		if(strRecieverPhoneNo.length()<=0)
		{
			Toast.makeText(context, "Please Enter Phone No", 2000).show();
			check=false;
		}
		if(strRecieverName.length()<=0)
		{	
			Toast.makeText(context, "Please Reciever Name", 2000).show();
			check=false;
		}
		if(callFlag==false&smsFlag==false)
		{
			Toast.makeText(context, "Please Select Atlease on Check Box", 2000).show();
			check=false;
			
		}
		
		
		return check;
	}

	public void selectFriendsList(){//get friend list from my mobile device exist contact
		final Dialog dialog = new Dialog(SettingScreenActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.for_select_friend_list_alert_dialog);
		dialog.setTitle("Select Friends");
		lvSelectedFriend= (ListView)dialog.findViewById(R.id.lv_select_friend_mobile_fall_setting);
	      new GetcontactAsynctask().execute();
	      lvSelectedFriend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String recieverName=alOnlyContacts.get(position).getfName();
				String revieverContact=alOnlyContacts.get(position).getfContactNo();
				etRecieverName.setText(recieverName);
				etRecieverContact.setText(revieverContact);
				dialog.dismiss();
			}
		});


		
		dialog.show();
	}
	
	class GetcontactAsynctask extends AsyncTask<Void, Void, Void> {//connect to the phone contact
		
		@Override
		protected void onPreExecute() {
			progress_Dialog = new Progress_Dialog(context);
			progress_Dialog.dialog_show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try{
				alOnlyContacts = new ArrayList<SelectFriendListBean>();
					ContentResolver cr = context.getContentResolver();
				    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
				    if(cursor.moveToFirst())
				    {
				        SelectFriendListBean beans;
				        do
				        {
				            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				            beans=new SelectFriendListBean();
				            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
				            {
				                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
				                while (pCur.moveToNext()) 
				                {
				                    String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				                	String displayName = pCur
											.getString(pCur
													.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				                    beans.setfId(id);
				                    beans.setfName(displayName);
				                    beans.setfContactNo(contactNumber);
				                    Bitmap pBitmap = getPhoto(id);
									beans.setfImageBitMap(pBitmap);
									alOnlyContacts.add(beans);
				                    break;
				                }
				                pCur.close();
				            }

				        } while (cursor.moveToNext()) ;
	
				    }
			}catch(Exception e)
			{
				Log.e("select friendactivity", "find contact error= "+e);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress_Dialog.dialog_dismiss();
			if(alOnlyContacts.size()>0)
			{
				CustomListFriendSelectAdapter adapter = new CustomListFriendSelectAdapter(
						SettingScreenActivity.this, alOnlyContacts);
				lvSelectedFriend.setAdapter(adapter);
			}
		}

	}
	 public Bitmap getPhoto(String contactID)//get photo bitmap of a contact person
		{
			Bitmap PhotobitMap=null;
			Uri my_contact_Uri = Uri.withAppendedPath(
					ContactsContract.Contacts.CONTENT_URI,
					String.valueOf(contactID));
			InputStream photo_stream = ContactsContract.Contacts
					.openContactPhotoInputStream(getContentResolver(),
							my_contact_Uri);
			if(photo_stream!=null)
			{
			BufferedInputStream buf = new BufferedInputStream(photo_stream);
			 PhotobitMap = BitmapFactory.decodeStream(buf);
			}
			else
			{
				PhotobitMap=null;
			}
			return PhotobitMap;
		}
}
