package com.example.photobook;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.List;
>>>>>>> origin/pr/5
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.example.photobook.R;
<<<<<<< HEAD
=======
import com.example.photobook.UploadService;
>>>>>>> origin/pr/5

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

/*Once picture is taken, this screen will display picture and information. It will allow user to 
 * write a caption and then save the picture. User clicks save to send all information to database. User
 * can also cancel to return to the camera to take a different picture.
 */

public class PictureEditor extends Activity implements LocationListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener{
	
	EditText captionField;
	ImageView photoView;
<<<<<<< HEAD

	File photo;
	String photoCaption, photoName, photoPath, timeStamp, gpsLocation, locAltitude, locTemp;
 	private String photoString;
	LocationClient locationClient;
	Location loc;
=======
	File photo;
	String photoCaption, photoName, photoPath, timeStamp, gpsLocation, locAltitude, locTemp, userID;
	private String photoString;
	LocationClient locationClient;
	Location loc;
	
	
	
>>>>>>> origin/pr/5
	
	/*Create menu with save and delete*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.editor, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save:
			saveClicked();
			return true;
		case R.id.delete:
			delete();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_editor);
<<<<<<< HEAD
=======
		
>>>>>>> origin/pr/5
		locationClient = new LocationClient(this, (ConnectionCallbacks) this, this);
		
		/*Initialize image loader*/
		ImageLoader imageLoader;
		DisplayImageOptions displayOptions;
		
		imageLoader = ImageLoader.getInstance();
		
		displayOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
				
		File cacheDir = StorageUtils.getCacheDirectory(this);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .threadPoolSize(3)
        .threadPriority(Thread.NORM_PRIORITY - 1)
        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 2 MBs
        .discCache(new UnlimitedDiscCache(cacheDir))
        .discCacheSize(50 * 1024 * 1024) // 50 MBs
        .defaultDisplayImageOptions(displayOptions)
        .build();
		ImageLoader.getInstance().init(config);
		
		
		/*Initialize caption field and layout*/
		captionField = (EditText) findViewById(R.id.captionText);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.pictureEditorLayout);
<<<<<<< HEAD
	
		
		/*Get photo from intent*/
		photoString = getIntent().getStringExtra("photoUri");
		photoName = getIntent().getStringExtra("photoName");
		File storageDirectory = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		photo = new File(photoString); // Temporary file name
=======
		
	/*Get photo from intent*/
	photoString = getIntent().getStringExtra("photoUri");
	userID = getIntent().getStringExtra("userID");
	
	File storageDirectory = new File(photoString);
	String fileName = userID + String.valueOf(System.currentTimeMillis()) + ".jpg";
	photo = new File(storageDirectory, fileName ); // Temporary file name
	photoName = fileName;
