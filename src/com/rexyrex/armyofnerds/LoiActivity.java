package com.rexyrex.armyofnerds;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoiActivity extends Activity {

	Button btn;
	ImageView iv;
	Random r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loi);
		r=new Random();
		int l = r.nextInt(15)+1;
		
		iv = (ImageView) findViewById(R.id.loiImageID);
		btn = (Button) findViewById(R.id.loiDoneBtnID);
		
		
		switch(l){
		case 1:	iv.setImageResource(R.drawable.loi1);
			break;
		case 2:	iv.setImageResource(R.drawable.loi2);
			break;
		case 3:	iv.setImageResource(R.drawable.loi3);
			break;
		case 4:	iv.setImageResource(R.drawable.loi4);
			break;
		case 5:	iv.setImageResource(R.drawable.loi5);
			break;
		case 6:	iv.setImageResource(R.drawable.loi6);
			break;
		case 7:	iv.setImageResource(R.drawable.loi7);
			break;
		case 8:	iv.setImageResource(R.drawable.loi8);
			break;
		case 9:	iv.setImageResource(R.drawable.loi9);
			break;
		case 10:	iv.setImageResource(R.drawable.loi10);
			break;
		case 11:	iv.setImageResource(R.drawable.loi11);
			break;
		case 12:	iv.setImageResource(R.drawable.loi12);
			break;
		case 13:	iv.setImageResource(R.drawable.loi13);
			break;
		case 14:	iv.setImageResource(R.drawable.loi14);
			break;
		case 15:	iv.setImageResource(R.drawable.loi15);
			break;
		}
		
		//iv.setImageResource(R.drawable.loi.loi1);
		
		btn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loi, menu);
		return true;
	}

}
