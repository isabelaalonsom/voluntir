package br.com.voluntir.voluntir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;


public class MainActivity extends AppCompatActivity {
    ControleCadastro controleCadastro;
    final String tabelaOng = "ong";
    final String tabelaVoluntario = "voluntario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        Context context = this.getApplicationContext();
        Preferencias preferencias = new Preferencias(context);
        Button btnONG = findViewById(R.id.btnONG);
        Button btnVoluntario = findViewById(R.id.btnVoluntario);
        if (preferencias.getEmailUsuarioLogado() != null || preferencias.getSenhaUsuarioLogado() != null) {
            controleCadastro = new ControleCadastro();
            if ((preferencias.getUsuarioLogado().equals("ong"))) {
                controleCadastro.buscaOng(preferencias.getEmailUsuarioLogado(), tabelaOng, getApplicationContext());
            } else if ((preferencias.getUsuarioLogado().equals("voluntario"))) {
                controleCadastro.buscaVoluntario(preferencias.getEmailUsuarioLogado(), tabelaVoluntario, getApplicationContext());
            }

        }

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



}