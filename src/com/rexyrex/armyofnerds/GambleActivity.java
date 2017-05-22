package com.rexyrex.armyofnerds;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class GambleActivity extends Activity {

	SeekBar seek;
	Player player;
	Boolean gambleDone;
	TextView gambleAmount;
	Button gambleBtn;
	TextView gambleStatus;
	
	String chanceStatus;
	TextView chancesTv;
	
	String status;
	SoundPool sounds;
	
	int gambleJackPotSound;
	int gambleSuccessSound;
	int gambleFailSound;
	int gambleNothingSound;
	int gambleBtnSound;
	int backSound;
	int btnSound;
	
	Random r;
	int loseAllChance;
	int loseHalfChance;
	int nothingChance;
	int gainChance;
	int jackPotChance;
	
	FileOutputStream fos;
	DataOutputStream dos;
	String path = "GAMBLEPATH";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamble);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		gambleJackPotSound = sounds.load(getApplicationContext(), R.raw.fightwincheer, 0);
		gambleSuccessSound = sounds.load(getApplicationContext(), R.raw.loisuccess1, 0);
		gambleFailSound = sounds.load(getApplicationContext(), R.raw.failgamble, 0);
		gambleNothingSound = sounds.load(getApplicationContext(), R.raw.clarity_potion, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		btnSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		
		gambleDone = false;
		
		
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		
		status = "Went to gamble but didn't gamble";
		gambleBtn = (Button) findViewById(R.id.GambleBtnID);
		gambleAmount = (TextView) findViewById(R.id.gambleAmountActualID);
		gambleStatus = (TextView) findViewById(R.id.gambleStatusID);
		chancesTv = (TextView) findViewById(R.id.gambleChancesTvID);
		seek = (SeekBar) findViewById(R.id.gambleSeekBar);
		seek.setMax(player.getMoney());
		seek.setProgress(0);
		seek.setOnSeekBarChangeListener(sbListener);
		
		//set up chances
			r = new Random();
				loseAllChance = r.nextInt(10)+10;
				loseHalfChance = r.nextInt(10)+20;
				nothingChance = r.nextInt(5)+35;
				jackPotChance = r.nextInt(7)+3;
				gainChance = 100-loseAllChance-loseHalfChance-nothingChance-jackPotChance;
				
				//display chances
				chanceStatus = "Your Chances :\n"
								+ "Chance to Lose All : " + loseAllChance + "%\n"
								+ "Chance to Lose Half : " + loseHalfChance + "%\n"
								+ "Chance to Gain 0 Lose 0 : " + nothingChance + "%\n"
								+ "Chance to Double : " + gainChance + "%\n"
								+ "Chance for JackPot (x5) : " + jackPotChance + "%\n";
				chancesTv.setText(chanceStatus);
				chancesTv.setTextColor(Color.GREEN);
		
		//sound off
		gambleBtn.setSoundEffectsEnabled(false);
		
		gambleBtn.setText("Leave without Gambling");
		
		gambleBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				
				if(!gambleDone && seek.getProgress()!=0){
					
					//save current time as gamble time
					try {
						fos = openFileOutput(path,Context.MODE_PRIVATE);
						dos = new DataOutputStream(fos);
						Date nowDate = new Date();
						long time = nowDate.getTime();
						dos.writeLong(time);
						dos.close();
						fos.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
					gambleStatus.setText("Gambling...");
					gambleBtn.setText("Gambling... Be patient");
					sounds.play(btnSound, 1, 1, 0, 0, 1);
					try {
						Thread.sleep(770);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					int luck = (int) (Math.random() * 100);
					int moneyBet = seek.getProgress();
					
					if(luck<loseAllChance){
						sounds.play(gambleFailSound, 1, 1, 0, 0, 1);
						gambleStatus.setText("You Lost all the money...");
						status = "You Lost all the money you gambled with";
						player.setMoney(player.getMoney()-moneyBet);
						player.incMoneyLostFromGambling(moneyBet);
					} else if(luck<loseAllChance+loseHalfChance){
						sounds.play(gambleFailSound, 1, 1, 0, 0, 1);
						gambleStatus.setText("You Lost half the money...");
						status = "You Lost half the money you gambled with";
						player.setMoney(player.getMoney()-(int)(moneyBet*0.5));
						player.incMoneyLostFromGambling((int)(moneyBet*0.5));
					} else if(luck<loseAllChance+loseHalfChance+nothingChance){
						sounds.play(gambleNothingSound, 1, 1, 0, 0, 1);
						gambleStatus.setText("No Gain No Loss");
						status = "No Gain No Loss from Gambling";
					} else if(luck<loseAllChance+loseHalfChance+nothingChance+gainChance){
						sounds.play(gambleSuccessSound, 1, 1, 0, 0, 1);
						gambleStatus.setText("You DOUBLED your money gaining "+moneyBet);
						status = "You doubled the money you gambled with!";
						player.setMoney(player.getMoney()+moneyBet);
						player.incMoneyFromGambling(moneyBet);
					} else {
						sounds.play(gambleJackPotSound, 1, 1, 0, 0, 1);
						gambleStatus.setText("JACKPOT: YOU GAINED "+moneyBet*5);
						status = "You quintupled the amount you gambled with! Gratz!";
						player.setMoney(player.getMoney()+moneyBet*5);
						player.incMoneyFromGambling(moneyBet*5);
					}
					
					gambleDone = true;
					gambleBtn.setText("OK");
					player.gamble();
					
					
				} else {					
					sounds.play(btnSound, 1, 1, 0, 0, 1);
					gambleBtn.setText("Getting the hell out!");
					getOut();
				}
				
				
			}
		});	
		
	}
	
	private void getOut(){
		Intent goIntent = new Intent();
		goIntent.putExtra("modifiedPlayer", player);
		goIntent.putExtra("status", status);
		setResult(RESULT_OK,goIntent);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	sounds.release();
	    		finish();

	        }
	    }, 1700);
		
	}
	
	@Override
	public void onBackPressed() {	
		sounds.play(backSound, 1, 1, 0, 0, 1);
	}

	
	private OnSeekBarChangeListener sbListener = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			gambleAmount.setText(Integer.toString(progress));
			gambleBtn.setText("Gamble");
			if(progress==0){
				gambleBtn.setText("Leave without Gambling");
			}
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	};


}
