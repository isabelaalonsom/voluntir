package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
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
import br.com.voluntir.model.Ong;
import br.com.voluntir.ong.CadastroONGActivity;
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

        String emailPreenchido;


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
        //String emailPreenchido;
        //emailPreenchido = ong.setEmailOng(email.getText().toString());
        ong.setEmailOng(email.getText().toString());
        ong.setSenhaOng(senha.getText().toString());
        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        }else{
            ong = controleCadastro.validarLoginOng(ong,nomeTabela,getApplicationContext());

            //coloquei o email da ong numa variavel
            String emailPreenchido;
            emailPreenchido = ong.getEmailOng();

            Intent intent = new Intent(getApplicationContext(), MenuOngActivity.class);

            //autenticacao com o banco para usar essa função getCurrentUser() que puxa varios dados do banco
            //ongLogada é um firebaseUser
                autenticacao = BancoFirebase.getFirebaseAutenticacao();
                FirebaseUser ongLogada = autenticacao.getCurrentUser();

                //esses pegaram
                String emailCurrentUser = ongLogada.getEmail();
                String idCurrentUser = ongLogada.getUid();

                //não pegou o fone
                //String foneCurrentUser = ongLogada.getPhoneNumber();

                //se o emailPreenchido for igual do usuário que está logado
                //então a ong vai receber os dados do usuário logado
                if (emailPreenchido.equals(emailCurrentUser)) {
                    ong.setEmailOng(emailCurrentUser);
                    ong.setIdOng(idCurrentUser);
                    //ong.setNome();
                }

                //toast para testar
                //Toast.makeText(this, "Fone: " + foneCurrentUser, Toast.LENGTH_SHORT).show();

                //passa dados da ong que foram recebidos no if la em cima
                intent.putExtra("ong", ong);

                //intent.putExtra("email_ong", emailPreenchido);
                startActivity(intent);
            }
        }
    }





