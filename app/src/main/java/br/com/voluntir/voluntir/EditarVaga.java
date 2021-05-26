package br.com.voluntir.voluntir;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.controller.ControleVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditarVaga extends AppCompatActivity {
    boolean entrou = false;
    private final String tabelaBanco = "vaga";
    private Ong ong;
    private boolean grava = false, mesdiaok = false;
    private Button botaoConfirmar;
    private Vaga vaga;
    boolean dataInicioValida = false;
    private TextView nome;
    private EditText cargaHoraria, periodicidade, especialidade, detalheVaga, qtdCandidatos, dataInicio, dataTermino;
    int mesAtual = LocalDate.now().getMonth().getValue();
    int diaAtual = LocalDate.now().getDayOfMonth();

    private TextView txtTituloNovaVagaEditarVaga;
    private ControleCadastro controleCadastro;

    private ControleVaga controleVaga;
    boolean dataTerminoValida = false;
    int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
    boolean podeGravar = false;
    boolean existe = false;
    private DatabaseReference refenciaBanco;
    private Vaga vaga2;
    private String idOng;
    private List<Vaga> listaVaga = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vaga);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

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
                                    vaga2 = listaVaga.get(i);
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
                            controleVaga.atualizaVagaOng(vaga, tabelaBanco, getApplicationContext());

                        }
                        if (entrou == true && existe == true) {
                            if (ong != null) {
                                vaga2.setNomeOng(ong.getNome());
                                vaga2.setIdOng(ong.getIdOng());
                            }
                            vaga2.setAreaConhecimento(especialidade.getText().toString());
                            vaga2.setDataInicio(dataInicio.getText().toString());
                            vaga2.setDataTermino(dataTermino.getText().toString());
                            vaga2.setPeriodicidade(periodicidade.getText().toString());
                            vaga2.setDescricaoVaga(detalheVaga.getText().toString());
                            vaga2.setCargaHoraria(cargaHoraria.getText().toString());
                            vaga2.setQtdCandidaturas(Integer.parseInt(qtdCandidatos.getText().toString()));
                            controleVaga = new ControleVaga();
                            controleVaga.atualizaVagaOng(vaga2, tabelaBanco, getApplicationContext());


                        }

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