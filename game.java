package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean playerXTurn = true;
    private int turnCount = 0;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);

        textViewStatus = findViewById(R.id.textViewStatus);

        // Set click listeners for grid buttons
        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(v -> onButtonClick(finalI));
        }

        // Set click listener for reset button
        findViewById(R.id.buttonReset).setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int index) {
        if (!buttons[index].getText().toString().isEmpty()) {
            return; // Ignore clicks on already marked buttons
        }

        // Mark the button with the current player's symbol
        buttons[index].setText(playerXTurn ? "X" : "O");
        turnCount++;

        // Check if the current player wins or if the game is a draw
        if (checkWinner()) {
            textViewStatus.setText("Player " + (playerXTurn ? "X" : "O") + " Wins!");
            disableButtons();
        } else if (turnCount == 9) {
            textViewStatus.setText("It's a Draw!");
        } else {
            // Switch turns
            playerXTurn = !playerXTurn;
            textViewStatus.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWinner() {
        // Winning combinations
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] combination : winCombinations) {
            String a = buttons[combination[0]].getText().toString();
            String b = buttons[combination[1]].getText().toString();
            String c = buttons[combination[2]].getText().toString();

            if (!a.isEmpty() && a.equals(b) && b.equals(c)) {
                return true; // Winner found
            }
        }
        return false; // No winner
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (Button button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        playerXTurn = true;
        turnCount = 0;
        textViewStatus.setText("Player X's Turn");
    }
}
