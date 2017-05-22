package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GoClubActivity extends Activity {

	int cost;
	Player player;
	Button ok;
	TextView nerdsPickedUp;
	int nerdsAquired;
	boolean loiPickUp = false;
	MediaPlayer clubSong;
	SoundPool sounds;
	int clickSound;
	int backSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_club);
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.zeeLayoutFohClub);
		
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		
		int randomMusic = (int) (Math.random()*2+1);
		if(randomMusic==1){
			clubSong = MediaPlayer.create(getApplicationContext(), R.raw.club2chicken_d);
		} else {
			clubSong = MediaPlayer.create(getApplicationContext(), R.raw.club3avicii_d);
			layout.setBackgroundResource(R.drawable.club_2);
		}
		
		clubSong.start();
		clubSong.setLooping(true);
		
		Intent getIntent = getIntent();
		cost = getIntent.getIntExtra("cost", 0);
		player = (Player) getIntent.getSerializableExtra("player");
		nerdsPickedUp = (TextView) findViewById(R.id.atcPickUpNerdsActualID);
		ok = (Button) findViewById(R.id.atcOKBtnID);
		
		player.setMoney(player.getMoney()-cost);
		//stats
		player.incMoneySpentOnClubbing(cost);
		player.goClub();
		
		nerdsAquired = (int) (cost/(100-50*Math.random()));
		
		int loiChance = (int) (Math.random()*100);
		if(loiChance==77 || loiChance==27){
			loiPickUp = true;
		}
		
		String status ="";
		
		if(player.getNerdRoom()-nerdsAquired<0){
			nerdsAquired = player.getNerdRoom();
			status+= "You reached your nerd cap of " +player.getNerdCap()+"\nYou better start selling nerds!\n";
		}
		player.setNerds(player.getNerds()+nerdsAquired);
		player.incNerdsFromClubbing(nerdsAquired);
		status += ""+nerdsAquired+" Nerds";
		if(loiPickUp){
			status += "\n You picked up a Lang Loi!\n1/50 Chance!\nGratz!";
			player.setLoi(player.getLoi()+1);
			player.incLangLoiFromClubbing();
		}
		nerdsPickedUp.setText(status);
		nerdsPickedUp.setTextColor(Color.GREEN);
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				clubSong.stop();
				final Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	clubSong.release();
						sounds.release();
						getOut();	
			        }
			    }, 27);
								
			}
		});			
	}
	
	public void getOut(){
		Intent goIntent = new Intent();
		goIntent.putExtra("modifiedPlayer", player);
		setResult(RESULT_OK, goIntent);
		finish();		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "just press ok...", Toast.LENGTH_SHORT).show();
	}
}
