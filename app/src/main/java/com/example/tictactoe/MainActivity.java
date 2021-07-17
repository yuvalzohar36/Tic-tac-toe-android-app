package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int round_counter;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        for(int i =0;i<3;i++) {
            for(int j =0;j<3;j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals(""))
            return;
        if(player1turn)
            ((Button) view).setText("X");
        else
            ((Button) view).setText("O");
        win_algorithm();
    }

    private void win_algorithm(){
        round_counter++;
        if(checkWin()){
            if(player1turn)
                player1Win();
            else
                player2Win();
        }
        else if(round_counter==9)
            draw();
        else
            player1turn =!player1turn;
    }

    private void player1Win(){
        player1Points++;
        Toast.makeText(this, "Player 1 Wins !", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Win(){
        player2Points++;
        Toast.makeText(this, "Player 2 Wins !", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("player 2: " + player2Points);
    }
    private void resetBoard(){
        for(int i = 0;i<3;i++){
            for(int j =0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        round_counter=0;
        player1turn=true;
    }
    private  boolean checkWin(){
        String [][]field = new String[3][3];
        for(int i = 0 ;i<3;i++){
            for(int j = 0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }
        for(int i = 0 ;i<3;i++){
                if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
                    return true;
        }
        for(int i = 0 ;i<3;i++){
                if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                    return true;
        }
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
            return true;
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
            return true;
        return false;
    }

    private void resetGame(){
        player1Points=0;
        player2Points=0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("round_counter",round_counter);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        round_counter = savedInstanceState.getInt("round_counter");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}

