package com.example.photobook;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

/*Dynamic picture feed*/
public class PictureStream extends Activity {
	
	ImageView newImage;
	LinearLayout imageStream;
	File photoStorage, photo;
	Uri imageUri;
	boolean firsttime = true;

	String photoString, photoName, userName;
	int userID;
	
	

	int TAKE_PICTURE_REQUEST_CODE = 123456;
	
/*START MOLLY*/
	/*Create menu with new photo option, logout, and refresh?*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_picture:
			/*Take new picture*/
			takePicture();
			return true;
		case R.id.signout:
			/*Remove active from database to prevent multiples from log in??*/
			Intent signOut = new Intent(PictureStream.this, StartScreen.class);
			startActivity(signOut);
			finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
/*END MOLLY*/	
	
/*START SUSHMA*/
	Handler streamHandler = new Handler(new Handler.Callback() {
/*END SUSHMA*/		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try {
				
/*START MOLLY*/
				JSONArray picturesJson = (JSONArray)msg.obj;
				
				if(picturesJson != null){
				for(int i = 0; i < picturesJson.length(); i++){
					putPhotoInLayout(picturesJson.getJSONObject(i));
				}
				}
				
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*END MOLLY*/			
			return false;
		}
	});
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		firsttime = false;
		//Show Welcome Message
		
/*START NEIL*/
		GlobalPhoto globalVariable = (GlobalPhoto)getApplicationContext();
		userName = globalVariable.getUserName();
		userID = globalVariable.getUserID();
		
		if(globalVariable.getAgain()) {
			String welcome = globalVariable.getMessage();
			if(getIntent().getStringExtra("login") == "01") {
				showDialog(welcome, "Take your first picture");
			} else showDialog(welcome, "Welcome back");
			
			globalVariable.setAgain(false);
		}
/*END NEIL*/		
		
/*START MOLLY*/
		imageStream = (LinearLayout) findViewById(R.id.imageStream);
		
		/*Check for local directory of photos, if not create one*/
		photoStorage = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
			photoStorage.mkdir();
		
		
		try {
			loadStream();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void loadStream() throws InterruptedException, ExecutionException{
		//	Use JSON Parser to load stream. Add each to layout with putPhotoInLayout	
		
		new jSonLoader(this).execute().get();
		
	}

	class jSonLoader extends AsyncTask<String, String, String>{
		private Context mContext;
		public  jSonLoader(Context context){
			mContext = context;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				JSONArray picturesJson = httpReq.getPhotos(mContext, userID);
				Message msg = Message.obtain();
				msg.obj = picturesJson;
				streamHandler.sendMessage(msg);
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	
	
	private View putPhotoInLayout(final JSONObject photoObject) throws JSONException{
		//Switch to stream??
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		initialzeLoader();

		ImageView photoImageView = new ImageView(this);
		ImageLoader.getInstance().displayImage(photoObject.getString("photoPath"), photoImageView);
		photoImageView.setBackgroundResource(R.drawable.photobackground);
		lp.setMargins(50, 10, 50, 20);
		photoImageView.setLayoutParams(lp);

		photoImageView.setPadding(70, 0, 70, 0);

		
		
		String photoURL = photoObject.getString("photoPath");

		// Check correct field of JSON Object
				
				imageStream.addView(photoImageView);
				// set on click listener for picture viewer, one for each or one to tell
				// which picture clicked
				photoImageView.setOnClickListener(new View.OnClickListener() {

					

					@Override
					public void onClick(View v) {

						openPictureViewer(photoObject.toString());
					}
				});

				return imageStream;
	}
	
	/*Take a picture*/
	
	
	private void takePicture(){
	
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		photoName = String.valueOf(userID)+"-" + String.valueOf(System.currentTimeMillis());
		photo = new File(photoStorage, photoName + ".jpg");
		takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));		
		imageUri = Uri.fromFile(photo);
		startActivityForResult(takePicture, TAKE_PICTURE_REQUEST_CODE);
		Log.i("ImageUri", imageUri.toString());
		Log.i("photoAbsolutePath", photo.getAbsolutePath());
	}
	
	/*When picture is returned, open picture editor*/
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE_REQUEST_CODE) {
			Intent openPictureEditor = new Intent(PictureStream.this, PictureEditor.class);
			String photoUri = photo.getAbsolutePath();
			openPictureEditor.putExtra("photoUri", photoUri);
			openPictureEditor.putExtra("photoName", photoName);
			openPictureEditor.putExtra("imageUri", imageUri.toString());
			startActivity(openPictureEditor);
			
		}
	
	}
	
	
	private void openPictureViewer(String photoJson){
	//send photo uri as intent to picture viewer

		Intent openViewer = new Intent(PictureStream.this, PictureViewer.class);
		openViewer.putExtra("photoJson", photoJson);
		startActivity(openViewer);
		
	}
	
	
	private void initialzeLoader(){
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
	}
/*END MOLLY*/	
	
/*START NEIL*/
	private void showDialog(String title, String message) {
		
		AlertDialog.Builder aDialog = new AlertDialog.Builder(PictureStream.this);
					// set title
		aDialog.setTitle(title);
		
		// set dialog message
		aDialog
		.setMessage(message)
		.setCancelable(false)
		.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
		}
		});
		// create alert dialog
		AlertDialog alertDialog = aDialog.create();
		// show it
		alertDialog.show();
		Toast.makeText(PictureStream.this, message, Toast.LENGTH_LONG).show();
	}
/*END NEIL*/	
	
/*START MOLLY*/
	@Override
	public void onBackPressed() {
	}
/*END MOLLY*/	
	
	
	
	
	
	
	
}
