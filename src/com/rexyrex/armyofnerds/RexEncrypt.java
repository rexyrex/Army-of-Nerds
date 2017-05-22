package com.rexyrex.armyofnerds;

public class RexEncrypt {
	private String message;		//original
	private String rexMessage; // encrypted
	private int[] chain = {1, 3, 2, 4, 0, 1, 2, 4, 1};
	
	
	public RexEncrypt(){
		message = "not initialized";
		rexMessage = "not encrypted";
	}
	
	public void encrypt(){
		String result = "";
		int l = message.length();
		char ch;
		int chainPos=0;
		for(int i = 0; i < l; i++){
			if(chainPos==chain.length){
				chainPos = 0;
			}
			ch = message.charAt(i);
			if(ch!='\n')
			ch += chain[chainPos];
			result += ch;
			chainPos++;
		}
		
		rexMessage = result;
	}
	
	public void decrypt(){
		String result = "";
		int l = rexMessage.length();
		char ch;
		int chainPos = 0;
		for(int i = 0; i < l; i++){
			if(chainPos==chain.length){
				chainPos = 0;
			}
			ch = rexMessage.charAt(i);
			if(ch!='\n')
			ch -= chain[chainPos];
			result += ch;
			chainPos++;
		}
		
		message = result;
		
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public String getEncryptedMessage(){
		return rexMessage;
	}
	
	public void setEncryptedMessage(String m){
		rexMessage = m;
	}
}
