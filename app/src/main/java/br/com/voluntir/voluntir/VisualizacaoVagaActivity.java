package br.com.voluntir.voluntir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizacaoVagaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_vaga);

        getSupportActionBar().hide();
    }

//    public void clicarBotaoEditar(View view) {
//
//
//        //nao sei se é aqui que configura os edit text alimentados
//        Intent intent = new Intent(this, CadastroVaga.class);
//        startActivity(intent);
//
//    }



}