package br.com.voluntir.voluntario;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class VagaVoluntarioActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Vaga> listaVaga = new ArrayList<>();
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    //cria referencia para o banco de dados e getinstance estamos recuperando a instancia do
    // firebase utilizada para salvar e o getReferecence volta pro nó raiz
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private DatabaseReference tabelaCandidatura = bancoReferencia.child("candidatura");
    private String nomeTabelaVaga = "vaga";
    Vaga vaga = new Vaga();
    Vaga vagaClicada;
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    Voluntario voluntario;
    ControleCadastro controleCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga_voluntario);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewVaga);
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("objeto");
        }


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
                    vaga.setIdVaga(dataSnapshot.getKey());
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

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {



                                /*Intent intent = new Intent(getApplicationContext(), CandidaturaActivity.class);
                                vagaClicada = listaVaga.get(position);
                                Toast.makeText(getApplicationContext(),
                                        "id " +position,
                                        Toast.LENGTH_SHORT).show();
                                intent.putExtra("voluntario", voluntario);
                                intent.putExtra("vaga", vagaClicada);
                                startActivity(intent);*/

                                //Vaga vagaClicada = listaVaga.get(position);
                                //vagaClicada.setVoluntario(voluntario);
                                //vagaClicada.setVoluntarios((List<Voluntario>) voluntario);
                                if (vaga.getVoluntarios().isEmpty()) {
                                    listaVoluntario.add(voluntario);
                                } else {
                                    vaga.getVoluntarios();
                                    listaVoluntario.addAll(vaga.getVoluntarios());
                                }


                                vaga.setVoluntarios(listaVoluntario);
                                controleCadastro = new ControleCadastro();
                                controleCadastro.atualizaVagaVoluntario(vaga, nomeTabelaVaga, getApplicationContext());

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}