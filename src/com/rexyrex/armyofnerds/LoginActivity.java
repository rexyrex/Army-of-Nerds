package com.rexyrex.armyofnerds;

import java.util.Locale;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rexyrex.armyofnerds.R.raw;

public class LoginActivity extends Activity {

	Button enterName;
	Button backToMain;
	EditText nameValue;
	String actualName;
	SoundPool btnSound;
	int btnSoundInt;
	TextView nameTitleTv;
	EditText passwordEt;
	TextToSpeech tts;
	boolean pressed=false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		btnSound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		btnSoundInt = btnSound.load(getApplicationContext(), raw.nimpact, 1);

		enterName = (Button) findViewById(R.id.playAfterName);
		nameValue = (EditText) findViewById(R.id.theNameEntered);
		backToMain = (Button) findViewById(R.id.backToMainMenu);
		nameTitleTv = (TextView) findViewById(R.id.nameEnter);
		passwordEt = (EditText) findViewById(R.id.lgPasswordID);
		
		enterName.setText("Scan Name");
		
		passwordEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				pressed=false;
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		nameValue.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				pressed=false;
				enterName.setText("Scan Name");
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tts = new TextToSpeech(LoginActivity.this, new TextToSpeech.OnInitListener() {
			
			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if(status != TextToSpeech.ERROR){
					tts.setLanguage(Locale.ENGLISH);
				}
			}
		});
		

		
		//sound off
		enterName.setSoundEffectsEnabled(false);
		backToMain.setSoundEffectsEnabled(false);
		
		backToMain.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				goBackToMain();			
			}
		});
		
		enterName.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				if(pressed==true){
					btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
					goToStory();
				}
				
				if(nameValue.getText() != null){
					actualName = nameValue.getText().toString();
				} else {
					actualName = "idiot";
				}
				
				int password;				
					try{
						password = Integer.parseInt(passwordEt.getText().toString());
						enterName.setText("START!");
					} catch(NumberFormatException e){
						password = 1;
					}
				
				
				if((password<1000 && password!=0000) || password>9999){
					Toast.makeText(getApplicationContext(), "Password must be 4 digits", Toast.LENGTH_SHORT).show();
					read("Password must be exactly 4 digits");
				} else {
				
					if((actualName.equals("Humphrey") || actualName.equals("humphrey"))){
						if(password==1214){
							actualName=("SKIP LECTURE MAN");
							nameTitleTv.setText("Welcome My Friend!");
							read("Welcome Humphrey Wong, A,k,a, Skip, Lecture, Man, A,k,a, Pain, Man, A,k,a, Noodle Man");
							pressed=true;	
						} else {
							impostorDetected();
						}
					} else if(actualName.equals("Alex") || actualName.equals("alex")){
						if(password==7428){
							actualName=("ALEX DA PLAYER");
							nameTitleTv.setText("Welcome My Friend!");
							read("Welcome Alex Chan, A,k,a, Alex, The, Player, A,k,a, Bicycle, Man, A,k,a, Banana Man");
							pressed=true;	
						} else {
							impostorDetected();
						}
					} else if(actualName.equals("Adrian") || actualName.equals("adrian")){
						if(password==7777){
							actualName="Double Burger Man";
							nameTitleTv.setText("Welcome My Friend!");
							read("Welcome Adrian Kim, A,k,a, Double Burger Man");
							pressed=true;	
						} else {
							impostorDetected();
						}
					} else if(actualName.equals("Pablo") || actualName.equals("pablo")){
						if(password==2310){
							actualName="Basket Ball Man";
							nameTitleTv.setText("Welcome My Friend!");
							read("Welcome Pablo Sanderson, A,k,a, Basket Ball Man");
							pressed=true;	
						} else {
							impostorDetected();
						}
					} else if(actualName.equals("Marcus") || actualName.equals("marcus")) { 
						if(password==0000){
							actualName="Dota Man";
							nameTitleTv.setText("Welcome My Friend!");
							read("Welcome Marcus Ken Loong Siew, A,k,a, Dota Man a,k,a, Singapore man who can speak a bit of korean man");
							pressed=true;	
						} else {
							impostorDetected();
						}					
					}else if(isValid()){
						read("Welcome, New, Player, "+actualName+", Thank you for playing army of nerds and I hope you have a fun time");
						pressed=true;	
					}
					
					if(nameValue.getText().toString().length()>14 || nameValue.getText().toString().length()<3){
						nameTitleTv.setText("Name must be between 3 and 14 Chars Long!");
						read("Name must be between 3 and 14 Characters Long!");
					} else if(nameValue.getText().toString().contains(" ")){
						nameTitleTv.setText("Name cannot contain spaces");
						read("Name cannot contain spaces");
					} 
				}//password check
			}
		});		
	}
	
	protected boolean isValid(){
		if(!(nameValue.getText().toString().length()>14 || nameValue.getText().toString().length()<3 || nameValue.getText().toString().contains(" "))){
			return true;
		} else {
			return true;
		}
		
		
	}
	
	protected void impostorDetected(){
		Toast.makeText(getApplicationContext(), "Impostor Detected!", Toast.LENGTH_SHORT).show();
		actualName="Mr. Impostor";
		read("Impostor Detected");
		pressed=false;
		enterName.setText("Scan Name");
	}
	
	@Override
	protected void onPause() {
		if(tts != null){
			tts.stop();
			tts.shutdown();
		}
		super.onPause();
	}

	public void goBackToMain(){
		btnSound.play(btnSoundInt, 1, 1, 0, 0, 1);
		btnSound.release();
		finish();
	}
	
	private void read(String s){
		tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	public void goToStory(){
		Intent gtsIntent = new Intent(this, StoryActivity.class);
		gtsIntent.putExtra("player_name", actualName);
		startActivity(gtsIntent);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	btnSound.release();
	    		finish();

	        }
	    }, 1200);
		
		
	}
	
	@Override
	public void onBackPressed(){
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}

}
