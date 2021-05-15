package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Vaga;

public class VisualizacaoVagaActivity extends AppCompatActivity {
    private Vaga vaga;
    private TextView textNome, textAreaConhecimento, textDataInicio, textDataFim, textCargaHoraria, textDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_vaga);
        vaga = new Vaga();
        getSupportActionBar().hide();

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            vaga = (Vaga) dados.getSerializable("vaga");
        }

        textNome = findViewById(R.id.txtViewONGVariavel);
        textAreaConhecimento = findViewById(R.id.txtViewAreaConhecimentoVariavel);
        textDataInicio = findViewById(R.id.txtViewDataInicioVariavel);
        textDataFim = findViewById(R.id.txtViewDataTeminoVariavel);
        textCargaHoraria = findViewById(R.id.txtViewCargaHorariaVariavel);
        textDescricao = findViewById(R.id.txtViewDescricaoVariavel);
        textNome.setText(vaga.getNomeOng());
        textAreaConhecimento.setText(vaga.getAreaConhecimento());
        textDataInicio.setText(vaga.getDataInicio());
        textDataFim.setText(vaga.getDataTermino());
        textCargaHoraria.setText(vaga.getCargaHoraria());
        textDescricao.setText(vaga.getDescricaoVaga());

    }

}