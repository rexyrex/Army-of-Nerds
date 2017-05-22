package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RaidGymActivity extends Activity {

	
	Player player;
	EditText minutes;
	TextView expGainTv;
	Button calc;
	TextView loiChanceTv;
	TextView mRequired;
	SoundPool sounds;
	int clickSound;
	int backSound;
	int gladosshit;
	int textChangeSound;
	boolean loiObtained = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raid_gym);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		gladosshit = sounds.load(getApplicationContext(), R.raw.gladosholyshit, 0);
		textChangeSound = sounds.load(getApplicationContext(), R.raw.ui_click_simple_01, 0);
		
		Intent intent = getIntent();		
		player = (Player) intent.getSerializableExtra("player");
		
		minutes = (EditText) findViewById(R.id.etGymMinutes);
		expGainTv = (TextView) findViewById(R.id.moneyAmountLeftAtGymID);
		calc = (Button) findViewById(R.id.gymMinutesOKBtnID);
		loiChanceTv = (TextView) findViewById(R.id.nNerdsToworkOutValueID);
		mRequired = (TextView) findViewById(R.id.estimatedCostToWorkOutValueID);
		Toast.makeText(getApplicationContext(), "HERE", Toast.LENGTH_SHORT).show();

		mRequired.setText("0");		
		expGainTv.setText("0");
		loiChanceTv.setText("0");
		
		minutes.addTextChangedListener(textListener);
		
		
		
		calc.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				int time=0;
				try{				
					time = Integer.parseInt(minutes.getText().toString());
				} catch(NumberFormatException e){
					time = 0;
					doneWorkOut("Was thinking about going to the gym but thought against it cuz too much pain");
				}
				
				int money = player.getMoney();
				if(time*27>money){
					Toast.makeText(getApplicationContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();
				} else {
					int loiChance = (int) (Math.random()*28);
					int n=0;
					
					for(int i=0; i<time; i++){
						n = (int) (Math.random()*28);
						if(n==loiChance){
							loiObtained = true;
							break;
						}
					}
					player.setExp(player.getExp()+time*3);					
					player.setMoney(player.getMoney()-time*27);
					player.incExpFromGym(time*3);
					player.incMinutesAtGym(time);
					player.incMoneySpentAtGym(time*27);
					player.goGym();
					if(loiObtained){
						player.incLoiFromGym();
						sounds.play(gladosshit, 1, 1, 0, 0, 1);
						player.setLoi(player.getLoi()+1);
						goGetLoi();
						
					} else {
						doneWorkOut("Went to Gym for "+time+" minutes which cost "+(time*27)+" money\nLee do mou lang loi!!");	
					}
				}				
			}
		});		
	}
	
	protected void goGetLoi() {
		Intent loiIntent = new Intent(RaidGymActivity.this, LoiActivity.class);
		startActivity(loiIntent);
		
	}

	private TextWatcher textListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
	

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			sounds.play(textChangeSound, 1, 1, 0, 0, 1);
			
			try{
				double chance = ((double)1/27);
				int minutesInput = Integer.parseInt(s.toString());
				int cost = minutesInput*27 ;
				mRequired.setText(Integer.toString(cost));
				
				
				for(int i=0; i<minutesInput; i++){
					chance += (double)1/27;
				}
				
				
				int chanceToInt = (int) (chance*100);
				
				loiChanceTv.setText(getLoiChance(chanceToInt));
				expGainTv.setText(Integer.toString(minutesInput*3));
				
				if(cost> player.getMoney()){
					mRequired.setTextColor(Color.RED);
				} else {
					mRequired.setTextColor(Color.GREEN);
				}
			}
			catch(NumberFormatException e){
				mRequired.setTextColor(Color.CYAN);				
				expGainTv.setText("Need valid Input dude...");
				loiChanceTv.setText("Need valid Input dude...");
				mRequired.setText("Need valid Input dude...");
			}
			
			
			
		}
		};
		
		
	public String getLoiChance(int chance){
		if(chance<6){
			return "You got no chance bro...";
		} else if(chance<20){
			return "it's a long shot!";
		} else if(chance<40){
			return "She took a glimps at you";
		} else if(chance<70){
			return "She's interested";
		} else if(chance<90){
			return "Very Probable";
		} else {
			return "almost certain but depends on a bit of luck";
		}
	}
	
	
	
	@Override
	protected void onResume() {		
		super.onResume();
		if(loiObtained == true){
			doneWorkOut("OMG YOU OBTAINED A LANG LOI LUCKY BASTARD");
		}
		
	}



	public void doneWorkOut(String status){
		Intent newIntent = new Intent();
		newIntent.putExtra("modifiedPlayer",player);
		newIntent.putExtra("status", status);
		setResult(RESULT_OK, newIntent);
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "Ur at the gym. Now make use of it.", Toast.LENGTH_SHORT).show();
	}

}
