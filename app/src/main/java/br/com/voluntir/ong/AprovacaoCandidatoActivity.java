package br.com.voluntir.ong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.adapter.AdapterAprovacao;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AprovacaoCandidatoActivity extends AppCompatActivity {
    TextView txtViewStatusVariavel, txtNomeVoluntario;
    private RecyclerView recyclerViewCandidato;
    private List<Voluntario> listaVoluntarios = new ArrayList<>();
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVoluntario = bancoReferencia.child("voluntario");
    Voluntario voluntario = new Voluntario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovacao_candidato);

        getSupportActionBar().hide();

        recyclerViewCandidato = findViewById(R.id.recyclerViewCandidatos);
        txtNomeVoluntario = findViewById(R.id.txtViewCandidatos);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidato.setLayoutManager(layoutManager);
        recyclerViewCandidato.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerViewCandidato.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

//        trazVagaClicada();

        tabelaVoluntario.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVoluntarios.clear();
                listaVoluntarios.add(voluntario);

                txtNomeVoluntario.setText("Victor Capel");

                //alterar aqui qualquer coisa

                AdapterAprovacao adapterAprovacao = new AdapterAprovacao(listaVoluntarios);

                recyclerViewCandidato.setAdapter(adapterAprovacao);
            }

            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void trazVagaClicada() {
//        Intent dados = getIntent();
//
//        if (dados.hasExtra("vaga")) {
//            vaga = (Vaga) dados.getSerializableExtra("vaga");
//
//            if (dados.hasExtra("vaga")) {
//
//                vaga = (Vaga) dados.getSerializableExtra("vaga");
//
//            } else {
//                vaga = new Vaga();
//            }
//        }
//    }

    public void clicarBotaoAprovarCandidato(View view) {
        Toast.makeText(this, "Candidato aprovado!", Toast.LENGTH_SHORT).show();
        txtViewStatusVariavel.setText("Aprovado");
        txtViewStatusVariavel.setTextColor(Color.GREEN);
    }

}