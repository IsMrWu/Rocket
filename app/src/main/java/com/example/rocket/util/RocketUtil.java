package com.example.rocket.util;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.rocket.R;
import com.example.rocket.SmokeActivity;

public class RocketUtil implements OnTouchListener {
	
	WindowManager.LayoutParams mRocketParams   , mBottomParams;
	private WindowManager mWM;
	Context mContext;
	private ImageView mRocketView;
	
	public RocketUtil(Context context) {
		
		this.mContext = context;
		 mWM = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		
		initRocketParams();
		
		initBottomParams();
		
	}
	
	//=====================火箭的配置 begin===========================
	//初始化火箭的params
	private void initRocketParams() {
		mRocketParams= new WindowManager.LayoutParams();
		
		//左上角显示
		mRocketParams.gravity = Gravity.TOP+Gravity.LEFT;
		
		//定义宽高
		mRocketParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mRocketParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		
		//定义标记  ：有没有焦点，能不能够触摸
		mRocketParams.flags =   WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                 /*|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE*/
                  | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		
		//是否透明
		mRocketParams.format = PixelFormat.TRANSLUCENT;
		
		//显示的这个view 是什么类型
		mRocketParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		
	}

	//显示火箭
	public void showRocket(){
		mRocketView = new ImageView (mContext);
		mRocketView.setImageResource(R.drawable.desktop_rocket_launch_1);
		
		//1. 设置背景
		mRocketView.setBackgroundResource(R.drawable.rocket_bg);
		
		//2. 获取背景
		AnimationDrawable drawable = (AnimationDrawable) mRocketView.getBackground();
		
		//播放
		drawable.start();
		
		mWM.addView(mRocketView, mRocketParams);
		
		mRocketView.setOnTouchListener(this);
		
	}
	
	//隐藏火箭
	public void hideRocket(){
		if (mRocketView != null) {
            if (mRocketView.getParent() != null) {
                mWM.removeView(mRocketView);
            }
            mRocketView = null;
        }
	}
	
	//=====================火箭的配置 end===========================
	
	//=====================底部发射台的配置 begin===========================
	
	private void initBottomParams(){
		mBottomParams= new WindowManager.LayoutParams();
		
		//左上角显示
		mBottomParams.gravity = Gravity.BOTTOM;
		
		//定义宽高
		mBottomParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mBottomParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		
		//定义标记  ：有没有焦点，能不能够触摸
		mBottomParams.flags =   WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                 /*|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE*/
                  | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		
		//是否透明
		mBottomParams.format = PixelFormat.TRANSLUCENT;
		
		//显示的这个view 是什么类型
		mBottomParams.type = WindowManager.LayoutParams.TYPE_TOAST;
		
		
	}
	
	//显示底部发射台
	private void showBottom(){
		
		mBottomView = new ImageView(mContext);
		
		
		//1. 设置背景
		mBottomView.setBackgroundResource(R.drawable.bottom_bg);

		// 2. 获取背景
		AnimationDrawable drawable = (AnimationDrawable) mBottomView
				.getBackground();

		// 播放
		drawable.start();
		
		mWM.addView(mBottomView, mBottomParams);
	}
	
	//隐藏发射台
	private void hideBottom(){
		if (mBottomView != null) {
            if (mBottomView.getParent() != null) {
                mWM.removeView(mBottomView);
            }
            mBottomView = null;
        }
	}
	
	//=====================底部发射台的配置 end===========================
	
	
	
