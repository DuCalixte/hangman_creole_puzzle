package com.anouvo.games.hangmancreolepuzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LetterAdapter extends BaseAdapter {

	public static final int ALPHABET_COUNT = 26;
	private String[] letters;
	private LayoutInflater letterInf;
	
	public LetterAdapter(Context context) {
		
		letters = new String[ALPHABET_COUNT];
		for (int l = 0; l < ALPHABET_COUNT; l++ ){
			letters[l] = "" + (char)(l + 'A');
		}
		
		letterInf = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return letters.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create a button for the letter at this position in the alphabet.
		Button letterBtn;
		
		if (convertView == null){
			// Inflate the button layout
			letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
		}
		else{
			letterBtn = (Button)convertView;
		}
		
		letterBtn.setText(letters[position]);
		
		return letterBtn;
	}

}
