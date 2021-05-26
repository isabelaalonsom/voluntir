package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.voluntir.model.Vaga;

public class VoluntarioVisualizarVaga extends AppCompatActivity {
    private Vaga vaga;
    private TextView textNome, textAreaConhecimento, textDataInicio, textDataFim, textCargaHoraria, textDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntario_visualizar_vaga);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        vaga = new Vaga();


        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            vaga = (Vaga) dados.getSerializable("vaga");
        }


        textNome = findViewById(R.id.txtViewVoluntarioVisualizarVaga);
        textAreaConhecimento = findViewById(R.id.txtViewAreaConhecimentoVoluntarioVisualizarVaga);
        textDataInicio = findViewById(R.id.txtViewDataInicioVoluntarioVisualizarVaga);
        textDataFim = findViewById(R.id.txtViewDataTeminoVoluntarioVisualizarVaga);
        textCargaHoraria = findViewById(R.id.txtViewCargaHorariaVoluntarioVisualizarVaga);
        textDescricao = findViewById(R.id.txtViewDescricaoVoluntarioVisualizarVaga);
        textNome.setText(vaga.getNomeOng());
        textAreaConhecimento.setText(vaga.getAreaConhecimento());
        textDataInicio.setText(vaga.getDataInicio());
        textDataFim.setText(vaga.getDataTermino());
        textCargaHoraria.setText(vaga.getCargaHoraria());
        textDescricao.setText(vaga.getDescricaoVaga());
    }

    public void visualizarPerfilOng(View view) {
        Intent intent = new Intent(getApplicationContext(), VisualizarPerfilOng.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("vaga", vaga);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voltar:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}