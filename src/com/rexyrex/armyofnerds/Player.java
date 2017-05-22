package com.rexyrex.armyofnerds;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int money;
	private int nerds;
	private int asians;
	private int supernerds;
	private int level;
	private int weapon;
	private int loi;
	private int nerdCap=500;
	String date;
	
	//admin stats totals
	private int totalMoney;
	private int totalMoneyLost;
	private int totalRoundsWon;
	private int totalRoundsLost;
	private int totalOverallWon;
	private int totalOverallLost;
	private boolean hasFinished;	
	private int totalExp;
	private int moneyFromLoi;
	private int expFromLoi;
	
	//gamble
	private int numberOfTimesGambling;
	private int moneyGainFromGambling;
	private int moneyLostFromGambling;
	private int gambleBeforeTimer;
	//club
	private int numberOfTimesClubbing;
	private int nerdsGainFromClubbing;
	private int moneySpentOnClubbing;
	private int langLoiGainFromClubbing;
	//nice
	private int moneyMuggedAskingNicely;
	private int nerdsGainFromAskingNicely;
	private int numberOfTimesAskingNicely;
	private int numberOfTimesCried;
	//soda
	private int numberOfSoda;
	private int nerdsBoughtFromSoda;
	private int superNerdsBoughtFromSoda;
	private int asiansBoughtFromSoda;
	private int moneySpentOnSoda;
	private int numberOfTimesVisitedSoda;
	//library
	private int numberOfTimesUpgraded;
	private int numberOfTimesVisitedLibrary;
	private int moneySpentInLibrary;
	//gym
	private int numberOfGymVisits;
	private int numberOfMinutesSpentInGym;
	private int moneySpentAtGym;
	private int loiFromGym;
	private int expFromGym;
	//save
	private int saves;
	//load
	private int loads;
	
	
	private int exp;
	int[] weaponCost = {0, 700, 1500, 2700, 3700, 5000, 7500, 12000};  
	String[] weaponType = {"pencil","ruler","mechanical pencil", "sissors","knife","compass needle","phone","NEXUS 7"};
	int[] expRequired = {100,250,650,1000,1500,2700,4000,5500, 7000,9000, 11500, 14000, 17000, 21000, 26000, 32000, 40000, 70000, 120000, 200000, 99999999};
	//constructor
	public Player(String n, int m){
		name = n;
		money = m;
		nerds = 1;
		supernerds =0;
		asians = 0;
		level = 1;
		weapon = 1;
		exp = 0;
		loi = 1;
		date = "";
		hasFinished = false;
		//admin
		totalMoney=0;
		totalMoneyLost=0;
		totalExp=0;
		totalRoundsWon=0;
		totalRoundsLost=0;
		totalOverallLost=0;
		totalOverallWon=0;
		moneyFromLoi=0;
		expFromLoi=0;

		numberOfTimesGambling=0;
		moneyGainFromGambling=0;
		moneyLostFromGambling=0;
		gambleBeforeTimer=0;
		
		numberOfTimesClubbing=0;
		nerdsGainFromClubbing=0;
		moneySpentOnClubbing=0;
		langLoiGainFromClubbing=0;
		
		moneyMuggedAskingNicely=0;
		nerdsGainFromAskingNicely=0;
		numberOfTimesAskingNicely=0;
		numberOfTimesCried=0;
		
		numberOfSoda=0;
		nerdsBoughtFromSoda=0;
		superNerdsBoughtFromSoda=0;
		asiansBoughtFromSoda=0;
		moneySpentOnSoda=0;
		numberOfTimesVisitedSoda=0;
		
		numberOfTimesUpgraded=0;
		numberOfTimesVisitedLibrary=0;
		moneySpentInLibrary=0;
		
		numberOfGymVisits=0;
		numberOfMinutesSpentInGym=0;
		moneySpentAtGym=0;
		loiFromGym=0;
		expFromGym=0;
		
		saves=0;
		loads=0;
		
	}
	public void incSaves(){
		saves++;
	}
	public void incLoads(){
		loads++;
	}
	public void incGambleBeforeTimer(){
		gambleBeforeTimer++;
	}
	public void goGym(){
		numberOfGymVisits++;
	}
	public void incMinutesAtGym(int gain){
		numberOfMinutesSpentInGym+= gain;
	}
	public void incMoneySpentAtGym(int gain){
		moneySpentAtGym+= gain;
	}
	public void incLoiFromGym(){
		loiFromGym++;
	}
	public void incExpFromGym(int gain){
		expFromGym+= gain;
	}
	public void goLibrary(){
		numberOfTimesVisitedLibrary++;
	}
	public void upgradeWeapon(){
		numberOfTimesUpgraded++;
	}
	public void incMoneySpentInLibrary(int gain){
		moneySpentInLibrary+= gain;
	}
	public void buySoda(){
		numberOfTimesVisitedSoda++;
	}
	public void incNumberOfSodaBought(int gain){
		numberOfSoda+= gain;
	}
	public void incNerdsFromSoda(int gain){
		nerdsBoughtFromSoda+= gain;
	}
	public void incSuperNerdsFromSoda(int gain){
		superNerdsBoughtFromSoda+= gain;
	}
	public void incAsiansFromSoda(int gain){
		asiansBoughtFromSoda+= gain;
	}
	public void incMoneySpentOnSoda(int gain){
		moneySpentOnSoda+= gain;
	}
	
	public void askNicely(){
		numberOfTimesAskingNicely++;
	}
	public void incNumberOfTimesCried(){
		numberOfTimesCried++;
	}
	public void incNerdsFromAskingNicely(int gain){
		nerdsGainFromAskingNicely+= gain;
	}
	public void incMoneyMuggedFromAskingNicely(int gain){
		moneyMuggedAskingNicely += gain;
	}
	
	public void goClub(){
		numberOfTimesClubbing++;
	}
	public void incMoneySpentOnClubbing(int gain){
		moneySpentOnClubbing+= gain;
	}
	public void incLangLoiFromClubbing(){
		langLoiGainFromClubbing++;
	}
	public void incNerdsFromClubbing(int gain){
		nerdsGainFromClubbing+=gain;
	}
	public void gamble(){
		numberOfTimesGambling++;
	}
	
	public void incMoneyFromGambling(int gain){
		moneyGainFromGambling += gain;
	}
	
	public void incMoneyLostFromGambling(int gain){
		moneyLostFromGambling += gain;
	}
	
	public void incTotalMoneyLost(int gain){
		totalMoneyLost += gain;
	}
	
	public void incLoiExp(int gain){
		expFromLoi += gain;
	}
	
	public void incLoiMoney(int gain){
		moneyFromLoi += gain;
	}
	
	public void incTotalExp(int gain){
		totalExp += gain;
	}
	
	public void incTotalMoney(int gain){
		totalMoney += gain;
	}
	
	public void incTotalRoundsWon(int won){
		totalRoundsWon += won;
	}
	
	public void incTotalRoundsLost(int lost){
		totalRoundsLost += lost;
	}
	
	public void incTotalOverallWon(int won){
		totalOverallWon += won;
	}
	
	public void incTotalOverallLost(int lost){
		totalOverallLost += lost;
	}
	
	public void finish(){
		hasFinished = true;
	}
	
	//get admin stats
	public int getSaves(){
		return saves;
	}
	public int getLoads(){
		return loads;
	}
	public int getGambleBeforeTimer(){
		return gambleBeforeTimer;
	}
	public int getNumberOfTimesGoGym(){
		return numberOfGymVisits;
	}
	public int getNumberOfLoiFromGym(){
		return loiFromGym;
	}
	public int getNumberOfMinutesAtGym(){
		return numberOfMinutesSpentInGym;
	}
	public int getMoneySpentInGym(){
		return moneySpentAtGym;
	}
	public int getExpFromGym(){
		return expFromGym;
	}
	public int getMoneySpentInLibrary(){
		return moneySpentInLibrary;
	}
	public int getNumberOfTimesGoLibrary(){
		return numberOfTimesVisitedLibrary;
	}
	public int getNumberOfTimesUpgraded(){
		return numberOfTimesUpgraded;
	}
	public int getNumberOfTimesBuySoda(){
		return numberOfTimesVisitedSoda;
	}
	public int getNumberOfSodaBought(){
		return numberOfSoda;
	}
	public int getNumberOfNerdsFromSoda(){
		return nerdsBoughtFromSoda;
	}
	public int getNumberOfSuperNerdsFromSoda(){
		return superNerdsBoughtFromSoda;
	}
	public int getNumberOfAsiansFromSoda(){
		return asiansBoughtFromSoda;
	}
	public int getMoneySpentOnSoda(){
		return moneySpentOnSoda;
	}
	public int getNumberOfTimesAskingNicely(){
		return numberOfTimesAskingNicely;
	}
	public int getNumberOfTimesCried(){
		return numberOfTimesCried;
	}
	public int getMuggedAmountAskingNicely(){
		return moneyMuggedAskingNicely;
	}
	public int getNerdsFromAskingNicely(){
		return nerdsGainFromAskingNicely;
	}
	public int getNumberOfTimesClubbing(){
		return numberOfTimesClubbing;
	}
	public int getMoneySpentClubbing(){
		return moneySpentOnClubbing;
	}
	public int getNerdsFromClubbing(){
		return nerdsGainFromClubbing;
	}
	public int getlangLoiFromClubbing(){
		return langLoiGainFromClubbing;
	}
	
	public int getNumberOfTimesGambling(){
		return numberOfTimesGambling;
	}
	
	public int getTotalMoneyGainedFromGambling(){
		return moneyGainFromGambling;
	}
	
	public int getTotalMoneyLostFromGambling(){
		return moneyLostFromGambling;
	}
	
	public int getTotalMoneyLost(){
		return totalMoneyLost;
	}
	public int getTotalExpGained(){
		return totalExp;
	}
	public int getLoiMoney(){
		return moneyFromLoi;
	}
	
	public int getLoiExp(){
		return expFromLoi;
	}
	
	public int getTotalMoney(){
		return totalMoney;
	}
	public int getTotalRoundsWon(){
		return totalRoundsWon;
	}
	public int getTotalRoundsLost(){
		return totalRoundsLost;
	}
	public int getTotalOverallWon(){
		return totalOverallWon;
	}
	public int getTotalOverallLost(){
		return totalOverallLost;
	}
	public boolean getHasFinished(){
		return hasFinished;
	}
	


	public int loseMoney(int m){
		int moneyBeforeLoss = money;
		if((money-m)<0){
			money =0;
			return moneyBeforeLoss;
		} else {
			money -= m;
			return m;
		}
	}
	
	public boolean isTimeToLevelUp(){
		if(exp>expRequired[level-1]){
			return true;
		}
		return false;
	}
	
	public void updateLevel(){
		while(isTimeToLevelUp()){			
			exp -= expRequired[level-1];
			level++;
		}
	}
	
	
	//set methods
		public void setDate(String date){
			this.date = date;
		}
	
		public void setAsians(int a){			
				asians = a;			
		}
		
		public void setLoi(int l){
			loi = l;
		}
	
		public void setSuperNerds(int sn){			
				supernerds = sn;			
		}
		
		public void setNerds(int n){			
				nerds = n;			
		}		
		
		public int incNerds(int n){
			if(isThereNerdRoom(n)){
				return n;
			} else {
				return getNerdRoom();
			}
		}
		
		
		public void setExp(int e){
			exp = e;
			updateLevel();
		}
	

	
		public void setWeapon(int w){
			weapon = w;
		}
	
		public void setLevel(int l){
			level = l;
		}
	
		
	
		public void setMoney(int m){
			money=m;
		}
		
		public void setName(String n){
			name = n;
		}
	
	//get methods
		
		public String getDate(){
			return date;
		}
		
		public int getNerdCap(){
			return nerdCap;
		}
		
		public boolean isThereNerdRoom(int n){
			if(getNerdRoom()+n>nerdCap){
				return false;
			} else {
				return true;
			}
		}
		public int getNerdRoom(){
			return nerdCap-getAllNerds();
		}
	
		
		public int getLoi(){
			return loi;
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
		return (nerds*weaponSigma2() + supernerds*3*weaponSigma2() + asians*7*weaponSigma2())*levelSigma();
	}
		
	public int getNeededExp(){
		return expRequired[level-1];
	}
	
	public int[] getAllWeaponCosts(){
		return weaponCost;
	}
		
	public String[] getAllWeaponTypes(){
		return weaponType;
	}
	public int getAllNerds(){
		return asians + supernerds + nerds;
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
		
	public int getExp(){
		return exp;
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
	
	public int getMoney(){
		return money;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	
}
