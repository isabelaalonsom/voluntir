package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.controller.ControleLogin;
import br.com.voluntir.model.Ong;
import br.com.voluntir.ong.CadastroONGActivity;


public class LoginActivityONG extends AppCompatActivity {

    private final String nomeTabela = "ong";
    private Ong ong;
    private EditText email;
    private EditText senha;
    private ControleLogin controleLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ong);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.edtTextEmailLogin);
        senha = (EditText) findViewById(R.id.edtTextSenhaLogin);

    }

    public void clicarCriarContaOng(View view) {
        Intent intent = new Intent(this, CadastroONGActivity.class);
        startActivity(intent);
    }

    public void clicarBotaoEsqueceuASenha(View view) {
        Intent i = new Intent(this, EsqueceuASenhaActivity.class);
        startActivity(i);
    }

    public void clicarBotaoEntrarOng(View view) {
        ong = new Ong();

        controleLogin = new ControleLogin();

        ong.setEmailOng(email.getText().toString());
        ong.setSenhaOng(senha.getText().toString());

        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else {
            controleLogin.validarLoginOng(ong, nomeTabela, getApplicationContext());
        }
    }
}





