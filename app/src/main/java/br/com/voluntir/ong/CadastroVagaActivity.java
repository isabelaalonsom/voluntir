package br.com.voluntir.ong;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

@RequiresApi(api = Build.VERSION_CODES.O)
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

    private Date data = Calendar.getInstance().getTime();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat diaFormat = new SimpleDateFormat("dd");
    private DateFormat mesFormat = new SimpleDateFormat("MM");
    private DateFormat anoFormat = new SimpleDateFormat("yyyy");
    private String hoje = dateFormat.format(data);
    private int anoAtual = Integer.parseInt(anoFormat.format(data));
    private int mesAtual = Integer.parseInt(mesFormat.format(data));
    private int diaAtual = Integer.parseInt(diaFormat.format(data));

    private boolean podeGravar = false;
    private boolean existe = false;
    private List<Vaga> listaVaga = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vaga);

        //getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
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


        SimpleMaskFormatter simpleMaskDataInicio = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataInicio = new MaskTextWatcher(dataInicio, simpleMaskDataInicio);
        dataInicio.addTextChangedListener(maskDataInicio);

        //mascara para a DataTermino
        SimpleMaskFormatter simpleMaskDataTermino = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskDataTermino = new MaskTextWatcher(dataTermino, simpleMaskDataTermino);
        dataTermino.addTextChangedListener(maskDataTermino);

        /*//mascara para o Horario
        SimpleMaskFormatter simpleMaskHorario = new SimpleMaskFormatter("NNN:NN");
        MaskTextWatcher maskHorario = new MaskTextWatcher(cargaHoraria, simpleMaskHorario);
        cargaHoraria.addTextChangedListener(maskHorario);*/

        botaoConfirmar = findViewById(R.id.btnConfirmarVaga);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaga = new Vaga();

                //verifica se todos os campos foram preenchidos
                if (especialidade.getText().toString().isEmpty() ||
                        dataInicio.getText().toString().isEmpty() || dataTermino.getText().toString().isEmpty() ||
                        cargaHoraria.getText().toString().isEmpty() ||
                        detalheVaga.getText().toString().isEmpty() || periodicidade.getText().toString().isEmpty() ||
                        qtdCandidatos.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (!validarData(dataInicio.getText().toString())) {
                        Toast.makeText(getApplicationContext(),
                                "A data de início da vaga é inválida",
                                Toast.LENGTH_SHORT).show();
                        dataInicioValida = false;
                    } else {
                        dataInicioValida = true;
                    }
                    if (!validarData(dataTermino.getText().toString())) {
                        Toast.makeText(getApplicationContext(),
                                "A data de término da vaga é inválida",
                                Toast.LENGTH_SHORT).show();
                        dataTerminoValida = false;
                    } else {
                        dataTerminoValida = true;
                    }
                    podeGravar = verificarDataMenor();


                    if (dataInicioValida == true && dataTerminoValida == true && podeGravar == true) {

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
                            limparDadosDoCadastroVaga(getCurrentFocus());

                        }

                    }
                }
            }
        });
    }


    public boolean validarData(String data) {
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
                        "Ano inválido",
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
        boolean podeGravar2 = false;
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
            Toast.makeText(getApplicationContext(), "Ano da data de início não pode ser maior que o ano da data de término da vaga", Toast.LENGTH_SHORT).show();
        } else if (anoInicio == anoTermino) {
            if (mesInicio > mesTermino) {
                Toast.makeText(getApplicationContext(), "Mês da data de início não pode ser maior que o mês da data de término da vaga quando os anos são os mesmos", Toast.LENGTH_LONG).show();
            } else if (mesInicio == mesTermino) {
                if (diaInicio > diaTermino) {
                    Toast.makeText(getApplicationContext(), "Dia da data de início não pode ser maior que o dia da data de término da vaga quando os meses e os anos são os mesmos", Toast.LENGTH_LONG).show();
                } else {
                    podeGravar = true;
                }
            } else if (mesInicio < mesTermino) {
                podeGravar = true;
            }

        } else if (anoInicio < anoTermino) {

            podeGravar = true;
        }


        if (podeGravar == true) {
            if (anoInicio == anoAtual) {
                if (mesInicio < mesAtual) {
                    Toast.makeText(getApplicationContext(), "Mês da data de início não pode ser menor do que o mês atual", Toast.LENGTH_SHORT).show();
                } else if (mesInicio == mesAtual) {
                    if (diaInicio < diaAtual) {
                        Toast.makeText(getApplicationContext(), "Dia da data de início não pode ser menor do que o dia atual", Toast.LENGTH_SHORT).show();
                    } else {
                        podeGravar2 = true;
                    }
                } else if (mesInicio > mesAtual) {

                    podeGravar2 = true;
                }
            } else {
                podeGravar2 = true;
            }

        }


        return podeGravar2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voltar:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}