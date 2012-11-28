package com.seven.connection.main;

import java.util.List;

import org.alljoyn.bus.sample.chat.ChatApplication;
import org.alljoyn.bus.sample.chat.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.connection.util.Utils;
import com.seven.game.differencessolver.DifferencesSolver;

public class MainActivity extends Activity{
	private static final String TAG = "chat.Connection";
	
	private Context mContext;
	private Utils mUtils;
	private TextView gameTextView;
	private ChatApplication mApp;
	private Button mCreateButton;
	private Button mSearchButton;
    private long waitTime = 2000;//twice press then exit
    private long touchTime = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.conn_main);

		// add by man.tang
		init();

//		mSearchGroupButton = (Button) findViewById(R.id.searchGroup);
//		mSearchGroupButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				// start search
//				new SearchGroupTask().execute(20000);
//			}
//		});

		// start wifi if wifi is not open or disconnect the current access point
//		new Thread(new Runnable() {
//			public void run() {
//				if (!mUtils.isWifiOpen()) {
//					mUtils.setWifiEnabled(true);
//				} else if (mUtils.isConnected()) {
//					// disconnect the wifi
//					mUtils.disConnectWifi();
//				}
//			}
//		}).start();
	}
	
	private void init(){
		mUtils = new Utils(this);
		mContext = MainActivity.this;
		mApp = (ChatApplication) getApplication();
		gameTextView = (TextView) findViewById(R.id.gameTV);
		mCreateButton = (Button) findViewById(R.id.createConBtn);
		mSearchButton = (Button) findViewById(R.id.searchConBtn);
		gameTextView.setOnClickListener(mClickListener);
		mCreateButton.setOnClickListener(mClickListener);
		mSearchButton.setOnClickListener(mClickListener);
	}
	
	OnClickListener mClickListener = new OnClickListener() {		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.gameTV:
				Intent intent = new Intent();
				intent.setClass(mContext, DifferencesSolver.class);
				startActivity(intent);
				break;
			case R.id.createConBtn:
				mSearchButton.setEnabled(false);
				// this thread is use to create a new connection group
				new CreateGroupTask().execute(5000);
				break;
			case R.id.searchConBtn:
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime - touchTime) >= waitTime) {
			Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT).show();
			touchTime = currentTime;
		} else {
			mApp.quit();
			finish();
		}
	}

    


	class CreateGroupTask extends AsyncTask<Integer, Void, Boolean> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage("Creating Group,Please wait...");
			mDialog.setCancelable(false);
			mDialog.setIndeterminate(true);
			mDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			boolean isCreateGroupSucess = false;
			if (!mUtils.isTetheringSupport()) {
				Log.i(TAG, "not support tethering");
				return isCreateGroupSucess;
			}
			mUtils.createHostGroup("Seven");
			for (int i = 0; i < params[0] / 1000; i++) {
				if (mUtils.isCreateGroupSucess()) {
					isCreateGroupSucess = true;
					break;
				}
				SystemClock.sleep(1000);
			}
			return isCreateGroupSucess;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			if (result) {
				Toast.makeText(mContext, "Create Group Successful!",
						Toast.LENGTH_SHORT).show();
				//do not jump to the hostActivity
//				Intent intent = new Intent();
//				intent.setClass(Connection.this, HostActivity.class);
//				startActivity(intent);
			} else {
				Toast.makeText(mContext, "Create Group Failed!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class SearchGroupTask extends AsyncTask<Integer, Void, Boolean> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage("Searching Group,Please wait...");
			mDialog.setCancelable(false);
			mDialog.setIndeterminate(true);
			mDialog.show();
			if (!mUtils.isWifiOpen()) {
				mUtils.setWifiEnabled(true);
			}
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			boolean isConnected = false;
			boolean isFirst = true;

			for (int i = 0; i < params[0] / 1000; i++) {
				if (mUtils.isWifiOpen()) {
					if (isFirst) {
						isFirst = false;
						List<ScanResult> results = mUtils.searchGroup();
						mUtils.connectGroup(results, "Seven");
					}
					if (mUtils.isConnected()) {
						isConnected = true;
						break;
					}
				}

				SystemClock.sleep(1000);
			}
			return isConnected;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			if (result) {
				Toast.makeText(mContext,
						"searched the group and connected!", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(mContext,
						"wifi open failed!can not search the group!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
