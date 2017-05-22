package com.rexyrex.armyofnerds;

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
import android.widget.SeekBar;
import android.widget.TextView;


public class SellNerdsActivity extends Activity {

	Player player;
	SeekBar sb;
	Button sellBtn;
	TextView sbValueTv;
	TextView moneyConversionTv;
	RadioGroup rg;
	RadioButton nerdsRBtn;
	RadioButton superNerdsRBtn;
	RadioButton asiansRBtn;
	int nerdToMoneyRatio = 32;
	int nerdTypeMultiplier=1;
	int moneyGained;
	int nerds=0;

	SoundPool sounds;
	int clickSound;
	int backSound;
	int gladosmonster;
	int gladosfamilies;
	int gladosgenocide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sell_nerds);
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		gladosmonster = sounds.load(getApplicationContext(), R.raw.gladosyoumonster, 0);
		gladosfamilies = sounds.load(getApplicationContext(), R.raw.gladoshadfamilies, 0);
		gladosgenocide = sounds.load(getApplicationContext(), R.raw.gladosgenocide, 0);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		
		sb = (SeekBar) findViewById(R.id.sellNerdsSeekBarID);
		sbValueTv = (TextView) findViewById(R.id.sellNerdsAmountActualID);
		moneyConversionTv = (TextView) findViewById(R.id.sellNerdMoneyActualID);
		rg = (RadioGroup) findViewById(R.id.sellNerdsRadioGroupID);
		nerdsRBtn = (RadioButton) findViewById(R.id.snNerdsRBtn);
		superNerdsRBtn = (RadioButton) findViewById(R.id.snSuperNerdsRBtnID);
		asiansRBtn = (RadioButton) findViewById(R.id.snAsianRBtnID);
		sellBtn = (Button) findViewById(R.id.sellNerdBtnID);
		
		sellBtn.setSoundEffectsEnabled(false);
		
		sellBtn.setText("Sell Nothing");
		
		
		sb.setProgress(0);
		sb.setMax(player.getNerds());
		nerdsRBtn.setChecked(true);
		
		sellBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				
				player.setMoney(player.getMoney()+moneyGained);
				if(nerdTypeMultiplier==1){
					player.setNerds(player.getNerds()-nerds);
					if(nerds>0)
					sounds.play(gladosfamilies, 1, 1, 0, 0, 1);
				} else if(nerdTypeMultiplier==3){
					player.setSuperNerds(player.getSuperNerds()-nerds);
					if(nerds>0)
					sounds.play(gladosmonster, 1, 1, 0, 0, 1);
				} else {
					player.setAsians(player.getAsians()-nerds);
					if(nerds>0)
					sounds.play(gladosgenocide, 1, 1, 0, 0, 1);
				}			
				
				sellBtn.setText("Poor Nerds :(\nThat could have been you!");
				getOut();
			}
		});
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int id) {
				if(id==nerdsRBtn.getId()){
					sb.setMax(player.getNerds());
					nerdTypeMultiplier=1;
				} else if(id==superNerdsRBtn.getId()){
					sb.setMax(player.getSuperNerds());
					nerdTypeMultiplier=3;
				} else {
					sb.setMax(player.getAsians());
					nerdTypeMultiplier=7;
				}
				sb.setProgress(0);
				updateMoney();
				
			}
		});
		
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				nerds=progress;
				setSbText(progress);				
				updateMoney();
				sellBtn.setText("Sell");
				if(progress==0){
					sellBtn.setText("Sell Nothing");
				}
				
			}
		});		
		
		
		
		
	}
	
	private void setSbText(int progress){
		sbValueTv.setText(Integer.toString(progress));
	}
	
	private void getOut(){
		Intent goIntent = new Intent();
		goIntent.putExtra("modifiedPlayer", player);
		goIntent.putExtra("status", "Player went to sell nerds!");
		setResult(RESULT_OK,goIntent);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	sounds.release();
	    		finish();
	        }
	    }, 2200);
		
	}

	
	private void updateMoney(){
		moneyGained = Integer.parseInt(sbValueTv.getText().toString())*nerdToMoneyRatio*nerdTypeMultiplier;
		moneyConversionTv.setText(Integer.toString(moneyGained));
	}

	@Override
	public void onBackPressed() {
		sounds.play(backSound, 1, 1, 0, 0, 1);
	}
	

}
