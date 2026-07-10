package com.game.android;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.game.GameEngine;

public class GameActivity extends AppCompatActivity {
    private TextView output;
    private EditText input;
    private GameEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        Button send = findViewById(R.id.send);

        output.setMovementMethod(new ScrollingMovementMethod());

        engine = new GameEngine();
        appendText(engine.getIntro());

        send.setOnClickListener(v -> handleInput());

        input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handleInput();
                return true;
            }
            return false;
        });
    }

    private void handleInput() {
        String cmd = input.getText().toString().trim();
        if (cmd.isEmpty()) return;
        input.setText("");
        appendText("> " + cmd);
        String response = engine.processCommand(cmd);
        if (!response.isEmpty()) {
            appendText(response);
        }
        if (!engine.isRunning()) {
            input.setEnabled(false);
        }
    }

    private void appendText(String text) {
        output.append(text + "\n");
        int scrollAmount = output.getLayout().getLineTop(output.getLineCount()) - output.getHeight();
        if (scrollAmount > 0) {
            output.scrollTo(0, scrollAmount);
        } else {
            output.scrollTo(0, 0);
        }
    }
}
