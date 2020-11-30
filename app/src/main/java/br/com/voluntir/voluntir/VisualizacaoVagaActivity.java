package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Vaga;

public class VisualizacaoVagaActivity extends AppCompatActivity {
    Vaga vaga;
    TextView textNome,textAreaConhecimento,textDataInicio,textDataFim;
    TextView textHora, textDescricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_vaga);
        vaga = new Vaga();
        getSupportActionBar().hide();

        //Recuperar os dados enviados
        Bundle dados = getIntent().getExtras();
        vaga = (Vaga) dados.getSerializable("vaga");

        textNome = findViewById(R.id.txtViewONGVariavel);
        textAreaConhecimento = findViewById(R.id.txtViewAreaConhecimentoVariavel);
        textDataInicio = findViewById(R.id.txtViewDataInicioVariavel);
        textDataFim = findViewById(R.id.txtViewDataTeminoVariavel);
        textHora = findViewById(R.id.txtViewHorarioVariavel);
        textDescricao = findViewById(R.id.txtViewDescricaoVariavel);

        textNome.setText(vaga.getNomeOng());
        textAreaConhecimento.setText(vaga.getAreaConhecimento());
        textDataInicio.setText(vaga.getDataInicio());
        textDataFim.setText(vaga.getDataTermino());
        textHora.setText(vaga.getHorario());
        textDescricao.setText(vaga.getDescricaoVaga());

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