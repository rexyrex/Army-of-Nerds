package com.rexyrex.armyofnerds;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class UpgradeActivity extends Activity {

	Button quitbtn;
	Button raidGymBtn;
	Button recruitNerdsBtn;
	Player player;
	String status;
	TextView statusView;
	TextView moneyTv;
	Button raidLibraryBtn;
	Button goGambleBtn;
	Button sellNerdsBtn;
	
	//gamble time 
	FileInputStream fis;
	DataInputStream dis;
	String path = "GAMBLEPATH";
	long lastGambleTime;
	FileOutputStream fos;
	DataOutputStream dos;
	
	
	int songPosition;
	
	MediaPlayer song;
	SoundPool sounds;
	int btnSound;
	int backSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrade);
		
		
		
		
		status = "None";
		recruitNerdsBtn = (Button) findViewById(R.id.recruitNerdsID);
		quitbtn = (Button) findViewById(R.id.backToMenuFromUpgradeID);
		raidGymBtn = (Button) findViewById(R.id.raidGymID);
		statusView = (TextView) findViewById(R.id.recentChangesActualID);
		moneyTv = (TextView) findViewById(R.id.uCurrentMoneyActualID);
		raidLibraryBtn = (Button) findViewById(R.id.raidLibraryID);
		goGambleBtn = (Button) findViewById(R.id.gambleID);
		sellNerdsBtn = (Button) findViewById(R.id.sellNerdsID);
		
		recruitNerdsBtn.setSoundEffectsEnabled(false);
		quitbtn.setSoundEffectsEnabled(false);
		raidGymBtn.setSoundEffectsEnabled(false);
		raidLibraryBtn.setSoundEffectsEnabled(false);
		goGambleBtn.setSoundEffectsEnabled(false);
		sellNerdsBtn.setSoundEffectsEnabled(false);
		
		Intent intent = getIntent();
		player = (Player) intent.getSerializableExtra("player");
		songPosition = intent.getIntExtra("songPosition", 0);
		
		song = MediaPlayer.create(getApplicationContext(), R.raw.upgrade_back3);
		
		song.seekTo(songPosition);
		
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		btnSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		
		sellNerdsBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				goSellNerds();				
			}
		});
		
		goGambleBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Date newDate = new Date();
				long currentTime = newDate.getTime();
				long timeDiff = currentTime-lastGambleTime;
				if(timeDiff<(1000*60*5)){
					sounds.play(backSound, 1, 1, 0, 0, 1);
					player.incGambleBeforeTimer();
					Toast.makeText(getApplicationContext(), "You have to wait : "+(int)(60*5-timeDiff/1000)+"seconds", Toast.LENGTH_SHORT).show();
				} else {
					sounds.play(btnSound, 1, 1, 0, 0, 1);
					goGamble();
				}
			}
		});
		
		raidLibraryBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				goRaidLibrary();				
			}
		});
		
		recruitNerdsBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				song.pause();
				
				goRecruitNerds();				
			}
		});
		
		
		raidGymBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				goGym();				
			}
		});
		quitbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				songPosition = song.getCurrentPosition();
				
				final Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	song.release();
						sounds.release();
						quitToMain();

			        }
			    }, 27);
				
								
			}
		});	
		
	}
	
	private void goSellNerds(){
		Intent gsnIntent = new Intent(this, SellNerdsActivity.class);
		gsnIntent.putExtra("player",player);
		startActivityForResult(gsnIntent,1);
	}
	
	public void goGamble(){
		Intent ggIntent = new Intent(this, GambleActivity.class);
		ggIntent.putExtra("player", player);
		startActivityForResult(ggIntent,1);
	}
	
	public void goRaidLibrary(){
		Intent grlIntent = new Intent(this, RaidLibraryActivity.class);
		grlIntent.putExtra("player", player);
		startActivityForResult(grlIntent,1);
	}
	
	public void goRecruitNerds(){
		Intent grnIntent = new Intent(this, RecruitNerdsActivity.class);
		grnIntent.putExtra("player", player);
		startActivityForResult(grnIntent,1);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "THERE's a BACK BUTTON FOR A REASON LOL", Toast.LENGTH_SHORT).show();
		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		try {
			fis = openFileInput(path);
			dis = new DataInputStream(fis);
			lastGambleTime = dis.readLong();
			dis.close();
			fis.close();			
			//Toast.makeText(getApplicationContext(), "open File success, lastGambleTime="+lastGambleTime, Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			try {
				Date daDate = new Date();
				lastGambleTime = daDate.getTime()-1000*60*5;
				
				fos = openFileOutput(path, Context.MODE_PRIVATE);
				dos = new DataOutputStream(fos);
				dos.writeLong(lastGambleTime);
				dos.close();
				fos.close();
				//Toast.makeText(getApplicationContext(), "write File success, lastGambleTime="+lastGambleTime, Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		updateStatus();
		moneyTv.setText(Integer.toString(player.getMoney()));
		moneyTv.setTextColor(Color.GREEN);
		song.start();		
		song.setLooping(true);
		
	}



	public void updateStatus(){
		statusView.setText(status);
		statusView.setTextColor(Color.MAGENTA);
	}
	
	public void goGym(){
		Intent ggIntent = new Intent(this, RaidGymActivity.class);
		ggIntent.putExtra("player", player);
		startActivityForResult(ggIntent, 1);
		
	}
	
	public void quitToMain(){
		
		Intent newIntent = new Intent();
		newIntent.putExtra("after_upgrade",player);
		newIntent.putExtra("songPosition", songPosition);
		setResult(RESULT_OK, newIntent);
		finish();
	}
	

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	/*Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
    	String code = Integer.toString(resultCode);
    	String code2 = Integer.toString(requestCode);
    	Toast.makeText(getApplicationContext(), "Request Code: "+code2, Toast.LENGTH_SHORT).show();
    	Toast.makeText(getApplicationContext(), "Result Code: "+code, Toast.LENGTH_SHORT).show();*/
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){
    			
                player = (Player) data.getSerializableExtra("modifiedPlayer");
                status = (String) data.getStringExtra("status");
            }        
    	}    	
    }
	
	
	
	
}
