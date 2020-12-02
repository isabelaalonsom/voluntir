package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.R;
import br.com.voluntir.voluntir.VoluntarioVisualizarVaga;

public class VagaVoluntarioActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Vaga> listaVaga = new ArrayList<>();
    //cria referencia para o banco de dados e getinstance estamos recuperando a instancia do
    // firebase utilizada para salvar e o getReferecence volta pro nó raiz
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private DatabaseReference tabelaCandidatura = bancoReferencia.child("candidatura");
    Vaga vaga = new Vaga();
    Vaga vagaClicada = new Vaga();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga_voluntario);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewVaga);


        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        tabelaVaga.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();
                //DataSnapshot é o retorno do firebase
                //Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    vaga = dataSnapshot.getValue(Vaga.class);
                    //Log.i("FIREBASE", snapshot.getValue().toString());
                    listaVaga.add(vaga);
                }
                AdapterVaga adapterVaga = new AdapterVaga(listaVaga);
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
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Item pressionado: " ,
                                        Toast.LENGTH_SHORT
                                ).show();
                                Intent intent = new Intent(getApplicationContext(), VoluntarioVisualizarVaga.class);
                                intent.putExtra("vaga",vaga);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Candidatura Enviada!" ,
                                        Toast.LENGTH_SHORT
                                ).show();

                                Intent intent = new Intent(getApplicationContext(), CandidaturaActivity.class);

                                Vaga vagaClicada = listaVaga.get(position);

                                intent.putExtra("vaga", vagaClicada);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}