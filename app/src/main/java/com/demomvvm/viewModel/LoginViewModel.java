package com.demomvvm.viewModel;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demomvvm.activity.MainActivity;
import com.demomvvm.model.LoginUser;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> mutableEmail = new MutableLiveData<>();
    public MutableLiveData<String> mutablePassword = new MutableLiveData<>();
    public MutableLiveData<LoginUser> mutableLoginUser = new MutableLiveData<>();
    private LoginUser loginUser;
    private Context context;

    public LoginViewModel(Context context, LoginUser loginUser) {
        this.context = context;
        this.loginUser = loginUser;
    }

    public MutableLiveData<LoginUser> getMutableLoginUser() {
        if (mutableLoginUser == null) {
            mutableLoginUser = new MutableLiveData<>();
        }

        loginUser.setEmail(mutableEmail.getValue());
        loginUser.setPassword(mutablePassword.getValue());
        mutableLoginUser.setValue(loginUser);

        return mutableLoginUser;
    }

    public void onSignInClick () {
        loginUser.setEmail(mutableEmail.getValue());
        loginUser.setPassword(mutablePassword.getValue());

        if (!loginUser.isEmailValid()) {
            Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else if (!loginUser.isPasswordLengthGreaterThan5()) {
            Toast.makeText(context, "Password Should be Minimum 6 Characters", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}