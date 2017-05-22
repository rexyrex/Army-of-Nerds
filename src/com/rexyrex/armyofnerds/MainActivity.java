package com.rexyrex.armyofnerds;

import android.app.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button startbtn;
	Button creditsbtn;
	Button loadbtn;
	Button quitbtn;
	Button helpbtn;
	MediaPlayer rexMusic;

	SoundPool btnSound;
	int btnSoundInt;
	int backSound;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		btnSound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		btnSoundInt = btnSound.load(getApplicationContext(), R.raw.nimpact, 1);
		backSound = btnSound.load(getApplicationContext(), R.raw.back, 0);
		
		rexMusic = MediaPlayer.create(MainActivity.this, R.raw.mainmusic);
		creditsbtn = (Button) findViewById(R.id.CreditsID);
		quitbtn = (Button) findViewById(R.id.quitID);
		startbtn= (Button) findViewById(R.id.startID);
		loadbtn = (Button) findViewById(R.id.loadID);
		helpbtn = (Button) findViewById(R.id.helpID);
		

		
		//sound off
		loadbtn.setSoundEffectsEnabled(false);
		startbtn.setSoundEffectsEnabled(false);
		quitbtn.setSoundEffectsEnabled(false);
		creditsbtn.setSoundEffectsEnabled(false);
		helpbtn.setSoundEffectsEnabled(false);
		
		helpbtn.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				goDecrypt();
				return false;
			}
		});
		
		helpbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
				goHelp();				
			}
		});
		
		loadbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
				rexMusic.seekTo(0);
				rexMusic.pause();
				goLoad();				
			}
		});
		
		startbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rexMusic.seekTo(0);
				rexMusic.pause();
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
				goToStart();				
			}
		});
		
		quitbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);	
				quitbtn.setText("Quitting...");
				final Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	rexMusic.release();
						btnSound.release();
						finish();	

			        }
			    }, 1700);
							
			}
		});
		
		creditsbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
				goToCredits();				
			}
		});
		
	}
	
	protected void goDecrypt() {
		Intent dIntent = new Intent(this, DecryptActivity.class);
		startActivity(dIntent);
		
	}

	public void goLoad(){
		Intent glIntent = new Intent(this, LoadActivity.class);
		startActivity(glIntent);
	}
	
	public void goToStart(){
		Intent sIntent = new Intent(this, LoginActivity.class);
		startActivity(sIntent);
	}
	
	public void goToCredits(){
		Intent cIntent = new Intent(this,CreditsActivity.class);	
		startActivity(cIntent);		
	}

	public void goHelp(){
		Intent hIntent = new Intent(this, HelpActivity.class);
		startActivity(hIntent);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		rexMusic.start();	// play music if it's not already			
		rexMusic.setLooping(true);
		
		
	}
	
	

	
	@Override
	public void onBackPressed(){
		btnSound.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
