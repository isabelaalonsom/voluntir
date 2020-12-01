package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth autenticacao;
    Voluntario voluntario = new Voluntario();
    Ong ong = new Ong();
    public String isVoluntario;
    public String isOng;
    ControleCadastro controleCadastro;
    OngDao ongDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button btnONG = findViewById(R.id.btnONG);
        Button btnVoluntario = findViewById(R.id.btnVoluntario);

        btnONG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivityONG.class);
                startActivity(i);
            }
        });

        btnVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivityVoluntario.class);
                startActivity(i);
            }
        });
    }

    private void verificarUsuarioLogado(){
        String id;
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null){
            id=autenticacao.getUid();
            if (autenticacao.getUid().equals(ongDao.busca(id, "ong"))){
                ong = new Ong();
                ong.setIdOng(id);
                menuOng();
            }else{
                voluntario = new Voluntario();
                voluntario.setIdVoluntario(id);
                menuVoluntario();
            }
        }
    }

    public void menuOng () {
        Intent intent = new Intent(this, MenuOngActivity.class);
        startActivity(intent);
    }

    public void menuVoluntario(){
        Intent intent = new Intent(this, MenuVoluntarioActivity.class);
        startActivity(intent);
    }

}