package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.voluntir.R;

public class MinhaContaONGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_ong);

        getSupportActionBar().hide();
    }

//    public void clicarBotaoEditar(View view) {
//
//        //nao sei se é aqui que configura os edit text alimentados (ver layout pq tem comentario)
//        Toast.makeText(this, "Editou!", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, CadastroONGActivity.class);
//        startActivity(intent);
//
//    }

}