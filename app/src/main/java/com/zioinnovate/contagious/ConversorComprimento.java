package com.zioinnovate.contagious;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConversorComprimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor_comprimento);

        // Inicializa os componentes da interface
        Spinner spinnerOrigem = findViewById(R.id.spinnerOrigem);
        Spinner spinnerDestino = findViewById(R.id.spinnerDestino);
        EditText editTextValor = findViewById(R.id.editTextValor);
        ImageButton btnConverter = findViewById(R.id.btnConverter);

        // Aplica estilo itálico ao hint do EditText
        applyItalicStyle(editTextValor, "Valor para converter");
        editTextValor.setHintTextColor(0xFF000000);

        // Configura os adaptadores para os Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unidades_comprimento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigem.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        // Configura o listener do botão de conversão
        btnConverter.setOnClickListener(v -> {
            String valorStr = editTextValor.getText().toString();
            if (!valorStr.isEmpty()) {
                double valor = Double.parseDouble(valorStr);

                // Obtém as unidades selecionadas
                String unidadeOrigem = getPrefix(spinnerOrigem.getSelectedItem().toString());
                String unidadeDestino = getPrefix(spinnerDestino.getSelectedItem().toString());

                // Converte o valor
                double resultado = converter(valor, unidadeOrigem, unidadeDestino);

                // Cria e configura o AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                builder.setTitle("Resultado");

                // Configura o conteúdo do AlertDialog
                TextView textViewResultado = new TextView(this);
                textViewResultado.setText("Resultado: " + resultado + " " + unidadeDestino);
                textViewResultado.setTextSize(18);
                textViewResultado.setTextColor(0xFF000000);
                textViewResultado.setPadding(50, 20, 50, 20);
                builder.setView(textViewResultado);

                // Adiciona um botão OK ao AlertDialog
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

                // Exibe o AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                // Exibe uma mensagem de erro se o valor não for fornecido
                Toast.makeText(this, "Por favor, insira um valor para converter.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para converter valores entre diferentes unidades de comprimento
    private double converter(double valor, String unidadeOrigem, String unidadeDestino) {
        // Converte o valor de entrada para metros
        double valorEmMetros = 0;
        switch (unidadeOrigem) {
            case "km":
                valorEmMetros = valor * 1000;
                break;
            case "hm":
                valorEmMetros = valor * 100;
                break;
            case "dam":
                valorEmMetros = valor * 10;
                break;
            case "m":
                valorEmMetros = valor;
                break;
            case "dm":
                valorEmMetros = valor / 10;
                break;
            case "cm":
                valorEmMetros = valor / 100;
                break;
            case "mm":
                valorEmMetros = valor / 1000;
                break;
        }

        // Converte o valor em metros para a unidade de destino
        double resultado = 0;
        switch (unidadeDestino) {
            case "km":
                resultado = valorEmMetros / 1000;
                break;
            case "hm":
                resultado = valorEmMetros / 100;
                break;
            case "dam":
                resultado = valorEmMetros / 10;
                break;
            case "m":
                resultado = valorEmMetros;
                break;
            case "dm":
                resultado = valorEmMetros * 10;
                break;
            case "cm":
                resultado = valorEmMetros * 100;
                break;
            case "mm":
                resultado = valorEmMetros * 1000;
                break;
        }

        return resultado;
    }

    // Método para aplicar o estilo itálico ao hint do EditText
    private void applyItalicStyle(EditText editText, String hint) {
        SpannableString spannable = new SpannableString(hint);
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(styleSpan, 0, hint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spannable);
    }

    // Método para extrair o prefixo da unidade
    private String getPrefix(String unidadeCompleta) {
        return unidadeCompleta.split(" ")[0];
    }
}