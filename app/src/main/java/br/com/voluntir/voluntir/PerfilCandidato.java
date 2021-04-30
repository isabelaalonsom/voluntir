package br.com.voluntir.voluntir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class PerfilCandidato extends AppCompatActivity {
    private List<Voluntario> listaVoluntario = new ArrayList<>();
    Voluntario voluntario;
    Vaga vaga;
    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtCpf;
    private TextView txtDataNasc;
    private TextView txtEmail;
    private TextView txtTelefone;
    private TextView txtEndereco;
    private TextView txtGenero;
    private TextView txtDescricaoTecnica;
    private TextView txtStatus;
    private String nomeTabelaVaga = "vaga";
    ControleCadastro controleCadastro;
    private ControleVaga controleVaga;
    String tabelaVoluntario = "voluntario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_candidato);

        txtNome = (TextView) findViewById(R.id.txtViewNomeDoVoluntarioVagaVariavel);
        txtSobrenome = (TextView) findViewById(R.id.txtViewSobrenomeVoluntarioVaga);
        txtCpf = (TextView) findViewById(R.id.txtViewCpfVoluntarioVaga);
        txtDataNasc = (TextView) findViewById(R.id.txtViewDataNascVoluntarioVaga);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVoluntarioVaga);
        txtEndereco = (TextView) findViewById(R.id.txtViewEnderecoVoluntarioVaga);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVoluntarioVaga);
        txtGenero = (TextView) findViewById(R.id.txtViewGeneroVoluntarioVaga);
        txtDescricaoTecnica = (TextView) findViewById(R.id.txtViewDescricaoTecnicaVoluntarioVaga);
        txtStatus = (TextView) findViewById(R.id.txtViewStatusVoluntarioVaga);

        limparCampos();

        Bundle dados = getIntent().getExtras();
        Bundle dadosVoluntario = getIntent().getExtras();

        if (dados != null) {
            voluntario = (Voluntario) dadosVoluntario.getSerializable("voluntario");
            vaga = (Vaga) dados.getSerializable("objeto");

            if (voluntario != null) {
                txtNome.setText(voluntario.getNome());
                txtSobrenome.setText(voluntario.getSobrenome());
                txtCpf.setText(voluntario.getCpf());
                txtDataNasc.setText(voluntario.getDatanasc());
                txtEmail.setText(voluntario.getEmail());
                txtEndereco.setText(voluntario.getEndereco());
                txtTelefone.setText(voluntario.getTelefone());
                txtGenero.setText(voluntario.getGenero());
                txtDescricaoTecnica.setText(voluntario.getEspecialidade());
                txtStatus.setText(voluntario.getStatusVaga());
            }
        }

    }

    public void atualizarPerfil() {

    }

    public void clicarBotaoAprovar(View view) {
        listaVoluntario.clear();
        if (vaga.getVoluntarios() != null) {
            for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                Voluntario voluntario2 = vaga.getVoluntarios().get(i);

                if (voluntario2.getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                    voluntario2.setStatusVaga("APROVADO");
                }
                listaVoluntario.add(voluntario2);
            }
        }
        vaga.setVoluntarios(listaVoluntario);
        controleVaga = new ControleVaga();
        //voluntario.setStatusVaga("aprovado");

        controleVaga.atualizaVagaVoluntario(vaga, nomeTabelaVaga, getApplicationContext());

    }

    public void clicarBotaoReprovar(View view) {
        listaVoluntario.clear();
        if (vaga.getVoluntarios() != null) {
            for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                Voluntario voluntario2 = vaga.getVoluntarios().get(i);

                if (voluntario2.getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                    voluntario2.setStatusVaga("REPROVADO");
                }
                listaVoluntario.add(voluntario2);
            }
        } else {
            listaVoluntario.add(voluntario);
        }

        vaga.setVoluntarios(listaVoluntario);
        controleVaga = new ControleVaga();
        //voluntario.setStatusVaga("aprovado");

        controleVaga.atualizaVagaVoluntario(vaga, nomeTabelaVaga, getApplicationContext());

    }

    public void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtCpf.setText("");
        txtDataNasc.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtGenero.setText("");
        txtDescricaoTecnica.setText("");
        txtStatus.setText("");
    }
}