package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

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
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.ong.AprovacaoCandidatoActivity;

public class VagaActivity extends AppCompatActivity {
    private final List<Vaga> listaVaga = new ArrayList<>();
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private RecyclerView recyclerView;
    private Vaga vaga = new Vaga();
    private Ong ong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga);


        recyclerView = findViewById(R.id.recyclerViewVagaVoluntario);
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
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
                    listaVaga.add(vaga);

                }

                AdapterVaga adapterVaga = new AdapterVaga(listaVaga);
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
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Intent i = new Intent(VagaActivity.this, AprovacaoCandidatoActivity.class);
                                startActivity(i);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }


}