package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroONGActivity;
import br.com.voluntir.voluntario.CadastroVoluntarioActivity;
//import br.com.voluntir.voluntario.CadastroVoluntarioActivity;


public class LoginActivityONG extends AppCompatActivity {

//    Button btnEsqueciASenha;
//    Button criarContaBtn = findViewById(R.id.criarContaBtn);
//    Voluntario voluntario = new Voluntario();
//    //Ong ong = new Ong();
//    Button btnEntrar = findViewById(R.id.btnEntrar);

//    EditText edtTextEmailLogin = findViewById(R.id.edtTextEmailLogin);
//    EditText edtTextSenhaLogin = findViewById(R.id.edtTextSenhaLogin);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ong);

        getSupportActionBar().hide();


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
          Intent intent = new Intent(this, CadastroONGActivity.class);
          startActivity(intent);
    }

}



