package com.example.photobook;




import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*Dynamic picture feed*/
public class PictureStream extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	
	/*Create menu with new photo option, logout, and refresh?*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
	
	
}