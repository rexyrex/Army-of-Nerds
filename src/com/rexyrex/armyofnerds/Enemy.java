package com.rexyrex.armyofnerds;

import java.io.Serializable;

public class Enemy implements Serializable {

	private static final long serialVersionUID = 1L;
	static String[][] names = {
		{"University of Liverpool","Manchester University","Nottingham University","UCL","Kings College London","Imperial College London","Cambridge"},
		{"Oregon University", "UC Berkley", "Brown University", "Stanford University", "Harvard University", "MIT"},
		{"NUS","Indian Institute of Technology", "University Of Hong Kong", "Shanghai University", "KAIST", "Seoul University"},
		{"Razer","Apple","LG","Samsung", "Google"},
		{"Bye Bye World Coorperation", "Alex Da Player Coorperation", "Double Burger Coorperation"}};
		
	int[][][] values = {
		{{10,0,0,1,1,10,70},{15,0,0,1,1,20,90},{20,0,0,2,1,30,110},{25,0,0,2,1,40,120},{30,0,0,3,1,50,130},{35,0,0,3,1,60,140},{40,0,0,3,1,70,150}},
		{{50,2,0,4,2,100,200}, {60,4,0,4,2,120,240}, {70,6,0,4,2,140,280}, {80,8,0,5,3,160,340}, {90,10,0,5,3,180,400}, {100,12,0,5,3,200,470}},
		{{0,0,30,6,4,250,570},{0,0,40,6,4,300,680}, {0,0,50,7,4,350,700}, {0,0,60,7,4,400,890}, {0,0,70,7,5,450,1000}, {0,0,80,7,5,500,1200}},
		{{100,50,100,8,5,600,1500},{120,70,120,8,5,700,1900},{140,90,140,8,5,800,2700},{160,110,160,8,6,900,3900}, {180,130,180,9,6,1000,5300}},
		{{0,200,200,11,7,1200,9000}, {100,200,200,13,7,1400,13000}, {0,0,500,17,8,0,70000}}};	
	
	double strengthMultiplier[][] = {
		{1,1,1,1,1,1,1},
		{1, 1, 1, 1, 1, 1},
		{1,1, 1, 1,1, 1},
		{1,1,1,1, 1},
		{1, 1, 1.2}};
	
	double strengthMultiplierInc[][] = {
			{0.1,0.1,0.1,0.1,0.1,0.1,0.1},
			{0.1, 0.1, 0.1, 0.1, 0.1, 0.1},
			{0.1,0.1, 0.1, 0.1,0.1, 0.1},
			{0.1,0.1,0.1,0.1, 0.1},
			{0.1, 0.1, 0.1}};
	
	int[][] reqLevel = {
			{1,1,1,2,2,2,3},
			{3, 3, 4, 4, 4, 4},
			{5,5, 5, 6,6, 6},
			{7,7,8,8, 9},
			{10, 10, 10}};
	
	int[][] reqMoney = {
			{0,10,10,20,20,20,30},
			{50, 50, 60, 70, 70, 80},
			{150,170, 200, 200,300, 300},
			{500,500,700,700, 800},
			{1000, 2000, 7000}};
	
	String[] weaponType = {"pencil","ruler","mechanical pencil", "sissors","knife","compass needle","phone","NEXUS 7"};
	
	
	private int g;
	private int c;
	private int nerds;
	private int supernerds;
	private int asians;
	private int level;
	private int weapon;
	private int expGive;
	private int moneyGive;
	private String name;
	
	
	public Enemy(){
		name = names[1][1];
		nerds = values[1][1][0];
		supernerds = values[1][1][1];
		asians = values[1][1][2];
		level = values[1][1][3];
		weapon = values[1][1][4];
		expGive = values[1][1][5];
		moneyGive = values[1][1][6];
		
	}
	
	public Enemy(int g, int c){
		this.g = g;
		this.c = c;
		name = names[g][c];
		nerds = values[g][c][0];
		supernerds = values[g][c][1];
		asians = values[g][c][2];
		level = values[g][c][3];
		weapon = values[g][c][4];
		expGive = values[g][c][5];
		moneyGive = values[g][c][6];
	}
	
	public void setEnemy(int g, int c){
		this.g = g;
		this.c = c;
		name = names[g][c];
		nerds = values[g][c][0];
		supernerds = values[g][c][1];
		asians = values[g][c][2];
		level = values[g][c][3];
		weapon = values[g][c][4];
		expGive = values[g][c][5];
		moneyGive = values[g][c][6];
	}
	
	public int getG(){
		return g;
	}
	
	public int getReqMoney(){
		return reqMoney[g][c];
	}
	public int getReqLevel(){
		return reqLevel[g][c];
	}
	
	public int giveExp(){
		return expGive;
	}
	public int giveMoney(){
		return moneyGive;
	}
	
	public int weaponSigma2(){
		int result = 0;
		for(int i=0; i<weapon+1; i++){
			result += i*2;
		}
		return result;
	}
	
	public int levelSigma(){
		int result = 0;
		for(int i=0; i<level+1; i++){
			result += i;
		}
		return result;
	}
		
	public int getBrainPower(){
		return (int) ((nerds*weaponSigma2() + supernerds*3*weaponSigma2() + asians*7*weaponSigma2())*levelSigma()*strengthMultiplier[g][c]);
	}
	
	public String getName(){
		return name;
	}
	
	
	
	public void incStrengthMultiplier(){
		strengthMultiplier[g][c] += strengthMultiplierInc[g][c];
		strengthMultiplierInc[g][c] /= 1.2;
	}
	
	public double getStrengthMultiplier(){
		return strengthMultiplier[g][c];
	}
	
	public int getAsians(){
		return asians;
	}
	public int getSuperNerds(){
		return supernerds;
	}
		
	public String getWeaponType(){
		return weaponType[weapon-1];
	}
	
	public int getWeapon(){
		return weapon;
	}
	public int getLevel(){
		return level;
	}
	public int getNerds(){
		return nerds;
	}
	
	
}
