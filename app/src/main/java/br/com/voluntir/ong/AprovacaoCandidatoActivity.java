package br.com.voluntir.ong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.voluntir.model.Candidatura;
import br.com.voluntir.voluntario.CandidaturaActivity;
import br.com.voluntir.voluntir.R;

public class AprovacaoCandidatoActivity extends AppCompatActivity {
    TextView txtViewStatusVariavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovacao_candidato);

        txtViewStatusVariavel = findViewById(R.id.txtStatusVariavel);

        getSupportActionBar().hide();

    }

    public void clicarBotaoAprovarCandidato(View view) {
        Toast.makeText(this, "Candidato aprovado!", Toast.LENGTH_SHORT).show();
        txtViewStatusVariavel.setText("Aprovado");
        txtViewStatusVariavel.setTextColor(Color.GREEN);
    }

}