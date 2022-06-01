package com.example.sep4android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.Board;
import com.example.sep4android.Repository.BoardRepository;

public class AddGreenhouseBoardViewModel extends AndroidViewModel {

    private BoardRepository boardRepository;

    public AddGreenhouseBoardViewModel(@NonNull Application application) {
        super(application);
        boardRepository = BoardRepository.getInstance(application);
    }

    public MutableLiveData<String> addBoard(Board board){
        return boardRepository.postBoard(board);
    }

    public MutableLiveData<String> assignBoard(String boardId, String userEmail){
       return boardRepository.putBoard(boardId, userEmail);
    }
}
