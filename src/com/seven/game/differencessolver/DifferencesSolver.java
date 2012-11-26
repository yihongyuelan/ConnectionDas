package com.seven.game.differencessolver;

import java.util.Random;
import org.alljoyn.bus.sample.chat.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DifferencesSolver extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private TextView duakTitle;
	private TextView duakAnte;
	private TextView playAnte;
	private TextView allScore;
	private TextView playTitle;

	private Button ante;
	private Button start;
	private Button moreScore;
	private ImageButton cloth;
	private ImageButton fist;
	private ImageButton shears;
	private ImageView duakchoose;
	private ImageView playchoose;

	private ImageView duak;
	
    boolean update_text = false;
    String displayText;

	private int clothNo=1;
	private int fistNo=2;
	private int shearsNo=3;
	
	private int duakChooseNo=fistNo;
	private int playChooseNo=fistNo;
	
	private int winNo=1;
	private int lostNo=2;
	private int drawNo=3;
	private int firstInFistAward=20;
	
	private int anteNo=0;
	private int currentScore=0;
	private Context mContext;
	private boolean isThreadStart=false;
	
	private int title[]={
			R.string.guess_title_01,
			R.string.guess_title_02,
			R.string.guess_title_03,
			R.string.guess_title_04,
			R.string.guess_title_05,
			R.string.guess_title_06,
			R.string.guess_title_07,
			R.string.guess_title_08

	};
	private int duckShow[]={
			R.drawable.test_result_mood_001,
			R.drawable.test_result_mood_002,
			R.drawable.test_result_mood_003,
			R.drawable.test_result_mood_004,
			R.drawable.test_result_mood_005,
			R.drawable.test_result_mood_006};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guessinggame); 
        this.setTitle("刀疤鸭猜拳");
