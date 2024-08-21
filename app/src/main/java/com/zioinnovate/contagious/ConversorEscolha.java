package com.zioinnovate.contagious;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConversorEscolha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor_escolha);

        // Botões para escolher o tipo de conversão
        Button comprimentoButton = findViewById(R.id.comprimento_button);
        Button volumeButton = findViewById(R.id.volume_button);

        comprimentoButton.setOnClickListener(v -> {
            // Navegar para a atividade de conversor de comprimento
            Intent intent = new Intent(ConversorEscolha.this, ConversorComprimento.class);
            startActivity(intent);
        });

        volumeButton.setOnClickListener(v -> {
            // Navegar para a atividade de conversor de volume
            Intent intent = new Intent(ConversorEscolha.this, ConversorVolume.class);
            startActivity(intent);
        });

    }
}