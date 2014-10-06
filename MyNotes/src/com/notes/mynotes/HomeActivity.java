package com.notes.mynotes;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeActivity extends ActionBarActivity {
	
	EditText usernameEditText;
	EditText passwordEditText;
	Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Parse.initialize(this, "u3qzURZ2rXLAtojWaldZhnzqvdJ11ErZdMN0CCQD", "4UKSvTU3iOcbcIXxok9ThpbUTscrV5C5zf7Vxk40");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void changeTitle(View view) {
    	CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_register);
    	button = (Button) findViewById(R.id.button_signIn_or_register);
    	if(checkBox.isChecked()) {
    		button.setText("Register");
    	} else {
    		button.setText("Sign in");
    	}
    }
    
    public void signIn_or_register(View view) {
    	button = (Button) findViewById(R.id.button_signIn_or_register);
    	usernameEditText = (EditText) findViewById(R.id.editText_User);
		passwordEditText = (EditText) findViewById(R.id.editText_Password);
		
		// get user details
		String sUsername = usernameEditText.getText().toString();
		String sPassword = passwordEditText.getText().toString();
		
    	if("Sign In".equalsIgnoreCase(button.getText().toString())) {
    		
    		if("".equalsIgnoreCase(sUsername) || "".equalsIgnoreCase(sPassword)) {
    			Toast.makeText(this, "Can't Register\n Forgot something?", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Users");
    		parseQuery.whereEqualTo("username", sUsername);
    		parseQuery.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e==null) {
						if(objects.size() > 0){
							Toast.makeText(getApplicationContext(), "Successfully signed in", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
						}
					}else {
						Log.e("sign in", "exception in fetching");
					}
				}
			});
    		
    	}else if("Register".equalsIgnoreCase(button.getText().toString())) {
    	   		
    		if("".equalsIgnoreCase(sUsername) || "".equalsIgnoreCase(sPassword)) {
    			Toast.makeText(this, "Can't Register\n Forgot something?", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		
    		// create user object and save to ParseObject in cloud
    		ParseObject user = new ParseObject("Users");
    		user.put("username", usernameEditText.getText().toString());
    		user.put("password", passwordEditText.getText().toString());
    		user.saveInBackground();
    		
    		Toast.makeText(this, "Successfully Registered the User", Toast.LENGTH_LONG).show();
    	}
    }
}