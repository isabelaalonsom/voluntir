package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleLogin;
import br.com.voluntir.model.Ong;
import br.com.voluntir.ong.CadastroONGActivity;
//import br.com.voluntir.voluntario.CadastroVoluntarioActivity;


public class LoginActivityONG extends AppCompatActivity {

    private Button botaoEntrar;
    private Button botaoCriarConta;
    private Button botaoEsqueciSenha;
    public Ong ongPegaDados;
    private Ong ong;
    private EditText email;
    private EditText senha;
    private String nomeTabela = "ong";
    private ControleCadastro controleCadastro;
    private ControleLogin controleLogin;

    private FirebaseAuth autenticacao;
    private DatabaseReference bancoFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ong);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.edtTextEmailLogin);
        senha = (EditText) findViewById(R.id.edtTextSenhaLogin);

        Button btnEsqueceuASenha = findViewById(R.id.esqueceuSenhaBtn);

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
            boolean resposta;
            controleLogin.validarLoginOng(ong, nomeTabela, getApplicationContext());


        }
    }
}





