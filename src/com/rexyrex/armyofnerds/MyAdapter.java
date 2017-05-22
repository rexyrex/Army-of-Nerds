package com.rexyrex.armyofnerds;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

	
	Typeface typeface;
	String[] parentList = {"1. UK (noob)", "2. US (beginner)", "3. ASIA (mediocre)", "4. Company (hard)", "5. Adrian (respectable)"};
	static String[][] childList = {
			{"University of Liverpool","Manchester University","Nottingham University","UCL","Kings College London","Imperial College London","Cambridge"},
			{"Oregon University", "UC Berkley", "Brown University", "Stanford University", "Harvard University", "MIT"},
			{"NUS","Indian Institute of Technology", "University Of Hong Kong", "Shanghai University", "KAIST", "Seoul University"},
			{"Razer","Apple","LG","Samsung", "Google"},
			{"Bye Bye World Coorperation", "Alex Da Player Coorperation", "Double Burger Coorperation"}			
	};
	
	private Context context;
	public MyAdapter(Context context) {
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setText(childList[groupPosition][childPosition]);
		tv.setPadding(100, 0, 0, 0);
		tv.setTextSize(32);
		//tv.setTextColor(Color.MAGENTA);
		return tv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parentList.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		typeface=Typeface.createFromAsset(context.getAssets(), "kbr.ttf");
		
		TextView tv = new TextView(context);
		tv.setText(parentList[groupPosition]);
		tv.setPadding(100, 0, 0, 0);
		tv.setTextSize(38);
		tv.setTypeface(typeface);
		if(groupPosition==0 || groupPosition==1){
			tv.setTextColor(Color.GREEN);
		}
		if(groupPosition==2 || groupPosition==3){
			tv.setTextColor(Color.MAGENTA);
		}
		if(groupPosition==4){
			tv.setTextColor(Color.RED);
		}
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
