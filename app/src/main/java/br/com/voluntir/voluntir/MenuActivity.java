package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.ong.MinhaContaONGActivity;

public class MenuActivity extends AppCompatActivity {
    private Voluntario voluntario;
    private Ong ong;
    private Button botaoCriarVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        botaoCriarVaga = findViewById(R.id.btnCriarVaga);

        Bundle dados = getIntent().getExtras();

        if (dados.getSerializable("ong") instanceof Ong) {
            ong = new Ong();
            ong = (Ong) dados.getSerializable("ong");

        } else {
            voluntario = (Voluntario) dados.getSerializable("voluntario");
            botaoCriarVaga.setEnabled(false);
        }
    }

    public void clicarCriarVaga(View view) {
        Intent intent = new Intent(this, CadastroVagaActivity.class);
        if (ong != null) {
            intent.putExtra("ong", ong);
        } else {
            intent.putExtra("voluntario", voluntario);
        }
        startActivity(intent);
    }

    public void clicarMinhaConta(View view) {
        Intent intent = new Intent(this, MinhaContaONGActivity.class);
        startActivity(intent);
    }

    public void clicarVagas(View view) {
        Intent intent = new Intent(this, VagaActivity.class);
        startActivity(intent);
    }
}