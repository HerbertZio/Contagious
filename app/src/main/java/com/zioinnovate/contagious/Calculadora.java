package com.zioinnovate.contagious;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Calculadora extends AppCompatActivity {

    private TextView visor;
    private StringBuilder inputBuffer = new StringBuilder();
    private double resultado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        visor = findViewById(R.id.ed_visor);

        // Vincular os botões aos métodos correspondentes
        ImageButton btnNumero0 = findViewById(R.id.imgBtn_0);
        btnNumero0.setOnClickListener(v -> adicionarNumero("0"));

        ImageButton btnNumero1 = findViewById(R.id.imgBtn_1);
        btnNumero1.setOnClickListener(v -> adicionarNumero("1"));

        ImageButton btnNumero2 = findViewById(R.id.imgBtn_2);
        btnNumero2.setOnClickListener(v -> adicionarNumero("2"));

        ImageButton btnNumero3 = findViewById(R.id.imgBtn_3);
        btnNumero3.setOnClickListener(v -> adicionarNumero("3"));

        ImageButton btnNumero4 = findViewById(R.id.imgBtn_4);
        btnNumero4.setOnClickListener(v -> adicionarNumero("4"));

        ImageButton btnNumero5 = findViewById(R.id.imgBtn_5);
        btnNumero5.setOnClickListener(v -> adicionarNumero("5"));

        ImageButton btnNumero6 = findViewById(R.id.imgBtn_6);
        btnNumero6.setOnClickListener(v -> adicionarNumero("6"));

        ImageButton btnNumero7 = findViewById(R.id.imgBtn_7);
        btnNumero7.setOnClickListener(v -> adicionarNumero("7"));

        ImageButton btnNumero8 = findViewById(R.id.imgBtn_8);
        btnNumero8.setOnClickListener(v -> adicionarNumero("8"));

        ImageButton btnNumero9 = findViewById(R.id.imgBtn_9);
        btnNumero9.setOnClickListener(v -> adicionarNumero("9"));

        ImageButton btnPonto = findViewById(R.id.imgBtn_ponto);
        btnPonto.setOnClickListener(v -> adicionarPonto());

        ImageButton btnLimpar = findViewById(R.id.imgBtn_limpar);
        btnLimpar.setOnClickListener(v -> limparVisor());

        ImageButton btnBackspace = findViewById(R.id.imgBtn_backspace);
        btnBackspace.setOnClickListener(v -> apagarUltimoDigito());

        ImageButton btnAdicao = findViewById(R.id.imgBtn_adicao);
        btnAdicao.setOnClickListener(v -> adicionarOperador("+"));

        ImageButton btnSubtracao = findViewById(R.id.imgBtn_subtracao);
        btnSubtracao.setOnClickListener(v -> adicionarOperador("-"));

        ImageButton btnMultiplicacao = findViewById(R.id.imgBtn_multiplicacao);
        btnMultiplicacao.setOnClickListener(v -> adicionarOperador("*"));

        ImageButton btnDivisao = findViewById(R.id.imgBtn_divisao);
        btnDivisao.setOnClickListener(v -> adicionarOperador("/"));

        ImageButton btnIgual = findViewById(R.id.imgBtn_igual);
        btnIgual.setOnClickListener(v -> calcularResultado());

        ImageButton btnPorcento = findViewById(R.id.imgBtn_porcento);
        btnPorcento.setOnClickListener(v -> calcularPorcentagem());

        ImageButton btnMaisMenos = findViewById(R.id.imgBtn_mais_menos);
        btnMaisMenos.setOnClickListener(v -> trocarSinal());
    }

    public void adicionarNumero(String numero) {
        inputBuffer.append(numero);
        atualizarVisor();
    }

    public void adicionarPonto() {
        if (inputBuffer.length() == 0 || inputBuffer.charAt(inputBuffer.length() - 1) != '.') {
            inputBuffer.append(".");
            atualizarVisor();
        }
    }

    public void limparVisor() {
        inputBuffer.setLength(0);
        resultado = 0;
        atualizarVisor();
    }

    public void apagarUltimoDigito() {
        if (inputBuffer.length() > 0) {
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            atualizarVisor();
        }
    }

    public void adicionarOperador(String operador) {
        if (inputBuffer.length() > 0 && !isOperador(inputBuffer.charAt(inputBuffer.length() - 1))) {
            inputBuffer.append(operador);
            atualizarVisor();
        }
    }

    public void calcularResultado() {
        if (inputBuffer.length() > 0) {
            resultado = evaluate(inputBuffer.toString());
            inputBuffer.setLength(0);
            if (resultado == (long) resultado) {
                visor.setText(String.format("%d", (long) resultado));
            } else {
                visor.setText(Double.toString(resultado));
            }
        }
    }

    public void calcularPorcentagem() {
        if (inputBuffer.length() > 0 && inputBuffer.indexOf("+") != -1) {
            String[] partes = inputBuffer.toString().split("\\+");

            try {
                double primeiroNumero = Double.parseDouble(partes[0]);
                double segundoNumero = Double.parseDouble(partes[1]);
                double porcentagem = segundoNumero * 0.01;
                double resultadoPorcentagem = primeiroNumero * porcentagem;
                double resultadoFinal = primeiroNumero + resultadoPorcentagem;

                visor.setText(String.format("%.2f + %.2f%% = %.2f", primeiroNumero, porcentagem * 100, resultadoFinal));
                inputBuffer.setLength(0);
                inputBuffer.append(resultadoFinal);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                visor.setText("Erro: Entrada inválida");
            }
        }
    }


    public void trocarSinal() {
        if (inputBuffer.length() > 0) {
            try {
                double valor = Double.parseDouble(inputBuffer.toString());
                valor *= -1; // Inverte o sinal
                inputBuffer.setLength(0); // Limpa o buffer
                inputBuffer.append(Double.toString(valor)); // Atualiza com o novo valor
                atualizarVisor(); // Atualiza o visor
            } catch (NumberFormatException e) {
                visor.setText("Erro: Entrada inválida");
            }
        }
    }

    private void atualizarVisor() {
        visor.setText(inputBuffer.toString());
    }

    private boolean isOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length())
                    throw new RuntimeException("Caractere inesperado: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Número esperado");
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }
}
