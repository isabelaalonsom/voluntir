package br.com.voluntir.ong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.CadastroVoluntarioActivity;
import br.com.voluntir.voluntir.MenuOngActivity;
import br.com.voluntir.voluntir.R;

public class MinhaContaONGActivity extends AppCompatActivity implements ValueEventListener {

    TextView txtNomeOng;
    TextView txtCnpj;
    private TextView txtLocalizacao;
    private TextView txtCausas;
    private TextView txtTelefone;
    private TextView txtSite;
    private TextView txtEmail;
    private TextView txtResumoOng;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
    private DatabaseReference tabelaVaga = databaseReference.child("ong");
    private DatabaseReference nomeOngDatabase = tabelaVaga.child("nome");
    private DatabaseReference cnpjOngDatabase = tabelaVaga.child("cnpj");
    String tabelaOng = "ong";
    Ong ong;
    ControleCadastro controleCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_ong);

        getSupportActionBar().hide();

        txtNomeOng = (TextView) findViewById(R.id.txtViewONGVariavel);
        txtCnpj = (TextView) findViewById(R.id.txtViewCpnjVariavel);
        txtLocalizacao = (TextView) findViewById(R.id.txtViewLocalizaoVariavel);
        txtCausas = (TextView) findViewById(R.id.txtViewCausasVariavel);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavel);
        txtSite = (TextView) findViewById(R.id.txtViewSiteVariavel);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavel);
        txtResumoOng = (TextView) findViewById(R.id.txtViewResumoOngVariavel);

        Bundle dados = getIntent().getExtras();
        ong = (Ong) dados.getSerializable("objeto");
        Toast.makeText(this, "Email: " + ong.getEmailOng(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Nome: " + ong.getNome(), Toast.LENGTH_SHORT).show();
        //txtNomeOng = (TextView) findViewById(R.id.txtViewNomeOng);

        txtNomeOng.setText(ong.getNome());
        txtCnpj.setText(ong.getCpnj());
        txtLocalizacao.setText(ong.getLocalizacao());
        txtCausas.setText(ong.getCausas());
        txtTelefone.setText(ong.getTelefone());
        txtSite.setText(ong.getSite());
        txtEmail.setText(ong.getEmailOng());
        txtResumoOng.setText(ong.getResumoOng());

    }


    public void clicarBotaoEditarOng(View view) {

        if (txtNomeOng.getText().toString().isEmpty() || txtCnpj.getText().toString().isEmpty() || txtLocalizacao.getText().toString().isEmpty() ||
                txtCausas.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty() ||
                txtSite.getText().toString().isEmpty() || txtResumoOng.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else {
            Ong dados = new Ong();
            dados.setIdOng(ong.getIdOng());
            dados.setCausas(txtCausas.getText().toString());
            dados.setCpnj(txtCnpj.getText().toString());
            dados.setEmailOng(txtEmail.getText().toString());
            dados.setNome(txtNomeOng.getText().toString());
            dados.setLocalizacao(txtLocalizacao.getText().toString());
            dados.setResumoOng(txtResumoOng.getText().toString());
            dados.setTelefone(txtTelefone.getText().toString());
            dados.setSite(txtSite.getText().toString());

            controleCadastro = new ControleCadastro();
            controleCadastro.atualizarDadosOng(dados, tabelaOng, getApplicationContext());
        }


    }

    public void clicarBotaoSairOng(View view) {
        this.finishAffinity();
    }

    public void clicarBotaoExcluirOng(View view) {
        Ong dados = new Ong();
        dados.setIdOng(ong.getIdOng());
        dados.setCausas(txtCausas.getText().toString());
        dados.setCpnj(txtCnpj.getText().toString());
        dados.setEmailOng(txtEmail.getText().toString());
        dados.setNome(txtNomeOng.getText().toString());
        dados.setLocalizacao(txtLocalizacao.getText().toString());
        dados.setResumoOng(txtResumoOng.getText().toString());
        dados.setTelefone(txtTelefone.getText().toString());
        dados.setSite(txtSite.getText().toString());

        controleCadastro = new ControleCadastro();
        controleCadastro.excluirDadosOng(dados, tabelaOng, getApplicationContext());

    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.getValue(String.class) != null) {

            String key = dataSnapshot.getKey();
            if (key.equals("nome")) {
                String txtViewONGVariavel = dataSnapshot.getValue(String.class);
                txtNomeOng.setText(txtViewONGVariavel);
            }
            if (key.equals("cnpj")) {
                String txtViewCnpjVariavel = dataSnapshot.getValue(String.class);
                txtCnpj.setText(txtViewCnpjVariavel);
            }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        nomeOngDatabase.addValueEventListener(this);
        cnpjOngDatabase.addValueEventListener(this);
    }
}