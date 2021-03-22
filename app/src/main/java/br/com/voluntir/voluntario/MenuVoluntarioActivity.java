package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.voluntir.MenuOngActivity;
import br.com.voluntir.voluntir.R;
import br.com.voluntir.voluntir.VagaActivity;

public class MenuVoluntarioActivity extends AppCompatActivity {
    Voluntario voluntario;
    Ong ong;
    Button botaoCriarVaga;
    TextView txtNomeVoluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_voluntario);

        getSupportActionBar().hide();

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("objeto");
        }


    }

    public void clicarMinhaContaVoluntario(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhaContaVoluntarioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",voluntario);
        startActivity(intent);
    }

    public void clicarVagasVoluntario(View view) {
        Intent intent = new Intent(getApplicationContext(), VagaVoluntarioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", voluntario);
        startActivity(intent);
    }

    public void clicarBotaoCandidaturas(View view) {
        Intent intent = new Intent(getApplicationContext(), CandidaturaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",ong);
        startActivity(intent);
    }

}