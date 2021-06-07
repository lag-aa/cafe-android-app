package com.example.cafe.screens.auth;


import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cafe.database.firebase.AppRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;


public class UserViewModel extends AndroidViewModel {
    private AppRepository repository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<String> msg;

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new AppRepository();
        userMutableLiveData = repository.getUserMutableLiveData();
        msg = repository.getMsg();
    }

    public void verificationSMS(String phone_number, String typeRequest, Activity activity, PhoneAuthProvider.OnVerificationStateChangedCallbacks onCodeSent){
        repository.verificationSMS(phone_number, typeRequest, activity, onCodeSent);
    }

    public void signupUser(String name, String surname, String email_address, String phone_number, String city, String gender, String birth_date, String address, String password, String customer_id, OnCompleteListener<AuthResult> onComplete) {
        repository.signupUser(name, surname, email_address, phone_number, city, gender, birth_date, address, password, customer_id, onComplete);
    }

    public void signInWithCredential(String id, String smsCode) {
        repository.signInWithCredential(id, smsCode);
    }

    public void signOut(){
        repository.signOut();
    }

    public void loginUser(String email_address, String password, OnCompleteListener<AuthResult> onComplete){
        repository.loginUser(email_address, password, onComplete);
    }

    public void updatePhoneNumber(String id, String smsCode, String typeRequest, OnCompleteListener<Void> onComplete) {
        repository.updatePhoneNumber(id, smsCode,typeRequest, onComplete);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<String> getMsg() {
        return msg;
    }
}