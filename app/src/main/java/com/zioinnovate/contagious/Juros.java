package com.zioinnovate.contagious;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Juros extends AppCompatActivity implements TextWatcher {
    EditText principalInput, monthsInput;
    ImageButton calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juros); // AQUI VOCÊ DEVE INFLAR O LAYOUT CORRETO DO JUROS

        principalInput = findViewById(R.id.editTextValorPrincipal);
        monthsInput = findViewById(R.id.editTextQuantidadeMeses);
        calculateButton = findViewById(R.id.buttonSimular);

        principalInput.addTextChangedListener(this);
        monthsInput.addTextChangedListener(this);

        // Aplicando estilo itálico aos hints dos EditTexts
        applyItalicStyle(principalInput);
        principalInput.setHintTextColor(0xFF000000);
        applyItalicStyle(monthsInput);
        monthsInput.setHintTextColor(0xFF000000);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Não é necessário fazer nenhuma manipulação automática do texto
    }

    private void calculate() {
        String principalStr = principalInput.getText().toString();
        String monthsStr = monthsInput.getText().toString();

        if (!principalStr.isEmpty() && !monthsStr.isEmpty()) {
            // Convertendo o valor principal para um formato numérico correto
            double principal = Double.parseDouble(principalStr);

            int months = Integer.parseInt(monthsStr);
            double rate = 0.005; // Taxa de 0.5%

            double finalValue = principal * Math.pow((1.0 + rate), months);
            double interest = finalValue - principal;

            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
            String formattedFinalValue = decimalFormat.format(finalValue);
            String formattedInterest = decimalFormat.format(interest);

            // Criar um AlertDialog.Builder com o tema personalizado
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setTitle("Resultado");

            // Configurar o conteúdo do AlertDialog
            String message = "O valor após " + months + " meses é:\n R$ " + formattedFinalValue +
                    "\n\nValor acrescentado de juros:\n R$ " + formattedInterest;
            builder.setMessage(message);


            // Adicionar um botão OK ao AlertDialog
            builder.setPositiveButton("OK", null);

            // Exibir o AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "Por favor, insira o valor principal e o número de meses.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para aplicar o estilo itálico aos hints dos EditTexts
    private void applyItalicStyle(EditText editText) {
        String hint = editText.getHint().toString();
        SpannableString spannable = new SpannableString(hint);
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(styleSpan, 0, hint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spannable);
    }
}