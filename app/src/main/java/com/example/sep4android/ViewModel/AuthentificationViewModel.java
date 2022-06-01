package com.example.sep4android.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sep4android.Model.User;
import com.example.sep4android.Repository.AuthentificationRepository;


public class AuthentificationViewModel extends ViewModel {


    private AuthentificationRepository authentificationRepository;

    public AuthentificationViewModel(AuthentificationRepository authentificationRepository) {
        this.authentificationRepository = authentificationRepository;
    }


    public int login(String email, String password) {
        return authentificationRepository.login(email, password);

    }

    public void register(String email, String password){
        authentificationRepository.register(email, password);
    }

    public void delete(User user){
        authentificationRepository.deleteUser(user);
    }

    public void logout(){
        authentificationRepository.logout();
    }


}