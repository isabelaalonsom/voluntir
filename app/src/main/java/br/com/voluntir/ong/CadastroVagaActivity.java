package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.voluntir.R;

public class CadastroVagaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vaga);

        getSupportActionBar().hide();
    }

    public void clicarBotaoConfirmar (View view) {
        Toast.makeText(this, "Vaga Criada!", Toast.LENGTH_SHORT).show();
    }
}