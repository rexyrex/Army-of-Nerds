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


public class RecruitNerdsActivity extends Activity {

	
	Player player;
	
	Button askNicelyBtn;
	Button backBtn;
	Button goClubbingBtn;
	Button buySodaBtn;
	
	TextView currentPlayerMoney;
	int clubCost;
	//int recruited;
	
	MediaPlayer song;
	SoundPool sounds;
	int clickSound;
	int backSound;

	
	String status;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		updateStatus();
		clubCost = (int) (300+player.getMoney()*0.2);
		goClubbingBtn.setText("Go Clubbing (Cost: "+clubCost+")");
		currentPlayerMoney.setText(Integer.toString(player.getMoney()));
		currentPlayerMoney.setTextColor(Color.GREEN);
		song.start();
		song.setLooping(true);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recruit_nerds);
		
		song = MediaPlayer.create(getApplicationContext(), R.raw.recruitnerdtheme);
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		backSound = sounds.load(getApplicationContext(), R.raw.back, 0);
		
		status = "None";
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		
		askNicelyBtn = (Button) findViewById(R.id.rnAskNicelyBtnID);
		backBtn = (Button) findViewById(R.id.rcBackBtnID);
		goClubbingBtn = (Button) findViewById(R.id.rnClubbingBtnID);
		buySodaBtn = (Button) findViewById(R.id.rnBuySodaBtnID);
		currentPlayerMoney = (TextView) findViewById(R.id.rnMoneyActualID);
		
		//sound off
		askNicelyBtn.setSoundEffectsEnabled(false);
		backBtn.setSoundEffectsEnabled(false);
		goClubbingBtn.setSoundEffectsEnabled(false);
		buySodaBtn.setSoundEffectsEnabled(false);
		
		buySodaBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				buySoda();
				
			}
		});
		
		goClubbingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isClubPossible()){
					sounds.play(clickSound, 1, 1, 0, 0, 1);
					song.pause();
					goClub();
				} else {
					sounds.play(backSound, 1, 1, 0, 0, 1);
					Toast.makeText(getApplicationContext(), "not enough money", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		askNicelyBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				askNicely();
				
			}
		});
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);			
				
				doneRecruiting();
				

			}
		});
		
	}
	
	public void buySoda(){
		Intent bsIntent = new Intent(this, BuySodaActivity.class);
		bsIntent.putExtra("player", player);
		startActivityForResult(bsIntent,1);
	}
	
	public void goClub(){
		Intent gcIntent = new Intent(this, GoClubActivity.class);
		gcIntent.putExtra("player", player);
		gcIntent.putExtra("cost", clubCost);
		startActivityForResult(gcIntent, 1);		
	}
	
	public boolean isClubPossible(){
		if(player.getMoney()>=clubCost){
			return true;
		} else {
			return false;
		}
	}
	
	public void askNicely(){
		Intent anIntent = new Intent(this, AskNicelyActivity.class);
		anIntent.putExtra("player", player);		
		startActivityForResult(anIntent, 1);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	/*Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
    	String code = Integer.toString(resultCode);
    	String code2 = Integer.toString(requestCode);
    	Toast.makeText(getApplicationContext(), "Request Code: "+code2, Toast.LENGTH_SHORT).show();
    	Toast.makeText(getApplicationContext(), "Result Code: "+code, Toast.LENGTH_SHORT).show();*/
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){    			
                player = (Player) data.getSerializableExtra("modifiedPlayer");
                //recruited += (int) data.getIntExtra("recruited", 0);
            }        
    	}    	
    }
	public void updateStatus(){
		status = "player has atttempted to recruit nerds";
	}
	
	


	public void doneRecruiting(){
		Intent newIntent = new Intent();
		newIntent.putExtra("modifiedPlayer",player);
		newIntent.putExtra("status", status);
		setResult(RESULT_OK, newIntent);
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	song.release();
				sounds.release();
	    		finish();	
	        }
	    }, 27);
		
	
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sounds.play(backSound, 1, 1, 0, 0, 1);
		Toast.makeText(getApplicationContext(), "back back back", Toast.LENGTH_SHORT).show();
	}

}
