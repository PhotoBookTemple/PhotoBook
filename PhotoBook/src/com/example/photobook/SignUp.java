package com.example.photobook;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.photobook.util.JSONParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	
	/*Name Widgets*/
	Button signUp;
	EditText username, password, passwordConfirm;
	String userName, userPwd, userPwdConfirm;
	final Context context = this;
	
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
	// url to create new user
    private static String url_create_user = "http://cis-linux2.temple.edu/~tuc69409/create_user.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		/*Stop keyboard from opening*/
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		
		/*Initialize widgets*/
		signUp = (Button) findViewById(R.id.signUpComplete);
		username = (EditText) findViewById(R.id.newUsername);
		password = (EditText) findViewById(R.id.newPassword);
		passwordConfirm = (EditText) findViewById(R.id.newPasswordConfirm);
		
		/*Complete sign up*/
		signUp.setOnClickListener(new View.OnClickListener() {
			
	//		@Override
			public void onClick(View v) {
				/*Get user input*/
				userName = username.getText().toString();
				userPwd = password.getText().toString();
				userPwdConfirm = passwordConfirm.getText().toString();
				
				/*Check for matching passwords*/
				if(userPwd.equals(userPwdConfirm)){
					
					 if(!TextUtils.isEmpty(userName) && userName.length() > 8 && !TextUtils.isEmpty(userPwd) && userPwd.length() > 8) {				 
						new CreateNewUser().execute();
					 } else {
						AlertDialog.Builder aDialog = new AlertDialog.Builder(context);
 						// set title
						aDialog.setTitle("Sign Up for PhotoBook");
			 
						// set dialog message
						aDialog
							.setMessage("User name and password has to be at least 8 character long")
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
						Toast.makeText(SignUp.this, "User name/Password are in wrong format", Toast.LENGTH_LONG).show(); 
					 }
					 
				}
				else{
					AlertDialog.Builder aDialog = new AlertDialog.Builder(context);
		 						// set title
					aDialog.setTitle("Sign Up for PhotoBook");
		 
					// set dialog message
					aDialog
						.setMessage("Passwords do not match")
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
					Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}

	/**
	†Background Async Task to Create new user
	†* */
	class CreateNewUser extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUp.this);
			pDialog.setMessage("Creating User..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();	
		}
		
		@Override
		protected String doInBackground(String... args) {
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userName", userName));
			params.add(new BasicNameValuePair("userPwd", userPwd));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_user, "POST", params);
			
			// check log cat from response
			Log.d("Create Response", json.toString());
			
			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					
					/*Open login screen*/
					Intent openStream = new Intent(SignUp.this, PictureStream.class);
					startActivity(openStream);
					
					// closing this screen
					finish();
					return json.getString(TAG_MESSAGE);
					
				} else {
					Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;	
		}
		
		//After completing background task Dismiss the progress dialog
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
		
	}
	
}
