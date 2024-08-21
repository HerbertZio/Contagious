package com.zioinnovate.contagious;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Financiamento extends AppCompatActivity {
    private EditText editTextValorFinanciamento;
    private EditText editTextQuantidadeParcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financiamento);

        // Obtém uma referência para os EditTexts
        editTextValorFinanciamento = findViewById(R.id.editTextValorFinanciamento);
        editTextQuantidadeParcelas = findViewById(R.id.editTextQuantidadeParcelas);

        // Define os textos com estilo diferente
        String hintValor = "Valor do financiamento";
        String hintParcelas = "Quantidade de parcelas";

        // Aplica o estilo aos textos
        applyItalicStyle(editTextValorFinanciamento, hintValor);
        editTextValorFinanciamento.setHintTextColor(0xFF000000);
        applyItalicStyle(editTextQuantidadeParcelas, hintParcelas);
        editTextQuantidadeParcelas.setHintTextColor(0xFF000000);

        // Configuração do botão "Simular"
        ImageButton buttonSimular = findViewById(R.id.buttonSimular); // Alterado para ImageButton
        buttonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se os campos estão vazios
                if (editTextValorFinanciamento.getText().toString().isEmpty() ||
                        editTextQuantidadeParcelas.getText().toString().isEmpty()) {
                    // Mostra uma mensagem de aviso ao usuário
                    Toast.makeText(Financiamento.this, "Você precisa preencher os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    // Calcula o financiamento se os campos não estiverem vazios
                    simularFinanciamento();
                }
            }
        });
    }

    private void simularFinanciamento() {
        double valorFinanciamento = Double.parseDouble(editTextValorFinanciamento.getText().toString());
        int quantidadeParcelas = Integer.parseInt(editTextQuantidadeParcelas.getText().toString());

        if (valorFinanciamento > 10000) {
            // Exibir AlertDialog se o valor do financiamento exceder R$ 10.000,00
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O valor do financiamento não pode ser superior a R$ 10.000,00.")
                    .setTitle("Aviso")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
            return;
        }

        if (quantidadeParcelas > 15) {
            // Exibir AlertDialog se a quantidade de parcelas for maior que 15
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("A quantidade de parcelas não pode ser maior que 15.")
                    .setTitle("Aviso")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
            return;
        }

        double valorParcela = calcularValorParcela(valorFinanciamento, quantidadeParcelas);
        double montanteTotal = valorParcela * quantidadeParcelas;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String resultado = "Total de parcelas:\n" + quantidadeParcelas + "\n\n"
                + "Valor de cada parcela:\nR$ " + df.format(valorParcela) + "\n\n"
                + "Montante total a ser pago:\nR$ " + df.format(montanteTotal);

        // Exibir AlertDialog com o resultado da simulação
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage(resultado)
                .setTitle("Resultado da Simulação")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private double calcularValorParcela(double valorFinanciamento, int quantidadeParcelas) {
        double juros = 0.20; // Taxa de juros de 20%
        return (valorFinanciamento * (1 + juros)) / quantidadeParcelas;
    }

    // Método para aplicar o estilo itálico aos hints dos EditTexts
    private void applyItalicStyle(EditText editText, String hint) {
        SpannableString spannable = new SpannableString(hint);
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(styleSpan, 0, hint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spannable);
    }
}