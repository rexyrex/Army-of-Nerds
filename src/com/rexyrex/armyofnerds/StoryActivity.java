package com.rexyrex.armyofnerds;

import com.rexyrex.armyofnerds.R.raw;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


public class StoryActivity extends Activity {

	Button play;
	String name;
	SoundPool btnSound;
	int btnSoundInt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
		btnSound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		btnSoundInt = btnSound.load(getApplicationContext(), raw.nimpact, 1);
		Intent gIntent = getIntent();
		name = gIntent.getStringExtra("player_name");
		play = (Button) findViewById(R.id.playFromStory);
		
		//sound off
		play.setSoundEffectsEnabled(false);
		
		play.setOnClickListener(new View.OnClickListener() {
			Player player = new Player(name,1200);
			Enemy enemy = new Enemy();
			@Override
			public void onClick(View v) {
				btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
				goToPlayMain(player,enemy);				
			}
		});		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();	
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	btnSound.release();
	    		finish();
	        }
	    }, 2000);
						
	}

	public void goToPlayMain(Player player,Enemy enemy){
		Intent gtpmIntent = new Intent(this, PlayMainActivity.class);
		gtpmIntent.putExtra("player", player);
		gtpmIntent.putExtra("enemy", enemy);
		gtpmIntent.putExtra("loadState", false);
		startActivity(gtpmIntent);
	}
	
	@Override
	public void onBackPressed(){
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}


}
