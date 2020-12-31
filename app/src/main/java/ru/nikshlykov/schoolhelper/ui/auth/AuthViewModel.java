package ru.nikshlykov.schoolhelper.ui.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import ru.nikshlykov.schoolhelper.db.AuthRepository;

public class AuthViewModel extends AndroidViewModel implements AuthRepository.OnSignInListener {

    private MutableLiveData<Integer> signInStatus;

    private AuthRepository authRepository;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = AuthRepository.getInstance(application);
        signInStatus = new MutableLiveData<>();
        signInStatus.setValue(0);
    }

    public MutableLiveData<Integer> getSignInStatus(){
        return signInStatus;
    }

    public void signIn(String login, String password){
        authRepository.signIn(login, password, this);
    }

    @Override
    public void onSignIn(int signInStatus) {
        this.signInStatus.setValue(signInStatus);
    }
}
