package com.demomvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.demomvvm.R;
import com.demomvvm.databinding.ActivityLoginBinding;
import com.demomvvm.model.LoginUser;
import com.demomvvm.viewModel.LoginViewModel;
import com.demomvvm.viewModel.factory.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up model
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this, new LoginUser())).get(LoginViewModel.class);
//        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // set up binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        // set observer
        setObserver();
    }

    private void setObserver() {
        loginViewModel.getMutableLoginUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {
                if (!loginUser.isEmailValid()) {
                    binding.emailAddress.setError("Enter a Valid E-mail Address");
                    binding.emailAddress.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.password.setError("Enter at least 6 Digit password");
                    binding.password.requestFocus();
                } else {
                    binding.emailAddress.setError(null);
                    binding.password.setError(null);
                }
            }
        });
    }
}