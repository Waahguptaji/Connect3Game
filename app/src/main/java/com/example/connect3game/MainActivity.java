package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: yellow, 1: red, 2: empty

    int[] gameState = {2,2,2,2,2,2,2,2,2};//it contains the state of the token whether it is 1 or 0
    int activePlayer = 0;
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameActive = true;
    



    //here we creating onclick method named as dropIn used for when the space clicked in it drop token
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;// here we accessing the variable view to know which one is tapped on
        int tappedCounter = Integer.parseInt(counter.getTag().toString());//we are converting it to int to store in the game state
        if (gameState[tappedCounter] == 2 && gameActive) {//if these condition are here to put the restriction on the refilling the filled box and after winning a game game stopped
            gameState[tappedCounter] = activePlayer;//here we are saving the value of token to the specific state
            counter.setTranslationY(-1000);
            if (activePlayer == 0) {//to set the colour of imageview when clicked
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000).rotation(500).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "yellow";

                    } else {
                        winner = "red";
                    }
                    Toast.makeText(this, winner + " has won", Toast.LENGTH_SHORT).show();

                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " has won!");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){// when playagain button is clicked
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView =  findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout =  findViewById(R.id.gridLayout);
        //Loop for iterating each image view in a grid layout
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);//remove the imageview source
        }

        for (int i = 0; i < gameState.length; i++) {//here we updating the game state to be empty
                gameState[i] = 2;
        }
        activePlayer = 0;
        gameActive = true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}