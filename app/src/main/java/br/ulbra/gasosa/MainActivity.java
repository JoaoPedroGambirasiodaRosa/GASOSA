package br.ulbra.gasosa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText etPrecoRef, etImpoEs, etImpoFe, etPorcEta, etPorcLuc;
    TextView txtResultado, txtPetro, txtRevenda, txtImposto;
    ProgressBar pbPetro, pbRevenda, pbImposto;
    Button btCalcular;
    ScrollView svTela1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String resultadoD, petroD, revendaD, impostoD;

        etPrecoRef = findViewById(R.id.etQuest1);
        etImpoEs = findViewById(R.id.etQuest2);
        etImpoFe = findViewById(R.id.etQuest3);
        etPorcEta = findViewById(R.id.etQuest4);
        etPorcLuc = findViewById(R.id.etQuest5);
        txtResultado = findViewById(R.id.txtresultado);
        txtPetro = findViewById(R.id.txtPetro);
        txtRevenda = findViewById(R.id.txtLucro);
        txtImposto = findViewById(R.id.txtImposto);
        pbPetro = findViewById(R.id.pbPetro);
        pbRevenda = findViewById(R.id.pbLucro);
        pbImposto = findViewById(R.id.pbImposto);
        btCalcular = findViewById(R.id.btCalcular);
        svTela1 = findViewById(R.id.svTela1);
        resultadoD = txtResultado.getText().toString();
        petroD = txtPetro.getText().toString();
        revendaD = txtRevenda.getText().toString();
        impostoD = txtImposto.getText().toString();

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double precoRef, impoEs, impoFe, porcEta, porcLuc;

                if (!etPrecoRef.getText().toString().equals("") && !etImpoEs.getText().toString().equals("") && !etImpoFe.getText().toString().equals("") && !etPorcEta.getText().toString().equals("") && !etPorcLuc.getText().toString().equals("")) {
                    if (Double.parseDouble(etImpoEs.getText().toString()) + Double.parseDouble(etImpoFe.getText().toString()) + Double.parseDouble(etPorcLuc.getText().toString()) + Double.parseDouble(etPorcEta.getText().toString()) <= 100) {
                        precoRef = Double.parseDouble(etPrecoRef.getText().toString());
                        impoEs = Double.parseDouble(etImpoEs.getText().toString());
                        impoFe = Double.parseDouble(etImpoFe.getText().toString());
                        porcEta = Double.parseDouble(etPorcEta.getText().toString());
                        porcLuc = Double.parseDouble(etPorcLuc.getText().toString());

                        calcular(precoRef, impoEs, impoFe, porcEta, porcLuc, resultadoD, petroD, revendaD, impostoD);
                        svTela1.smoothScrollTo(0, 0);
                    } else {
                        mensagem("A soma das alíquotas não podem passar de 100%");
                    }
                } else {
                    mensagem("Por favor, insira todos os dados");
                }
            }
        });
    }

    public void calcular(double preco1, double impo1, double impo2, double porc1, double porc2, String resultadoD, String petroD, String revendaD, String impostoD) {
        double valorF, porcentP, valorR, valorI, porcentI;

        DecimalFormat df = new DecimalFormat("0.00");

        valorF = preco1 + ((impo1 / 100) * preco1) + ((impo2 / 100) * preco1) + ((porc2 / 100) * preco1) + ((porc1 / 100) * 2.8378);
        porcentP = (preco1 * 100) / valorF;
        valorR = (porc2 / 100) * preco1;
        valorI = ((impo1 / 100) * preco1) + ((impo2 / 100) * preco1);
        porcentI = impo1 + impo2;

        txtResultado.setText(resultadoD + df.format(valorF));
        txtPetro.setText(petroD + df.format(preco1) + "(" + df.format(porcentP) + "%)");
        pbPetro.setProgress((int) Math.round(porcentP));
        txtRevenda.setText(revendaD + df.format(valorR) + "(" + df.format(porc2) + "%)");
        pbRevenda.setProgress((int) Math.round(porc2));
        txtImposto.setText(impostoD + df.format(valorI) + "(" + df.format(porcentI) + "%)");
        pbImposto.setProgress((int) Math.round(porcentI));
    }

    public void mensagem(String mensagem) {
        AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
        d.setTitle("Aviso!");
        d.setMessage(mensagem);
        d.setNeutralButton("Ok", null);
        d.show();
    }
}