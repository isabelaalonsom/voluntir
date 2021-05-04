package br.com.voluntir.ong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterAprovacao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.Perfil;
import br.com.voluntir.voluntir.PerfilCandidato;
import br.com.voluntir.voluntir.R;

public class AprovacaoCandidatoActivity extends AppCompatActivity {
    private TextView txtViewStatusVariavel, txtNomeVoluntario;
    private List<Vaga> listaVaga = new ArrayList<>();
    private RecyclerView recyclerViewCandidato;
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVoluntario = bancoReferencia.child("voluntario");
    private DatabaseReference refenciaBanco;
    private DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private AdapterAprovacao adapterAprovacao;
    int tamanho = 0;
    private Vaga vaga;
    private Ong ong;
    private Voluntario voluntario;
    private Voluntario voluntario2;
    private Button botaoAprovado;
    Vaga vagaAtualizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_aprovacao_candidato);

        getSupportActionBar().hide();
        Bundle dados = getIntent().getExtras();
        vaga = (Vaga) dados.getSerializable("objeto");
        ong = (Ong) dados.getSerializable("ong");
        recyclerViewCandidato = findViewById(R.id.recyclerViewCandidatos);
        txtNomeVoluntario = findViewById(R.id.txtViewCandidatos);
        //botaoAprovado = (Button) findViewById(R.id.btnAprovar);
        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCandidato.setLayoutManager(layoutManager);
        recyclerViewCandidato.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerViewCandidato.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

//        trazVagaClicada();

        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();
                listaVoluntario.clear();
                //DataSnapshot é o retorno do firebase
                //Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Vaga vaga1 = dataSnapshot.getValue(Vaga.class);
                    vaga1.setIdVaga(dataSnapshot.getKey());
                    //Log.i("Voluntario", vaga.getIdVaga());
                    if (vaga1.getIdVaga().equals(vaga.getIdVaga())) {
                        vagaAtualizada = vaga1;

                        if (vaga1.getVoluntarios() != null) {
                            for (int i = 0; i < vaga1.getVoluntarios().size(); i++) {
                                voluntario = vaga1.getVoluntarios().get(i);

                                listaVoluntario.add(voluntario);
                            }
                        }
                    }


                    //Log.i("FIREBASE", snapshot.getValue().toString());
                    listaVaga.add(vagaAtualizada);

                }

                adapterAprovacao = new AdapterAprovacao(listaVoluntario);
                adapterAprovacao.notifyDataSetChanged();
                recyclerViewCandidato.setAdapter(adapterAprovacao);


            }

            //trata o erro se a operação for cancelada
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





        /*AdapterAprovacao adapterAprovacao = new AdapterAprovacao(listaVoluntarios);

        recyclerViewCandidato.setAdapter(adapterAprovacao);*/

        //evento de click
        recyclerViewCandidato.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewCandidato,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                voluntario = vagaAtualizada.getVoluntarios().get(position);

                                Intent intent = new Intent(getApplicationContext(), Perfil.class);
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

    //    public void trazVagaClicada() {
//        Intent dados = getIntent();
//
//        if (dados.hasExtra("vaga")) {
//            vaga = (Vaga) dados.getSerializableExtra("vaga");
//
//            if (dados.hasExtra("vaga")) {
//
//                vaga = (Vaga) dados.getSerializableExtra("vaga");
//
//            } else {
//                vaga = new Vaga();
//            }
//        }
//    }

    public void clicarBotaoAprovarCandidato(View view) {
        /*botaoAprovado = (Button) findViewById(R.id.btnAprovar);
        botaoAprovado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Candidato aprovado!", Toast.LENGTH_SHORT).show();
                botaoAprovado.setText("Aprovado");
                botaoAprovado.setTextColor(Color.GREEN);

            }
        });*/
    }

}