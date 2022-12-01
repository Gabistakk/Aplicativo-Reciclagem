package com.example.aplicativoreciclagem;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cadastro extends AppCompatActivity {


    Button btnCadastro;
    EditText etEmail;
    EditText etSenha;
    EditText etConfirmarSenha;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        db = FirebaseFirestore.getInstance();


        Button backButton = (Button) findViewById(R.id.backbutton);
        btnCadastro = findViewById(R.id.buttonCadastrar);
        etEmail = findViewById(R.id.editTextEmail);
        etSenha = findViewById(R.id.editTextSenha);
        etConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);



        mAuth = FirebaseAuth.getInstance();

        btnCadastro.setOnClickListener(view -> {
            createUser();
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastro.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createUser() {
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        String confirmarsenha = etConfirmarSenha.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email não pode estar vazio!");
            etEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(senha)) {
            etSenha.setError("Email não pode estar vazio!");
            etSenha.requestFocus();
        }
        else if (TextUtils.isEmpty(confirmarsenha)) {
            etConfirmarSenha.setError("Este campo não pode estar vazio!");
            etConfirmarSenha.requestFocus();
        }
        else if(!Objects.equals(confirmarsenha, senha)){
            etSenha.setError("As senhas não coincidem!");
            etSenha.requestFocus();
            etConfirmarSenha.setError("As senhas não coincidem!");
            etConfirmarSenha.requestFocus();
        }
        else{
            Map<String, Object> user = new HashMap<>();
            user.put("email", email);
            user.put("meta", "30000");
            db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Documento adicionado com ID: " + documentReference.getId());
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Erro adicionando o Documento", e);
                                                }
                                            });
            mAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Cadastro.this, "O Usuário foi criado com Sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Cadastro.this, login.class));
                    }
                    else{
                        Toast.makeText(Cadastro.this, "Ocorreu um erro." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}