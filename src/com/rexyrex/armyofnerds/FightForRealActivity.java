package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;




public class FightForRealActivity extends Activity {
	
	Chronometer c;
	Button cheer2Btn;
	Button cheerBtn;
	Button doneBtn;
	
	Typeface typeface;
	
	TextView titleTv;
	long time;
	Player player;
	Enemy enemy;
	boolean fightDone;
	int fightLength;
	int round;
	int playerScore;
	int enemyScore;
	
	int loiExtraMoney;
	int loiExtraExp;
	
	String fightStatus;
	String actualStatusP;
	String actualStatusVs;
	String actualStatusE;
	String actualStatusStats;
	
	String actualLoiOnClick1;
	String actualLoiOnClick2;
	
	TextView statusPTv;
	TextView statusETv;
	TextView statusVsTv;
	TextView statusStatsTv;
	
	TextView loi1Tv;
	TextView loi2Tv;
	
	TextView scoreStatusTv;
	TextView scoreStatus2Tv;
	String actualScoreStatus;
	String actualScoreStatus2;
	
	//to be sent to parent activity
	String playerStatus;
	String enemyStatus;
	String winnerStatus;
	String moneyChangeStatus;
	String expGainStatus;
	String enemyUpgradeStatus;
	
	int nextFightTime;
	
	int roundMoneyGained;
	int roundMoneyLoss;
	int roundExpGained;
	
	int totalMoneyChange;
	int totalExpGained;
	
