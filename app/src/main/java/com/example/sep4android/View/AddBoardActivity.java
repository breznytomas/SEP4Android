package com.example.sep4android.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.AddGreenhouseBoardViewModel;

public class AddBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addBoardButton;
    private EditText boardId, boardName, boardDescription;
    private AddGreenhouseBoardViewModel viewModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_greenhouse_board);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewModel =
                new ViewModelProvider(this).get(AddGreenhouseBoardViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_add_board);
        backButton.setOnClickListener(this);

        addBoardButton = findViewById(R.id.addBoardButton);
        addBoardButton.setOnClickListener(this);

        boardId = findViewById(R.id.addBoardIdEditText);
        boardName = findViewById(R.id.addBoardNameEditText);
        boardDescription = findViewById(R.id.addBoardDescriptionEditText);

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_add_board) {
            onBackPressed();
        } else if (view.getId() == R.id.addBoardButton) {
            attachBoardToAccount();
            startActivity(new Intent(this, GreeneticsHomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void attachBoardToAccount() {
        String id = boardId.getText().toString().trim();
        String name = boardName.getText().toString().trim();
        String description = boardDescription.getText().toString();

        Board board = new Board(id,name,description);
        viewModel.addBoard(board).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String checkBoard) {
                    Toast.makeText(AddBoardActivity.this,checkBoard,Toast.LENGTH_SHORT).show();

            }
        });
        viewModel.assignBoard(id,viewModel.getCurrentUser().getEmail()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(AddBoardActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });



        Log.d("AddBoard", viewModel.getCurrentUser().getEmail());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
