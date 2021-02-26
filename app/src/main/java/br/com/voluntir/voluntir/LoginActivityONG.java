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

        controleCadastro = new ControleCadastro();
        //emailPreenchido = ong.setEmailOng(email.getText().toString());

        ong.setEmailOng(email.getText().toString());
        ong.setSenhaOng(senha.getText().toString());

        //aqui eu tenho que alimentar a ong com os dados que faltam
        //aqui o cesar setou o email e a senha, nao ta puxando do banco
        //setar o resto relacionado com o id do currentuser


        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else {
            boolean resposta;
            controleCadastro.validarLoginOng(ong, nomeTabela, getApplicationContext());
            Log.i("ONG", ong.toString());
            //coloquei o email da ong numa variavel
            String emailPreenchido, nomepuxado;
            emailPreenchido = ong.getEmailOng();
            //nomepuxado = ong.getNome();


            //autenticacao com o banco para usar essa função getCurrentUser() que puxa varios dados do banco
            //ongLogada é um firebaseUser
            autenticacao = BancoFirebase.getFirebaseAutenticacao();
            FirebaseUser ongLogada = autenticacao.getCurrentUser();

    /*
            //esses pegaram
            String emailCurrentUser = ongLogada.getEmail();
            String idCurrentUser = ongLogada.getUid();

            //OngDao dao = new OngDao();

            //ongPegaDados = new Ong();

            //não pegou o fone
            //String foneCurrentUser = ongLogada.getPhoneNumber();

            //se o emailPreenchido for igual do usuário que está logado
            //então a ong vai receber os dados do usuário logado
            if (emailPreenchido.equals(emailCurrentUser)) {
                ong.setEmailOng(emailCurrentUser);
                ong.setIdOng(idCurrentUser);
            }

            //toast para testar
            //Toast.makeText(this, "Nome: " + ong.getNome(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Id: " + ong.getIdOng(), Toast.LENGTH_SHORT).show();
            ong.setIdOng(ongLogada.getUid());

            //passa dados da ong que foram recebidos no if la em cima
            intent.putExtra("ong", ong);

            //intent.putExtra("email_ong", emailPreenchido);
            startActivity(intent);*/
        }
    }
}





