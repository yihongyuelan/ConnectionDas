package com.seven.connection.main;
/**
 * 
 * if there's no user name,create and store it.
 * if there is an user name(check in the sharedprefrences or databases)
 * initial it in the application
 * */

import org.alljoyn.bus.sample.chat.ChatApplication;
import org.alljoyn.bus.sample.chat.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity{
	private EditText userName;
	private Button createUser;
	private SharedPreferences mPreferences;
	private String userNameStr;
	private Context mContext;
	private ChatApplication mApp;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conn_login);
		init();
	}
	
	private void init(){
		mContext = this;
		mApp = (ChatApplication) getApplication();
		userName = (EditText) findViewById(R.id.userName);
		createUser = (Button) findViewById(R.id.createUser);
		createUser.setOnClickListener(new ButtonListener());
		
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		userNameStr = mPreferences.getString("userName", "no user");
		if("no user".equals(userNameStr)){
			Toast.makeText(mContext, "Please input your name first", Toast.LENGTH_SHORT).show();
		}else {
			//go to another activity
			mApp.setUserName(userNameStr);
			Intent intent = new Intent();
			intent.setClass(mContext, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	class ButtonListener implements OnClickListener{

		public void onClick(View v) {
			userNameStr = userName.getText().toString();
			if("".equals(userNameStr)){
				Toast.makeText(mContext, "Please input correct name", Toast.LENGTH_SHORT).show();
			}else {
				mPreferences.edit().putString("userName", userNameStr).commit();
				mApp.setUserName(userNameStr);
				Intent intent = new Intent();
				intent.setClass(mContext, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	}

}