//        AppConnect.getInstance(this);
//		AppConnect.getInstance(this).awardPoints(firstInFistAward, this);

        init();
        dataInit();

    }

	 DelayThread dThread = null;       

	 public void startProgressUpdate(){   
	        //开辟Thread 用于定期刷新SeekBar
	        isThreadStart=true;
	        dThread.start();  

	 
	    } 
	 public void stopProgressUpdate(){   
	        //开辟Thread 用于定期刷新SeekBar   
	        isThreadStart=false;
	        Log.e("guessgame", "guessgame");
	        dThread.stop();  

	 
	    } 
	
	 private Handler mHandle = new Handler(){   
	        public void handleMessage(Message msg){   	               
	   
	        	setDuakChoose();
	        }   
	    };   
	  
	    public class DelayThread extends Thread {   
	        int milliseconds;   
	           
	        public DelayThread(int i){   
	            milliseconds = i;   
	        }   
	        public void run() {

	            while(isThreadStart){
	                duakChooseNo=++duakChooseNo%3+1;   
	                mHandle.sendEmptyMessage(0);  
	                try {   
	                    sleep(milliseconds);   
	                } catch (InterruptedException e) {   
	                    // TODO Auto-generated catch block   
	                    e.printStackTrace();   
	                }   
	     
	            }   
	        }   
	    } 
    
	private void setDuakChoose(){
		
					Log.e("guessgame", ""+duakChooseNo);

					if(duakChooseNo==fistNo)
						duakchoose.setBackgroundResource(R.drawable.guessgame_fist);
					else if(duakChooseNo==clothNo)
						duakchoose.setBackgroundResource(R.drawable.guessgame_cloth);
					else if(duakChooseNo==shearsNo)
						duakchoose.setBackgroundResource(R.drawable.guessgame_shears);

	}


	private void dataInit() {
		// TODO Auto-generated method stub
		Random rd1 = new Random();
		
		duakTitle.setText(title[rd1.nextInt(title.length)]);
		duak.setBackgroundResource(duckShow[rd1.nextInt(duckShow.length)]);
		GetGameTitlesClass mGetGameTitlesClass=new GetGameTitlesClass(this);
		playTitle.setText(mGetGameTitlesClass.getTitles(currentScore));
//		AppConnect.getInstance(this).getPoints(this);

	}




	private void init() {
		// TODO Auto-generated method stub
		mContext=this;
		duakTitle = (TextView) this.findViewById(R.id.guessgame_activity_tv_title);
		duakAnte = (TextView) this.findViewById(R.id.guessgame_activity_tv_duak_ante);
		playAnte = (TextView) this.findViewById(R.id.guessgame_activity_tv_play_ante);
		allScore = (TextView) this.findViewById(R.id.guessgame_activity_tv_score);
		playTitle = (TextView) this.findViewById(R.id.guessgame_activity_tv_play_title);

		

		ante = (Button) this.findViewById(R.id.guessgame_activity_b_ante);
		ante.setOnClickListener(this);
		start = (Button) this.findViewById(R.id.guessgame_activity_b_start);
		start.setOnClickListener(this);		
		moreScore = (Button) this.findViewById(R.id.guessgame_activity_b_morescore);
		moreScore.setOnClickListener(this);

		cloth = (ImageButton) this.findViewById(R.id.guessgame_activity_ib_cloth);
		cloth.setOnClickListener(this);

		fist = (ImageButton) this.findViewById(R.id.guessgame_activity_ib_fist);
		fist.setOnClickListener(this);
	
		shears = (ImageButton) this.findViewById(R.id.guessgame_activity_ib_shears);
		shears.setOnClickListener(this);
	
		duakchoose = (ImageView) this.findViewById(R.id.guessgame_activity_iv_duak_choose);
		playchoose = (ImageView) this.findViewById(R.id.guessgame_activity_iv_play_choose);
	
		duak=(ImageView)this.findViewById(R.id.guessgame_activity_iv_imageshow);
	}




	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.guessgame_activity_b_ante: {
			anteNo=0;
           	new AlertDialog.Builder(this).setTitle("单选框").setIcon(
        			android.R.drawable.ic_dialog_info).setSingleChoiceItems(
        			new String[] { "0", "10" ,"20","30","40","50"}, 0,
        			new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							
							switch (which) {
							case 0:
								anteNo=0;
								break;
							case 1:
								anteNo=10;
								break;
							case 2:
								anteNo=20;
								break;
							case 3:
								anteNo=30;
								break;
							case 4:
								anteNo=40;
								break;
							case 5:
								anteNo=50;
								break;
							default:
								break;
							}
							
						}
        		}
        			).setNegativeButton("确定", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub						

							if(currentScore-anteNo>=0)
							{
					        	duakAnte.setText(""+anteNo);
					        	playAnte.setText(""+anteNo);
					        	//allScore.setText(""+(currentScore-anteNo));
							}						
							else
							{
								anteNo=0;
	                            Toast.makeText(DifferencesSolver.this, "亲，没有积分哦！", Toast.LENGTH_SHORT).show();

							}
						}}).show();
           	

        	
		}break;
		case R.id.guessgame_activity_b_start: {
			if(isThreadStart)
			{	
				
				start.setText("开始 ");
				
				if(currentScore-anteNo<0)
				{
					anteNo=0;
		        	duakAnte.setText(""+anteNo);
		        	playAnte.setText(""+anteNo);
				}
				Log.e("testcurrentScore", ""+currentScore);
				Log.e("testanteNo", ""+anteNo);

		        stopProgressUpdate();
				cloth.setClickable(true);
				fist.setClickable(true);		
				shears.setClickable(true);
				ante.setClickable(true);
				String result="猜拳本次结果：";

		        if(isWin(duakChooseNo,playChooseNo)==winNo)
		        {
		        	//Toast.makeText(GuessingGameActivity.this, "亲，赢了！", Toast.LENGTH_SHORT).show();		        
		        	result=result+ "亲，赢了"+anteNo+"积分";
		        	currentScore=anteNo+currentScore;
//	            	AppConnect.getInstance(this).awardPoints(anteNo, this);
	        		//allScore.setText(""+(currentScore-anteNo)+"积分");

		        }
		        else if(isWin(duakChooseNo,playChooseNo)==lostNo)
		        {
		        	result=result+ "亲，输了"+anteNo+"积分";
		        	//Toast.makeText(GuessingGameActivity.this, "亲，输了！", Toast.LENGTH_SHORT).show();
		        	currentScore=currentScore-anteNo;
//	                AppConnect.getInstance(this).spendPoints(anteNo, this);
	        		//allScore.setText(""+(currentScore-anteNo)+"积分");

		        }
		        else if(isWin(duakChooseNo,playChooseNo)==drawNo)
		        	{
		        	 result=result+ "亲，和了";
		        	//Toast.makeText(GuessingGameActivity.this, "亲，和了！", Toast.LENGTH_SHORT).show();
		        	}
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.guessgame_fist_icon)
				.setTitle("本次猜拳结果")
				.setMessage(result)
//				.setNegativeButton("离开战场",
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//									AppConnect.getInstance(GuessingGameActivity.this).finalize();
//									finish();
//							}
//						})
				.setPositiveButton("继续厮杀",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								    dataInit() ;
								    
							}
						}).show();
				
			}
			else
			{
				dThread = new DelayThread(50);				
		        start.setText("停止 ");
		        startProgressUpdate();
				cloth.setClickable(false);
				fist.setClickable(false);		
				shears.setClickable(false);
				ante.setClickable(false);
			}
		}break;
		case R.id.guessgame_activity_b_morescore: {
//            AppConnect.getInstance(this).showOffers(this);

		}break;
		case R.id.guessgame_activity_ib_cloth: {
			playchoose.setBackgroundResource(R.drawable.guessgame_cloth);
			playChooseNo=clothNo;
		}break;
		case R.id.guessgame_activity_ib_fist: {
			playchoose.setBackgroundResource(R.drawable.guessgame_fist);
			playChooseNo=fistNo;
		}break;
		case R.id.guessgame_activity_ib_shears: {
			playchoose.setBackgroundResource(R.drawable.guessgame_shears);
			playChooseNo=shearsNo;

		}break;
	}
}
	
	private int isWin(int duakChooseNo,int playChooseNo)
	{
		if(playChooseNo==fistNo && duakChooseNo==shearsNo)		return winNo;
		if(playChooseNo==clothNo && duakChooseNo==fistNo)		return winNo;
		if(playChooseNo==shearsNo && duakChooseNo==clothNo)		return winNo;

		if(playChooseNo==fistNo && duakChooseNo==fistNo)		return drawNo;
		if(playChooseNo==clothNo && duakChooseNo==clothNo)		return drawNo;
		if(playChooseNo==shearsNo && duakChooseNo==shearsNo)	return drawNo;
		
		return lostNo;
		
	}
    final Handler mHandler = new Handler();
	
    // 创建一个线程
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            if (allScore != null) {
                if (update_text) {
                	
                	allScore.setText(displayText);
            		if(currentScore-anteNo<0)
            		{
            			anteNo=0;
            			duakAnte.setText(""+anteNo);
            			playAnte.setText(""+anteNo);
            		}
                    update_text = false;
                }
            }
        }
    };
    /**
     * AppConnect.getPoints()方法的实现，必须实现
     *
     * @param currencyName
     *            虚拟货币名称.
     * @param pointTotal
     *            虚拟货币余额.
     */
    public void getUpdatePoints(String currencyName, int pointTotal) {
       
    	currentScore=pointTotal;
    	
    	displayText=""+(currentScore-anteNo)+"积分";
    	update_text = true;        
        mHandler.post(mUpdateResults);
    }

    /**
     * AppConnect.getPoints() 方法的实现，必须实现
     *
     * @param error
     *            请求失败的错误信息
     */

    public void getUpdatePointsFailed(String error) {
        update_text = true;
        displayText = "网络连接中";
        mHandler.post(mUpdateResults);
    }
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			AppConnect.getInstance(GuessingGameActivity.this).finalize();
			finish();
			return true;

		} else {
			return super.onKeyDown(keyCode, event);
		}

}
	
	@Override
    protected void onDestroy() {
//        AppConnect.getInstance(this).finalize();
        super.onDestroy();
    }
	
    @Override
    protected void onResume() {
//        AppConnect.getInstance(this).getPoints(this);
        super.onResume();
    }
}