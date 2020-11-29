package br.com.voluntir.voluntir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.controller.ControleCriarVagas;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class VagaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Vaga> listaVaga = new ArrayList<>();
    ControleCriarVagas controleCriarVagas;
    private String nomeTabela = "vaga";
    Ong ong;
    Voluntario voluntario;

    //cria referencia para o banco de dados e getinstance estamos recuperando a instancia do
    // firebase utilizada para salvar e o getReferecence volta pro nó raiz
    private DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaServico = bancoReferencia.child("servico");
    Vaga vaga = new Vaga();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagas);

        //Recuperar os dados vindos de outra activity
        Bundle dados = getIntent().getExtras();
        String email = dados.getString("email");

        if ( dados.getSerializable("ong") instanceof Ong){
            ong = (Ong)  dados.getSerializable("ong");
        }else{
            voluntario = (Voluntario) dados.getSerializable("voluntario");

        }

        recyclerView = findViewById(R.id.recyclerView);

        //Listagem de Serviços
        //this.criarServico();

        //Configurar Adapter


        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


        //bancoReferencia permite salvar os dados no Firebase
        //bancoReferencia.child( "servico" ).child("01").child("descricao").setValue("Redes Sociais");
       /* vaga.setDescricao("Redes Sociais");
        vaga.setServicoVoluntarioModel(3);

        tabelaServico.child("03").setValue(vaga);*/


        controleCriarVagas = new ControleCriarVagas();
        listaVaga = controleCriarVagas.listarVaga(null,nomeTabela);

        AdapterVaga adaptervaga = new AdapterVaga(listaVaga);
        recyclerView.setAdapter(adaptervaga);

        /*
        recyclerView = findViewById(R.id.recyclerView);

        //Listagem de Serviços
        //this.criarServico();

        //Configurar Adapter
        AdapterVaga adapterServicoVoluntario = new AdapterVaga(listaVaga);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //coloca uma linha para separar
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerView.setAdapter(adapterServicoVoluntario);
*/
        //evento de click
        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(
                    getApplicationContext(),
                    recyclerView,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Vaga servico = listaVaga.get(position);
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Item pressionado: " +servico.getDescricaoVaga(),
                                    Toast.LENGTH_SHORT
                            ).show();
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



}