>>>>>>> origin/pr/5
	
		/* Display photo */
		photoView = new ImageView(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		//lp.addRule(RelativeLayout.ALIGN_TOP, R.id.pictureEditorLayout);
		lp.setMargins(180, 0, 180, 430);
		
		photoView.setLayoutParams(lp);
		
		ImageLoader.getInstance().displayImage(photoString, photoView);
<<<<<<< HEAD
		layout.addView(photoView);	
	
	
		/*Clicks save to start service to fetch info and send to database*/
			
		//	LocationManager to get GPS info
		//	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		//	
		//	public void onLocationChanged(Location location){
		//		
		//		/*Get altitude*/
		//		 location.getAltitude();
		//		 
		//		 /*Get location*/
		//		 location.getLatitude();
		//		 location.getLongitude();
		//	}		 
=======
		layout.addView(photoView);
	
		
		
	/*Clicks save to start service to fetch info and send to database*/
	
//	LocationManager to get GPS info
//	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//	
//	public void onLocationChanged(Location location){
//		
//		/*Get altitude*/
//		 location.getAltitude();
//		 
//		 /*Get location*/
//		 location.getLatitude();
//		 location.getLongitude();
//	}
		
	 
>>>>>>> origin/pr/5
	
	}
	
	/*Save caption and take picture to information gathering service*/
	private void saveClicked(){
		photoCaption = captionField.getText().toString();
<<<<<<< HEAD
		//Intent startService = new Intent(PictureEditor.this, "service class name");
		//Pass caption and image as extra to service intent
		Intent uploadPhotobookIntent = new Intent(this, Photo.class);
		uploadPhotobookIntent.putExtra("directory", Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		uploadPhotobookIntent.putExtra("uri", photoString);
		uploadPhotobookIntent.putExtra("photoCaption", photoCaption);
		uploadPhotobookIntent.putExtra("photoName", photoName);
		uploadPhotobookIntent.putExtra("timeStamp", timeStamp);
		uploadPhotobookIntent.putExtra("gpsLocation", gpsLocation);
		uploadPhotobookIntent.putExtra("locAltitude", locAltitude);
		uploadPhotobookIntent.putExtra("locTemp", locTemp);
		startService(uploadPhotobookIntent);
		Toast.makeText(this, "Uploading Photo", Toast.LENGTH_SHORT).show();
		finish();
		Intent returnToStream = new Intent(this, PictureStream.class);
		startActivity(returnToStream);

//		returnToStream();
		
	}
	
	/*	private void uploadPhoto(){
		Intent uploadPhotobookIntent = new Intent(this, UploadService.class);
		uploadPhotobookIntent.putExtra(UploadService.directory, Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		uploadPhotobookIntent.putExtra(UploadService.image, photo.getAbsolutePath());
		startService(uploadPhotobookIntent);
		Toast.makeText(this, "Uploading Photo", Toast.LENGTH_SHORT).show();
	}*/
=======
		photoPath = "../photobook_files/";
		
		  SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
		    
		timeStamp = strDate;
		//Intent startService = new Intent(PictureEditor.this, "service class name");
		//Pass caption and image as extra to service intent
		//photoName = userName + "-"+ String.valueOf(System.currentTimeMillis()) + ".jpg";
		uploadPhoto();
		returnToStream();
		
	}
	
	private void uploadPhoto(){
		
		Intent uploadPhotobookIntent = new Intent(this, UploadService.class);
		uploadPhotobookIntent.putExtra(UploadService.directory, Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		uploadPhotobookIntent.putExtra(UploadService.image, photo.getAbsolutePath());
		uploadPhotobookIntent.putExtra(UploadService.userID, userID);
		uploadPhotobookIntent.putExtra(UploadService.photoName,photoName);
		uploadPhotobookIntent.putExtra(UploadService.photoCaption, photoCaption);
		uploadPhotobookIntent.putExtra(UploadService.photoPath,photoPath);
		uploadPhotobookIntent.putExtra(UploadService.timeStamp, timeStamp);
		uploadPhotobookIntent.putExtra(UploadService.gpsLocation,gpsLocation);
		uploadPhotobookIntent.putExtra(UploadService.locAltitude, locAltitude);
		uploadPhotobookIntent.putExtra(UploadService.locTemp,locTemp);
		startService(uploadPhotobookIntent);
		Toast.makeText(this, "Uploading Photo", Toast.LENGTH_SHORT).show();
	}
>>>>>>> origin/pr/5
	
	/*Delete picture and restart - go back to stream?*/
	private void delete(){
		finish();
		Intent returnToStream = new Intent(this, PictureStream.class);
		startActivity(returnToStream);
		//returnToStream();	
	}
	
	
<<<<<<< HEAD
/*	private void returnToStream(){
		Intent returns = new Intent(PictureEditor.this, PictureStream.class);
	
		//FOR DEMO//
		returns.putExtra("photoString", photoString);
	
		startActivity(returns);
	}
*/
	@Override
	protected void onStart()
	{
		super.onStart();
		locationClient.connect();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		locationClient.disconnect();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		if(locationClient.getLastLocation() != null )
		{
			loc = locationClient.getLastLocation();
			gpsLocation = getAddressDetails(this, loc.getLatitude(), loc.getLongitude());
			locAltitude = String.valueOf(loc.getAltitude());
			locTemp = "70";
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			timeStamp = df.format(dateobj);
			(Toast.makeText(this, "Address is: " + gpsLocation + "and Altitude is : " + locAltitude, Toast.LENGTH_SHORT)).show();
		}
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
=======
	private void returnToStream(){
		Intent returns = new Intent(PictureEditor.this, PictureStream.class);
		//FOR DEMO//
		returns.putExtra("photoString", photoString);
		startActivity(returns);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		locationClient.connect();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		locationClient.disconnect();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		if(locationClient.getLastLocation() != null )
		{
			loc = locationClient.getLastLocation();
			gpsLocation = getAddressDetails(this, loc.getLatitude(), loc.getLongitude());
			locAltitude = String.valueOf(loc.getAltitude());
			//(Toast.makeText(this, "Address is: " + gpsLocation + "and Altitude is : " + locAltitude, Toast.LENGTH_SHORT)).show();
		}
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	
	public String getAddressDetails(Context context, double latitude, double longitude)
	{
		String address = "";
		try {
			
			
	        Geocoder geo = new Geocoder(context, Locale.getDefault());
	        List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
	        if (addresses.isEmpty()) {
	           address = "No Location";
	        }
	        else {
	            if (addresses.size() > 0) {
	               address = addresses.get(0).getAddressLine(0).substring(9) + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
	                //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
	            }
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace(); // getFromLocation() may sometimes fail
	    }
		return address;
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		(Toast.makeText(this, "Connection unsuccessful" + result.toString(), Toast.LENGTH_LONG)).show();
>>>>>>> origin/pr/5
	}
	
	public String getAddressDetails(Context context, double latitude, double longitude)
	{
		String address = "";
		try {
			
			
	        Geocoder geo = new Geocoder(context, Locale.getDefault());
	        List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
	        if (addresses.isEmpty()) {
	           address = "No Location";
	        }
	        else {
	            if (addresses.size() > 0) {
	               address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
	                //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
	            }
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace(); // getFromLocation() may sometimes fail
	    }
		return address;
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		(Toast.makeText(this, "Connection unsuccessful" + result.toString(), Toast.LENGTH_LONG)).show();
	}
	
}
