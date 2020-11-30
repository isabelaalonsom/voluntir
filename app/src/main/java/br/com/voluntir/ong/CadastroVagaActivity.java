package br.com.voluntir.ong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class CadastroVagaActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Button botaoConfirmar;
    private Vaga vaga;
    private TextView nome;
    private EditText dataInicio, dataTermino;
    private EditText horario, periodicidade, especialidade, detalheVaga;
    private ControleCadastro controleCadastro;
    private String tabelaBanco= "vaga";
    Ong ong;
    Voluntario voluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vaga);

        getSupportActionBar().hide();

        nome = (TextView) findViewById(R.id.txtViewNomeDaONG);
        especialidade = (EditText) findViewById(R.id.edtTextAreaConhecimento);
        dataInicio = (EditText) findViewById(R.id.edtTextDataInicio);
        dataTermino = (EditText) findViewById(R.id.edtTextDataTermino);
        horario = (EditText) findViewById(R.id.edtTextHorario);
        periodicidade = (EditText) findViewById(R.id.edtTextPeriodicidade);
        detalheVaga = (EditText) findViewById(R.id.edtTextDetalhesVaga);

        //Recuperar os dados vindos de outra activity
        Bundle dados = getIntent().getExtras();
        //String email = dados.getString("email");


        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        nome.setText(autenticacao.getCurrentUser().getDisplayName());
        //mascara para a DataInicio
        SimpleMaskFormatter simpleMaskDataInicio = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataInicio = new MaskTextWatcher(dataInicio,simpleMaskDataInicio);
        dataInicio.addTextChangedListener(maskDataInicio);

        //mascara para a DataTermino
        SimpleMaskFormatter simpleMaskDataTermino = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataTermino = new MaskTextWatcher(dataTermino,simpleMaskDataTermino);
        dataTermino.addTextChangedListener(maskDataTermino);

        //mascara para o Horario
        SimpleMaskFormatter simpleMaskHorario = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher maskHorario = new MaskTextWatcher(horario,simpleMaskHorario);
        horario.addTextChangedListener(maskHorario);

        botaoConfirmar = findViewById(R.id.btnConfirmarVaga);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clicarBotaoConfirmar();
                vaga = new Vaga();
                controleCadastro = new ControleCadastro();

                //pegas os dados digitados
                vaga.setNomeOng(autenticacao.getCurrentUser().getDisplayName());
                vaga.setAreaConhecimento(especialidade.getText().toString());
                vaga.setDataInicio(dataInicio.getText().toString());
                vaga.setDataTermino(dataTermino.getText().toString());
                vaga.setPeriodicidade(periodicidade.getText().toString());
                vaga.setDescricaoVaga(detalheVaga.getText().toString());
                vaga.setHorario(horario.getText().toString());


                //verifica se todos os campos foram preenchidos
                if (especialidade.getText().toString().isEmpty() ||
                        dataInicio.getText().toString().isEmpty() || dataTermino.getText().toString().isEmpty() ||
                         horario.getText().toString().isEmpty() ||
                        detalheVaga.getText().toString().isEmpty() || periodicidade.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //VagaDao vagaDao = new VagaDao();

                    Boolean retorno = controleCadastro.cadastrarVaga(vaga, tabelaBanco, getApplicationContext());
                    //vagaDao.adiciona(vaga,tabelaBanco);

                }
            }
        });
    }


            public void clicarBotaoConfirmar(View view) {
                Toast.makeText(this, "VagaActivity Criada!", Toast.LENGTH_SHORT).show();
            }

            public void limparDadosDoCadastroVaga(View view) {
                nome.setText("Nome da ONG");
                especialidade.setText("");
                dataInicio.setText("");
                dataTermino.setText("");
                periodicidade.setText("");
                detalheVaga.setText("");
                horario.setText("");
            }
        }


