package com.example.aplicativoreciclagem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {


    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth;
    TextView tvNome;
    TextView tvLogout;
    Button bt1;
    Button btSalvar;
    EditText edMeta;
    Button btResetar;
    Button btBack;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String META = "meta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        tvLogout = findViewById(R.id.textViewLogout);
        tvNome = findViewById(R.id.textViewNome);
        mAuth = FirebaseAuth.getInstance();
        bt1 = findViewById(R.id.Button1);
        btSalvar = findViewById(R.id.buttonSalvar);
        edMeta = findViewById(R.id.editTextMeta);
        btBack = findViewById(R.id.backbutton);
        btResetar = findViewById(R.id.buttonResetar);


        btSalvar.setOnClickListener(view -> {
            saveData();
        });

        if (User != null){
            String email = User.getEmail();
            tvNome.setText(email);
        }
        else{
            //sem usuario
        }
        tvLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(Perfil.this, login.class));
            finish();
        });

        btBack.setOnClickListener(view -> {
            startActivity(new Intent(Perfil.this, TelaInicial.class));
            finish();
        });

        btResetar.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.remove(META);
            editor.apply();
            Toast.makeText(this, "Meta resetada com sucesso.", Toast.LENGTH_SHORT).show();
        });

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(edMeta.getText().toString().isEmpty()){
            edMeta.setError("A meta não pode estar vazia.");
            edMeta.requestFocus();
        }
        else {
            editor.putString(META, edMeta.getText().toString());
            editor.apply();
            Toast.makeText(this, "Meta salva com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }
}
