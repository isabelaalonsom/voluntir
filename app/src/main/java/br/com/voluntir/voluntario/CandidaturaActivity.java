package br.com.voluntir.voluntario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.adapter.AdapterCandidatura;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class CandidaturaActivity extends AppCompatActivity {

    private TextView txtViewStatus, txtViewStatusVariavel;
    private RecyclerView recyclerViewCandidatura;
    private List<Vaga> listaVagaCandidatada = new ArrayList<>();
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");

    Ong ong;
    Voluntario voluntario;
    Vaga vaga = new Vaga();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatura);

        getSupportActionBar().hide();

        /*recyclerViewCandidatura = findViewById(R.id.recyclerViewCandidaturas);
        txtViewStatus = findViewById(R.id.txtStatus);
        txtViewStatusVariavel = findViewById(R.id.txtStatusVariavel);
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("voluntario");
            listaVoluntario.add(voluntario);
        }


        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidatura.setLayoutManager(layoutManager);
        recyclerViewCandidatura.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerViewCandidatura.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        trazVagaClicada();
        //vaga.setVoluntario(listaVoluntario);
        tabelaVaga.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVagaCandidatada.clear();
                listaVagaCandidatada.add(vaga);

                AdapterCandidatura adapterCandidatura = new AdapterCandidatura(listaVagaCandidatada);

                recyclerViewCandidatura.setAdapter(adapterCandidatura);
            }

            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().hide();
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
                //DataSnapshot é o retorno do firebase
                //Log.i("FIREBASE", snapshot.getValue().toString());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    //Log.i("FIREBASE", snapshot.getValue().toString());
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

            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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

}