	MediaPlayer fightMusic;
	SoundPool sounds;
	int winSound;
	int loseSound;
	int loiSuccessSound1;
	int loiSuccessSound2;
	int clickSound;
	int winRoundSound;
	int loseRoundSound;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fight_for_real);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		enemy = (Enemy) getIntent.getSerializableExtra("enemy");
		
		fightDone = false;
		
		typeface=Typeface.createFromAsset(this.getAssets(), "kbr.ttf");
		
		actualStatusP = "";
		actualStatusVs = "";
		actualStatusE = "";
		
		actualLoiOnClick1 = "";
		actualLoiOnClick2 = "";
		
		round = 0;
		playerScore = 0;
		enemyScore = 0;
		nextFightTime = 1;
		
		/*fightLength = (int) (Math.random() * 4 + 4);
		if(fightLength%2==0){
			fightLength++;
		}*/
		
		fightLength = 5;
		
		
		//Music choice
		int mChoice = enemy.getG()+1;
		switch(mChoice){
		case 1: fightMusic = MediaPlayer.create(FightForRealActivity.this, R.raw.fightmusic);
			break;
		case 2: fightMusic = MediaPlayer.create(FightForRealActivity.this, R.raw.fight_lvl5);
			break;
		case 3:	fightMusic = MediaPlayer.create(FightForRealActivity.this, R.raw.fight_lvl2);
			break;
		case 4:	fightMusic = MediaPlayer.create(FightForRealActivity.this, R.raw.fight_lvl3);
			break;
		case 5:	fightMusic = MediaPlayer.create(FightForRealActivity.this, R.raw.fight_lvl4);
			break;
		}
		
		
		
		
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		winSound = sounds.load(getApplicationContext(), R.raw.fightwincheer, 0);
		loseSound = sounds.load(getApplicationContext(), R.raw.fightlose, 0);
		loiSuccessSound1 = sounds.load(getApplicationContext(), R.raw.thip_cheer, 0);
		loiSuccessSound2 = sounds.load(getApplicationContext(), R.raw.deedoo, 0);
		clickSound = sounds.load(getApplicationContext(), R.raw.click, 0);
		winRoundSound = sounds.load(getApplicationContext(), R.raw.greevil_point_team, 0);
		loseRoundSound = sounds.load(getApplicationContext(), R.raw.lose, 0);
		
		c = (Chronometer) findViewById(R.id.fChromometer);
		cheer2Btn = (Button) findViewById(R.id.fDoneBtnID);
		cheerBtn = (Button) findViewById(R.id.fCheerID);
		doneBtn = (Button) findViewById(R.id.fRealDoneBtnID);
		statusPTv = (TextView) findViewById(R.id.fStatusPID);
		statusVsTv = (TextView) findViewById(R.id.fStatusVsID);
		statusETv = (TextView) findViewById(R.id.fStatusEID);
		statusStatsTv = (TextView) findViewById(R.id.fStatusStatsID);
		
		loi1Tv = (TextView) findViewById(R.id.fCheerStatus1ID);
		loi2Tv = (TextView) findViewById(R.id.fCheerStatus2ID);
		
		titleTv = (TextView) findViewById(R.id.fTitleID);
		
		scoreStatusTv = (TextView) findViewById(R.id.fBigStatusID);
		scoreStatus2Tv = (TextView) findViewById(R.id.fBigCurrentStatusID);
		
		scoreStatusTv.setTextColor(Color.CYAN);
		
		//sound off
		cheerBtn.setSoundEffectsEnabled(false);
		cheer2Btn.setSoundEffectsEnabled(false);
		doneBtn.setSoundEffectsEnabled(false);
		
		titleTv.setText("Fighting... \nRound:"+"1/"+fightLength);
		
		fightMusic.start();
		//start chronometer
		c.setBase(SystemClock.elapsedRealtime()+time);
		c.start();
	
		
		c.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			
			@Override
			public void onChronometerTick(Chronometer chronometer) {				
				String cText = c.getText().toString();
				String array[] = cText.split(":");
				int timeElapsed = Integer.parseInt(array[1]);
				
				if(timeElapsed==nextFightTime){
					
					
					//get next fight time
					nextFightTime += 3;
							//(int) (Math.random()*4+2);
					
					//get each player power
					int pPower = (int) (Math.random() * player.getBrainPower());
					int ePower = (int) (Math.random() * enemy.getBrainPower());
					
					//increment round
					round++;
					
					tvReset();
					
					//Small text status
					
					
												
					actualStatusP += player.getName()+" produced\n"
									+ pPower +  " Brain Power!\n";
					actualStatusVs = "VS\n";
					actualStatusE = enemy.getName()+" produced\n"
									+ ePower + " Brain Power!\n";
					
					
					//Determine round winner
					if(pPower>ePower){
						//Player win case
						sounds.play(winRoundSound, 1, 1, 0, 0, 1);
						
						actualStatusStats += player.getName() + " wins this round!\n";
						actualScoreStatus2 = Integer.toString(pPower)+" > "+Integer.toString(ePower);
						scoreStatus2Tv.setTextColor(Color.GREEN);
						playerScore++;
						
						roundExpGained = (int) (enemy.giveExp()*(1/(double)fightLength) * (Math.random()+1));
						roundMoneyGained = (int) (enemy.giveMoney()*(1/(double)fightLength) * (Math.random()+1)*enemy.getStrengthMultiplier());
						roundMoneyGained += loiExtraMoney;
						roundExpGained += loiExtraExp;
						
						actualStatusStats += "Loi $ :" + loiExtraMoney;
						actualStatusStats += "\nLoi exp :" + loiExtraExp+'\n';						
						
						totalMoneyChange += roundMoneyGained;
						totalExpGained += roundExpGained;
						
						actualStatusStats += "MONEY EARNED : " + roundMoneyGained+"\n";
						actualStatusStats += "EXP   EARNED : " + roundExpGained+"\n";
						
						statusPTv.setTextColor(Color.GREEN);
						statusPTv.setTextSize(30);
						statusETv.setTextColor(Color.RED);
						statusETv.setTextSize(20);
						
					} else if (ePower>pPower){
						//Player Lose Case
						sounds.play(loseRoundSound, 1, 1, 0, 0, 1);
						
						actualStatusStats += enemy.getName() + " wins this round!\n";
						actualScoreStatus2 = Integer.toString(pPower)+" < "+Integer.toString(ePower);
						scoreStatus2Tv.setTextColor(Color.RED);
						enemyScore++;
						
						roundMoneyLoss = (int) (enemy.giveMoney()*(1/(double)fightLength) * (Math.random()+1)*enemy.getStrengthMultiplier());
						totalMoneyChange -= roundMoneyLoss;
						
						statusPTv.setTextColor(Color.GREEN);
						statusPTv.setTextSize(20);
						statusETv.setTextColor(Color.MAGENTA);
						statusETv.setTextSize(30);
						
						//Player No Money To lose Case
						if((player.getMoney()+totalMoneyChange)<0){
							roundMoneyLoss = 0;
							totalMoneyChange = -player.getMoney();
							actualStatusStats += "MONEY LOST : 0 (Cuz u have no Money!)\n";
						} else {
							actualStatusStats += "MONEY LOST : " + roundMoneyLoss+"\n";
						}
						//Player never loses Exp
						actualStatusStats += "EXP   LOST : " + 0+"\n\n";
						
						
					} else {
						//Tie
						actualStatusStats += "It's a TIE! Lets just say "+player.getName()+" wins!\n\n\n";
						actualScoreStatus2 = Integer.toString(pPower)+" = "+Integer.toString(ePower);
						scoreStatus2Tv.setTextColor(Color.WHITE);
						playerScore++;
					}
					
					loiExtraMoney=0;
					loiExtraExp=0;
					
					//Update Text Views
					statusUpdate();
				}
				
				//All rounds have been played
				if(round==fightLength){
					
					
					
					fightStatus = player.getName()+" won "+playerScore+" rounds\n"+enemy.getName()+" won "+enemyScore+"rounds\n";
					
					
	
					
					if(playerScore>enemyScore){
						sounds.play(winSound, 1, 1, 0, 0, 1);
						
						winnerStatus = player.getName()+" wins overall!";						
						enemy.incStrengthMultiplier();
						enemyUpgradeStatus ="Since you won, enemy is slightly stronger";
						
						player.incTotalOverallWon(1);
						
						//WIN THE ENTIRE GAME CASE
						if(enemy.getName().equals("Double Burger Coorperation")){
							player.finish();
							gameIsFinished();
						}
						
					} else {	
						sounds.play(loseSound, 1, 1, 0, 0, 1);
						if(totalMoneyChange>0){
							totalMoneyChange=0;
						}
						if(totalExpGained>0){
							totalExpGained=0;
						}
						winnerStatus = enemy.getName()+" wins overall!";
						enemyUpgradeStatus = "Better Luck Next time!";
						
						player.incTotalOverallLost(1);
						
					}
					
					//update player money and exp
					player.setMoney(player.getMoney()+totalMoneyChange);
					player.setExp(player.getExp()+totalExpGained);
					
					if(totalMoneyChange>0){
						player.incTotalMoney(totalMoneyChange);
					} else {
						player.incTotalMoneyLost(totalMoneyChange);
					}
					
					player.incTotalExp(totalExpGained);
					
					player.incTotalRoundsLost(enemyScore);
					player.incTotalRoundsWon(playerScore);
					
					//strings to be sent to parent activity
					playerStatus=player.getName()+" : "+playerScore;
					enemyStatus=enemy.getName()+" : "+enemyScore;				
					if(totalMoneyChange<0){
						moneyChangeStatus="Net Money Change = " + totalMoneyChange;
					} else {
						moneyChangeStatus="Net Money Change = +" + totalMoneyChange;
					}
					expGainStatus="Total Exp Gained = +" + totalExpGained;
					
					
					//done
					c.stop();					
					fightDone=true;
					tvReset();
					statusUpdate();
					scoreStatusTv.setTextSize(60);
					scoreStatusTv.setTextColor(Color.CYAN);
					scoreStatus2Tv.setText("FIGHT IS OVER");
					doneBtn.setText("Fight is Over! Click to see results!");
					fightMusic.pause();
				}				
			}
		});
		
		cheer2Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!fightDone){
					int chance = (int) (Math.random()*100);
					if(chance<10){
						loiChanceProc2();
						
					} else if(chance<40){
						loiChanceProc1();
						
					}
					actualLoiOnClick2 = "+" + loiExtraMoney + " $\n";
					actualLoiOnClick2 += "+" + loiExtraExp + " exp\n";
					loi2Tv.setText(actualLoiOnClick2);
				} else {
					cheer2Btn.setText("fight is over");
				}
			}
		});
		
		doneBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sounds.play(clickSound, 1, 1, 0, 0, 1);
				if(!fightDone){
					
					doneBtn.setText("Cheer Below!");
				} else {
					fightMusic.release();
					doneFight();
				} 
			}
		});		
		
		cheerBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!fightDone){
					int chance = (int) (Math.random()*100);
					if(chance<10){
						loiChanceProc2();						
					} else if(chance<40){
						loiChanceProc1();						
					}
					actualLoiOnClick1 = "+" + loiExtraMoney + " $\n";
					actualLoiOnClick1 += "+" + loiExtraExp + " exp\n";
					loi1Tv.setText(actualLoiOnClick1);
				} else {
					cheerBtn.setText("fight is over");
				}
			}
		});
	}
	
	private void statusUpdate(){
		actualScoreStatus = player.getName() + " "+playerScore+" VS "+enemyScore+" "+enemy.getName();
		
		scoreStatus2Tv.setText(actualScoreStatus2);					
		scoreStatusTv.setText(actualScoreStatus);
		titleTv.setText("Fighting... \nRound:"+round+"/"+fightLength);
		//print small text status to screen
		statusPTv.setText(actualStatusP);
		statusVsTv.setText(actualStatusVs);
		statusETv.setText(actualStatusE);
		statusStatsTv.setText(actualStatusStats);
		
		statusPTv.setTypeface(typeface);
		statusETv.setTypeface(typeface);
	}
	
	private void tvReset(){
		actualStatusP = "";
		actualStatusVs = "";
		actualStatusE = "";
		actualStatusStats = "";		
		//loi reset
		actualLoiOnClick1 = "";
		actualLoiOnClick2 = "";
	}
	
	private void loiChanceProc1(){
		sounds.play(loiSuccessSound1, (float)0.7, (float)0.7, 0, 0, 1);
		loiExtraExp += player.getLoi()+(int)(enemy.giveExp()*0.0047);
		loiExtraMoney += (int)(player.getLoi()*1.5)+1+(int) (enemy.giveMoney()*0.007);	
		player.incLoiExp(player.getLoi());
		player.incLoiMoney((int)(player.getLoi()*1.5));
	}
	
	private void loiChanceProc2(){
		sounds.play(loiSuccessSound2, (float)0.7, (float)0.7,0, 0, 1);
		loiExtraExp += player.getLoi()*2+(int)(enemy.giveExp()*0.0077);
		loiExtraMoney += player.getLoi()*4+2+(int) (enemy.giveMoney()*0.017);
		player.incLoiExp(player.getLoi()*2);
		player.incLoiMoney(player.getLoi()*4);
	}

	@Override
	public void onBackPressed() {	
	
	}
	
	private void doneFight(){
		Intent dfIntent = new Intent();
		dfIntent.putExtra("after_fight", player);
		dfIntent.putExtra("e_after_fight", enemy);
		dfIntent.putExtra("status", fightStatus);
		dfIntent.putExtra("playerStatus", playerStatus);
		dfIntent.putExtra("enemyStatus", enemyStatus);
		dfIntent.putExtra("winnerStatus", winnerStatus);
		dfIntent.putExtra("moneyChangeStatus", moneyChangeStatus);
		dfIntent.putExtra("expGainStatus", expGainStatus);
		dfIntent.putExtra("enemyUpgradeStatus", enemyUpgradeStatus);
	    	
    	
		setResult(RESULT_OK, dfIntent);
		sounds.release();
		fightMusic.release();
		finish();
	}
	
	private void gameIsFinished(){
		Intent doneIntent = new Intent(this, FinishActivity.class);
		doneIntent.putExtra("player", player);
		startActivity(doneIntent);		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(player.getHasFinished()==true){
			doneFight();
		}
	}



}
