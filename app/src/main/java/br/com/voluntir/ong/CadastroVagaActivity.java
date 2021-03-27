package br.com.voluntir.ong;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.EsqueceuASenhaActivity;
import br.com.voluntir.voluntir.MenuOngActivity;
import br.com.voluntir.voluntir.R;
import br.com.voluntir.voluntir.VagaActivity;

public class CadastroVagaActivity extends AppCompatActivity {

    private Button botaoConfirmar;
    private Vaga vaga;
    private TextView nome;
    private EditText dataInicio, dataTermino;
    private EditText cargaHoraria, periodicidade, especialidade, detalheVaga;
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
        cargaHoraria = (EditText) findViewById(R.id.edtTextCargaHoraria);
        periodicidade = (EditText) findViewById(R.id.edtTextPeriodicidade);
        detalheVaga = (EditText) findViewById(R.id.edtTextDetalhesVaga);

        //Recuperar os dados vindos de outra activity
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            final Ong ong = (Ong) dados.getSerializable("objeto");
            nome.setText(ong.getNome());
        }


        SimpleMaskFormatter simpleMaskDataInicio = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataInicio = new MaskTextWatcher(dataInicio, simpleMaskDataInicio);
        dataInicio.addTextChangedListener(maskDataInicio);

        //mascara para a DataTermino
        SimpleMaskFormatter simpleMaskDataTermino = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataTermino = new MaskTextWatcher(dataTermino, simpleMaskDataTermino);
        dataTermino.addTextChangedListener(maskDataTermino);

        //mascara para o Horario
        SimpleMaskFormatter simpleMaskHorario = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher maskHorario = new MaskTextWatcher(cargaHoraria,simpleMaskHorario);
        cargaHoraria.addTextChangedListener(maskHorario);

        botaoConfirmar = findViewById(R.id.btnConfirmarVaga);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaga = new Vaga();
                controleCadastro = new ControleCadastro();

                //verifica se todos os campos foram preenchidos
                if (especialidade.getText().toString().isEmpty() ||
                        dataInicio.getText().toString().isEmpty() || dataTermino.getText().toString().isEmpty() ||
                        cargaHoraria.getText().toString().isEmpty() ||
                        detalheVaga.getText().toString().isEmpty() || periodicidade.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (ong != null) {
                        vaga.setNomeOng(ong.getNome());
                    }
                    vaga.setAreaConhecimento(especialidade.getText().toString());
                    vaga.setDataInicio(dataInicio.getText().toString());
                    vaga.setDataTermino(dataTermino.getText().toString());
                    vaga.setPeriodicidade(periodicidade.getText().toString());
                    vaga.setDescricaoVaga(detalheVaga.getText().toString());
                    vaga.setCargaHoraria(cargaHoraria.getText().toString());
                    vaga.setIdOng(ong.getIdOng());

                    Boolean retorno = controleCadastro.cadastrarVaga(vaga, tabelaBanco, getApplicationContext());

                }
            }
        });
    }

            public void limparDadosDoCadastroVaga(View view) {
                especialidade.setText("");
                dataInicio.setText("");
                dataTermino.setText("");
                periodicidade.setText("");
                detalheVaga.setText("");
                cargaHoraria.setText("");
            }
        }


