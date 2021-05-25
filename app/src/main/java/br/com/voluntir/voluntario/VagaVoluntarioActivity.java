package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;
import br.com.voluntir.voluntir.VoluntarioVisualizarVaga;

public class VagaVoluntarioActivity extends AppCompatActivity {

    private final List<Vaga> listaVaga = new ArrayList<>();
    private final List<Voluntario> listaVoluntario = new ArrayList<>();
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private RecyclerView recyclerView;
    private final String nomeTabelaVaga = "vaga";
    private Vaga vagaClicada, vaga;
    private Voluntario voluntario;
    private AdapterVaga adapterVaga;
    private int qtdCanddidatos;
    private boolean usuarioCadastrado = false;
    private ControleVaga controleVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioCadastrado = false;
        setContentView(R.layout.activity_vaga_voluntario);

        //getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewVaga);
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("objeto");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        tabelaVaga.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    vaga.setIdVaga(dataSnapshot.getKey());

                    listaVaga.add(vaga);
                }

                adapterVaga = new AdapterVaga(listaVaga);
                recyclerView.setAdapter(adapterVaga);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Vaga vaga = listaVaga.get(position);
                                Intent intent = new Intent(getApplicationContext(), VoluntarioVisualizarVaga.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("vaga", vaga);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                usuarioCadastrado = false;
                                listaVoluntario.clear();
                                Vaga vaga = listaVaga.get(position);

                                if (vaga.getVoluntarios() != null) {

                                    for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                                        Voluntario voluntario2 = vaga.getVoluntarios().get(i);
                                        listaVoluntario.add(voluntario2);

                                        if (listaVoluntario.get(i).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                            usuarioCadastrado = true;
                                        }
                                    }
                                }


                                if (!usuarioCadastrado) {
                                    vagaClicada = listaVaga.get(position);

                                    if (vagaClicada.getVoluntarios() != null) {
                                        qtdCanddidatos = (vagaClicada.getQtdCandidaturas() - vagaClicada.getVoluntarios().size());
                                    } else {
                                        qtdCanddidatos = vagaClicada.getQtdCandidaturas();
                                    }


                                    if (qtdCanddidatos <= 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "Não é possível se candidatar. Essa vaga já excedeu o limite de candidaturas.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {

                                        voluntario.setStatusVaga("EM ANÁLISE");
                                        listaVoluntario.add(voluntario);
                                        vagaClicada.setVoluntarios(listaVoluntario);
                                        controleVaga = new ControleVaga();

                                        controleVaga.cadastrarVoluntarioVaga(vagaClicada, nomeTabelaVaga, getApplicationContext());
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Você já se candidatou a essa vaga",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


}