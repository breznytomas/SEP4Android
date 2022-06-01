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


    public MutableLiveData<User> login(String email, String password) {
        // can be launched in a separate asynchronous job
//        Result<User> result = authentificationRepository.login(email, password);
//    Log.d("dupaVM", email+password);
//        if (result instanceof Result.Success) {
//            User data = ((Result.Success<User>) result).getData();
//            authResult.setValue(new AuthResult(new LoggedUserView(data.getEmail())));
//            Log.d("duparesult",data.getEmail());
//        } else {
//            authResult.setValue(new AuthResult(R.string.login_failed));
//        }
        authentificationRepository.login(email, password);
        return authentificationRepository.getUserResponse();

    }

    public void register(String email, String password){
        authentificationRepository.register(email, password);
    }




}