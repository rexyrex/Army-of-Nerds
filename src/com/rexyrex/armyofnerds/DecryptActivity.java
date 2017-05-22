package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DecryptActivity extends Activity {

	Button backBtn;
	EditText inputEt;
	EditText outputEt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_decrypt);
		backBtn = (Button) findViewById(R.id.backFromDecrypt);
		inputEt = (EditText) findViewById(R.id.encryptedEtID);
		outputEt = (EditText) findViewById(R.id.decryptedEtID);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	
	
		inputEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s != null){
					RexEncrypt encryption = new RexEncrypt();
					encryption.setEncryptedMessage(s.toString());
					encryption.decrypt();
					outputEt.setText(encryption.getMessage());					
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	
	



}