	//是否满足了发射
	private boolean isReadyToFly(){
		
		
		
		int [] location = new  int[2];
		
		//location 输出函数， 值已经赋给了传递进来的参数
		mRocketView.getLocationOnScreen(location);
		int rocketX = location[0];
		int rocketY = location[1];
		
		
		mBottomView.getLocationOnScreen(location);
		int bottomX = location[0];
		int bottomY = location[1];
		
		
		//顶部的判断ｎ：发射台的Y坐标 - 火箭的Y坐标 <= 火箭的高度/2 
		boolean isTop = bottomY - rocketY <= mRocketView.getHeight() / 2;
		
		
		//左边的判断： 发射台的X坐标 - 火箭的X坐标  <= 火箭的宽度 /2
		
		boolean isLeft = bottomX - rocketX <= mRocketView.getWidth() / 2;
		
		
		//右边判断： 发射台的x坐标 + 发射台的宽度  - 火箭的x坐标 >= 火箭的宽度/2
		boolean isRight = bottomX + mBottomView.getWidth() - rocketX >= mRocketView.getWidth() / 2;
		
	
		return isLeft && isTop && isRight;
		
	}
	
	
	
	/**
	 * 即将发射
	 * 让火箭移动到中间的位置。
	 */
	private void readyToFly() {
		
		Display display = mWM.getDefaultDisplay();
		
		Point outSize = new Point();
		display.getSize(outSize );
		
		
		mRocketParams.x = outSize.x /2  - mRocketView.getWidth() /2 ;
		
		mWM.updateViewLayout(mRocketView, mRocketParams);
		
	}
	
	//发射火箭
	private void fly() {
		
		//从现在的Y坐标一直递减到0   300 -- 0 ;
		int [] location = new int[2];
		mRocketView.getLocationOnScreen(location);
		int rocketY = location[1];
		
		//这里执行一个动画， 但是这个动画并不跟任何的控件挂钩。
		//之所以执行这个动画，是因为我们想捕捉到动画执行到哪一个具体的数值。 
		ValueAnimator animator = ValueAnimator.ofInt(rocketY , 0);
		
		animator.setDuration(600);
		
		animator.start();
		
		//注册一个数值发生改变更新的监听器
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				
				mRocketParams.y  = (Integer) animation.getAnimatedValue();
				
				System.out.println("y=="+mRocketParams.y );
				
				mWM.updateViewLayout(mRocketView, mRocketParams);
			}
		});
		
		//由于目前启动界面使用的上下文是服务的上下文，所以并没有任何的任务栈可言， 系统也懒得去检测当前这个应用
		//到底有没有任务栈，直接抛出一个异常。只要是非activity的上下文启动，都必须加上flag
		Intent intent = new Intent(mContext, SmokeActivity.class) ;
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
		
		
		
		/*
		for (int i = rocketY; i > 0; i--) {
			mRocketParams.y  = i;
			
			mWM.updateViewLayout(mRocketView, mRocketParams);
			
			SystemClock.sleep(10);
		}*/
	}

	
	
	
	boolean isReadyFly ;

	float mDownX , mDownY , mMoveX , mMoveY;
	private ImageView mBottomView;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			mDownX = event.getRawX();
			mDownY= event.getRawY();
			
			showBottom();
			break;
		case MotionEvent.ACTION_MOVE:
			
			mMoveX = event.getRawX();
			mMoveY= event.getRawY();
			
			
			//满足了发射条件
			if(isReadyToFly()){
				
				isReadyFly = true;
				
				//1. 变化背景
				mBottomView.setBackgroundResource(R.drawable.desktop_bg_tips_3);
			}else{
				isReadyFly = false;
				
				//1. 设置背景
				mBottomView.setBackgroundResource(R.drawable.bottom_bg);

				// 2. 获取背景
				AnimationDrawable drawable = (AnimationDrawable) mBottomView
						.getBackground();

				// 播放
				drawable.start();
			}
			
			//移动的时候一定要累加起来，不然就移不动。
			mRocketParams.x += Math.round(mMoveX - mDownX);  
			mRocketParams.y += Math.round(mMoveY - mDownY);
			
			mWM.updateViewLayout(mRocketView, mRocketParams);
			
			mDownX = mMoveX;
			mDownY = mMoveY;
					
			break;
		case MotionEvent.ACTION_UP:
			
			//什么时候能跑到中间去呢。 up的时候可以过去 &&  是否满足前面的发射条件
			if(isReadyFly){
				//移动到中间位置
				readyToFly();
				
				//执行发射
				fly();
			}
			
			hideBottom();
			break;
		}
		return true;
	}

	

}
