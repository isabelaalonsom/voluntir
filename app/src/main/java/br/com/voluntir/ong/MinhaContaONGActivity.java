package br.com.voluntir.ong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.voluntir.voluntario.CadastroVoluntarioActivity;
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
    private DatabaseReference databaseReference = firebaseDatabase.getReference("ong");
    private DatabaseReference nomeOngDatabase = databaseReference.child("nome");
    private DatabaseReference cnpjOngDatabase = databaseReference.child("cnpj");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_ong);

        getSupportActionBar().hide();

        txtNomeOng = findViewById(R.id.txtViewONGVariavel);
        txtCnpj = findViewById(R.id.txtViewCpnjVariavel);
        txtLocalizacao = findViewById(R.id.txtViewLocalizaoVariavel);
        txtCausas = findViewById(R.id.txtViewCausasVariavel);
        txtTelefone = findViewById(R.id.txtViewTelefoneVariavel);
        txtSite = findViewById(R.id.txtViewSiteVariavel);
        txtEmail = findViewById(R.id.txtViewEmailVariavel);
        txtResumoOng = findViewById(R.id.txtViewResumoOngVariavel);
    }


    public void clicarBotaoEditarOng(View view) {

        String nome = txtNomeOng.getText().toString();
        String cnpj = txtCnpj.getText().toString();
        String localizacao = txtLocalizacao.getText().toString();
        String causa = txtCausas.getText().toString();
        String email = txtEmail.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String site = txtSite.getText().toString();
        String resumoOng = txtResumoOng.getText().toString();

        Intent i = new Intent(this, CadastroONGActivity.class);
        Bundle parametros = new Bundle();

        parametros.putString("chave_nome_ong", nome);
        parametros.putString("chave_cnpj_ong", cnpj);
        parametros.putString("chave_localizacao_ong", localizacao);
        parametros.putString("chave_causas_ong", causa);
        parametros.putString("chave_email_ong", email);
        parametros.putString("chave_telefone_ong", telefone);
        parametros.putString("chave_site_ong", site);
        parametros.putString("chave_resumo_ong", resumoOng);

        i.putExtras(parametros);

        startActivity(i);

    }

    public void clicarBotaoSairOng (View view) {
        this.finishAffinity();
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.getValue(String.class)!=null) {

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