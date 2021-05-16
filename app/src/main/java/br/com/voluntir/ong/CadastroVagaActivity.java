package br.com.voluntir.ong;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.R;

public class CadastroVagaActivity extends AppCompatActivity {
    private DatabaseReference refenciaBanco;
    private Button botaoConfirmar;
    private Vaga vaga;
    private TextView nome;
    private EditText dataInicio, dataTermino;
    private EditText cargaHoraria, periodicidade, especialidade, detalheVaga, qtdCandidatos;
    private String tabelaBanco = "vaga";
    private ControleVaga controleVaga;
    private Ong ong;
    private String idOng;
    boolean entrou = false;
    boolean dataInicioValida = false;
    boolean dataTerminoValida = false;
    int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
    boolean podeGravar = false;
    boolean existe = false;
    private List<Vaga> listaVaga = new ArrayList<>();

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
        refenciaBanco = BancoFirebase.getBancoReferencia();
        Query pesquisa = refenciaBanco.child(tabelaBanco).orderByChild("idOng").equalTo(ong.getIdOng());
        pesquisa.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaVaga.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                    if (vaga.getIdOng().equals(ong.getIdOng())) {
                        listaVaga.add(vaga);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

//        String dataDeHoje = java.text.DateFormat.getDateTimeInstance().format(new Date());
////        Log.e("DATADEHOJE: ", " " + dataDeHoje);
////
////        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
////        int mesAtual = Calendar.getInstance().get(Calendar.MONTH);
////        int diaAtual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
////
////        Log.e("ANO: ", " " + anoAtual);
////        Log.e("MES: ", " " + mesAtual);
////        Log.e("DIA: ", " " + diaAtual);


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
                        entrou = false;

                        if (listaVaga != null) {
                            entrou = true;
                            existe = false;
                            for (int i = 0; i < listaVaga.size(); i++) {
                                if (listaVaga.get(i).getAreaConhecimento().equalsIgnoreCase(especialidade.getText().toString())) {
                                    existe = true;
                                }
                            }
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
                        if (existe == true && entrou == true) {
                            Toast.makeText(getApplicationContext(),
                                    "Nome da vaga ja existente ",
                                    Toast.LENGTH_SHORT).show();
                        } else if (entrou == true && existe == false) {
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
            /*Toast.makeText(getApplicationContext(),
                    "exception" + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();*/
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
        boolean fazOutraVerificacao = false;
        boolean agoraVerificaDataTermino = false;
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

        int diaHoje = 0;
        int mesHoje = 0;
        int anoHoje = 0;

        // CONFIGURANDO DATA ATUAL PARA DATA DE INICIO
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataFormatadaHoje = formataData.format(data);
        Log.e("DATADEHOJE2: ", " " + dataFormatadaHoje);

        String dataAtualConfig = dataFormatadaHoje;

        if (dataAtualConfig.length() == 10) {
            diaHoje = Integer.parseInt(dataAtualConfig.substring(0, 2));
            mesHoje = Integer.parseInt(dataAtualConfig.substring(3, 5));
            anoHoje = Integer.parseInt(dataAtualConfig.substring(6, 10));
        }

        if (anoInicio < anoHoje) {
            Toast.makeText(getApplicationContext(), "O ano da data de início da vaga é muito antigo. Coloque um ano mais atual.", Toast.LENGTH_SHORT).show();
        } else if (anoInicio == anoHoje) {
            if (mesInicio < mesHoje) {
                Toast.makeText(getApplicationContext(), "O mês da data de início da vaga é muito antigo. Coloque um mês mais atual.", Toast.LENGTH_SHORT).show();
            } else if (mesInicio == mesHoje) {
                if (diaInicio < diaHoje) {
                    Toast.makeText(getApplicationContext(), "O dia da data de início da vaga é muito antigo. Coloque um dia mais atual.", Toast.LENGTH_SHORT).show();
                } else {
                    agoraVerificaDataTermino = true;
                }
            }
        }

        // CONFIGURANDO DATA ATUAL PARA DATA DE INICIO

        if (agoraVerificaDataTermino) {
            if (anoTermino < anoHoje) {
                Toast.makeText(getApplicationContext(), "O ano da data de término da vaga é muito antigo. Coloque um ano mais atual.", Toast.LENGTH_SHORT).show();
            } else if (anoTermino == anoHoje) {
                if (mesTermino < mesHoje) {
                    Toast.makeText(getApplicationContext(), "O mês da data de término da vaga é muito antigo. Coloque um mês mais atual.", Toast.LENGTH_SHORT).show();
                } else if (mesTermino == mesHoje) {
                    if (diaTermino < diaHoje) {
                        Toast.makeText(getApplicationContext(), "O dia da data de término da vaga é muito antigo. Coloque um dia mais atual.", Toast.LENGTH_SHORT).show();
                    } else {
                        fazOutraVerificacao = true;
                    }
                }
            }
        }


        if (fazOutraVerificacao) {
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
        }

        return podeGravar;
    }

}