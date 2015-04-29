package com.technorizen.mobilefalling.main;

import android.graphics.Bitmap;

public class SelectFriendListBean {// for getting friend detail in a object
	
	String fImage,fName,fId,fContactNo;
	Bitmap fImageBitMap;
	
	public String getfContactNo() {
		return fContactNo;
	}
	public void setfContactNo(String fContactNo) {
		this.fContactNo = fContactNo;
	}
	boolean isSelect;
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getfImage() {
		return fImage;
	}
	public void setfImage(String fImage) {
		this.fImage = fImage;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public Bitmap getfImageBitMap() {
		return fImageBitMap;
	}
	public void setfImageBitMap(Bitmap fImageBitMap) {
		this.fImageBitMap = fImageBitMap;
	}

}
