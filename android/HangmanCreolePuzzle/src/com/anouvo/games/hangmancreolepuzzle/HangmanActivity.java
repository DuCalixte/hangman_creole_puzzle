package com.anouvo.games.hangmancreolepuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HangmanActivity extends Activity implements OnClickListener {

	private Button playBtn;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        
        playBtn = (Button)findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hangman, menu);
        return true;
    }


	@Override
	public void onClick(View view) {
		
		if (view.getId() == R.id.playBtn){
			Intent playIntent = new Intent(this, GameActivity.class);
			this.startActivity(playIntent);
		}
		
	}
    
}
