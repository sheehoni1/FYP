package com.technorizen.mobilefalling.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.technorizen.mobilefalling.main.utils.UniversalImageLoader;


public class CustomListFriendSelectAdapter extends BaseAdapter {
    private Activity localactivity;
    private ArrayList<SelectFriendListBean> arSelectedFriend;
    private  LayoutInflater inflater=null;
    Context context;
    private int position;
    public CustomListFriendSelectAdapter(Activity a, ArrayList<SelectFriendListBean> d) {
    	localactivity = a;
            context=a;
            arSelectedFriend=d;
             inflater = ( LayoutInflater )localactivity.
                                         getSystemService(Context.LAYOUT_INFLATER_SERVICE);          
    }
     public int getCount() {           
        if(arSelectedFriend.size()<=0)
            return 1;
        return arSelectedFriend.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{          
        public TextView tvFriendName;
        public ImageView ivChecked;
        public ImageView ivFriendImage;
        public ProgressBar loaderImage;
        public LinearLayout lyCheck;
      
 
    }
    public View getView(final int position, View convertView, ViewGroup parent) {          
        View vi = convertView;
        final ViewHolder holder; 
        this.position=position;
        if(convertView==null){
             vi = inflater.inflate(R.layout.activites_custom_select_friend_list, null);
             holder = new ViewHolder();
             UniversalImageLoader.initUniversalImageLoaderOptionsForRoundImage();
             holder.lyCheck=(LinearLayout) vi.findViewById(R.id.ly_checked_custom_friend_lists);
            holder.tvFriendName = (TextView) vi.findViewById(R.id.tv_friend_name_custom_friend_list);
            holder.ivChecked= (ImageView) vi.findViewById(R.id.iv_check_custom_friend_lists);
           holder.ivFriendImage=(ImageView)vi.findViewById(R.id.iv_profile_pic_custom_friend_list);
           holder.loaderImage=(ProgressBar) vi.findViewById(R.id.pb_progress_profile_pic_custom_friend_list);  
           vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();    
        
        if(arSelectedFriend.size()<=0)
        {
            holder.tvFriendName.setText("No Data");
             
        }
        else
        {
             holder.tvFriendName.setText(arSelectedFriend.get( position ).getfName());  
             
             if(arSelectedFriend.get(position).getfImageBitMap()!=null)
				{
     			
     			 /*UniversalImageLoader.initUniversalImageLoaderOptionsForRoundImage();
 				 UniversalImageLoader.loadImageFromURI(userProfileIcon, UserImageUrl, null);*/     			 
            	 holder.ivFriendImage.setImageBitmap(arSelectedFriend.get(position).getfImageBitMap());
				}
				else
				{
					holder.ivFriendImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pic1));
				}
//             UniversalImageLoader.loadImageFromURI( holder.ivFriendImage, arSelectedFriend.get(position).getfImage(), holder.loaderImage);
           }
      
        holder.ivChecked.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if(!arSelectedFriend.get(position).isSelect())
	             {
					 arSelectedFriend.get(position).setSelect(true);
	              holder.ivChecked.setBackgroundResource(R.drawable.check_box_checked);           
	             }else
	             {
	            	 arSelectedFriend.get(position).setSelect(false);
	            	 holder.ivChecked.setBackgroundResource(R.drawable.check_box);
	             }
			}
		});
       return vi;
    }
	
	
}
