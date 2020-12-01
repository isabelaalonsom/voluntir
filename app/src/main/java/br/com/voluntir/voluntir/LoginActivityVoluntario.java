package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.voluntario.CadastroVoluntarioActivity;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;
import br.com.voluntir.voluntario.MinhaContaVoluntarioActivity;
//import br.com.voluntir.voluntario.CadastroVoluntarioActivity;


public class LoginActivityVoluntario extends AppCompatActivity {

//    Button btnEsqueciASenha;
//    Button criarContaBtn = findViewById(R.id.criarContaBtn);
//    Button criarContaONGBtn = findViewById(R.id.criarContaONGBtn);
//    Voluntario voluntario = new Voluntario();
//    //Ong ong = new Ong();
//    Button btnEntrar = findViewById(R.id.btnEntrar);

//    EditText edtTextEmailLogin = findViewById(R.id.edtTextEmailLogin);
//    EditText edtTextSenhaLogin = findViewById(R.id.edtTextSenhaLogin);

    private Voluntario voluntario;
    private EditText email;
    private EditText senha;
    private String nomeTabela = "voluntario";
    private ControleCadastro controleCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_voluntario);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.edtTextEmailLogin);
        senha = (EditText) findViewById(R.id.edtTextSenhaLogin);


//        btnEntrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String login = edtTextEmailLogin.getText().toString();
//                String senha = edtTextSenhaLogin.getText().toString();
//
//                if (login.equals("voluntario.voluntir@gmail.com") && senha.equals("voluntir2020")) {
//                    mensagemLogin("Login realizado!");
////                    Intent i = new Intent(LoginActivity.this, VagasActivity.class);
//                    //startActivity(i);
//                } else {
//                    mensagemLogin("E-mail e/ou senha incorretos");
//                }
//            }
//        });

//        btnEsqueciASenha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, EsqueceuASenhaActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    public void mensagemLogin(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void clicarCriarConta(View view) {
        Intent intent = new Intent(this, CadastroVoluntarioActivity.class);
        startActivity(intent);
    }

    public void clicarBotaoEsqueceuASenha(View view) {
        Intent i = new Intent(LoginActivityVoluntario.this, EsqueceuASenhaActivity.class);
        startActivity(i);
    }

    public void clicarBotaoEntrarVoluntario (View view) {
        voluntario = new Voluntario();
        controleCadastro = new ControleCadastro();
        voluntario.setEmail(email.getText().toString());
        voluntario.setSenha(senha.getText().toString());
        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show();
        } else {
            voluntario = controleCadastro.validarLoginVoluntario(voluntario, nomeTabela, getApplicationContext());

            Intent i = new Intent(LoginActivityVoluntario.this, MenuVoluntarioActivity.class);
            startActivity(i);
        }
    }
}



