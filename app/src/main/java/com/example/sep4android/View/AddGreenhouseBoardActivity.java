package com.example.sep4android.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.ViewModel.AddEventViewModel;
import com.example.sep4android.ViewModel.AddGreenhouseBoardViewModel;

public class AddGreenhouseBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addBoardButton;
    private EditText boardId, boardName, boardDescription;
    private AddGreenhouseBoardViewModel viewModel;
    private final String EMAIL_TEST = "policja@gov.pl";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_greenhouse_board);

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
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addBoardButton) {
            attachBoardToAccount();
            startActivity(new Intent(this, GreenhouseHomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void attachBoardToAccount() {
        String id = boardId.getText().toString().trim();
        String name = boardName.getText().toString().trim();
        String description = boardDescription.getText().toString();

        Board board = new Board(id,name,description);
        viewModel.addBoard(board);
        viewModel.assignBoard(id, AuthentificationDataSource.loggedUser.getEmail());


        Toast.makeText(this,
                "Board Attached",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
