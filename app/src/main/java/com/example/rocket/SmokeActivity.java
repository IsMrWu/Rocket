package com.example.rocket;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SmokeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_smoke);
		
		View containerView = findViewById(R.id.container);

		
		//出来的时候动画
		ObjectAnimator obj01 = 	ObjectAnimator.ofFloat(containerView, "alpha", 0 , 1);
		//隐藏
		ObjectAnimator obj02 = ObjectAnimator.ofFloat(containerView, "alpha", 1 , 0);
		
		
		AnimatorSet set  = new AnimatorSet();
		
		set.playSequentially(obj01 , obj02);
		
		set.setDuration(400);
		
		
		set.start();
		
		set.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				finish();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		
		
		
		//containerView.startAnimation(set);
	}
}
