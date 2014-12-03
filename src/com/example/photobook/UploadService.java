package com.example.photobook;

import java.io.File;

import com.example.photobook.*;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/*START SUSHMA*/
public class UploadService extends IntentService{

	public static final String image = "image", directory = "directory", photoCaption = "photoCaption",
			visibilityKey = "visibility", photoName = "photoName", photoPath = "photoPath", userID = "userID",
					timeStamp = "timeStamp", gpsLocation ="gpsLocation", locAltitude = "locAltitude", 
					locTemp = "locTemp";
	public static final String REFRESH_ACTION = "refreshStream";
	
	public UploadService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public UploadService(){
		super("default");
	}
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		try {
			
			
			boolean status = httpReq.uploadPhotos(this, intent.getIntExtra(userID,00),
			intent.getStringExtra(photoName), intent.getStringExtra(photoCaption), intent.getStringExtra(photoPath),
			intent.getStringExtra(timeStamp), intent.getStringExtra(gpsLocation),intent.getStringExtra(locAltitude),
			intent.getStringExtra(locTemp), new File(intent.getStringExtra(image)));	
			Log.i("In UploadService", "In Progress");
			
			//START -SUSHMA add notificationBuild for photobook
			if(status)
			{
				NotificationCompat.Builder photobookBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("PhotoBook Notification")
				.setContentText("PhotoBook uploaded");
				long[] pattern = {1000, 1000};
				photobookBuilder.setVibrate(pattern);
				
				Intent resultIntent = new Intent(this, Class.forName(PictureEditor.class.getName())).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
				stackBuilder.addParentStack(PictureViewer.class);
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				photobookBuilder.setContentIntent(resultPendingIntent);
				NotificationManager photobookManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				photobookManager.notify(1000, photobookBuilder.build());
			}
			//END SUSHMA
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/*END SUSHMA*/   
}
