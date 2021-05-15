package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class MenuVoluntarioActivity extends AppCompatActivity {
    Voluntario voluntario;
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

        runOnUiThread(new Thread((Runnable) () -> {
            try {
                txtNomeVoluntario = (TextView) findViewById(R.id.txtViewNomeVoluntario);
                txtNomeVoluntario.setText(voluntario.getNome());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "erro: " + e.getCause(), Toast.LENGTH_LONG).show();
            }
        }));


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
        Intent intent = new Intent(getApplicationContext(), MinhaCandidaturaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", voluntario);
        startActivity(intent);
    }

}