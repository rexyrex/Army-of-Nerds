package com.rexyrex.armyofnerds;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreditsActivity extends Activity {

	Button backFromCreditsBtn;
	Button sendEmailBtn;
	MediaPlayer gunSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		// Show the Up button in the action bar.
		
		gunSound = MediaPlayer.create(this, R.raw.nimpact);
		backFromCreditsBtn = (Button) findViewById(R.id.backFromCredit);
		sendEmailBtn = (Button) findViewById(R.id.sendEmailBtn);
		
		backFromCreditsBtn.setSoundEffectsEnabled(false);
		sendEmailBtn.setSoundEffectsEnabled(false);
		
		sendEmailBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendEmail();				
			}
		});
		
		backFromCreditsBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				gunSound.start();
				backFromCreditsBtn.setText("Rexyrex");
				gunSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						gunSound.release();
						finish();						
					}
				});
				
			}
		});
		
		
	}
	
	protected void sendEmail() {
		// TODO Auto-generated method stub
		String emailAddresses[] = {"minhyung.kim1234@gmail.com"};
		String message = "Dear Adrian,\n"
							+ "I wanted to let you know that I hate you!\n"
							+ "Also, I was in the Credits just now";
		Intent newIntent = new Intent(android.content.Intent.ACTION_SEND);
		newIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddresses);
		newIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ArmyOfNerds Credits Email!");
		newIntent.setType("plain/text");
		newIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(newIntent);
		Toast.makeText(getApplicationContext(), "send it! :D", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed(){
		Toast.makeText(getApplicationContext(), "OH NO YOU DONT", Toast.LENGTH_SHORT).show();
	}

}
