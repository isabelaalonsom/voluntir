package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.ong.CadastroONGActivity;
import br.com.voluntir.voluntir.R;


public class MinhaContaVoluntarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_voluntario);

        getSupportActionBar().hide();
    }


    public void clicarBotaoSair (View view) {
        this.finishAffinity();
    }


}