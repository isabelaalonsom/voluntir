package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.ong.MinhaContaONGActivity;
import br.com.voluntir.voluntir.R;
import br.com.voluntir.voluntir.VagaActivity;

public class MenuVoluntarioActivity extends AppCompatActivity {
    Voluntario voluntario;
    Ong ong;
    Button botaoCriarVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_voluntario);

        getSupportActionBar().hide();

//        botaoCriarVaga = findViewById(R.id.btnCriarVaga);
//        //Recuperar os dados vindos de outra activity
//        Bundle dados = getIntent().getExtras();
//        String email = dados.getString("email");
//
//        if ( dados.getSerializable("ong") instanceof Ong){
//            ong = new Ong();
//            ong = (Ong)  dados.getSerializable("ong");
//
//        }else{
//            voluntario = (Voluntario) dados.getSerializable("voluntario");
//            botaoCriarVaga.setEnabled(false);
//        }
    }

    public void clicarMinhaContaVoluntario(View view) {
        Intent intent = new Intent(this, MinhaContaVoluntarioActivity.class);
        startActivity(intent);
    }

    public void clicarVagasVoluntario(View view) {
        Intent intent = new Intent(this, VagaVoluntarioActivity.class);
        startActivity(intent);
    }

    public void clicarBotaoCandidaturas(View view) {
        Intent intent = new Intent(this, CandidaturaActivity.class);
        startActivity(intent);
    }

}