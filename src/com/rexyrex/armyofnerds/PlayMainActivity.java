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
import android.widget.TextView;
import android.widget.Toast;


public class PlayMainActivity extends Activity {

	Button fightbtn;
	Button upgradebtn;
	Button savebtn;
	Button quitbtn;
	String name;
	Player player;
	Enemy enemy;
	TextView nerdsValue;
	TextView levelValue;
	TextView weaponValue;
	TextView moneyValue;
	TextView nameValue;
	TextView superNerdsValue;
	TextView asiansValue;
	TextView experienceValue;
	TextView brainPowerValue;
	TextView langLoiValue;
	
	boolean finishedAndSaved=false;
	
	int upgradeSongPosition=0;
	
	SoundPool sounds;
	int clickSound;
	int developSound;
	int backSound;

	MediaPlayer mainSong;
	
	boolean loadState;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_main);
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		developSound = sounds.load(getApplicationContext(), R.raw.developerbell, 0);
		
		mainSong = MediaPlayer.create(getApplicationContext(), R.raw.playmaintheme);
		
		Intent gIntent = getIntent();
		

			player = (Player) gIntent.getSerializableExtra("player");
			enemy = (Enemy) gIntent.getSerializableExtra("enemy");
			
			Toast.makeText(getApplicationContext(), "Initializing", Toast.LENGTH_SHORT).show();

		loadState = gIntent.getBooleanExtra("loadState", false);
	
		levelValue = (TextView) findViewById(R.id.mainValueNerdLevelID);
		weaponValue = (TextView) findViewById(R.id.mainValueWeaponID);
		moneyValue = (TextView) findViewById(R.id.mainValueMoneyID);
		nerdsValue = (TextView) findViewById(R.id.mainValueNerdsID);
		upgradebtn = (Button) findViewById(R.id.btn_upgrade);
		quitbtn = (Button) findViewById(R.id.btn_quit);
		fightbtn = (Button) findViewById(R.id.btn_fight);
		nameValue = (TextView) findViewById(R.id.playerNameDisplay);
		superNerdsValue = (TextView) findViewById(R.id.mainValueSuperNerdsID);
		asiansValue = (TextView) findViewById(R.id.mainValueAsiansID);
		experienceValue = (TextView) findViewById(R.id.mainValueExpID);
		brainPowerValue = (TextView) findViewById(R.id.mainValueBrainPowerID);
		langLoiValue = (TextView) findViewById(R.id.mainValueLoiID);
		savebtn = (Button) findViewById(R.id.btn_save);
		
		

		
		nameValue.setOnLongClickListener(new View.OnLongClickListener() {			
			@Override
			public boolean onLongClick(View v) {
				sounds.play(developSound, 1, 1, 0, 0, 1);
				goDeveloperMode();
				return loadState;
			}
		});
		
		savebtn.setSoundEffectsEnabled(false);
		
		savebtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				goSave();			
			}
		});
		
		fightbtn.setSoundEffectsEnabled(false);
		
		fightbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainSong.pause();
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				goFight();				
			}
		});
		
		
		upgradebtn.setSoundEffectsEnabled(false);
		
		upgradebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainSong.pause();
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				goToUpgrade();				
			}
		});
		
		quitbtn.setSoundEffectsEnabled(false);
		
		quitbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				
				quitAndGoMain();
			}
		});
		

	}
	
	private void goDeveloperMode(){
		Intent gdmIntent = new Intent(this, DeveloperModeActivity.class);
		gdmIntent.putExtra("player", player);

		startActivityForResult(gdmIntent, 4);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		
		
		mainSong.start();
		mainSong.setLooping(true);
		nameValue.setText(player.getName()+"'s Empire!");
		nerdsValue.setText("Number of Nerds : " + Integer.toString(player.getNerds()));
		superNerdsValue.setText("Number of Super Nerds : " + Integer.toString(player.getSuperNerds()));
		asiansValue.setText("Number of Asians : " + Integer.toString(player.getAsians()));
		levelValue.setText("Level : " + Integer.toString(player.getLevel()));
		experienceValue.setText("Experience : " + player.getExp() + "/" + player.getNeededExp());	
		weaponValue.setText("Weapon : " + player.getWeaponType());
		langLoiValue.setText("Lang Loi : " + player.getLoi());
		moneyValue.setText("Money : " + Integer.toString(player.getMoney()));
		moneyValue.setTextColor(Color.GREEN);
		brainPowerValue.setText("TOTAL BRAIN POWER : " + player.getBrainPower());
		
		if(player.getHasFinished()==true && finishedAndSaved==false){
			goSave();
			finishedAndSaved=true;
		} else if(finishedAndSaved==true){
			quitAndGoMain();
		}
		
		
	}
	
	private void goSave(){
		Intent gsIntent = new Intent(this, SaveActivity.class);
		gsIntent.putExtra("player", player);
		gsIntent.putExtra("enemy", enemy);
		startActivityForResult(gsIntent, 3);
	}
	
	public void goFight(){
		Intent gfIntent = new Intent(this, FightActivity.class);
		gfIntent.putExtra("player", player);
		gfIntent.putExtra("enemy", enemy);
		startActivityForResult(gfIntent, 2);
	}
	
	public void goToUpgrade(){
		Intent gtuIntent = new Intent(this, UpgradeActivity.class);
		gtuIntent.putExtra("player", player);
		gtuIntent.putExtra("songPosition", upgradeSongPosition);
		startActivityForResult(gtuIntent, 1);
	}
	
	@Override
	public void onBackPressed(){
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "nope", Toast.LENGTH_SHORT).show();
	}
	
	private void quitAndGoMain(){
		//Toast.makeText(getApplicationContext(), "Dont be such a PUSS and finish the game!!", Toast.LENGTH_SHORT).show();
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	mainSong.stop();
	        	mainSong.release();
				sounds.release();
				finish();	

	        }
	    }, 27);
		
	}



	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
    	//String code = Integer.toString(resultCode);
    	//String code2 = Integer.toString(requestCode);
    	//Toast.makeText(getApplicationContext(), "Request Code: "+code2, Toast.LENGTH_SHORT).show();
    	//Toast.makeText(getApplicationContext(), "Result Code: "+code, Toast.LENGTH_SHORT).show();
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){
    			
                player = (Player) data.getSerializableExtra("after_upgrade");
                upgradeSongPosition = data.getIntExtra("songPosition", 0);
            }        
    	}
    	if(requestCode == 2){
    		if(resultCode == RESULT_OK){
    			
                player = (Player) data.getSerializableExtra("after_fight");
                enemy = (Enemy) data.getSerializableExtra("e_after_fight");
            }        
    	}
    	if(requestCode == 3){
    		if(resultCode == RESULT_OK){
    			
                player = (Player) data.getSerializableExtra("after_save");
                enemy = (Enemy) data.getSerializableExtra("e_after_save");
            }        
    	}
    	if(requestCode == 4){
    		if(resultCode == RESULT_OK){
    			
                player = (Player) data.getSerializableExtra("after_develop");
         
            }        
    	}
    }
	
	
	
}
