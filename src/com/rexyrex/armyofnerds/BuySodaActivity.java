package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class BuySodaActivity extends Activity {

	
	Player player;
	Button buy;
	TextView moneyRequiredTv;
	TextView nerdIncomeTv;
	EditText sodaAmountEt;
	int moneyRequired;
	int nerdIncome;
	int randomRange1;
	int randomRange2;
	double setRandom;
	RadioGroup rg;
	RadioButton nerdsRb;
	RadioButton superNerdsRb;
	RadioButton asiansRb;
	int multiplier;
	
	SoundPool sounds;
	int clickSound;
	int backSound;
	int textChangeSound;
	int buySound;
	
	int sodaAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_soda);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		textChangeSound = sounds.load(getApplicationContext(), R.raw.ui_click_simple_01, 0);
		buySound = sounds.load(getApplicationContext(), R.raw.quickbuy_confirm, 0);
		
		Intent getIntent = getIntent();		
		player = (Player) getIntent.getSerializableExtra("player");
		
		buy = (Button) findViewById(R.id.bsBuySodaBtnID);
		moneyRequiredTv = (TextView) findViewById(R.id.bsCostActualID);
		nerdIncomeTv = (TextView) findViewById(R.id.bsEstimatedNerdIncomeActualID);
		sodaAmountEt = (EditText) findViewById(R.id.bsETSodaInput);
		rg = (RadioGroup) findViewById(R.id.bsRadioGroupID);
		nerdsRb = (RadioButton) findViewById(R.id.bsRadioNerdsID);
		superNerdsRb = (RadioButton) findViewById(R.id.bsRadioSuperNerdsID);
		asiansRb = (RadioButton) findViewById(R.id.bsRadioAsiansID);
		
		//sound off
		buy.setSoundEffectsEnabled(false);
		sodaAmountEt.setSoundEffectsEnabled(false);
		
		//var initialize
		multiplier=1;
		moneyRequired = -1;
		nerdIncome = 0;
		setRandom = Math.random();
		
		update();
		
		sodaAmountEt.addTextChangedListener(daWatcher);
		nerdsRb.setChecked(true);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int id) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				if(id==nerdsRb.getId()){
					multiplier=1;
				} else if(id==superNerdsRb.getId()){
					multiplier = 4;
				} else if(id==asiansRb.getId()){
					multiplier = 11;
				} else {
					multiplier = 0;
				}
				try{
					int input = Integer.parseInt(sodaAmountEt.getText().toString());
					sodaAmount = input;
					moneyRequired = input * 27;
					nerdIncome = (int) (input*27/(70*multiplier));
				} catch (NumberFormatException e){
					moneyRequired = -1;
					nerdIncome = 0;
				}
				
				update();
				
			}
		});
		
		buy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				
				buySoda();				
			}
		});		
	}
	
	public void buySoda(){
		if(moneyRequired==-1 && nerdIncome ==0){
			sounds.play(backSound, 1, 1, 0, 0, 1);
			Toast.makeText(getApplicationContext(), "INVALID INPUT", Toast.LENGTH_SHORT).show();
		} else	if(player.getMoney()>=moneyRequired){
			sounds.play(buySound, 1, 1, 0, 0, 1);
			if(player.getNerdRoom()-nerdIncome<0){
				Toast.makeText(getApplicationContext(), "Nerd limit reached.", Toast.LENGTH_SHORT).show();
				nerdIncome = player.getNerdRoom();
			} 
				player.setMoney(player.getMoney()-moneyRequired);
				player.incMoneySpentOnSoda(moneyRequired);
				if(multiplier==1){
					player.setNerds(player.getNerds()+nerdIncome);
					player.incNerdsFromSoda(nerdIncome);
				} else if(multiplier==4){
					player.setSuperNerds(player.getSuperNerds()+nerdIncome);
					player.incSuperNerdsFromSoda(nerdIncome);
				} else {
					player.setAsians(player.getAsians()+nerdIncome);
					player.incAsiansFromSoda(nerdIncome);
				}
				player.buySoda();
				player.incNumberOfSodaBought(sodaAmount);
				backFromSoda();
			
		} else {
			sounds.play(backSound, 1, 1, 0, 0, 1);
			Toast.makeText(getApplicationContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void backFromSoda(){
		Intent bfsIntent = new Intent();
		bfsIntent.putExtra("modifiedPlayer", player);
		setResult(RESULT_OK, bfsIntent);
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
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			sounds.play(textChangeSound, 1, 1, 0, 0, 1);
			try{
				int input = Integer.parseInt(s.toString());
				sodaAmount = input;
				moneyRequired = input * 27;
				nerdIncome = (int) (input*27/(70*multiplier));
			} catch (NumberFormatException e){
				moneyRequired = -1;
				nerdIncome = 0;
			}
			update();
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}		
	};
	
	
	
	private void update(){
		if(moneyRequired>-1){		
			moneyRequiredTv.setText(Integer.toString(moneyRequired));
			if(moneyRequired>player.getMoney()){
				moneyRequiredTv.setTextColor(Color.RED);
			} else {
				moneyRequiredTv.setTextColor(Color.GREEN);
			}
			randomRange1 = (int) (nerdIncome * 0.5 * Math.random());
			randomRange2 = (int) (0-nerdIncome * 0.5 * Math.random());
			if(nerdIncome + randomRange2 < 0){
				randomRange2 = 0;
			}
			
			nerdIncomeTv.setText(""+(nerdIncome+randomRange2)+"~"+(nerdIncome+randomRange1));
			if(player.getNerdRoom()-nerdIncome<0){
				nerdIncomeTv.setText("You will reach your nerd limit");
			}
		} else {
			moneyRequiredTv.setText("Invalid Input");
			nerdIncomeTv.setText("Invalid Input");
			moneyRequiredTv.setTextColor(Color.CYAN);
			nerdIncomeTv.setTextColor(Color.CYAN);
		}
	}
	
	@Override
	public void onBackPressed() {
		sounds.play(backSound, 1, 1, 0, 0, 1);
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "BUY SODA PLZ", Toast.LENGTH_SHORT).show();
	}
	
}
