package com.rexyrex.armyofnerds;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;


public class RaidLibraryActivity extends Activity {

	TextView test;
	Player player;
	String str;
	Button back;
	Button buy;
	int cost;
	TextView costTv;
	EditText itemEt;
	String status;
	
	SoundPool sounds;
	int clickSound;
	int backSound;
	int buySound;
	int textChangeSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raid_library);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		buySound = sounds.load(getApplicationContext(), R.raw.quickbuy_confirm, 0);
		textChangeSound = sounds.load(getApplicationContext(), R.raw.ui_click_simple_01, 0);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		test = (TextView) findViewById(R.id.test);
		back = (Button) findViewById(R.id.backToUpgradeFromLibraryID);
		buy = (Button) findViewById(R.id.buyFromLibraryBtnID);
		itemEt = (EditText) findViewById(R.id.lBuyItemEtID);
		costTv = (TextView) findViewById(R.id.lEstimatedCostActualID);
		status = "Raided Library but didn't find any useful weapons";
		itemEt.addTextChangedListener(daWatcher);		
		
		back.setSoundEffectsEnabled(false);
		buy.setSoundEffectsEnabled(false);
		itemEt.setSoundEffectsEnabled(false);
		
		player.goLibrary();
		
		str = "";
		for(int i=player.getWeapon(); i<player.getAllWeaponTypes().length; i++){
			cost = player.getAllWeaponCosts()[i];
			
			str += "\n"+(i+1)+". "+player.getAllWeaponTypes()[i] + "\t cost: "+cost;
		}		
		test.setText(str);
		
		
		buy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buy();
				
			}
		});
		
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				getOut();				
			}
		});
		
	}
	
	private void buy(){
		try{
			int input = Integer.parseInt(itemEt.getText().toString());
			if(input>player.getWeapon() && input<player.getAllWeaponTypes().length+1){
				cost = player.getAllWeaponCosts()[input-1];
				
				if(cost>player.getMoney()){
					sounds.play(backSound, 1, 1, 0, 0, 1);
					Toast.makeText(getApplicationContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();
				} else {
					sounds.play(buySound, 1, 1, 0, 0, 1);
					player.setWeapon(input);
					player.setMoney(player.getMoney()-cost);
					status = "Bought new weapon from library: " + player.getWeaponType();
					player.upgradeWeapon();
					player.incMoneySpentInLibrary(cost);
					getOut();
				}
				
			} else {
				Toast.makeText(getApplicationContext(), "Invalid Item", Toast.LENGTH_SHORT).show();
			}
		} catch(NumberFormatException e){
			sounds.play(backSound, 1, 1, 0, 0, 1);
			Toast.makeText(getApplicationContext(), "Input an integer... idiot...", Toast.LENGTH_SHORT).show();
		}			
	}
	
	private void getOut(){
		Intent b = new Intent();
		b.putExtra("modifiedPlayer", player);
		b.putExtra("status", status);
		setResult(RESULT_OK, b);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	sounds.release();
	    		finish();

	        }
	    }, 500);
		
	}
	
	private TextWatcher daWatcher = new TextWatcher(){

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
				int input = Integer.parseInt(s.toString());
				if(input>player.getWeapon() && input<player.getAllWeaponTypes().length+1){
					cost = player.getAllWeaponCosts()[input-1];
					costTv.setText(Integer.toString(cost));
				} else {
					costTv.setText("Invalid Item");
				}
			} catch(NumberFormatException e){
				costTv.setText("Invalid Input");
			}			
		}		
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "There's a back button for a reason...", Toast.LENGTH_SHORT).show();
	}
	
}
