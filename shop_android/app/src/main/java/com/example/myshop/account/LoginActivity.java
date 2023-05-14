package com.example.myshop.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myshop.BaseActivity;
import com.example.myshop.R;
import com.example.myshop.application.HomeApplication;
import com.example.myshop.catalog.CatalogActivity;
import com.example.myshop.dto.account.LoginDTO;
import com.example.myshop.dto.account.LoginResponse;
import com.example.myshop.security.JwtSecurityService;
import com.example.myshop.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    TextInputLayout tfEmail;
    TextInputLayout tfPassword;

    TextInputEditText txtEmail;
    TextInputEditText txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(HomeApplication.getInstance().isAuth())
        {
            Intent intent = new Intent(LoginActivity.this, CatalogActivity.class);
            startActivity(intent);
            finish();
        }

        tfEmail=findViewById(R.id.tfEmail);
        tfPassword=findViewById(R.id.tfPassword);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
    }

    public void onClickLogin(View view) {
        LoginDTO login = new LoginDTO();
        login.setEmail(txtEmail.getText().toString());
        login.setPassword(txtPassword.getText().toString());
        ApplicationNetwork
                .getInstance()
                .getAccountApi()
                .login(login)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful())
                        {
                            String token = response.body().getToken();
                            JwtSecurityService jwt = HomeApplication.getInstance();
                            jwt.saveJwtToken(token);
                            Intent intent = new Intent(LoginActivity.this, CatalogActivity.class);
                            startActivity(intent);
                            finish();
                            //Toast.makeText(LoginActivity.this, "Вхід успішний "+token, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Не вірно вказано дані ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
    }
}