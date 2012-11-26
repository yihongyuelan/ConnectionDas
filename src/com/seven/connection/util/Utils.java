package com.seven.connection.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.os.SystemClock;
import android.util.Log;

public class Utils {
	private static final String TAG = "Utils";

	private Context context;
	private WifiManager mWifiManager;
	private ConnectivityManager mConnectivityManager;
	private boolean isSucess = false;
	private boolean isConnected = false;

	public Utils(Context context) {
		super();
		this.context = context;
		initial();
	}

	/**
	 * initial the global object
	 * 
	 * */
	private void initial() {
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean isTetheringSupport() {
		boolean isSupport = false;
		try {
			Object obj = context.getSystemService(Context.CONNECTIVITY_SERVICE);
			Method m = obj.getClass().getDeclaredMethod("isTetheringSupported");
			isSupport = ((Boolean) m.invoke(obj)).booleanValue();
			Log.i(TAG, "isSupport=" + isSupport);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return isSupport;
	}

	public void disConnectWifi(){
		mWifiManager.disconnect();
	}
	
	public boolean isConnected() {
		State wifiState = mConnectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();
		if(State.CONNECTED == wifiState){
			isConnected = true;
		}
		return isConnected;
	}

	public boolean isCreateGroupSucess() {
		return isSucess;
	}

	/**
	 * create a hotSpot with out password
	 * 
	 * */
	public void createHostGroup(String ssid) {
		WifiConfiguration config = new WifiConfiguration();
		config.SSID = ssid;
		config.allowedKeyManagement.get(KeyMgmt.NONE);
		setTetherConfiguration(config);
		isSucess = setTetherEnabled(true);
		// thread can not support the toast
		// if (isCreateSucess){
		// Toast.makeText(context, "create group sucess!!",
		// Toast.LENGTH_SHORT).show();
		// }else {
		// Toast.makeText(context, "create group failed!!",
		// Toast.LENGTH_SHORT).show();
		// }
	}

	/**
	 * enable or disable the Tethering
	 * */
	public boolean setTetherEnabled(boolean enable) {
		Class c = WifiManager.class;
		try {
			Class[] parameterTypes = new Class[] { WifiConfiguration.class,
					boolean.class };
			Method m = c.getDeclaredMethod("setWifiApEnabled", parameterTypes);
			Object[] args = new Object[] { null, enable };
			m.invoke(mWifiManager, args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * update the wificonfiguration
	 * 
	 * */
	public void setTetherConfiguration(WifiConfiguration config) {
		Class c = WifiManager.class;
		try {
			Class[] parameterTypes = new Class[] { WifiConfiguration.class };
			Method m = c.getDeclaredMethod("setWifiApConfiguration",
					parameterTypes);
			Object[] args = new Object[] { config };
			m.invoke(mWifiManager, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isWifiApEnabled() {
		boolean isEnabled = false;
		try {
			Class c = WifiManager.class;
			Method m = c.getDeclaredMethod("isWifiApEnabled");
			isEnabled = ((Boolean) m.invoke(mWifiManager)).booleanValue();
			Log.i(TAG, "isEnabled=" + isEnabled);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return isEnabled;
	}

	public boolean isWifiOpen() {
		if (mWifiManager.isWifiEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void setWifiEnabled(boolean enalbe) {
		mWifiManager.setWifiEnabled(enalbe);
	}

	public List<ScanResult> searchGroup() {
		if (!isWifiOpen()) {
			Log.e(TAG, "wifi is not open!");
			return null;
		}
		if (!mWifiManager.startScan()) {
			Log.e(TAG, "start scan return false!");
			return null;
		}
		SystemClock.sleep(3000);
		List<ScanResult> scanResultList = mWifiManager.getScanResults();
		if (null == scanResultList)
			SystemClock.sleep(1000);
		scanResultList = mWifiManager.getScanResults();
		// scanResultList.get(0);
		// Log.i(TAG, "scanResultList.size():"+scanResultList.size());
		return scanResultList;
	}

	public void connectGroup(List<ScanResult> scanResultList, String GroupName) {
		if (null == scanResultList) {
			Log.e(TAG, "Scan result is null!!");
			return;
		}
		for (int i = 0; i < scanResultList.size(); i++) {
			ScanResult scanRet = scanResultList.get(i);
			if (scanRet.SSID.equalsIgnoreCase(GroupName)) {
				Log.i(TAG, "find it");
				WifiConfiguration config = new WifiConfiguration();
				config.SSID = "\"" + scanRet.SSID + "\"";
				config.hiddenSSID = true;
				config.allowedAuthAlgorithms
						.set(WifiConfiguration.AuthAlgorithm.OPEN);
				config.allowedGroupCiphers
						.set(WifiConfiguration.GroupCipher.TKIP);
				config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
				config.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.TKIP);
				config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
				config.status = WifiConfiguration.Status.ENABLED;
				int netID = mWifiManager.addNetwork(config);
				Log.i(TAG, "netID:" + netID);
				boolean bRet = mWifiManager.enableNetwork(netID, true);
				Log.i(TAG, "bRet:" + bRet);
			}
		}
	}

}
