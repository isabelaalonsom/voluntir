package br.com.voluntir.ong;

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

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterAprovacao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.PerfilVoluntario;
import br.com.voluntir.voluntir.R;

public class AprovacaoCandidatoActivity extends AppCompatActivity {
    private final List<Voluntario> listaVoluntario = new ArrayList<>();
    private RecyclerView recyclerViewCandidato;
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    Vaga vagaAtualizada;
    private AdapterAprovacao adapterAprovacao;
    private Vaga vaga;
    private Ong ong;
    private Voluntario voluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovacao_candidato);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();


        //getSupportActionBar().hide();
        Bundle dados = getIntent().getExtras();
        vaga = (Vaga) dados.getSerializable("objeto");
        ong = (Ong) dados.getSerializable("ong");
        recyclerViewCandidato = findViewById(R.id.recyclerViewCandidatos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidato.setLayoutManager(layoutManager);
        recyclerViewCandidato.setHasFixedSize(true);

        recyclerViewCandidato.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        Query teste = tabelaVaga;
        teste.orderByKey().equalTo(vaga.getIdVaga()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVoluntario.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Vaga vaga1 = dataSnapshot.getValue(Vaga.class);
                    vaga1.setIdVaga(dataSnapshot.getKey());

                    if (vaga1.getIdVaga().equals(vaga.getIdVaga())) {
                        vagaAtualizada = vaga1;

                        if (vaga1.getVoluntarios() != null) {
                            for (int i = 0; i < vaga1.getVoluntarios().size(); i++) {
                                voluntario = vaga1.getVoluntarios().get(i);


                                listaVoluntario.add(voluntario);
                            }

                        }

                    }
                }

                adapterAprovacao = new AdapterAprovacao(listaVoluntario);
                adapterAprovacao.notifyDataSetChanged();
                recyclerViewCandidato.setAdapter(adapterAprovacao);
                if (listaVoluntario.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Nenhum candidato cadastrado ",
                            Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerViewCandidato.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewCandidato,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                voluntario = vagaAtualizada.getVoluntarios().get(position);

                                Intent intent = new Intent(getApplicationContext(), PerfilVoluntario.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("objeto", vagaAtualizada);
                                intent.putExtra("ong", ong);
                                intent.putExtra("voluntario", voluntario);
                                startActivity(intent);

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