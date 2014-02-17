package com.anouvo.games.hangmancreolepuzzle;

import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	private String[] words;
	private Random rand;
	private String currentWord;
	private LinearLayout wordLayout;
	private TextView[] charViews;
	private GridView letters;
	private LetterAdapter ltrAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Resources res = getResources();
		words = res.getStringArray(R.array.words);
		
		letters = (GridView)findViewById(R.id.gridViewLetters);
		
		rand = new Random();
		currentWord = "";
		
		wordLayout = (LinearLayout) findViewById(R.id.word);
		playGame();
	}
	
	private void playGame(){
		// Start playing the game
		String newWord = words[rand.nextInt(words.length)];
		while(newWord.equals(currentWord)) newWord = words[rand.nextInt(words.length)];
		currentWord = newWord;
		
		ltrAdapter = new LetterAdapter(this);
		letters.setAdapter(ltrAdapter);
		
		charViews = new TextView[currentWord.length()];
		wordLayout.removeAllViews();
		
		for (int bar = 0; bar < currentWord.length(); bar++){
			charViews[bar] = new TextView(this);
			charViews[bar].setText("" + currentWord.charAt(bar));
			
			charViews[bar].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			charViews[bar].setGravity(Gravity.CENTER);
			charViews[bar].setTextColor(Color.WHITE);
			charViews[bar].setBackgroundResource(R.drawable.letter_bg);
			
			// Add layout
			wordLayout.addView(charViews[bar]);
		}
	}

}
