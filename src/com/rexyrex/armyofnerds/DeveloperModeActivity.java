package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DeveloperModeActivity extends Activity {
	
	EditText et;
	EditText passEt;
	RadioGroup rg;
	RadioButton moneyBtn;
	RadioButton levelBtn;
	RadioButton loiBtn;
	Button setBtn;
	Button backBtn;
	Player player;
	int selection=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_developer_mode);
		
		Intent getIntent = getIntent();
		player = (Player) getIntent.getSerializableExtra("player");
		et = (EditText) findViewById(R.id.developerETID);
		passEt = (EditText) findViewById(R.id.developerPasswordID);
		rg = (RadioGroup) findViewById(R.id.developerRGID);
		moneyBtn = (RadioButton) findViewById(R.id.developerMoneyID);
		levelBtn = (RadioButton) findViewById(R.id.developerLevelID);
		loiBtn = (RadioButton) findViewById(R.id.developerLangLoiID);
		setBtn = (Button) findViewById(R.id.developerSetBtnID);
		backBtn = (Button) findViewById(R.id.developerBackBtnID);
		
		setBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = "not initialized";
				if(passEt.getText().toString() != null){
					password = passEt.getText().toString();
				}
				
				if(password.equals("7777")){
					int value = Integer.parseInt(et.getText().toString());
					if(selection==1){
						player.setMoney(Integer.parseInt(et.getText().toString()));
						Toast.makeText(getApplicationContext(), "Money set to: "+value, Toast.LENGTH_SHORT).show();
					} else if(selection ==2){
						player.setLevel(Integer.parseInt(et.getText().toString()));
						Toast.makeText(getApplicationContext(), "lvl set to: "+value, Toast.LENGTH_SHORT).show();
					} else if(selection ==3){
						player.setLoi(Integer.parseInt(et.getText().toString()));
						Toast.makeText(getApplicationContext(), "loi set to: "+value, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "ERROR SELECTION", Toast.LENGTH_SHORT).show();
					}
				} else {
					//if password wrong
					Toast.makeText(getApplicationContext(), "JUST GET OUT BRO LOL", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==moneyBtn.getId()){
					selection = 1;
				} else if(checkedId==levelBtn.getId()){
					selection = 2;
				} else {
					selection = 3;
				}				
			}
		});
		
		
		
		backBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				goBack();				
			}
		});
		
		
	}
	
	private void goBack(){
		Intent gbIntent = new Intent();
		gbIntent.putExtra("after_develop", player);
		setResult(RESULT_OK, gbIntent);
		finish();
	}


}
