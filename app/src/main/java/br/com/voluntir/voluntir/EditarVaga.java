package br.com.voluntir.voluntir;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Calendar;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;

public class EditarVaga extends AppCompatActivity {

    private final String tabelaBanco = "vaga";
    private Ong ong;
    private boolean grava = false, mesdiaok = false;
    private Button botaoConfirmar;
    private Vaga vaga;

    private TextView nome;
    private EditText cargaHoraria, periodicidade, especialidade, detalheVaga, qtdCandidatos, dataInicio, dataTermino;

    private TextView txtTituloNovaVagaEditarVaga;
    private ControleCadastro controleCadastro;

    private ControleVaga controleVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vaga);

        getSupportActionBar().hide();

        nome = (TextView) findViewById(R.id.txtViewNomeDaONGEditarVaga);
        especialidade = (EditText) findViewById(R.id.edtTextAreaConhecimentoEditarVaga);
        dataInicio = (EditText) findViewById(R.id.edtTextDataInicioEditarVaga);
        dataTermino = (EditText) findViewById(R.id.edtTextDataTerminoEditarVaga);
        cargaHoraria = (EditText) findViewById(R.id.edtTextCargaHorariaEditarVaga);
        periodicidade = (EditText) findViewById(R.id.edtTextPeriodicidadeEditarVaga);
        detalheVaga = (EditText) findViewById(R.id.edtTextDetalhesVagaEditarVaga);
        qtdCandidatos = (EditText) findViewById(R.id.edtTextQtdCandidatosEditarVaga);
        txtTituloNovaVagaEditarVaga = findViewById(R.id.txtViewTituloNovaVagaEditarVaga);

        txtTituloNovaVagaEditarVaga.setText("Editar Vaga");


        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            ong = (Ong) dados.getSerializable("ong");
            vaga = (Vaga) dados.getSerializable("vaga");
        }

        if (vaga != null) {
            nome.setText(vaga.getNomeOng());
            especialidade.setText(vaga.getAreaConhecimento());
            dataInicio.setText(vaga.getDataInicio());
            dataTermino.setText(vaga.getDataTermino());
            cargaHoraria.setText(vaga.getCargaHoraria());
            periodicidade.setText(vaga.getPeriodicidade());
            detalheVaga.setText(vaga.getDescricaoVaga());
            qtdCandidatos.setText(Integer.toString(vaga.getQtdCandidaturas()));
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
        MaskTextWatcher maskHorario = new MaskTextWatcher(cargaHoraria, simpleMaskHorario);
        cargaHoraria.addTextChangedListener(maskHorario);

        botaoConfirmar = findViewById(R.id.btnConfirmarVagaEditarVaga);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    Toast.makeText(getApplicationContext(), "Mês inválido ", Toast.LENGTH_SHORT).show();
                } else if ((mesInicio == 4 || mesInicio == 6 || mesInicio == 9 || mesInicio == 11) ||
                        (mesTermino == 4 || mesTermino == 6 || mesTermino == 9 || mesTermino == 11)) {
                    if ((diaInicio > 30 || diaInicio < 1) || (diaTermino > 30 || diaTermino < 1)) {
                        Toast.makeText(getApplicationContext(), "Dia inválido ", Toast.LENGTH_SHORT).show();
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
                        (mesTermino == 1 || mesTermino == 3 || mesTermino == 5 || mesTermino == 7 || mesTermino == 8 || mesTermino == 10 || mesTermino == 12)) {
                    if ((diaInicio > 31 || diaInicio < 1) || (diaTermino > 31 || diaTermino < 1)) {
                        Toast.makeText(getApplicationContext(), "Dia inválido ", Toast.LENGTH_SHORT).show();
                    } else {
                        mesdiaok = true;
                    }
                } else {
                    mesdiaok = true;
                }

                if (mesdiaok) {
                    grava = true;
                }

                if (grava) {
                    if (ong != null) {
                        Vaga vaga2 = vaga;
                        vaga2.setNomeOng(ong.getNome());
                        vaga2.setIdOng(ong.getIdOng());
                        vaga2.setAreaConhecimento(especialidade.getText().toString());
                        vaga2.setDataInicio(dataInicio.getText().toString());
                        vaga2.setDataTermino(dataTermino.getText().toString());
                        vaga2.setPeriodicidade(periodicidade.getText().toString());
                        vaga2.setDescricaoVaga(detalheVaga.getText().toString());
                        vaga2.setCargaHoraria(cargaHoraria.getText().toString());
                        vaga2.setQtdCandidaturas(Integer.parseInt(qtdCandidatos.getText().toString()));
                        if (vaga2.getVoluntarios() != null)
                            vaga2.setVoluntarios(vaga.getVoluntarios());
                        controleVaga = new ControleVaga();
                        controleVaga.atualizaVagaOng(vaga2, tabelaBanco, getApplicationContext());
                    }

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
        qtdCandidatos.setText("");
    }

    public void excluirVaga(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Excluir Vaga");
        dialog.setMessage("Deseja excluir vaga?");

        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (vaga != null) {
                    controleVaga = new ControleVaga();
                    controleVaga.deletarVaga(vaga, tabelaBanco, getApplicationContext());
                }

            }
        });

        dialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Exclusão cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.create();
        dialog.show();

    }


}