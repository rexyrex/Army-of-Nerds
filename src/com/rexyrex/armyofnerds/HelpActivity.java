package com.rexyrex.armyofnerds;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class HelpActivity extends Activity {
	
	TextView daText;
	Button daButton;
	String help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		daText = (TextView) findViewById(R.id.helpTextID);

		
		help = "MAIN OBJECTIVE : Defeat the last level (Double Burger Company)\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I defeat enemies?\n";
		help+= "----------------------------\n";
		help+= "Get Money -> Get Nerds -> Get Levels -> Upgrade Weapon -> Fight \n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I get Money?\n";
		help+= "----------------------------\n";
		help+= "You start off with 1200 money\n";
		help+= "1. You can GAMBLE to increase money\n";
		help+= "2. You can FIGHT and WIN to increase money\n";
		help+= "3. You can CHEER while you FIGHT to increase money\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I get Nerds?\n";
		help+= "----------------------------\n";
		help+= "There are several ways to get Nerds\n";
		help+= "1. Go Clubbing \n";
		help+= "What it does: You aquire (cost)/(45~100) nerds.\n";
		help+= "[Pros: It is possible to get more nerds for less money]\n";
		help+= "[Cons: It is possible to get less nerds for more money]\n";
		help+= "\n";
		help+= "2. Ask Nicely\n";
		help+= "What it does: 60% chance to gain nerds for HALF PRICE (35) that 30% of your money can buy. You will not lose any money if you succeed\n";
		help+= "40% chance to lose 30% money. You gain nothing. Very Risky.\n";
		help+= "[Pros: You can get Nerds for almost half price]\n";
		help+= "[Cons: 40% chance to lose 30% of your money + 200. If you have little money, this option is not worth it.]\n";
		help+= "\n";
		help+= "3. Buy Soda to make friends\n";
		help+= "What it does: A soda costs 27$. You can buy nerds, supernerds, and asians with sodas\n";
		help+= "[Pros: No random factor. You get what you pay for. This is also the only way to obtain supernerds and asians]\n";
		help+= "[Cons: It is stable, but you might lose out on random benefits that other options provide. How lucky do you feel?]\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I get Levels?\n";
		help+= "----------------------------\n";
		help+= "1. Go Gym\n";
		help+= "[Pros: Chance to obtain Lang Loi and little exp]\n";
		help+= "[Cons: Not an efficient use of money for exp. Use this option mostly for lang loi]\n";
		help+= "\n";
		help+= "2. Fight\n";
		help+= "[Pros: If you win many rounds, you can get a lot of exp+money]\n";
		help+= "[Cons: You can lose money if you lose. However, you will not lose exp.]\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I upgrade my Weapon?\n";
		help+= "----------------------------\n";
		help+= "1. Go to Library and buy it. Weapons make a HUGE difference in brain power.\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I Fight?\n";
		help+= "----------------------------\n";
		help+= "1. Select Enemy\n";
		help+= "2. Meet Level and Money Requirements\n";
		help+= "3. Pay Entry Fee\n";
		help+= "4. Fight\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How does a fight work?\n";
		help+= "----------------------------\n";
		help+= "You fight a random odd number of rounds vs your opponent\n";
		help+= "Each Round, a random number between 0 and your brain power is selected\n";
		help+= "This random number is compared to your enemy's random number\n";
		help+= "\n";
		help+= "This comparison determines the winner of the round\n";
		help+= "\n";
		help+= "Whoever wins more rounds, wins the battle\n";
		help+= "\n";
		help+= "During the battle, you can cheer with two buttons on the bottom corners\n";
		help+= "This will give you extra money and exp when you win a round\n";
		help+= "\n";
		help+= "Each cheer has 40% chance of giving you extra money+exp based on enemy strength and n. of lang loi you have\n";
		help+= "\n";
		help+= "Winning a battle will make your enemy slightly stronger the next time you fight that particular enemy\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How do I obtain Lang Loi?\n";
		help+= "----------------------------\n";
		help+= "1. Go Gym\n";
		help+= "[Pros: High chance of lang loi if you spend more time in gym. ]\n";
		help+= "[Cons: Possibility of not obtaining lang loi because it is random chance. You can get max 1 lang loi per gym session. You do the math.]\n";
		help+= "(Probability of obtaining lang loi (per minute) is 1/27)\n";
		help+= "\n";
		help+= "2. Go Clubbing\n";
		help+= "[Pros: 1/50 chance of obtaining lang loi each entry]\n";
		help+= "[Cons: Expensive. Use for obtaining Nerds mostly]\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "Where is Lang Loi used?\n";
		help+= "----------------------------\n";
		help+= "1. When you cheer during a fight, lang loi increases money and exp income FOR EVERY CHEER. This is why Lang Loi are expensive\n";
		help+= "Lang Loi are mostly effective early game as it gives a raw boost (But they do not scale late game)";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "TYPES OF NERDS\n";
		help+= "----------------------------\n";
		help+= "\n";
		help+= "1. NORMAL NERD\n";
		help+= "AVERAGE COST : 70\n";
		help+= "Effectiveness (brain power / cost) : 100%\n";
		help+= "\n";
		help+= "2. Super NERD\n";
		help+= "Average Cost : 280\n";
		help+= "Effectiveness (brain power / cost) : 75%\n";
		help+= "\n";
		help+= "3. ASIAN\n";
		help+= "Average Cost : 770\n";
		help+= "Effectiveness (brain power / cost) : 63.6%\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "Why should I upgrade Nerd when effectiveness decreases?\n";
		help+= "----------------------------\n";
		help+= "1. There is a NERD CAP OF 500.\n";
		help+= "Once you reach this limit, you need to sell your weak nerds and buy stronger ones if you want to maximize your brain power\n";
		help+= "\n";
		help+= "----------------------------\n";
		help+= "How is Brain Power Calculated?\n";
		help+= "----------------------------\n";
		help+= "(NORMAL NERDS + 3*SuperNerds + 7*Asians) * LevelSigma * WeaponSigma \n";
		help+= "Where LevelSigma = 1 + 2 + 3 + ... + Your level\n";
		help+= "\n";
		help+= "\n";
		help+= "\n";
		help+= "\n";
		help+= "\n";
		help+= "GOOD LUCK BEATING THE DOUBLE BURGER COORPORATION!!!\n";
		
		daText.setText(help);
		
		
	
		
	}


}
