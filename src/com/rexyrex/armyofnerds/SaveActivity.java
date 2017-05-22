package com.rexyrex.armyofnerds;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SaveActivity extends Activity {

	Player player;
	Player playerToSend;
	Enemy enemy;
	Enemy enemyToSend;
	
	TextView title;

	//load
	FileInputStream fis;
	ObjectInputStream ois;
	
	//save
	FileOutputStream fos=null;
	ObjectOutputStream oss=null;
	String FILENAME = "rexyrex";
	String FILENAMES[] = {"slot1","slot2","slot3"};
	Button savebtn;
	Button quitbtn;
	
	Boolean[] fileAvailability = {true, true, true};
	RadioGroup rg;
	RadioButton slot1Btn;
	RadioButton slot2Btn;
	RadioButton slot3Btn;
	int currentSelection;
	
	SoundPool sounds;
	int saveSound;
	int backSound;
	int btnSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		sounds = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
		btnSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		saveSound = sounds.load(getApplicationContext(), R.raw.save, 0);
		
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		enemy = (Enemy) getIntent.getSerializableExtra("enemy");
		
		playerToSend = (Player) getIntent.getSerializableExtra("player");
		enemyToSend = (Enemy) getIntent.getSerializableExtra("enemy");
		
		savebtn = (Button) findViewById(R.id.sSaveBtnID);
		quitbtn = (Button) findViewById(R.id.sQuitBtnID);
		
		title = (TextView) findViewById(R.id.saveTitleID);
		
		//sound off
		savebtn.setSoundEffectsEnabled(false);
		quitbtn.setSoundEffectsEnabled(false);
		
		rg = (RadioGroup) findViewById(R.id.sRadioGroupID);
		slot1Btn = (RadioButton) findViewById(R.id.sSlot1ID);
		slot2Btn = (RadioButton) findViewById(R.id.sSlot2ID);
		slot3Btn = (RadioButton) findViewById(R.id.sSlot3ID);
		
		slot1Btn.setChecked(true);
		currentSelection=0;
		
		if(player.getHasFinished()==true){
			title.setText("Gratz on beating the Double Burger Coorperation! Please save! You will only be given this one chance to save!");
		}
		
		
		
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
		
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playerToSend.incSaves();
				String date =  DateFormat.getDateTimeInstance().format(new Date());
				playerToSend.setDate(date);
				//Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
				try {
					fos = openFileOutput(FILENAMES[currentSelection], Context.MODE_PRIVATE);
					oss = new ObjectOutputStream(fos);
					oss.writeObject(playerToSend);
					oss.writeObject(enemyToSend);
					oss.close();
					fos.close();
					fileAvailability[currentSelection]=true;
					
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), "FILE NOT FOUND", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), "IO EXCEPTION", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}						
				
				Toast.makeText(getApplicationContext(), "file saved", Toast.LENGTH_SHORT).show();
				
				sounds.play(saveSound, 1, 1, 0, 0, 1);
				for(int i=0; i<3; i++){			
					load(i);
				}
			}
		});		
		
		quitbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(btnSound, 1, 1, 0, 0, 1);
				getOut();				
			}
		});
		
	}
	
	public void load(int i){
		try {
			fis = openFileInput(FILENAMES[i]);
			//fis = new FileInputStream(FILENAME);
			ois = new ObjectInputStream(fis);
			player = (Player) ois.readObject();
			updateInfo(i);
			enemy = (Enemy) ois.readObject();
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
		
		
	}
	
	public void updateInfo(int i){		
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
	
	private void getOut(){
		Intent goIntent = new Intent();
		goIntent.putExtra("after_save", playerToSend);
		goIntent.putExtra("e_after_save", enemyToSend);
		setResult(RESULT_OK, goIntent);
		sounds.release();
		finish();
	}
	
	@Override
	public void onBackPressed(){
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}
	

}
