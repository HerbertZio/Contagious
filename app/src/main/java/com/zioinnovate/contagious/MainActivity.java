package com.zioinnovate.contagious;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botões
        ImageButton btnCalc = findViewById(R.id.btnCalc);
        ImageButton btnConv = findViewById(R.id.btnConv);
        ImageButton btnFinan = findViewById(R.id.btnFinan);
        ImageButton btnJuros = findViewById(R.id.btnJuros);

        // Definindo listeners para os botões
        btnCalc.setOnClickListener(v -> {
            // Navegar para a CalculadoraActivity
            Intent intent = new Intent(MainActivity.this, Calculadora.class);
            startActivity(intent);
        });
        btnConv.setOnClickListener(v -> {
            // Navegar para a atividade de escolha do tipo de conversão
            Intent intent = new Intent(MainActivity.this, ConversorEscolha.class);
            startActivity(intent);
        });

        btnFinan.setOnClickListener(v -> {
            // Navegar para a atividade Financiamento
            Intent intent = new Intent(MainActivity.this, Financiamento.class);
            startActivity(intent);
        });

        btnJuros.setOnClickListener(v -> {
            // Navegar para a atividade Juros
            Intent intent = new Intent(MainActivity.this, Juros.class);
            startActivity(intent);
        });
    }

    // Método para exibir uma mensagem Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}