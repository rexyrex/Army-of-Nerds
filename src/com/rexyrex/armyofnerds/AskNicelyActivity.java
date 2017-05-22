package com.rexyrex.armyofnerds;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AskNicelyActivity extends Activity {
	
	Button accept;
	Button cry;
	TextView status;
	Player player;
	int chance;
	int nerdsAquired;
	int muggedAmount;
	
	SoundPool sounds;
	int clickSound;
	int backSound;
	int gladosWrong;
	int successSound;
	int failSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_nicely);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		gladosWrong = sounds.load(getApplicationContext(), R.raw.gladoswrong, 0);
		successSound = sounds.load(getApplicationContext(), R.raw.successclap, 0);
		failSound = sounds.load(getApplicationContext(), R.raw.failbell, 0);
		
		Intent getIntent = getIntent();		
		player = (Player) getIntent.getSerializableExtra("player");
		
		//stats
		player.askNicely();
		
		accept = (Button) findViewById(R.id.askAcceptBtnID);
		cry = (Button) findViewById(R.id.askTryAgainBtnID);
		status = (TextView) findViewById(R.id.askStatusActualID);
		
		accept.setSoundEffectsEnabled(false);
		cry.setSoundEffectsEnabled(false);
		
		chance = (int) (Math.random()*100);
		
		try {
			Thread.sleep(270);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(player.getMoney()<200){
			status.setText("You need at least 200 money to ask cuz ur friends are pretty spoiled");
			status.setTextColor(Color.CYAN);
		} else if(chance<60){
			sounds.play(successSound, 1, 1, 0, 0, 1);	
			String statusStr = "";
			nerdsAquired = (int) (0.3*(double)player.getMoney()/35);
			if(player.getNerdRoom()-nerdsAquired<0){
				nerdsAquired=player.getNerdRoom();
				statusStr += "You reached your nerd limit. Time to kill.";
			}
			statusStr += "You just recruited "+nerdsAquired+" nerds!";
			status.setText(statusStr);
			status.setTextColor(Color.GREEN);
			player.setNerds(player.getNerds()+nerdsAquired);
			player.incNerdsFromAskingNicely(nerdsAquired);
		} else {
			sounds.play(failSound, 1, 1, 0, 0, 1);	
			nerdsAquired = 0;
			muggedAmount = (int) (player.getMoney()*0.3+200);			
			if(player.getMoney()-muggedAmount<0){
				muggedAmount = player.getMoney();
			}
			player.incMoneyMuggedFromAskingNicely(muggedAmount);
			status.setText("LOL you just got mugged and lost "+player.loseMoney(muggedAmount)+" money");
			status.setTextColor(Color.RED);			
		}
		

		
		
		accept.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);	
				accept();				
			}
		});
		
		cry.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(gladosWrong, 1, 1, 0, 0, 1);
				cry();				
			}
		});		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "just accept it", Toast.LENGTH_SHORT).show();
	}

	public void cry(){
		player.incNumberOfTimesCried();
		Toast.makeText(getApplicationContext(), "You achieved Nothing", Toast.LENGTH_SHORT).show();		
	}
	
	public void accept(){
		Intent aIntent = new Intent();
		aIntent.putExtra("modifiedPlayer", player);
		setResult(RESULT_OK, aIntent);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	sounds.release();
	    		finish();	
	        }
	    }, 27);
			
	}

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
    	//String code = Integer.toString(resultCode);
    	//String code2 = Integer.toString(requestCode);
    	//Toast.makeText(getApplicationContext(), "Request Code: "+code2, Toast.LENGTH_SHORT).show();
    	//Toast.makeText(getApplicationContext(), "Result Code: "+code, Toast.LENGTH_SHORT).show();
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){
    			//Toast.makeText(getApplicationContext(), "LOOLOOLALA", Toast.LENGTH_SHORT).show();
                player = (Player) data.getSerializableExtra("player");
                //recruited += (int) data.getIntExtra("recruited", 0);
                
            }        
    	}    	
    }
	

}
