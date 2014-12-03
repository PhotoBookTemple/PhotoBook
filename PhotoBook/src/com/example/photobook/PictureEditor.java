package com.example.photobook;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

/*Once picture is taken, this screen will display picture and information. It will allow user to 
 * write a caption and then save the picture. User clicks save to send all information to database. User
 * can also cancel to return to the camera to take a different picture.
 */

public class PictureEditor extends Activity{
	
	EditText captionField;
	ImageView photoView;
	String caption;
	private String photoString;
	
	
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
		
		
		
		initialzeLoader();
		
		
		/*Initialize caption field and layout*/
		captionField = (EditText) findViewById(R.id.captionText);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.pictureEditorLayout);
	
		
	/*Get photo from intent*/
	photoString = getIntent().getStringExtra("photoUri");
	
	
		/* Display photo */
		photoView = new ImageView(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		//lp.addRule(RelativeLayout.ALIGN_TOP, R.id.pictureEditorLayout);
		lp.setMargins(180, 0, 180, 430);
		
		photoView.setLayoutParams(lp);
		
		ImageLoader.getInstance().displayImage(photoString, photoView);
		layout.addView(photoView);
	
	
	
	 
	
	}
	
	/*Save caption and take picture to information gathering service*/
	private void saveClicked(){
		caption = captionField.getText().toString();
		//Intent startService = new Intent(PictureEditor.this, "service class name");
		//Pass caption and image as extra to service intent
		
		returnToStream();
		
	}
	
	/*Delete picture and restart - go back to stream?*/
	private void delete(){
		returnToStream();	
	}
	
	
	private void returnToStream(){
	Intent returns = new Intent(PictureEditor.this, PictureStream.class);
	
	//FOR DEMO//
	returns.putExtra("photoString", photoString);
	
	
	startActivity(returns);
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
	
}
