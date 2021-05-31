package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterCandidatura;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class MinhaCandidaturaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCandidatura;
    private final List<Vaga> listaVagaCandidatada = new ArrayList<>();
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private final String nomeTabelaVaga = "vaga";
    private ControleVaga controleVaga;
    private Voluntario voluntario;
    private Vaga vaga = new Vaga();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_candidatura);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerViewCandidatura = findViewById(R.id.recyclerViewCandidaturas);

        Bundle dados = getIntent().getExtras();
        voluntario = (Voluntario) dados.getSerializable("objeto");
        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidatura.setLayoutManager(layoutManager);
        recyclerViewCandidatura.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerViewCandidatura.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        Query teste = tabelaVaga.orderByChild("voluntarios");
        teste.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVagaCandidatada.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    if (vaga.getVoluntarios() != null) {

                        for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                            if (vaga.getVoluntarios().get(i).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                listaVagaCandidatada.add(vaga);

                            }
                        }
                    }


                }
                AdapterCandidatura adapterCandidatura = new AdapterCandidatura(listaVagaCandidatada, voluntario);
                recyclerViewCandidatura.setAdapter(adapterCandidatura);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        recyclerViewCandidatura.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewCandidatura,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                            }

                            @Override
                            public void onLongItemClick(View view, int position) throws IOException, DocumentException {
                                Vaga vaga = listaVagaCandidatada.get(position);
                                for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                                    if (!vaga.getVoluntarios().get(i).getStatusVaga().equals("EM ANÁLISE")) {
                                        Toast.makeText(getApplicationContext(),
                                                "Você só pode desistir das candidaturas que estão com o status Em Análise",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        vaga.getVoluntarios().remove(i);
                                        controleVaga = new ControleVaga();

                                        controleVaga.cancelarCandidatura(vaga, nomeTabelaVaga, getApplicationContext());
                                    }
                                }


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    public void trazVagaClicada() {
        Intent dados = getIntent();

        if (dados.hasExtra("vaga")) {
            vaga = (Vaga) dados.getSerializableExtra("vaga");

            if (dados.hasExtra("vaga")) {

                vaga = (Vaga) dados.getSerializableExtra("vaga");

            } else {
                vaga = new Vaga();
            }
        }
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