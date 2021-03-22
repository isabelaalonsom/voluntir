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

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterAprovacao;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.AprovacaoCandidatoActivity;

public class MinhasVagasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Vaga> listaVaga = new ArrayList<>();
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    //cria referencia para o banco de dados e getinstance estamos recuperando a instancia do
    // firebase utilizada para salvar e o getReferecence volta pro nó raiz
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    Vaga vaga = new Vaga();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    Ong ong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewVaga);
        Bundle dados = getIntent().getExtras();
        ong = (Ong) dados.getSerializable("objeto");
        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();
                //DataSnapshot é o retorno do firebase
                //Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    //Log.i("FIREBASE", snapshot.getValue().toString());
                    if (vaga.getIdOng().equals(ong.getIdOng())) {
                        listaVaga.add(vaga);
                    }

                }
                AdapterVaga adapterVaga = new AdapterVaga(listaVaga);
                AdapterAprovacao adapterAprovacao = new AdapterAprovacao(listaVoluntario);
                recyclerView.setAdapter(adapterVaga);
            }
            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Vaga vaga = listaVaga.get(position);
                                /*Toast.makeText(
                                        getApplicationContext(),
                                        "Item pressionado: " ,
                                        Toast.LENGTH_SHORT
                                ).show();*/
                                //Intent intent = new Intent(getApplicationContext(), VoluntarioVisualizarVaga.class);
                                Intent intent = new Intent(getApplicationContext(), AprovacaoCandidatoActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("objeto", vaga);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Intent i = new Intent(this, AprovacaoCandidatoActivity.class);

                                //i.putExtra("nome_voluntario", vaga);
                                //i.putExtra("nome_voluntario", voluntario.getNome());

                                //startActivity(i);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }
}