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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
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
    String idOng;
    boolean grava = false;
    boolean gravaTermino = false;
    boolean mesdiaok = false;
    boolean mesdiaokTermino = false;
    boolean diaok = false;
    boolean anook = false;
    boolean dataInicioValida = false;
    boolean dataTerminoValida = false;
    int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
    boolean podeGravar = false;

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






                //verifica se todos os campos foram preenchidos
                if (especialidade.getText().toString().isEmpty() ||
                        dataInicio.getText().toString().isEmpty() || dataTermino.getText().toString().isEmpty() ||
                        cargaHoraria.getText().toString().isEmpty() ||
                        detalheVaga.getText().toString().isEmpty() || periodicidade.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (!validarData(dataInicio.getText().toString())) {
                        Toast.makeText(getApplicationContext(),
                                "Data início inválida ",
                                Toast.LENGTH_SHORT).show();
                        dataInicioValida = false;
                    } else {
                        dataInicioValida = true;
                    }
                    if (!validarData(dataTermino.getText().toString())) {
                        Toast.makeText(getApplicationContext(),
                                "Data termino inválida ",
                                Toast.LENGTH_SHORT).show();
                        dataTerminoValida = false;
                    } else {
                        dataTerminoValida = true;
                    }
                    podeGravar = verificarDataMenor();

                    if (dataInicioValida == true && dataTerminoValida == true && podeGravar == true) {
                        verificarDataMenor();

                        VagaDao vagaDao = new VagaDao();
                        vagaDao.buscaVaga(especialidade.getText().toString(), tabelaBanco, getApplicationContext(), new VagaDao.OnGetDataListener() {
                            @Override
                            public void onSucess(Vaga vaga2) {
                                if (vaga2 != null) {
                                    Toast.makeText(getApplicationContext(),
                                            "já existe uma vaga com essa area de conhecimento ",
                                            Toast.LENGTH_SHORT).show();
                                } else {
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
                                    controleVaga = new ControleVaga();
                                    controleVaga.cadastrarVaga(vaga, tabelaBanco, getApplicationContext());
                                }
                            }

                            @Override
                            public void onStart() {

                            }
                        });
                    }
                }
            }
        });
    }



    public boolean validarData(String data) {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int ano = 0;
        String dataConfig = data;
        if (dataConfig.length() == 10) {
            ano = Integer.parseInt(dataConfig.substring(6, 10));
        }


        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(data);
            if (ano < anoAtual) {
                Toast.makeText(getApplicationContext(),
                        "ano inválido",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (ParseException ex) {
            Toast.makeText(getApplicationContext(),
                    "exception" + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

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

    public boolean verificarDataMenor() {
        boolean podeGravar = false;
        int diaInicio = 0;
        int mesInicio = 0;
        int anoInicio = 0;
        String dataInicioConfig = dataInicio.getText().toString();
        if (dataInicioConfig.length() == 10) {
            diaInicio = Integer.parseInt(dataInicioConfig.substring(0, 2));
            mesInicio = Integer.parseInt(dataInicioConfig.substring(3, 5));
            anoInicio = Integer.parseInt(dataInicioConfig.substring(6, 10));
        }
        int diaTermino = 0;
        int mesTermino = 0;
        int anoTermino = 0;

        String dataTerminoConfig = dataTermino.getText().toString();
        if (dataTerminoConfig.length() == 10) {
            diaTermino = Integer.parseInt(dataTerminoConfig.substring(0, 2));
            mesTermino = Integer.parseInt(dataTerminoConfig.substring(3, 5));
            anoTermino = Integer.parseInt(dataTerminoConfig.substring(6, 10));
        }

        if (anoInicio > anoTermino) {
            Toast.makeText(getApplicationContext(), "Ano início não pode ser maior que ano término ", Toast.LENGTH_SHORT).show();
        } else if (anoInicio == anoTermino) {
            if (mesInicio > mesTermino) {
                Toast.makeText(getApplicationContext(), "mês início não pode ser maior que mês término ", Toast.LENGTH_SHORT).show();
            } else if (mesInicio == mesTermino) {
                if (diaInicio > diaTermino) {
                    Toast.makeText(getApplicationContext(), "dia início não pode ser maior que dia término ", Toast.LENGTH_SHORT).show();
                } else {
                    podeGravar = true;
                }
            } else
                podeGravar = true;
        } else {
            podeGravar = true;
        }
        return podeGravar;
    }

    /*public void verificarMesInicio() {
        mesdiaok = false;
        int diaInicio = 0;
        int mesInicio = 0;
        int anoInicio = 0;

        String dataInicioConfig = dataInicio.getText().toString();
        if (dataInicioConfig.length() == 10) {
            diaInicio = Integer.parseInt(dataInicioConfig.substring(0, 2));
            mesInicio = Integer.parseInt(dataInicioConfig.substring(3, 5));
            anoInicio = Integer.parseInt(dataInicioConfig.substring(6, 10));

            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

            if (mesInicio < 1 || mesInicio > 12) {
                Toast.makeText(getApplicationContext(), "Mês início inválido ", Toast.LENGTH_SHORT).show();
                mesdiaok = false;
            } else if (mesInicio == 4 || mesInicio == 6 || mesInicio == 9 || mesInicio == 11) {
                if (diaInicio > 30 || diaInicio < 1) {
                    Toast.makeText(getApplicationContext(), "Dia início inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaok = false;
                } else {
                    mesdiaok = true;
                }
            } else if (mesInicio == 2) {
                if (diaInicio > 28 || diaInicio < 1) {
                    Toast.makeText(getApplicationContext(), "Dia início inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaok = false;
                } else {
                    mesdiaok = true;
                }
            } else if (mesInicio == 1 || mesInicio == 3 || mesInicio == 5 || mesInicio == 7 || mesInicio == 8 || mesInicio == 10 || mesInicio == 12) {
                if (diaInicio > 31 || diaInicio < 1) {
                    Toast.makeText(getApplicationContext(), "Dia início inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaok = false;
                } else {
                    mesdiaok = true;
                }
            } else if (anoInicio < anoAtual) {
                Toast.makeText(getApplicationContext(), "Ano início inválido ", Toast.LENGTH_SHORT).show();
                mesdiaok = false;
            } else {
                mesdiaok = true;
            }

            if (mesdiaok) {
                if (anoInicio < anoAtual) {
                    Toast.makeText(getApplicationContext(), "Ano início inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaok = false;
                } else {
                    grava = true;
                }
            }
        }
    }

    public void verificarMesFinal() {
        mesdiaokTermino = false;
        int diaTermino = 0;
        int mesTermino = 0;
        int anoTermino = 0;

        String dataTerminoConfig = dataTermino.getText().toString();
        if (dataTerminoConfig.length() == 10) {
            diaTermino = Integer.parseInt(dataTerminoConfig.substring(0, 2));
            mesTermino = Integer.parseInt(dataTerminoConfig.substring(3, 5));
            anoTermino = Integer.parseInt(dataTerminoConfig.substring(6, 10));

            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

            if (mesTermino < 1 || mesTermino > 12) {
                Toast.makeText(getApplicationContext(), "Mês termino inválido ", Toast.LENGTH_SHORT).show();
                mesdiaokTermino = false;
            } else if (mesTermino == 4 || mesTermino == 6 || mesTermino == 9 || mesTermino == 11) {
                if (diaTermino > 30 || diaTermino < 1) {
                    Toast.makeText(getApplicationContext(), "Dia termino inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaokTermino = false;
                } else {
                    mesdiaokTermino = true;
                }
            } else if (mesTermino == 2) {
                if (diaTermino > 28 || diaTermino < 1) {
                    Toast.makeText(getApplicationContext(), "Dia termino inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaokTermino = false;
                } else {
                    mesdiaokTermino = true;
                }
            } else if (mesTermino == 1 || mesTermino == 3 || mesTermino == 5 || mesTermino == 7 || mesTermino == 8 || mesTermino == 10 || mesTermino == 12) {
                if (diaTermino > 31 || diaTermino < 1) {
                    Toast.makeText(getApplicationContext(), "Dia termino inválido ", Toast.LENGTH_SHORT).show();
                    mesdiaokTermino = false;
                } else {
                    mesdiaokTermino = true;
                }
            } else if (anoTermino < anoAtual) {
                Toast.makeText(getApplicationContext(), "Ano termino inválido ", Toast.LENGTH_SHORT).show();
                mesdiaokTermino = false;
            } else {
                mesdiaokTermino = true;
            }

            if (mesdiaokTermino = true) {

                gravaTermino = true;

            }
        }
    }*/
}


