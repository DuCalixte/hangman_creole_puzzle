package com.anouvo.games.hangmancreolepuzzle;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	public static final int BODY_PARTS_COUNT = 6;
	
	private String[] words;
	private Random rand;
	private String currentWord;
	private LinearLayout wordLayout;
	private TextView[] charViews;
	private GridView letters;
	private LetterAdapter ltrAdapter;
	
	private ImageView[] bodyParts;
	private int currentPart;
	private int numChars;
	private int numCorrect;
	
	private AlertDialog helpAlert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Resources res = getResources();
		words = res.getStringArray(R.array.words);
		
		letters = (GridView)findViewById(R.id.gridViewLetters);
		initializeBodyParts();
		
		rand = new Random();
		currentWord = "";
		
		wordLayout = (LinearLayout) findViewById(R.id.word);
		playGame();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hangman, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_help:
			showHelp();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showHelp(){
		AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
		
		helpBuild.setTitle("Help");
		helpBuild.setMessage("You need to guess the word by selecting the letters.\n\nYou have at most six possible wrong answers before game is over.");
		
		helpBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				helpAlert.dismiss();				
			}
		});
		
		helpAlert = helpBuild.create();
		helpBuild.show();
	}
	
	private void initializeBodyParts(){
		bodyParts = new ImageView[BODY_PARTS_COUNT];
		
		bodyParts[0] = (ImageView)findViewById(R.id.imageViewHead);
		bodyParts[1] = (ImageView)findViewById(R.id.imageViewBody);
		bodyParts[2] = (ImageView)findViewById(R.id.imageViewLeftArm);
		bodyParts[3] = (ImageView)findViewById(R.id.imageViewRightArm);
		bodyParts[4] = (ImageView)findViewById(R.id.imageViewLeftLeg);
		bodyParts[5] = (ImageView)findViewById(R.id.imageViewRightLeg);	
		
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
		
		for (int part = 0; part < BODY_PARTS_COUNT; part++){
			bodyParts[part].setVisibility(View.INVISIBLE);
		}
		
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
		
		currentPart = 0;
		numChars = currentWord.length();
		numCorrect = 0;
	}
	
	/*private void exitGame(){
		// Code to ensure game is exiting
	}*/
	
	public void letterPressed(View view){
		// Triggered when a user pressed a letter to guess
		String ltrChoice = ((TextView)view).getText().toString();
		char ltrChar = ltrChoice.charAt(0);
		
		view.setEnabled(false);
		view.setBackgroundResource(R.drawable.letter_down);
		
		boolean correct = false;
		
		for (int word = 0; word < currentWord.length(); word++){
			if (currentWord.charAt(word) == ltrChar){
				correct = true;
				numCorrect++;
				charViews[word].setTextColor(Color.BLACK);
			}
		}		
		
		if (numCorrect == numChars){
			// Game is over - User has won
			disableButtons();
			
			optionToExitGame("YAY!!!", "You win :-)!\n\nThe answer was:\t\b" + currentWord + ".\n\n");
		}		
		
		if (correct){
			// User has made a correct guess
			Toast.makeText(getApplicationContext(), "Correct guess!", Toast.LENGTH_LONG).show();
			
		}else if (currentPart < BODY_PARTS_COUNT){
			// One more body part becomes visible
			bodyParts[currentPart].setVisibility(View.VISIBLE);
			
			// Increases the number of body parts being visible
			currentPart++;
		}else{
			// Game is over - User has lost
			disableButtons();
			
			optionToExitGame("OOPS!!!", "You loose :-(!\n\nThe answer was:\t\b" + currentWord + ".\n\n");
		}	
		
	}
	
	public void disableButtons(){
		int numLetters = letters.getChildCount();
		for (int letter = 0; letter < numLetters; letter++){
			letters.getChildAt(letter).setEnabled(false);
		}
	}
	
	private void optionToExitGame(String title, String msg){
		AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
		buildDialog.setTitle(title);
		buildDialog.setMessage(msg);
		
		buildDialog.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.playGame();				
			}
		});
		
		buildDialog.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				GameActivity.this.finish();				
			}
		});
		
		buildDialog.show();
	}

}
