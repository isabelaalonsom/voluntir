package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.voluntir.R;

public class CadastroONGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);

        getSupportActionBar().hide();
    }
    
    public void clicarBotaoConfirmar(View view) {
        Toast.makeText(this, "Cadastro Criado!", Toast.LENGTH_SHORT).show();
        //aqui tem que jogar pro banco de dados os edit text preenchidos
    }
}