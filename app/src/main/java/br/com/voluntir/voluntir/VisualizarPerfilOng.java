package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;

public class VisualizarPerfilOng extends AppCompatActivity {
    private TextView txtNomeOng;
    private TextView txtCnpj;
    private TextView txtLocalizacao;
    private TextView txtCausas;
    private TextView txtTelefone;
    private TextView txtSite;
    private TextView txtEmail;
    private TextView txtResumoOng;
    private Ong ong;
    private Ong ong2;
    private ControleCadastro controleCadastro;
    private Vaga vaga;
    private OngDao ongDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_ong);
        getSupportActionBar().hide();

        txtNomeOng = (TextView) findViewById(R.id.txtViewNomePerfilOng);
        txtCnpj = (TextView) findViewById(R.id.txtViewCpnjVariavelPerfilOng);
        txtLocalizacao = (TextView) findViewById(R.id.txtViewLocalizaoVariavelPerfilOng);
        txtCausas = (TextView) findViewById(R.id.txtViewCausasVariavelPerfilOng);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavelPerfilOng);
        txtSite = (TextView) findViewById(R.id.txtViewSiteVariavelPerfilOng);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavelPerfilOng);
        txtResumoOng = (TextView) findViewById(R.id.txtViewResumoOngVariavelPerfilOng);

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("ong");
            vaga = (Vaga) dados.getSerializable("vaga");
        }

        /*ControleCadastro controleCadastro = new ControleCadastro();
        controleCadastro.buscarOngTeste(vaga);*/

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


}