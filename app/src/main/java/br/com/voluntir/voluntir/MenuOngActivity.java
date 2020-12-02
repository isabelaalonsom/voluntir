package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.ong.MinhaContaONGActivity;


public class MenuOngActivity extends AppCompatActivity {
    Voluntario voluntario;
    Ong ong;
    Button botaoCriarVaga;
    TextView txtEmailOng, txtNomeOng, txtIdOng;
    private FirebaseAuth autenticacao;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
    private DatabaseReference bancoFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ong);

        getSupportActionBar().hide();

        ong = new Ong();

        txtEmailOng = findViewById(R.id.txtViewEmailOngVariavel);
        txtNomeOng = findViewById(R.id.txtViewNomeOngVariavel);

        autenticacao = BancoFirebase.getFirebaseAutenticacao();


        //private DatabaseReference tabelaOng = databaseReference.child("ong");
        //private DatabaseReference emailOngDatabase = tabelaOng.child("emailOng");
        //private DatabaseReference nomeOngDatabase = tabelaOng.child("nome");
        FirebaseAuth autenticacao = BancoFirebase.getFirebaseAutenticacao();

        carregaDadosOng();
        preencheOng();

//        tabelaOng.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                if (snapshot.getValue(String.class) != null) {
////                    String key = snapshot.getKey();
////
////                    if (key.equals("emailOng")) {
////                        String email = snapshot.getValue(String.class);
////                        Toast.makeText(MenuOngActivity.this, "Email: " + email, Toast.LENGTH_SHORT).show();
////                    }
////                }
//
//                for (DataSnapshot ongDatasnap : snapshot.getChildren()) {
//                    Ong ong = ongDatasnap.getValue(Ong.class);
//                    String email = ong.getEmailOng();
//                    String nome = ong.getNome();
//                    Toast.makeText(MenuOngActivity.this, "Nome: " + nome, Toast.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });



        //comparar o email puxado com o do banco de dados

//        botaoCriarVaga = findViewById(R.id.btnCriarVaga);
//        //Recuperar os dados vindos de outra activity
//        Bundle dados = getIntent().getExtras();
//        String email = dados.getString("email");
//
//        if ( dados.getSerializable("ong") instanceof Ong){
//            ong = new Ong();
//            ong = (Ong)  dados.getSerializable("ong");
//
//        }else{
//            voluntario = (Voluntario) dados.getSerializable("voluntario");
//            botaoCriarVaga.setEnabled(false);
//        }
    }

//    private void consultaNoDao() {
//        OngDao dao = new OngDao();
//        dao.buscaOngPeloId();
//
//    }

    private void preencheTextViewEmail() {
        txtEmailOng.setText(ong.getEmailOng());
        txtNomeOng.setText(ong.getNome());
    }

    private void preencheOng() {
        String email = txtEmailOng.getText().toString();
        String nome = txtNomeOng.getText().toString();
        ong.setEmailOng(email);
        ong.setNome(nome);

//        if (email.equals(tabelaOng.child("emailOng"))) {
//            Toast.makeText(this, "São iguais", Toast.LENGTH_SHORT).show();
//        }

//        Bundle parametros = new Bundle();
//
//        Intent i = new Intent(this, CadastroONGActivity.class);
//        parametros.putString("email_ong", email);
//        parametros.putString("nome", nome);



        //ver o temIdValido
    }

    public void carregaDadosOng() {

        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        FirebaseUser ongLogada = autenticacao.getCurrentUser();
        OngDao dao = new OngDao();
        Ong ongTeste;

        String emailCurrentUser = ongLogada.getEmail();
        String idCurrentUser = ongLogada.getUid();

        ong.setEmailOng(emailCurrentUser);
        ong.setIdOng(idCurrentUser);

        Intent dados = getIntent();


        if (dados.hasExtra("ong")) {
            ong = (Ong) dados.getSerializableExtra("ong");
            txtEmailOng.setText(ong.getEmailOng());
            //txtIdOng.setText(ong.setIdOng();
            txtNomeOng.setText(ong.getNome());
            if (dados.hasExtra("ong")) {
                //ong = (Ong) dados.getSerializableExtra("ong");

                ong = (Ong) dados.getSerializableExtra("ong");

                //consegui pegar a senha e o email
                //Toast.makeText(this, "Senha: " + ong.getSenhaOng(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Id: " + ong.getIdOng(), Toast.LENGTH_SHORT).show();

                preencheTextViewEmail();
            } else {
                ong = new Ong();
            }
        }

//        if (dados.hasExtra("email_ong")) {
//            ong = (Ong) dados.getSerializableExtra("email_ong");
//            txtEmailOng.setText(ong.getEmailOng());
////          txtNomeOng.setText(ong.getNome()); }
    }


    public void clicarMinhaConta(View view) {
        Intent intent = new Intent(this, MinhaContaONGActivity.class);
        startActivity(intent);
    }

    public void clicarVagas(View view) {
        Intent intent = new Intent(this, VagaActivity.class);
        startActivity(intent);
    }

    public void clicarCriarVaga(View view) {
        Intent intent = new Intent(this, CadastroVagaActivity.class);
        startActivity(intent);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        nomeOngDatabase.addValueEventListener(this));
//        emailOngDatabase.addValueEventListener(this));
//    }

}