package com.technorizen.mobilefalling.main.constant;

import android.app.ProgressDialog;
import android.content.Context;

public class Progress_Dialog {
	public ProgressDialog progress_Dialog;
	Context context;
	public Progress_Dialog(Context c)
	{
	context=c;
	progress_Dialog= new ProgressDialog(context);
	progress_Dialog.setCancelable(false);
	progress_Dialog.setMessage("Wait...");
	}
	public void dialog_show()
	{
	progress_Dialog.show();
	}
	public void dialog_dismiss()
	{
	progress_Dialog.dismiss();
	}
	}



