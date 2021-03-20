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
import br.com.voluntir.controller.ControleCadastro;
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
    ControleCadastro controleCadastro;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference bancoFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ong);

        getSupportActionBar().hide();

        Bundle dados = getIntent().getExtras();

        ong = (Ong) dados.getSerializable("objeto");

        txtNomeOng = (TextView) findViewById(R.id.txtViewNomeOng);
        txtNomeOng.setText(ong.getNome());
        autenticacao = BancoFirebase.getFirebaseAutenticacao();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void preencheTextViewEmail() {
        txtEmailOng.setText(ong.getEmailOng());
        txtNomeOng.setText(ong.getNome());
    }

    private void preencheOng() {
        String email = txtEmailOng.getText().toString();
        String nome = txtNomeOng.getText().toString();
        ong.setEmailOng(email);
        ong.setNome(nome);

    }

    public void clicarMinhaConta(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhaContaONGActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",ong);
        startActivity(intent);
    }

    public void clicarVagas(View view) {
        Intent intent = new Intent(this, VagaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }

    public void clicarCriarVaga(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastroVagaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",ong);
        startActivity(intent);

    }

    public void clicarMinhasVagas(View view) {
        //criar logica que aqui puxe um adapter só das vagas vinculadas a essa ONG
        Intent intent = new Intent(getApplicationContext(), MinhasVagasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }


}