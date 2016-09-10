package com.example.rocket.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;

import com.example.rocket.util.RocketUtil;

public class RocketService extends Service {

	private RocketUtil mRocketUtil;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//显示火箭
	@Override
	public void onCreate() {
		super.onCreate();
		
		AnimationDrawable animationDrawable;
		
		mRocketUtil = new RocketUtil(this);
		mRocketUtil.showRocket();
	}
	
	
	//隐藏火箭
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mRocketUtil.hideRocket();
	}
}
