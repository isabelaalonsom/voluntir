package br.com.voluntir.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import br.com.voluntir.ong.CadastroONGActivity;
import br.com.voluntir.voluntir.R;


public class MinhaContaVoluntarioActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtCpf;
    private TextView txtDataNasc;
    private TextView txtEmail;
    private TextView txtTelefone;
    private TextView txtEndereco;
    private TextView txtGenero;
    private TextView txtDescricaoTecnica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_voluntario);

        getSupportActionBar().hide();

        txtNome = findViewById(R.id.txtViewNomeDoVoluntarioVariavel);
        txtSobrenome = findViewById(R.id.txtViewSobrenomeVariavel);
        txtCpf = findViewById(R.id.txtViewCpfVariavel);
        txtDataNasc = findViewById(R.id.txtViewDataNascVariavel);
        txtEmail = findViewById(R.id.txtViewEmailVariavel);
        txtEndereco = findViewById(R.id.txtViewEnderecoVariavel);
        txtTelefone = findViewById(R.id.txtViewTelefoneVariavel);
        txtGenero = findViewById(R.id.txtViewGeneroVariavel);
        txtDescricaoTecnica = findViewById(R.id.txtViewDescricaoTecnicaVariavel);

    }

    public void clicarBotaoEditar (View view) {

        String nome = txtNome.getText().toString();
        String sobrenome = txtSobrenome.getText().toString();
        String cpf = txtCpf.getText().toString();
        String dataNasc = txtDataNasc.getText().toString();
        String email = txtEmail.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String genero = txtGenero.getText().toString();
        String endereco = txtEndereco.getText().toString();
        String descricaoTecnica = txtDescricaoTecnica.getText().toString();

        Intent intent = new Intent(this, CadastroVoluntarioActivity.class);
        Bundle parametros = new Bundle();

        parametros.putString("chave_nome", nome);
        parametros.putString("chave_sobrenome", sobrenome);
        parametros.putString("chave_cpf", cpf);
        parametros.putString("chave_dataNasc", dataNasc);
        parametros.putString("chave_email", email);
        parametros.putString("chave_telefone", telefone);
        parametros.putString("chave_endereco", endereco);
        parametros.putString("chave_genero", genero);
        parametros.putString("chave_descricaoTecnica", descricaoTecnica);

        intent.putExtras(parametros);

        startActivity(intent);
    }

    public void clicarBotaoSair (View view) {
        this.finishAffinity();
    }


}