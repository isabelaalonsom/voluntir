package br.com.voluntir.ong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.MudarSenha;
import br.com.voluntir.voluntir.R;

public class MinhaContaONGActivity extends AppCompatActivity {

    private final String tabelaOng = "ong";
    private TextView txtLocalizacao, txtNomeOng, txtCausas, txtCnpj;
    private TextView txtTelefone, txtSite, txtEmail, txtResumoOng;
    private Ong ong;
    private ControleCadastro controleCadastro;
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private List<Vaga> listaVaga = new ArrayList<>();
    private Vaga vaga = new Vaga();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_ong);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        txtNomeOng = (TextView) findViewById(R.id.txtViewONGVariavel);
        txtCnpj = (TextView) findViewById(R.id.txtViewCpnjVariavel);
        txtLocalizacao = (TextView) findViewById(R.id.txtViewLocalizaoVariavel);
        txtCausas = (TextView) findViewById(R.id.txtViewCausasVariavel);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavel);
        txtSite = (TextView) findViewById(R.id.txtViewSiteVariavel);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavel);
        txtResumoOng = (TextView) findViewById(R.id.txtViewResumoOngVariavel);

        //mascara para o Cnpj
        SimpleMaskFormatter simpleMaskCnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher maskCnpj = new MaskTextWatcher(txtCnpj, simpleMaskCnpj);
        txtCnpj.addTextChangedListener(maskCnpj);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(txtTelefone, simpleMaskTelefone);
        txtTelefone.addTextChangedListener(maskTelefone);

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
        }
        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    if (vaga.getIdOng().equals(ong.getIdOng())) {
                        listaVaga.add(vaga);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                txtResumoOng.getText().toString().isEmpty()) {

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
                if (!ong.getEmailOng().equals(txtEmail.getText().toString())) {
                    controleCadastro.alterarEmailOng(dados, getApplicationContext());
                    finish();
                } else {
                    controleCadastro.atualizarDadosOng(dados, tabelaOng, getApplicationContext());
                    finish();
                }
                try {
                    ControleVaga controleVaga = new ControleVaga();
                    controleVaga.atualizaNomeOng(listaVaga, dados, "vaga", getApplicationContext());
                } catch (Exception e) {

                }


            } else {

            }
        }


    }

    public void clicarBotaoSairOng(View view) {
        Preferencias preferencias = new Preferencias(getApplicationContext());
        preferencias.salvarUsuarioPreferencias(null, null, null);
        this.finishAffinity();
    }

    public void alterarSenha(View view) {
        Intent intent = new Intent(getApplicationContext(), MudarSenha.class);
        if (ong != null) {
            intent.putExtra("email", txtEmail.getText().toString());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void clicarBotaoExcluirOng(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Excluir Conta");
        dialog.setMessage("Deseja excluir conta?");

        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listaVaga != null) {
                    VagaDao vagaDao = new VagaDao();
                    vagaDao.removeListaVaga(listaVaga, ong, getApplicationContext());
                } else {
                    controleCadastro = new ControleCadastro();
                    controleCadastro.excluirDadosOng(ong, tabelaOng, getApplicationContext());
                }

                Ong dados = new Ong();
                if (ong != null) {
                    dados.setIdOng(ong.getIdOng());
                }
                dados.setCausas(txtCausas.getText().toString());
                dados.setCpnj(txtCnpj.getText().toString());
                dados.setEmailOng(txtEmail.getText().toString());
                dados.setNome(txtNomeOng.getText().toString());
                dados.setLocalizacao(txtLocalizacao.getText().toString());
                dados.setResumoOng(txtResumoOng.getText().toString());
                dados.setTelefone(txtTelefone.getText().toString());
                dados.setSite(txtSite.getText().toString());

                controleCadastro = new ControleCadastro();
                if (dados != null) {
                    controleCadastro.excluirDadosOng(dados, tabelaOng, getApplicationContext());
                }

            }
        });

        dialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Exclusão cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.create();
        dialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voltar:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}