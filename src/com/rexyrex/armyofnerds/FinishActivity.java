package com.rexyrex.armyofnerds;

import java.io.IOException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinishActivity extends Activity {

	Button emailBtn;
	Button backBtn;
	RexEncrypt encryption;
	Player player;
	String playerStatsTitle;
	String playerStatsToBeEncrypted;
	MediaPlayer song;
	boolean emailSent=false;
	TextView stats;
	
	
	NotificationManager nm;
	static final int uniqueID = 234234;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		
		
		
		stats = (TextView) findViewById(R.id.finishPlayerStatsTvID);
		
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		
		if (player == null){			
			finish();
		}
		
		song = MediaPlayer.create(getApplicationContext(), R.raw.finish_song);
		song.start();
		song.setLooping(true);
		
		encryption = new RexEncrypt();
				
		String message = "Name : "+player.getName()+"\n"+
				"Current Exp : " + player.getExp() +"\n"+
				"Current Money : " + player.getMoney() +"\n"+
				"Nerds : " + player.getNerds() +"\n"+
				"SuperNerds : " + player.getSuperNerds() +"\n"+
				"Asians : " + player.getAsians() +"\n"+
				"Level : " + player.getLevel() +"\n"+
				"Date : " + player.getDate() +"\n"+
				"Weapon : " + player.getWeaponType() +"\n"+
				"Loi : " + player.getLoi() +"\n"+
				"Brain Power : " + player.getBrainPower() +"\n"+
				"\n---- FIGHT STATS ----\n" +
				"Total Money Gain from Fighting : " + player.getTotalMoney() +"\n"+
				"Total Money Lost from Fighting : " + player.getTotalMoneyLost() +"\n"+
				"Total Exp Gain from Fighting : " + player.getTotalExpGained() +"\n"+
				"Total Money Gain from Lang Loi : " + player.getLoiMoney() +"\n"+
				"Total Exp Gain from Lang Loi : " + player.getLoiExp() +"\n"+
				"Total Rounds Won : " + player.getTotalRoundsWon() +"\n"+
				"Total Rounds Lost : " + player.getTotalRoundsLost() +"\n"+
				"Total Battles Won : " + player.getTotalOverallWon() +"\n"+
				"Total Battles Lost : " + player.getTotalOverallLost() +"\n"+
				"\n----GAMBLE STATS----\n" +
				"Number of times Gambled : " + player.getNumberOfTimesGambling()+"\n"+
				"Money Gained from Gambling : " + player.getTotalMoneyGainedFromGambling()+"\n"+
				"Money Lost from Gambling : " + player.getTotalMoneyLostFromGambling()+"\n"+
				"Number of times tried to gamble before timer : " + player.getGambleBeforeTimer()+"\n"+
				"\n----CLUBBING STATS----\n" +
				"Number of Times Clubbing : " + player.getNumberOfTimesClubbing()+"\n"+
				"Money Spent Clubbing : " + player.getMoneySpentClubbing()+"\n"+
				"Number of Nerds Gain From Clubbing : " + player.getNerdsFromClubbing()+"\n"+
				"Number of Lang Loi From Clubbing : " + player.getlangLoiFromClubbing()+"\n"+
				"\n----ASK NICELY STATS----\n" +
				"Number of Times Asked Nicely : " + player.getNumberOfTimesAskingNicely()+"\n" +
				"Number of Nerds Gain from Asking Nicely : " + player.getNerdsFromAskingNicely()+"\n" +
				"Money Mugged from Asking Nicely : " + player.getMuggedAmountAskingNicely()+"\n" +
				"Number of times Cried like a baby : " + player.getNumberOfTimesCried()+"\n" +
				"\n----SODA STATS----\n" +
				"Number Of Times went to Buy Soda : " + player.getNumberOfTimesBuySoda()+"\n" +
				"Total Number of Soda's Bought : " + player.getNumberOfSodaBought()+"\n"+
				"Number of Nerds Bought with Soda : " + player.getNumberOfNerdsFromSoda()+"\n"+
				"Number of SuperNerds Bought with Soda : " + player.getNumberOfSuperNerdsFromSoda() +"\n"+
				"Number of Asians Bought with Soda : " + player.getNumberOfAsiansFromSoda()+"\n"+
				"Money Spent on Buying Soda : " + player.getMoneySpentOnSoda() +"\n"+
				"\n----LIBRARY STATS----\n" +
				"Number of Times went to Library : " + player.getNumberOfTimesGoLibrary() +"\n"+
				"Number of Times weapon Upgraded : " + player.getNumberOfTimesUpgraded() +"\n"+
				"Total Money spent on weapons : " + player.getMoneySpentInLibrary() +"\n"+
				"\n----GYM STATS----\n" +
				"Gym Visits : " + player.getNumberOfTimesGoGym()+"\n"+
				"Minutes at the Gym : " + player.getNumberOfMinutesAtGym()+"\n"+
				"Money Spent at Gym : " + player.getMoneySpentInGym()+"\n"+
				"Number of loi from Gym : " + player.getNumberOfLoiFromGym()+"\n"+
				"Exp From Gym : " + player.getExpFromGym()+"\n"+
				"\n----OTHER STATS----\n" +
				"Total Number of Saves : " + player.getSaves()+"\n"+
				"Total Number of Loads : " + player.getLoads()+"\n"+
				"";
		
		stats.setText(message);
		
		encryption.setMessage(message);
		
		encryption.encrypt();
		
		playerStatsToBeEncrypted = encryption.getEncryptedMessage();
		
		playerStatsTitle = "<This Message is encrypted using RexEncryption version 1.0>\n" +
						"[This msg is encrypted cuz I dont want you to change your stats before sending me them ;). Thanks again for playing!]" +
						"<RexEncryptStart>"+
						playerStatsToBeEncrypted +
						"<RexEncryptEnd>";
		
		
		emailBtn = (Button) findViewById(R.id.finishEmailBtnID);
		backBtn = (Button) findViewById(R.id.finishBackBtnID);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				nm.cancel(uniqueID);
				song.stop();
				song.release();
				finish();				
			}
		});
		
		emailBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!emailSent){
					WallpaperManager wm = WallpaperManager
						    .getInstance(getApplicationContext());
					
					try {
						wm.setResource(R.drawable.double_burger);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					notifyRex();
					sendEmail();
					emailSent=true;
					emailBtn.setText("Go Save Your Finished Game!");
				} else {
					song.stop();
					song.release();
					finish();
				}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	protected void notifyRex(){
		Intent nIntent = new Intent(this, ByeByeWorldActivity.class);
		
		PendingIntent pi = PendingIntent.getActivity(this, 0, nIntent, 0);
		String body = "Thanks a lot for playing ArmyOfNerds! Double Burger Man Approves!";
		String title = "You're awesome!";		
		
		Notification n = new Notification(R.drawable.status_icon, body, System.currentTimeMillis());
		n.setLatestEventInfo(this, title, body, pi);
		n.defaults = Notification.DEFAULT_ALL;		
		nm.notify(uniqueID, n);
	}
	protected void sendEmail() {
		// TODO Auto-generated method stub
		String emailAddresses[] = {"minhyung.kim1234@gmail.com"};
		String message = playerStatsTitle;
		Intent newIntent = new Intent(android.content.Intent.ACTION_SEND);
		newIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddresses);
		newIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, player.getName() + " finished ArmyOfNerds!!");
		newIntent.setType("plain/text");
		newIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(newIntent);
		Toast.makeText(getApplicationContext(), "sent!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {		
	}

	

}
