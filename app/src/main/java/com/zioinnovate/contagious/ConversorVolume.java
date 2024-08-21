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

public class ConversorVolume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor_volume);

        // Inicializa os componentes da interface
        Spinner spinnerOrigem = findViewById(R.id.spinnerOrigemVolume);
        Spinner spinnerDestino = findViewById(R.id.spinnerDestinoVolume);
        EditText editTextValor = findViewById(R.id.editTextValorVolume);
        ImageButton btnConverter = findViewById(R.id.btnConverterVolume);

        // Aplica estilo itálico ao hint do EditText
        applyItalicStyle(editTextValor, "Valor para converter");
        editTextValor.setHintTextColor(0xFF000000);

        // Configura os adaptadores para os Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unidades_volume, android.R.layout.simple_spinner_item);
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

    // Método para converter valores entre diferentes unidades de volume
    private double converter(double valor, String unidadeOrigem, String unidadeDestino) {
        double resultado = 0;

        // Converte o valor para litros
        switch (unidadeOrigem) {
            case "kL":
                valor *= 1000;
                break;
            case "hL":
                valor *= 100;
                break;
            case "daL":
                valor *= 10;
                break;
            case "L":
                // Nenhuma conversão necessária
                break;
            case "dL":
                valor /= 10;
                break;
            case "cL":
                valor /= 100;
                break;
            case "mL":
                valor /= 1000;
                break;
        }

        // Converte o valor de litros para a unidade desejada
        switch (unidadeDestino) {
            case "kL":
                resultado = valor / 1000;
                break;
            case "hL":
                resultado = valor / 100;
                break;
            case "daL":
                resultado = valor / 10;
                break;
            case "L":
                resultado = valor;
                break;
            case "dL":
                resultado = valor * 10;
                break;
            case "cL":
                resultado = valor * 100;
                break;
            case "mL":
                resultado = valor * 1000;
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