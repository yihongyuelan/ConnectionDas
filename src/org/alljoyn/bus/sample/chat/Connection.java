/*
 * Copyright 2011, Qualcomm Innovation Center, Inc.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.alljoyn.bus.sample.chat;

import java.util.List;
import com.seven.connection.util.Utils;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class Connection extends Activity {
	private static final String TAG = "chat.Connection";
	
	private Button mSearchGroupButton;
	private Button mCreateGroupButton;
	private Utils mUtils;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conn);

		// add by man.tang
		mUtils = new Utils(this);

		mSearchGroupButton = (Button) findViewById(R.id.searchGroup);
		mSearchGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// start search
				new SearchGroupTask().execute(20000);
			}
		});

		mCreateGroupButton = (Button) findViewById(R.id.createGroup);
		mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// this thread is use to create a new connection group
				new CreateGroupTask().execute(5000);
			}
		});

		// start wifi if wifi is not open or disconnect the current access point
		new Thread(new Runnable() {
			public void run() {
				if (!mUtils.isWifiOpen()) {
					mUtils.setWifiEnabled(true);
				} else if (mUtils.isConnected()) {
					// disconnect the wifi
					mUtils.disConnectWifi();
				}
			}
		}).start();
	}

	class CreateGroupTask extends AsyncTask<Integer, Void, Boolean> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(Connection.this);
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
				Toast.makeText(Connection.this, "Create Group Successful!",
						Toast.LENGTH_SHORT).show();
				//do not jump to the hostActivity
//				Intent intent = new Intent();
//				intent.setClass(Connection.this, HostActivity.class);
//				startActivity(intent);
			} else {
				Toast.makeText(Connection.this, "Create Group Failed!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class SearchGroupTask extends AsyncTask<Integer, Void, Boolean> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(Connection.this);
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
				Toast.makeText(Connection.this,
						"searched the group and connected!", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(Connection.this,
						"wifi open failed!can not search the group!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}