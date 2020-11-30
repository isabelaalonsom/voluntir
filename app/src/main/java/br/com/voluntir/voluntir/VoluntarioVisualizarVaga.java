package br.com.voluntir.voluntir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class VoluntarioVisualizarVaga extends AppCompatActivity {
    Vaga vaga;
    Voluntario voluntario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntario_visualizar_vaga);
    }

    public void botaoCandidatar(){
        vaga = new Vaga();
        vaga.getIdVaga();
        vaga.setVoluntario(voluntario);
    }
}