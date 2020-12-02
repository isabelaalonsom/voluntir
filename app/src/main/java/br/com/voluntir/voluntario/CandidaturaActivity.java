package br.com.voluntir.voluntario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Candidatura;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.R;

public class CandidaturaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCandidatura;
    private List<Vaga> listaVagaCandidatada = new ArrayList<>();
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    Vaga vaga = new Vaga();
    //Candidatura candidatura = new Candidatura();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatura);

        getSupportActionBar().hide();

        recyclerViewCandidatura = findViewById(R.id.recyclerViewCandidaturas);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidatura.setLayoutManager(layoutManager);
        recyclerViewCandidatura.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerViewCandidatura.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        Intent dados = getIntent();

        if (dados.hasExtra("vaga")) {
            vaga = (Vaga) dados.getSerializableExtra("vaga");

            if (dados.hasExtra("ong")) {

                vaga = (Vaga) dados.getSerializableExtra("vaga");

            } else {
                vaga = new Vaga();
            }
        }


        tabelaVaga.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVagaCandidatada.clear();
                //DataSnapshot é o retorno do firebase
                //Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    vaga = dataSnapshot.getValue(Vaga.class);
                    //Log.i("FIREBASE", snapshot.getValue().toString());
                    listaVagaCandidatada.add(vaga);
                }
                AdapterVaga adapterVaga = new AdapterVaga(listaVagaCandidatada);
                recyclerViewCandidatura.setAdapter(adapterVaga);
            }
            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}