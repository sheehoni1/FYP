package com.technorizen.mobilefalling.main.utils;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class UniversalImageLoader {
	static DisplayImageOptions options;

	 protected static ImageLoader imageLoader ;


	 public static void initUniversalImageLoaderOptions(){
		 
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(0)
				.showImageOnFail(0)
				.cacheInMemory()
				.cacheOnDisc()
				.showImageOnLoading(0)
				.displayer(new RoundedBitmapDisplayer(100))
				.build();
		
		  }
	 public static void initUniversalImageLoaderOptionsRectangle(){
		 
			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(0)
					.showImageOnFail(0)
					.cacheInMemory()
					.cacheOnDisc()
					.showImageOnLoading(0)
					.displayer(new RoundedBitmapDisplayer(0))
					.build();
			
			  }
	 public static void initUniversalImageLoaderOptionsForRoundImage(){
		 
			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(0)
					.showImageOnFail(0)
					.cacheInMemory(true)
					.cacheOnDisc(true)
					
					.showImageOnLoading(0)
					.displayer(new RoundedBitmapDisplayer(100))
					.build();
			  }

	 public static void loadImageFromURI(ImageView img, final String url, final ProgressBar spinner)
	   {
		
		// img.setBackgroundColor(Color.TRANSPARENT);
		 //img.setImageResource(R.drawable.empty_photo);
		ImageLoader.getInstance().displayImage(url, img,options, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				if(spinner!=null){
					 spinner.setVisibility(View.VISIBLE);
					}
				
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
				// TODO Auto-generated method stub
				if(spinner!=null){
					 spinner.setVisibility(View.GONE);
					}
			}
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				if(spinner!=null){
					 spinner.setVisibility(View.GONE);
					}
			}
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				if(spinner!=null){
					 spinner.setVisibility(View.GONE);
					}
			}
		});
	}
	 
	 public static Bitmap loadImageInView(String url){
		 Bitmap bm = ImageLoader.getInstance().loadImageSync(url);
		 return bm;
	 }
}
