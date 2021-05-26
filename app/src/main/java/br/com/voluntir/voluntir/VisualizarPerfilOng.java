package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;

public class VisualizarPerfilOng extends AppCompatActivity {
    private TextView txtNomeOng, txtCnpj, txtLocalizacao, txtCausas, txtTelefone, txtSite, txtEmail, txtResumoOng, txtViewTituloTopo;
    private Ong ong;
    private ControleCadastro controleCadastro;
    private Vaga vaga;
    private OngDao ongDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_ong);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        txtNomeOng = (TextView) findViewById(R.id.txtViewNomePerfilOng);
        txtCnpj = (TextView) findViewById(R.id.txtViewCpnjVariavelPerfilOng);
        txtLocalizacao = (TextView) findViewById(R.id.txtViewLocalizaoVariavelPerfilOng);
        txtCausas = (TextView) findViewById(R.id.txtViewCausasVariavelPerfilOng);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavelPerfilOng);
        txtSite = (TextView) findViewById(R.id.txtViewSiteVariavelPerfilOng);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavelPerfilOng);
        txtResumoOng = (TextView) findViewById(R.id.txtViewResumoOngVariavelPerfilOng);
        txtViewTituloTopo = (TextView) findViewById(R.id.txtViewPerfilOng);

        txtViewTituloTopo.setText("Descrição da ONG");

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("ong");
            vaga = (Vaga) dados.getSerializable("vaga");
        }


        ongDao = new OngDao();
        ongDao.buscarOngTeste(new OngDao.FirebaseCallback() {
            @Override
            public void onCallback(Ong ong) {
                txtNomeOng.setText(ong.getNome());
                txtCnpj.setText(ong.getCpnj());
                txtLocalizacao.setText(ong.getLocalizacao());
                txtCausas.setText(ong.getCausas());
                txtTelefone.setText(ong.getTelefone());
                txtSite.setText(ong.getSite());
                txtEmail.setText(ong.getEmailOng());
                txtResumoOng.setText(ong.getResumoOng());


            }
        }, vaga.getIdOng());
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