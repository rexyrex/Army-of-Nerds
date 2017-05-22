package com.rexyrex.armyofnerds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class LoadActivity extends Activity {

	FileInputStream fis;
	ObjectInputStream ois;
	Player player;
	Enemy enemy;
	String FILENAME = "rexyrex";
	String FILENAMES[] = {"slot1","slot2","slot3"};
	Boolean[] fileAvailability = {true, true, true};
	RadioGroup rg;
	RadioButton slot1Btn;
	RadioButton slot2Btn;
	RadioButton slot3Btn;
	int currentSelection;
	Button loadbtn;
	Button backbtn;
	SoundPool sounds;
	int btnSound;
	int backSound;
	int saveSound;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		
		sounds = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		btnSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		saveSound = sounds.load(getApplicationContext(), R.raw.save, 0);
		
		rg = (RadioGroup) findViewById(R.id.ldLoadSlotsGroupID);
		slot1Btn = (RadioButton) findViewById(R.id.ldSlot1ID);
		slot2Btn = (RadioButton) findViewById(R.id.ldSlot2ID);
		slot3Btn = (RadioButton) findViewById(R.id.ldSlot3ID);
		loadbtn = (Button) findViewById(R.id.ldLoadBtnID);
		backbtn = (Button) findViewById(R.id.ldBackBtnID);		
		
		//sound off
		loadbtn.setSoundEffectsEnabled(false);
		backbtn.setSoundEffectsEnabled(false);
		
		player = new Player("ERROR", 0);
		enemy = new Enemy();
		
		slot1Btn.setChecked(true);
		currentSelection=0;
		
		for(int i=0; i<3; i++){			
			load(i);
		}
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				if(checkedId==slot1Btn.getId()){
					currentSelection=0;
				} else if(checkedId==slot2Btn.getId()){
					currentSelection=1;
				} else {
					currentSelection=2;
				}
				
			}
		});
		
		loadbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(fileAvailability[currentSelection]==true){
					load(currentSelection);
					if(player.getHasFinished()==true){
						Toast.makeText(getApplicationContext(), "Thank You for playing! You're done with the game now!", Toast.LENGTH_SHORT).show();
						// new method
						Intent goFinish = new Intent(LoadActivity.this, FinishActivity.class);
						goFinish.putExtra("player", player);
						startActivity(goFinish);
						
						sounds.release();
						finish();
					} else {
						sounds.play(saveSound, 1, 1, 0, 0, 1);
						player.incLoads();
						goPlay();
					}
				} else {
					sounds.play(backSound, 1, 1, 0, 0, 1);
					Toast.makeText(getApplicationContext(), "Cannot Load you silly!", Toast.LENGTH_SHORT).show();
				}				
			}
		});
		
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.release();
				finish();				
			}
		});	
	}
	
	public void load(int i){
		try {
			fis = openFileInput(FILENAMES[i]);
			//fis = new FileInputStream(FILENAME);
			ois = new ObjectInputStream(fis);
			player = (Player) ois.readObject();
			enemy = (Enemy) ois.readObject();
			updateInfo(i);
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileAvailability[i]=false;			
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "streamCorruptedException", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "ClassNotFoundException", Toast.LENGTH_SHORT).show();
		}
		
		
	}	public void updateInfo(int i){		
		if(i==0){
			if(fileAvailability[0]==true){
				if(player.getHasFinished()==false){
					slot1Btn.setText("1. "+player.getName()+", Brain Power : "+player.getBrainPower()+", Time : "+player.getDate());
				} else {
					slot1Btn.setText("1. "+player.getName()+": GAME COMPLETE!");
				}
			} else {
				slot1Btn.setText("1. Empty Slot");
			}
		}
		if(i==1){
			if(fileAvailability[1]==true){
				if(player.getHasFinished()==false){
					slot2Btn.setText("2. "+player.getName()+", Brain Power : "+player.getBrainPower()+", Time : "+player.getDate());
				} else {
					slot2Btn.setText("2. "+player.getName()+": GAME COMPLETE!");
				}
			} else {
				slot2Btn.setText("2. Empty Slot");
			}
		}
		
		if(i==2){
			if(fileAvailability[2]==true){
				if(player.getHasFinished()==false){
					slot3Btn.setText("3. "+player.getName()+", Brain Power : "+player.getBrainPower()+", Time : "+player.getDate());
				} else {
					slot3Btn.setText("3. "+player.getName()+": GAME COMPLETE!");
				}
			} else {
				slot3Btn.setText("3. Empty Slot");
			}
		}
	}
	
	public void goPlay(){
		Intent gpIntent = new Intent(this, PlayMainActivity.class);
		gpIntent.putExtra("loadState", true);
		gpIntent.putExtra("player", player);
		gpIntent.putExtra("enemy", enemy);
		startActivity(gpIntent);
		
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
	public void onBackPressed(){
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}


}
