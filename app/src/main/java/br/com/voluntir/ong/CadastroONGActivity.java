package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.voluntir.R;

public class CadastroONGActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private Button botaoConfirmar;
    private Ong ong;
    private EditText email,senha,cnpj,resumo;
    private EditText nome,telefone,endereco;
    private EditText causa,localizacao,site;
    private ControleCadastro controleCadastro;
    private String tabelaBanco= "ong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);

        getSupportActionBar().hide();
        email = (EditText) findViewById(R.id.edtTextEmail);
        senha = (EditText) findViewById(R.id.edtTextSenha);
        cnpj = (EditText) findViewById(R.id.edtTextCpnj);
        nome = (EditText) findViewById(R.id.edtTextNome);
        telefone = (EditText) findViewById(R.id.edtTextTelefone);
        localizacao = (EditText) findViewById(R.id.edtTextLocalizacao);
        causa = (EditText) findViewById(R.id.edtTextCausas);
        site = (EditText) findViewById(R.id.edtTextSite);
        resumo = (EditText) findViewById(R.id.edtTextResumoOng);

        //mascara para o Cnpj
        SimpleMaskFormatter simpleMaskCnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher maskCnpj = new MaskTextWatcher(cnpj,simpleMaskCnpj);
        cnpj.addTextChangedListener(maskCnpj);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        botaoConfirmar = findViewById(R.id.confirmarBtn);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clicarBotaoConfirmar();
                controleCadastro = new ControleCadastro();

                //pegas os dados digitados
                ong = new Ong();
                ong.setEmailOng(email.getText().toString());
                ong.setSenhaOng(senha.getText().toString());
                ong.setCpnj(cnpj.getText().toString());
                ong.setNome(nome.getText().toString());
                ong.setCausas(causa.getText().toString());
                ong.setTelefone(telefone.getText().toString());
                ong.setLocalizacao(localizacao.getText().toString());
                ong.setResumoOng(resumo.getText().toString());

                //verifica se não tem campos em branco
                if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() ||
                        cnpj.getText().toString().isEmpty() || nome.getText().toString().isEmpty() ||
                        causa.getText().toString().isEmpty() || telefone.getText().toString().isEmpty() ||
                        localizacao.getText().toString().isEmpty() || resumo.getText().toString().isEmpty()){

                    //exibe mensagem na tela
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                }else{

                    Boolean retorno =controleCadastro.cadastrarOng(ong,tabelaBanco,getApplicationContext());

                }

            }
        });


    }
    
    public void clicarBotaoConfirmar(View view) {
        Toast.makeText(this, "Cadastro Criado!", Toast.LENGTH_SHORT).show();
        //aqui tem que jogar pro banco de dados os edit text preenchidos
    }

    public void limparDados(View view) {
        nome.setText("");
        cnpj.setText("");
        localizacao.setText("");
        causa.setText("");
        telefone.setText("");
        site.setText("");
        email.setText("");
        senha.setText("");
        resumo.setText("");
    }

}