package com.example.aplicativoreciclagem;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;


public class TelaInicial extends AppCompatActivity {

    Button btPerfil;
    ImageButton btPlastic;
    ImageButton btPapper;
    ImageButton btGlass;
    ImageButton btMetal;
    TextView tvMeta;
    Button btApagar;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String META = "meta";
    public static final String ATUAL = "atual";

    public String text;
    public String tvAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        btPerfil = findViewById(R.id.ButtonPerfil);
        btPlastic = findViewById(R.id.imageButtonPlastic);
        btPapper = findViewById(R.id.imageButtonPapper);
        btGlass = findViewById(R.id.imageButtonGlass);
        btMetal = findViewById(R.id.imageButtonMetal);


        btApagar = findViewById(R.id.buttonApagar);
        tvMeta = findViewById(R.id.textMeta);




        btPlastic.setOnClickListener(view ->{
            startActivity(new Intent(TelaInicial.this, plastico.class));
        });

        btPapper.setOnClickListener(view ->{
            startActivity(new Intent(TelaInicial.this, papel.class));
        });

        btGlass.setOnClickListener(view ->{
            startActivity(new Intent(TelaInicial.this, vidro.class));
        });

        btMetal.setOnClickListener(view ->{
            startActivity(new Intent(TelaInicial.this, metal.class));
        });


        btPerfil.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(TelaInicial.this, Perfil.class));
        });

        btApagar.setOnClickListener(view ->{
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.remove(ATUAL);
            editor.apply();
            if(text != "Defina uma meta no seu Perfil."){
                tvMeta.setText("Sua meta: "+ "0" + "/" + text);
                Toast.makeText(this, "Progresso removido com sucesso.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Progresso removido com sucesso. Para ve-lo, selecione uma meta.", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();
        updateViews();
        checkmeta();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(META, "Defina uma meta no seu Perfil.");
        tvAtual = sharedPreferences.getString(ATUAL, "0");
    }
    public void updateViews(){
        if(text.equals("Defina uma meta no seu Perfil.")){
            tvMeta.setText(text);
        }
        else{
            tvMeta.setText("Sua meta: "+ tvAtual + "/" + text);
        }
    }
    public void checkmeta(){
        try{
                if(parseDouble(text) <= parseDouble(tvAtual)){
                    Toast.makeText(this, "Meta atingida! Aumente a meta em Perfil e continue reciclando!", Toast.LENGTH_LONG).show();
            }
        }
        finally {
            return;
        }
    }
}