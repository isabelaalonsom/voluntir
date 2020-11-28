package br.com.voluntir.voluntario;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.voluntir.R;


public class CadastroVoluntarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_voluntario);

        getSupportActionBar().hide();
    }

    public void clicarBotaoConfirmar(View view) {
        Toast.makeText(this, "Cadastro criado do Voluntário!", Toast.LENGTH_SHORT).show();
        //aqui tem que jogar pro banco de dados os edit text preenchidos
    }

    public void clicarBotaoLimpar(View view) {

        //aqui fazer a programacao para limpar os Edit Text
    }
}