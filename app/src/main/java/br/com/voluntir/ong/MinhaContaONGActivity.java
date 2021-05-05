package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.voluntir.R;

public class MinhaContaONGActivity extends AppCompatActivity {

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
        limparCampos();
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
        }

        if (ong != null) {
            txtNomeOng.setText(ong.getNome());
            txtCnpj.setText(ong.getCpnj());
            txtLocalizacao.setText(ong.getLocalizacao());
            txtCausas.setText(ong.getCausas());
            txtTelefone.setText(ong.getTelefone());
            txtSite.setText(ong.getSite());
            txtEmail.setText(ong.getEmailOng());
            txtResumoOng.setText(ong.getResumoOng());
        }

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
            if (ong != null) {
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
            } else {

            }
        }


    }

    public void clicarBotaoSairOng(View view) {
        Preferencias preferencias = new Preferencias(getApplicationContext());
        preferencias.salvarUsuarioPreferencias(null, null, null);
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

    public void limparCampos() {
        txtNomeOng.setText("");
        txtCnpj.setText("");
        txtLocalizacao.setText("");
        txtCausas.setText("");
        txtTelefone.setText("");
        txtSite.setText("");
        txtEmail.setText("");
        txtResumoOng.setText("");
    }



}