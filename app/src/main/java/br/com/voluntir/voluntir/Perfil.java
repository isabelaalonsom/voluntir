package br.com.voluntir.voluntir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterPerfil;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class Perfil extends AppCompatActivity {
    private RecyclerView recyclerViewPerfil;
    private String nomeTabelaVoluntario = "voluntario";
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private Voluntario voluntario;
    private AdapterPerfil adapterPerfil;
    private Voluntario voluntarioAtualizado;
    private Vaga vaga;
    private Ong ong;
    private Vaga vagaAtualizada;
    private ControleVaga controleVaga;
    private String nomeTabelaVaga = "vaga";
    private List<Voluntario> listaVoluntario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("voluntario");
            vaga = (Vaga) dados.getSerializable("objeto");
            ong = (Ong) dados.getSerializable("ong");
            voluntarioAtualizado = voluntario;
        }
        recyclerViewPerfil = findViewById(R.id.recyclerViewPerfil);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPerfil.setLayoutManager(layoutManager);
        recyclerViewPerfil.setHasFixedSize(true);

        recyclerViewPerfil.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVoluntario.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Vaga vaga1 = dataSnapshot.getValue(Vaga.class);
                    if (vaga1.getIdVaga().equals(vaga.getIdVaga())) {
                        vagaAtualizada = vaga1;
                        if (vaga1.getVoluntarios() != null) {
                            for (int i = 0; i < vaga1.getVoluntarios().size(); i++) {
                                Voluntario voluntario2 = vaga1.getVoluntarios().get(i);
                                if (vaga1.getVoluntarios().get(i).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                    voluntarioAtualizado = vaga1.getVoluntarios().get(i);

                                }
                                listaVoluntario.add(voluntario2);

                            }
                        }
                    }

                }

                adapterPerfil = new AdapterPerfil(voluntarioAtualizado);
                adapterPerfil.notifyDataSetChanged();
                recyclerViewPerfil.setAdapter(adapterPerfil);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerViewPerfil.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewPerfil,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    public void clicarBotaoAprovar(View view) {
        if (!voluntarioAtualizado.getStatusVaga().equals("APROVADO")) {


            if (vagaAtualizada.getVoluntarios() != null) {
                listaVoluntario.clear();
                for (int i = 0; i < vagaAtualizada.getVoluntarios().size(); i++) {
                    Voluntario voluntario2 = vagaAtualizada.getVoluntarios().get(i);
                    if (vagaAtualizada.getVoluntarios().get(i).getIdVoluntario().equals(voluntarioAtualizado.getIdVoluntario())) {
                        voluntario2.setStatusVaga("APROVADO");
                    }
                    listaVoluntario.add(voluntario2);

                }
            } else {
                listaVoluntario.add(voluntarioAtualizado);
            }

            vagaAtualizada.setVoluntarios(listaVoluntario);
            controleVaga = new ControleVaga();


            controleVaga.atualizaVagaVoluntario(vagaAtualizada, nomeTabelaVaga, getApplicationContext());
        }
    }

    public void clicarBotaoReprovar(View view) {
        if (!voluntarioAtualizado.getStatusVaga().equals("REPROVADO")) {
            if (vagaAtualizada.getVoluntarios() != null) {
                listaVoluntario.clear();
                for (int i = 0; i < vagaAtualizada.getVoluntarios().size(); i++) {
                    Voluntario voluntario2 = vagaAtualizada.getVoluntarios().get(i);
                    if (vagaAtualizada.getVoluntarios().get(i).getIdVoluntario().equals(voluntarioAtualizado.getIdVoluntario())) {
                        voluntario2.setStatusVaga("REPROVADO");
                    }
                    listaVoluntario.add(voluntario2);

                }
            } else {
                listaVoluntario.add(voluntarioAtualizado);
            }

            vagaAtualizada.setVoluntarios(listaVoluntario);
            controleVaga = new ControleVaga();


            controleVaga.atualizaVagaVoluntario(vagaAtualizada, nomeTabelaVaga, getApplicationContext());
        }
    }
}