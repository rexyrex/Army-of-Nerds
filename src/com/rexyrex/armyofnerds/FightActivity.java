package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;


public class FightActivity extends Activity {

	ExpandableListView list;
	Button back;
	Player player;
	Enemy enemy;
	MediaPlayer song;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fight);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		enemy = (Enemy) getIntent.getSerializableExtra("enemy");
		
		song = MediaPlayer.create(getApplicationContext(), R.raw.fight_info_actual);
		
		list = (ExpandableListView) findViewById(R.id.expandableListView1);
		back = (Button) findViewById(R.id.backToMenuFromFightID);
		
		back.setSoundEffectsEnabled(false);
		
		list.setAdapter(new MyAdapter(this));
		
		back.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				goBack();
			}
		});		
		
		list.setOnChildClickListener(new OnChildClickListener() {			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String item = MyAdapter.childList[groupPosition][childPosition];
				enemy.setEnemy(groupPosition,childPosition);
				if(enemy.getReqLevel()>player.getLevel() || enemy.getReqMoney()>player.getMoney()){
					Toast.makeText(getApplicationContext(), item+" requires lvl "+enemy.getReqLevel() + " and "+enemy.getReqMoney() + " money", Toast.LENGTH_SHORT).show();
				} else {
					goFightInfo(enemy);
					song.pause();
				}				
				return false;
			}
		});
	}	
	
	private void goFightInfo(Enemy enemy){
		Intent gfiIntent = new Intent(this, FightInfoActivity.class);
		gfiIntent.putExtra("player", player);
		gfiIntent.putExtra("enemy", enemy);
		startActivityForResult(gfiIntent, 1);
		
	}
	
	private void goBack(){
		Intent gbIntent = new Intent();
		gbIntent.putExtra("after_fight", player);
		gbIntent.putExtra("e_after_fight", enemy);
		setResult(RESULT_OK, gbIntent);
		song.stop();
		song.release();
		finish();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		song.start();
		song.setLooping(true);
		
		if(player.getHasFinished()==true){
			goBack();
		}
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    	
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK){    			
                player = (Player) data.getSerializableExtra("after_fight");
                enemy = (Enemy) data.getSerializableExtra("e_after_fight");
            }        
    	}    	
    }


	@Override
	public void onBackPressed() {
	
	}
	
	

}
