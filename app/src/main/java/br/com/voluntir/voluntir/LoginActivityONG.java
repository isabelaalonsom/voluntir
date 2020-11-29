package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.ong.CadastroONGActivity;
import br.com.voluntir.ong.CadastroVagaActivity;
//import br.com.voluntir.voluntario.CadastroVoluntarioActivity;


public class LoginActivityONG extends AppCompatActivity {
    //    Button criarContaBtn = findViewById(R.id.criarContaBtn);
//    Voluntario voluntario = new Voluntario();
//    //Ong ong = new Ong();
//    Button btnEntrar = findViewById(R.id.btnEntrar);

//    EditText edtTextEmailLogin = findViewById(R.id.edtTextEmailLogin);
//    EditText edtTextSenhaLogin = findViewById(R.id.edtTextSenhaLogin);
    private Button botaoEntrar;
    private Button botaoCriarConta;
    private Button botaoEsqueciSenha;
    private Ong ong;
    private EditText email;
    private EditText senha;
    private String nomeTabela = "ong";
    private ControleCadastro controleCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ong);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.edtTextEmailLogin);
        senha = (EditText) findViewById(R.id.edtTextSenhaLogin  );

        Button btnEsqueceuASenha = findViewById(R.id.esqueceuSenhaBtn);


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

    }

    public void mensagemLogin(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void clicarCriarConta(View view) {
          Intent intent = new Intent(this, CadastroONGActivity.class);
          startActivity(intent);
    }

    public void clicarBotaoEsqueceuASenha(View view) {
        Intent i = new Intent(this, EsqueceuASenhaActivity.class);
        startActivity(i);
    }

    public void clicarBotaoEntrarOng(View view) {
        ong = new Ong();
        controleCadastro = new ControleCadastro();
        ong.setEmailOng(email.getText().toString());
        ong.setSenhaOng(senha.getText().toString());
        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        }else{
            ong = controleCadastro.validarLoginOng(ong,nomeTabela,getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            //para passar a ong para a proxima tela
            intent.putExtra("email", ong.getEmailOng());
            intent.putExtra("ong", (Serializable) ong);
            startActivity(intent);
        }

        /*//isso está errado, fiz so para testar a CadastroVagaActivity
        Intent i = new Intent(this, CadastroVagaActivity.class);
        startActivity(i);*/
    }

}



