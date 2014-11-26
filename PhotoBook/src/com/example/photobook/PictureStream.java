package com.example.photobook;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

/*Dynamic picture feed*/
public class PictureStream extends Activity {
	
	ImageView newImage;
	GridLayout imageStream;
	File photoStorage, photo;
	Uri imageUri;
	boolean firsttime = true;
	
<<<<<<< HEAD
	String photoString, photoName;
=======
	
	String photoString, userName;
	
	
>>>>>>> origin/pr/5
	
	int TAKE_PICTURE_REQUEST_CODE = 123456;
	int userID = 1999;
	
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
<<<<<<< HEAD
		
		if(firsttime){
			firsttime = false;
			//Show Welcome Message
			Intent intent = getIntent();
			String welcome = intent.getStringExtra("welcome");
		//	userID = Integer.parseInt(intent.getStringExtra("userID"));
			showDialog("Welcome to PhotoBook", welcome);
		}
		
=======
		userName = String.valueOf(getIntent().getStringExtra("username"));
>>>>>>> origin/pr/5
		imageStream = (GridLayout) findViewById(R.id.imageStream);
		
		/*Check for local directory of photos, if not create one*/
		photoStorage = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		if(!photoStorage.isDirectory()){
			photoStorage.mkdir();
		}
		
		loadStream();
	}
	
	private void loadStream(){
		//	Use JSON Parser to load stream. Add each to layout with putPhotoInLayout	
	}
	
	
	private View putPhotoInLayout(final JSONObject photoObject){
		//Switch to stream??
		GridLayout photoLayout = new GridLayout(this);
		ImageView photoImageView = new ImageView(this);
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams(); 
		photoImageView.setLayoutParams(lp);
		
			try {
				//Check correct field of JSON Object
				ImageLoader.getInstance().displayImage(photoObject.getString("image_url"), photoImageView);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		photoLayout.addView(photoImageView);
		//set on click listener for picture viewer, one for each or one to tell which picture clicked
		photoImageView.setOnClickListener(new View.OnClickListener() {
			String photoUri = imageUri.toString();
			
			@Override
			public void onClick(View v) {
				openPictureViewer(photoUri);
			}
		});
		
		return photoLayout;
	}
	
	/*Take a picture*/
	private void takePicture(){
	
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		photoName = String.valueOf(System.currentTimeMillis());
		photo = new File(photoStorage, photoName + ".jpg");
		takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		
		imageUri = Uri.fromFile(photo);
		
		startActivityForResult(takePicture, TAKE_PICTURE_REQUEST_CODE);
	}
	
	/*When picture is returned, open picture editor*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE_REQUEST_CODE) {
			Intent openPictureEditor = new Intent(PictureStream.this, PictureEditor.class);
			String photoUri = imageUri.toString();
			openPictureEditor.putExtra("photoUri", photoUri);
			openPictureEditor.putExtra("photoName", photoName);
			startActivity(openPictureEditor);
			
		}
	
	}
	
	private void openPictureViewer(String photoUri){
	//send photo uri as intent to picture viewer

		Intent openViewer = new Intent(PictureStream.this, PictureViewer.class);
		openViewer.putExtra("photoUri", photoUri);
		startActivity(openViewer);
		
	}
	
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
	
	
}
