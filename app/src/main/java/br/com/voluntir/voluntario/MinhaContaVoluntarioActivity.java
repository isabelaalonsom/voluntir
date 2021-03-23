package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroONGActivity;
import br.com.voluntir.voluntir.R;


public class MinhaContaVoluntarioActivity extends AppCompatActivity {
    Voluntario voluntario;
    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtCpf;
    private TextView txtDataNasc;
    private TextView txtEmail;
    private TextView txtTelefone;
    private TextView txtEndereco;
    private TextView txtGenero;
    private TextView txtDescricaoTecnica;
    ControleCadastro controleCadastro;
    String tabelaVoluntario = "voluntario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_voluntario);

        getSupportActionBar().hide();

        txtNome = (TextView) findViewById(R.id.txtViewNomeDoVoluntarioVariavel);
        txtSobrenome = (TextView) findViewById(R.id.txtViewSobrenomeVariavel);
        txtCpf = (TextView) findViewById(R.id.txtViewCpfVariavel);
        txtDataNasc = (TextView) findViewById(R.id.txtViewDataNascVariavel);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavel);
        txtEndereco = (TextView) findViewById(R.id.txtViewEnderecoVariavel);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavel);
        txtGenero = (TextView) findViewById(R.id.txtViewGeneroVariavel);
        txtDescricaoTecnica = (TextView) findViewById(R.id.txtViewDescricaoTecnicaVariavel);
        limparCampos();
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("objeto");
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
            }
        }


    }

    public void clicarBotaoEditar(View view) {

        if (txtNome.getText().toString().isEmpty() || txtCpf.getText().toString().isEmpty() || txtDataNasc.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() || txtEndereco.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty() ||
                txtGenero.getText().toString().isEmpty() || txtDescricaoTecnica.getText().toString().isEmpty() || txtSobrenome.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else {
            Voluntario dados = new Voluntario();
            if (voluntario != null) {
                dados.setIdVoluntario(voluntario.getIdVoluntario());
            }

            dados.setNome(txtNome.getText().toString());
            dados.setSobrenome(txtSobrenome.getText().toString());
            dados.setCpf(txtCpf.getText().toString());
            dados.setDatanasc(txtDataNasc.getText().toString());
            dados.setEmail(txtEmail.getText().toString());
            dados.setEndereco(txtEndereco.getText().toString());
            dados.setTelefone(txtTelefone.getText().toString());
            dados.setGenero(txtGenero.getText().toString());
            dados.setEspecialidade(txtDescricaoTecnica.getText().toString());

            controleCadastro = new ControleCadastro();
            controleCadastro.atualizarDadosOng(dados, tabelaVoluntario, getApplicationContext());
        }
    }

    public void clicarBotaoExcluir(View view) {
        Voluntario dados = new Voluntario();
        if (voluntario != null) {
            dados.setIdVoluntario(voluntario.getIdVoluntario());
        }
        dados.setNome(txtNome.getText().toString());
        dados.setSobrenome(txtSobrenome.getText().toString());
        dados.setCpf(txtCpf.getText().toString());
        dados.setDatanasc(txtDataNasc.getText().toString());
        dados.setEmail(txtEmail.getText().toString());
        dados.setEndereco(txtEndereco.getText().toString());
        dados.setTelefone(txtTelefone.getText().toString());
        dados.setGenero(txtGenero.getText().toString());
        dados.setEspecialidade(txtDescricaoTecnica.getText().toString());

        controleCadastro = new ControleCadastro();
        if (dados != null) {
            controleCadastro.excluirDadosVoluntario(dados, tabelaVoluntario, getApplicationContext());
        }


    }

    public void clicarBotaoSair(View view) {
        Preferencias preferencias = new Preferencias(getApplicationContext());
        preferencias.salvarUsuarioPreferencias(null, null, null);
        this.finishAffinity();

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
    }


}