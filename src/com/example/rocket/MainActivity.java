package com.example.rocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rocket.service.RocketService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	public void start(View v){
		startService(new Intent(this , RocketService.class));
	}
	
	public void stop(View v){
		stopService(new Intent(this , RocketService.class));
		
	}
}
