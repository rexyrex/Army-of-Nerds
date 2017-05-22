package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FightInfoActivity extends Activity {

	boolean hasFought;
	
	Enemy enemy;
	Player player;
	TextView pNameTv;
	TextView pNerdsTv;
	TextView pSuperNerdsTv;
	TextView pAsiansTv;
	TextView pLevelTv;
	TextView pWeaponTv;
	TextView pBrainTv;
	
	TextView eNameTv;
	TextView eNerdsTv;
	TextView eSuperNerdsTv;
	TextView eAsiansTv;
	TextView eLevelTv;
	TextView eWeaponTv;
	TextView eBrainTv;
	
	TextView fightCostTv;
	
	Button backBtn;
	Button fightBtn;
	
	String playerStatus;
	String enemyStatus;
	String winnerStatus;
	String moneyChangeStatus;
	String expGainStatus;
	String enemyUpgradeStatus;
	MediaPlayer song;
	SoundPool sounds;
	int backSound;
	
	RelativeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fight_info);
		
		
		
		
		
		Intent getIntent = getIntent();
		enemy = (Enemy) getIntent.getSerializableExtra("enemy");
		player = (Player) getIntent.getSerializableExtra("player");
		
		layout = (RelativeLayout) findViewById(R.id.fight_info_layout);
		
		
		hasFought = false;
		
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		
		pNameTv = (TextView) findViewById(R.id.fiPlayerNameID);
		pNerdsTv = (TextView) findViewById(R.id.fiPlayerNerdsID);
		pSuperNerdsTv = (TextView) findViewById(R.id.fiPlayerSuperNerdsID);
		pAsiansTv = (TextView) findViewById(R.id.fiPlayerAsiansID);
		pLevelTv = (TextView) findViewById(R.id.fiPlayerLevelID);
		pWeaponTv = (TextView) findViewById(R.id.fiPlayerWeaponID);
		pBrainTv = (TextView) findViewById(R.id.fiPlayerBrainPowerID);
		
		eNameTv = (TextView) findViewById(R.id.fiEnemyNameID);
		eNerdsTv = (TextView) findViewById(R.id.fiEnemyNerdsID);
		eSuperNerdsTv = (TextView) findViewById(R.id.fiEnemySuperNerdsID);
		eAsiansTv = (TextView) findViewById(R.id.fiEnemyAsiansID);
		eLevelTv = (TextView) findViewById(R.id.fiEnemyLevelID);
		eWeaponTv = (TextView) findViewById(R.id.fiEnemyWeaponID);
		eBrainTv = (TextView) findViewById(R.id.fiEnemyBrainPowerID);
		
		fightCostTv = (TextView) findViewById(R.id.fiCost);
		
		fightBtn = (Button) findViewById(R.id.fiFightBtnID);
		backBtn = (Button) findViewById(R.id.fiBackBtnID);
		
		fightBtn.setSoundEffectsEnabled(false);
		backBtn.setSoundEffectsEnabled(false);
		
		backBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				back();				
			}
		});	
		
		fightBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(hasFought){
					if(player.getMoney()-enemy.getReqMoney()<0){
						Toast.makeText(getApplicationContext(), "Not enough money", Toast.LENGTH_SHORT).show();
						sounds.play(backSound, 1, 1, 0, 0, 1);
					} else {
						player.setMoney(player.getMoney()-enemy.getReqMoney());
						fight();
					}					
				} else {
					player.setMoney(player.getMoney()-enemy.getReqMoney());
					fight();
				}
			}
		});
		
		
	}
	
	private void fight(){		
		hasFought = true;
		Intent fIntent = new Intent(this,FightForRealActivity.class);
		fIntent.putExtra("player", player);
		fIntent.putExtra("enemy", enemy);
		startActivityForResult(fIntent, 1);
		song.pause();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		if(!hasFought){
			song = MediaPlayer.create(getApplicationContext(), R.raw.fight_info_actual_2);
			
			pNameTv.setText(player.getName() +"'s Empire!");
			pNerdsTv.setText("Number of Nerds : " + player.getNerds());
			pSuperNerdsTv.setText("Number of Super Nerds : " + player.getSuperNerds());
			pAsiansTv.setText("Number of Asians : " + player.getAsians());
			pLevelTv.setText("Level : " + player.getLevel());
			pWeaponTv.setText("Weapon : " + player.getWeaponType());
			pBrainTv.setText("BRAIN POWER : " + player.getBrainPower());
			pBrainTv.setTextColor(Color.GREEN);
			
			eNameTv.setText(enemy.getName() +"'s ARMY");
			eNerdsTv.setText("Number of Nerds : " + enemy.getNerds());
			eSuperNerdsTv.setText("Number of Super Nerds : " + enemy.getSuperNerds());
			eAsiansTv.setText("Number of Asians : " + enemy.getAsians());
			eLevelTv.setText("Level : " + enemy.getLevel());
			eWeaponTv.setText("Weapon : " + enemy.getWeaponType());
			eBrainTv.setText("BRAIN POWER : " + enemy.getBrainPower());
			eBrainTv.setTextColor(Color.RED);
			fightCostTv.setText("Fight Cost : "+enemy.getReqMoney());
		} else {
			song = MediaPlayer.create(getApplicationContext(), R.raw.fight_info);
			layout.setBackgroundResource(R.drawable.fight_done);
			
			fightBtn.setText("Fight Again?");
			
			pNameTv.setText(playerStatus);
			pNameTv.setTextColor(Color.GREEN);
			pNerdsTv.setText(enemyStatus);
			pNerdsTv.setTextColor(Color.RED);
			pSuperNerdsTv.setText(winnerStatus);
			pAsiansTv.setText(moneyChangeStatus);
			pLevelTv.setText(expGainStatus);
			pWeaponTv.setText(enemyUpgradeStatus);
			pWeaponTv.setTextColor(Color.CYAN);
			pBrainTv.setText("");			
			
			eNameTv.setText("");
			eNerdsTv.setText("");
			eSuperNerdsTv.setText("");
			eAsiansTv.setText("");
			eLevelTv.setText("");
			eWeaponTv.setText("");
			eBrainTv.setText("");
			fightCostTv.setText("");
		}
		song.start();
		
		if(player.getHasFinished()==true){
			back();
		}
		
	}



	public void back(){
		Intent bIntent = new Intent();
		bIntent.putExtra("after_fight", player);
		bIntent.putExtra("e_after_fight", enemy);
		setResult(RESULT_OK,bIntent);
		song.stop();
		song.release();
		finish();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    	
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){    			
                player = (Player) data.getSerializableExtra("after_fight");
                enemy = (Enemy) data.getSerializableExtra("e_after_fight");                
                playerStatus = data.getStringExtra("playerStatus");
            	enemyStatus = data.getStringExtra("enemyStatus");
            	winnerStatus = data.getStringExtra("winnerStatus");
            	moneyChangeStatus = data.getStringExtra("moneyChangeStatus");
            	expGainStatus = data.getStringExtra("expGainStatus");
            	enemyUpgradeStatus = data.getStringExtra("enemyUpgradeStatus");
            }        
    	}    	
    }

	@Override
	public void onBackPressed() {
		sounds.play(backSound, 1, 1, 0, 0, 1);		
	}
	
	


}
