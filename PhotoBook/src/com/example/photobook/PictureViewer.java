package com.example.photobook;



import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PictureViewer extends Activity {
	
	/*Opens up clicked picture and displays information recorded
	 * - maybe has option to edit caption, send to social media
	 * - needs back button to return to main viewer
	 */
	
	RelativeLayout picture;
	String temperatureData, locationData, timeStampData, altitudeData;
	TextView temperature, location, timeStamp, altitude;
	private String photoLocation;
	
	/*Create menu with back button*/
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.viewer, menu);
			return true;
		}
		
		/*User clicked back; return to stream*/
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.return_to_Stream:
				Intent openStream = new Intent(PictureViewer.this, PictureStream.class);
				startActivity(openStream);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_viewer);
	
		/*Initialize widgets*/
		temperature = (TextView)findViewById(R.id.temperature);
		location = (TextView)findViewById(R.id.location);
		timeStamp = (TextView)findViewById(R.id.timeStamp);
		altitude = (TextView)findViewById(R.id.altitude);
		
		/*Initialize layout*/
		picture = (RelativeLayout)findViewById(R.id.pictureViewerLayout);
		
		/*Get clicked picture from intent and display*/
//		picture.setBackground(background);
		//Use JSON Parser
		
		/*Get picture information from database and display*/
		
		
		//DEMO
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
		
		photoLocation = getIntent().getStringExtra("photoUri");
		
		
		/* Display photo */
		ImageView photoView = new ImageView(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		lp.addRule(RelativeLayout.ABOVE, R.id.temperature);
		photoView.setLayoutParams(lp);
		
		ImageLoader.getInstance().displayImage(photoLocation, photoView);
		picture.addView(photoView);
		
		
	}

}
