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

import com.google.firebase.auth.FirebaseAuth;
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
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class EditarMinhasVagas extends AppCompatActivity {

    Vaga vaga = new Vaga();
    String informacoes = "";
    Ong ong;
    private RecyclerView recyclerViewEditarVaga;
    private List<Vaga> listaVaga = new ArrayList<>();
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_editar_minhas_vagas);

        getSupportActionBar().hide();

        recyclerViewEditarVaga = findViewById(R.id.recyclerViewVagaditarMinhasVagas);
        Bundle dados = getIntent().getExtras();
        ong = (Ong) dados.getSerializable("objeto");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewEditarVaga.setLayoutManager(layoutManager);
        recyclerViewEditarVaga.setHasFixedSize(true);

        recyclerViewEditarVaga.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);

                    if (vaga.getIdOng().equals(ong.getIdOng())) {
                        listaVaga.add(vaga);

                    }

                }
                AdapterVaga adapterVaga = new AdapterVaga(listaVaga);
                recyclerViewEditarVaga.setAdapter(adapterVaga);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerViewEditarVaga.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewEditarVaga,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Vaga vaga = listaVaga.get(position);

                                Intent intent = new Intent(getApplicationContext(), EditarVaga.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("vaga", vaga);
                                intent.putExtra("ong", ong);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) throws IOException, DocumentException {
                                Vaga vaga = listaVaga.get(position);


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}