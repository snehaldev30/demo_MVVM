package com.demomvvm.viewModel.factory;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demomvvm.model.LoginUser;
import com.demomvvm.viewModel.LoginViewModel;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private LoginUser loginUser;
    private Context context;


    public LoginViewModelFactory(Context context, LoginUser user) {
        this.context = context;
        this.loginUser = user;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LoginViewModel(context, loginUser);
    }
}