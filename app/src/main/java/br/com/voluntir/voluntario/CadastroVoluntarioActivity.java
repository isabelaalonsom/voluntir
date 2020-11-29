package br.com.voluntir.voluntario;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;


public class CadastroVoluntarioActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private Button botaoConfirmar;
    private Voluntario voluntario;
    private EditText email, senha,cpf,data,nome,telefone;
    private EditText endereco,especialidade,sobrenome;
    private Spinner generoSelecionado;
    private ControleCadastro controleCadastro;
    private String tabelaBanco= "voluntario";
    private RadioButton radioButtonFeminino;
    private RadioButton radioButtonMasculino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_voluntario);

        getSupportActionBar().hide();
        email = (EditText) findViewById(R.id.edtTextEmail);
        senha = (EditText) findViewById(R.id.edtTextSenha);
        cpf = (EditText) findViewById(R.id.edtTextCpf);
        nome = (EditText) findViewById(R.id.edtTextNome);
        sobrenome = (EditText) findViewById(R.id.edtTextSobrenome);
        data = (EditText) findViewById(R.id.edtTextDataNasc);
        telefone = (EditText) findViewById(R.id.edtTextTel);
        endereco = (EditText) findViewById(R.id.edtTextEndereco);
        especialidade = (EditText) findViewById(R.id.edtTextDescricao);
        radioButtonFeminino = findViewById(R.id.rdBtnFeminino);
        radioButtonMasculino = findViewById(R.id.rdBtnMasculino);

        //mascara para o Cpf
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(cpf,simpleMaskCpf);
        cpf.addTextChangedListener(maskCpf);

        //mascara para a Data
        SimpleMaskFormatter simpleMaskData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskData = new MaskTextWatcher(data,simpleMaskData);
        data.addTextChangedListener(maskData);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        botaoConfirmar = findViewById(R.id.btnConfirmar);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clicarBotaoConfirmar();
                voluntario = new Voluntario();
                controleCadastro = new ControleCadastro();

                //pegas os dados digitados
                voluntario.setEmail(email.getText().toString());
                voluntario.setSenha(senha.getText().toString());
                voluntario.setCpf(cpf.getText().toString());
                voluntario.setNome(nome.getText().toString());
                voluntario.setEspecialidade(especialidade.getText().toString());
                voluntario.setTelefone(telefone.getText().toString());
                voluntario.setEndereco(endereco.getText().toString());
                voluntario.setDatanasc(data.getText().toString());
                voluntario.setSobrenome(sobrenome.getText().toString());
                if (radioButtonFeminino.isSelected()){

                    voluntario.setGenero("F");
                }else if (radioButtonMasculino.isSelected()){
                    voluntario.setGenero("M");
                }

                //verifica se todos os campos foram preenchidos
                if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() ||
                        cpf.getText().toString().isEmpty() || nome.getText().toString().isEmpty() ||
                        especialidade.getText().toString().isEmpty() || telefone.getText().toString().isEmpty() ||
                        endereco.getText().toString().isEmpty() || data.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                }else{
                    //VoluntarioDao voluntarioDao = new VoluntarioDao();

                    Boolean retorno =controleCadastro.cadastrarVoluntario(voluntario,tabelaBanco,getApplicationContext());
                    //voluntarioDao.adiciona(voluntario,tabelaBanco);

                }

            }
        });
    }

    public void clicarBotaoConfirmar() {

        Toast.makeText(this, "Cadastro criado do Voluntário!", Toast.LENGTH_SHORT).show();
        //aqui tem que jogar pro banco de dados os edit text preenchidos
    }

    public void clicarBotaoLimpar(View view) {

        //aqui fazer a programacao para limpar os Edit Text
    }
}