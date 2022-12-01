package com.example.aplicativoreciclagem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText etLogin;
    EditText etSenha;
    Button btEntrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        etLogin = findViewById(R.id.editTextEmail);
        etSenha = findViewById(R.id.editTextSenha);
        btEntrar = findViewById(R.id.buttonEntrar);
        TextView botaoCadastro = (TextView) findViewById(R.id.buttonCadastro);
        mAuth = FirebaseAuth.getInstance();

        btEntrar.setOnClickListener(view -> {
            loginUser();
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, Cadastro.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(){
        String email = etLogin.getText().toString();
        String senha = etSenha.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etLogin.setError("Email não pode estar vazio!");
            etLogin.requestFocus();
        }
        else if (TextUtils.isEmpty(senha)) {
            etSenha.setError("Email não pode estar vazio!");
            etSenha.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(login.this, "O Usuário foi Logado com Sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, TelaInicial.class));
                    }
                    else{
                        Toast.makeText(login.this, "Ocorreu um erro." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(login.this, TelaInicial.class));
        }
    }
}