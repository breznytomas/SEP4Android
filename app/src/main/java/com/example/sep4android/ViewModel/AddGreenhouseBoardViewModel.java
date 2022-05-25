package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Repository.BoardRepository;

public class AddGreenhouseBoardViewModel extends AndroidViewModel {
    private BoardRepository boardRepository;
    public AddGreenhouseBoardViewModel(@NonNull Application application) {
        super(application);
        boardRepository = BoardRepository.getInstance(application);
    }

    public void addBoard(Board board){
        boardRepository.postBoard(board);
    }
}
