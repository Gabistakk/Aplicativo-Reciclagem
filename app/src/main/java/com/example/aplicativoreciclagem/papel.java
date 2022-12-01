package com.example.aplicativoreciclagem;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class papel extends AppCompatActivity {

    EditText etPeso;
    EditText etPreco;
    Button btnCalcular;
    Button btnReciclar;
    Button btnBack;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ATUAL = "atual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papel);


        etPeso = findViewById(R.id.editPeso);
        etPreco = findViewById(R.id.editPreco);
        btnCalcular = findViewById(R.id.buttonCalcular);
        btnReciclar = findViewById(R.id.buttonReciclar);
        btnBack = findViewById(R.id.backbutton);


        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(papel.this, TelaInicial.class));
            finish();
        });

        btnCalcular.setOnClickListener(view -> {
            if(etPeso.getText().toString().isEmpty()){
                etPeso.setError("Este campo não pode estar vazio.");
                etPeso.requestFocus();
            }
            else{
                Double peso = parseDouble(String.valueOf(etPeso.getText()));
                Double preco = peso * 0.60;
                Double cutpreco = round(preco, 2);
                etPreco.setText(cutpreco.toString());
            }
        });

        btnReciclar.setOnClickListener(view ->{
            saveData();
        });

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void saveData() {
        if (etPeso.getText().toString().isEmpty()){
            etPeso.setError("Este campo não pode estar vazio.");
            etPeso.requestFocus();
        }
        else if(etPreco.getText().toString().isEmpty()){
            etPreco.setError("Calcule Primeiro!");
            etPreco.requestFocus();
        }
        else{
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String stringAtual = sharedPreferences.getString(ATUAL, "0");

            Double adicionar = parseDouble(stringAtual);
            adicionar = adicionar + parseDouble(String.valueOf(etPreco.getText()));
            editor.putString(ATUAL, adicionar.toString());
            editor.apply();
            Toast.makeText(this, "Reciclado com Sucesso.", Toast.LENGTH_SHORT).show();
        }
    }
}