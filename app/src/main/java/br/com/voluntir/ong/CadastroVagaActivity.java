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

import java.util.Calendar;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class CadastroVagaActivity extends AppCompatActivity {

    private Button botaoConfirmar;
    private Vaga vaga;
    private TextView nome;
    private EditText dataInicio, dataTermino;
    private EditText cargaHoraria, periodicidade, especialidade, detalheVaga, qtdCandidatos;
    private ControleCadastro controleCadastro;
    private String tabelaBanco = "vaga";
    private ControleVaga controleVaga;
    Ong ong;
    Voluntario voluntario;
    String idOng;
    boolean grava = false;
    boolean mesdiaok = false;

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
        qtdCandidatos = (EditText) findViewById(R.id.edtTextQtdCandidatos);

        //Recuperar os dados vindos de outra activity
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
            nome.setText(ong.getNome());
            idOng = ong.getIdOng();
            Toast.makeText(getApplicationContext(),
                    "Id " + ong.getIdOng(),
                    Toast.LENGTH_SHORT).show();
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

                int diaInicio = 0;
                int mesInicio = 0;
                int anoInicio = 0;

                int diaTermino = 0;
                int mesTermino = 0;
                int anoTermino = 0;

                String dataInicioConfig = dataInicio.getText().toString();
                if (dataInicioConfig != null) {
                    diaInicio = Integer.parseInt(dataInicioConfig.substring(0, 2));
                    mesInicio = Integer.parseInt(dataInicioConfig.substring(3, 5));
                    anoInicio = Integer.parseInt(dataInicioConfig.substring(6, 10));
                }

                String dataTerminoConfig = dataTermino.getText().toString();
                if (dataTerminoConfig != null) {
                    diaTermino = Integer.parseInt(dataTerminoConfig.substring(0, 2));
                    mesTermino = Integer.parseInt(dataTerminoConfig.substring(3, 5));
                    anoTermino = Integer.parseInt(dataTerminoConfig.substring(6, 10));
                }

                int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

                //verifica se todos os campos foram preenchidos
                if (especialidade.getText().toString().isEmpty() ||
                        dataInicio.getText().toString().isEmpty() || dataTermino.getText().toString().isEmpty() ||
                        cargaHoraria.getText().toString().isEmpty() ||
                        detalheVaga.getText().toString().isEmpty() || periodicidade.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else if (anoInicio > anoTermino) {
                    Toast.makeText(getApplicationContext(), "A data de início deve ser menor do que a data de término da Vaga", Toast.LENGTH_SHORT).show();
                } else if ((mesInicio < 1 || mesInicio > 12) || (mesTermino < 1 || mesTermino > 12)) {
                    Toast.makeText(getApplicationContext(),"Mês inválido ", Toast.LENGTH_SHORT).show();
                } else if ((mesInicio == 4 || mesInicio == 6 || mesInicio == 9 || mesInicio == 11) ||
                        (mesTermino == 4 || mesTermino == 6 || mesTermino == 9 || mesTermino == 11)) {
                    if ((diaInicio > 30 || diaInicio < 1) || (diaTermino > 30 || diaTermino < 1)) {
                        Toast.makeText(getApplicationContext(),"Dia inválido ",Toast.LENGTH_SHORT).show();
                    } else {
                        mesdiaok = true;
                    }
                } else if ((mesInicio == 2) || (mesTermino == 2)) {
                    if ((diaInicio > 28 || diaInicio < 1) || (diaTermino > 28 || diaTermino < 1)) {
                        Toast.makeText(getApplicationContext(), "Dia inválido ", Toast.LENGTH_SHORT).show();
                    } else {
                        mesdiaok = true;
                    }
                } else if ((mesInicio == 1 || mesInicio == 3 || mesInicio == 5 || mesInicio == 7 || mesInicio == 8 || mesInicio == 10 || mesInicio == 12) ||
                        (mesTermino == 1 || mesTermino == 3 ||mesTermino == 5 || mesTermino == 7 || mesTermino == 8 || mesTermino == 10 || mesTermino == 12)) {
                    if ((diaInicio > 31 || diaInicio < 1) || (diaTermino > 31 || diaTermino < 1)) {
                        Toast.makeText(getApplicationContext(), "Dia inválido ", Toast.LENGTH_SHORT).show();
                    } else {
                        mesdiaok = true;
                    }
                } else {
                    mesdiaok = true;
                }

                if (mesdiaok) {
//                    if (anoInicio > anoTermino) {
//                        Toast.makeText(getApplicationContext(), "A data de início deve ser menor do que a data de término da Vaga", Toast.LENGTH_SHORT).show();
//                    } else {
                        grava = true;
                    //}
                }

                if (grava) {
                    if (ong != null) {
                        vaga.setNomeOng(ong.getNome());
                        vaga.setIdOng(ong.getIdOng());
                    }
                    vaga.setAreaConhecimento(especialidade.getText().toString());
                    vaga.setDataInicio(dataInicio.getText().toString());
                    vaga.setDataTermino(dataTermino.getText().toString());
                    vaga.setPeriodicidade(periodicidade.getText().toString());
                    vaga.setDescricaoVaga(detalheVaga.getText().toString());
                    vaga.setCargaHoraria(cargaHoraria.getText().toString());
                    vaga.setQtdCandidaturas(Integer.parseInt(qtdCandidatos.getText().toString()));
                    /*Toast.makeText(getApplicationContext(),
                            "Nome Ong "+vaga.getNomeOng(),
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),
                            "Id Ong "+vaga.getIdOng(),
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),
                            "Data Inicio "+vaga.getDataInicio(),
                            Toast.LENGTH_SHORT).show();*/

                    controleVaga = new ControleVaga();
                    controleVaga.cadastrarVaga(vaga, tabelaBanco, getApplicationContext());
                }
            }
        });
    }

    public void CadastrarVaga() {

    }

    public void limparDadosDoCadastroVaga(View view) {
        especialidade.setText("");
        dataInicio.setText("");
        dataTermino.setText("");
        periodicidade.setText("");
        detalheVaga.setText("");
        cargaHoraria.setText("");
        qtdCandidatos.setText("");
    }
}


