package br.com.voluntir.voluntario;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MinhaContaVoluntarioActivity extends AppCompatActivity {

    private final String tabelaVoluntario = "voluntario";
    private Voluntario voluntario;
    private TextView txtSobrenome, txtNome, txtCpf, txtDataNasc, txtEmail, txtTelefone, txtEndereco, txtGenero, txtDescricaoTecnica;
    private ControleCadastro controleCadastro;
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private List<Vaga> listaVaga = new ArrayList<>();
    private List<Vaga> listaVagaSemVoluntario = new ArrayList<>();
    private List<Vaga> listaVagaComVoluntario = new ArrayList<>();
    private Vaga vaga;
    private boolean acabou = false;
    int mesAtual = LocalDate.now().getMonth().getValue();
    int diaAtual = LocalDate.now().getDayOfMonth();
    int anoAtual = LocalDate.now().getYear();
    boolean dataValida = false;
    private String genero;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton botaoFeminino;
    private RadioButton botaoMasculino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_voluntario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        //getSupportActionBar().hide();
        botaoFeminino = findViewById(R.id.rdBtnFemininoE);
        botaoMasculino = findViewById(R.id.rdBtnMasculinoE);

        txtNome = (TextView) findViewById(R.id.txtViewNomeDoVoluntarioVariavel);
        txtSobrenome = (TextView) findViewById(R.id.txtViewSobrenomeVariavel);
        txtCpf = (TextView) findViewById(R.id.txtViewCpfVariavel);
        txtDataNasc = (TextView) findViewById(R.id.txtViewDataNascVariavel);
        txtEmail = (TextView) findViewById(R.id.txtViewEmailVariavel);
        txtEndereco = (TextView) findViewById(R.id.txtViewEnderecoVariavel);
        txtTelefone = (TextView) findViewById(R.id.txtViewTelefoneVariavel);
        //txtGenero = (TextView) findViewById(R.id.txtViewGeneroVariavel);
        txtDescricaoTecnica = (TextView) findViewById(R.id.txtViewDescricaoTecnicaVariavel);
        radioGroup = findViewById(R.id.rdBtnGrpGeneroE);
        //genero = "vazio";
        //mascara para o Cpf
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(txtCpf, simpleMaskCpf);
        txtCpf.addTextChangedListener(maskCpf);

        //mascara para a Data
        SimpleMaskFormatter simpleMaskData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskData = new MaskTextWatcher(txtDataNasc, simpleMaskData);
        txtDataNasc.addTextChangedListener(maskData);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(txtTelefone, simpleMaskTelefone);
        txtTelefone.addTextChangedListener(maskTelefone);


        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            voluntario = (Voluntario) dados.getSerializable("objeto");


            Query teste = tabelaVaga;
            teste.orderByKey().addValueEventListener(new ValueEventListener() {
                //recuperar os dados sempre que for mudado no banco
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listaVaga.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        vaga = dataSnapshot.getValue(Vaga.class);
                        //Log.i("FIREBASE", snapshot.getValue().toString());
                        if (vaga.getVoluntarios() != null) {

                            for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                                if (vaga.getVoluntarios().get(i).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                    listaVaga.add(vaga);
                                }
                            }
                        }
                    }
                }

                //trata o erro se a operação for cancelada
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }

    }

    @Override
    protected void onResume() {
        if (voluntario != null) {
            txtNome.setText(voluntario.getNome());
            txtSobrenome.setText(voluntario.getSobrenome());
            txtCpf.setText(voluntario.getCpf());
            txtDataNasc.setText(voluntario.getDatanasc());
            txtEmail.setText(voluntario.getEmail());
            txtEndereco.setText(voluntario.getEndereco());
            txtTelefone.setText(voluntario.getTelefone());
            //txtGenero.setText(voluntario.getGenero());
            txtDescricaoTecnica.setText(voluntario.getEspecialidade());
            if (voluntario.getGenero().equalsIgnoreCase("Masculino")) {
                radioGroup.check(botaoMasculino.getId());
                genero = "Masculino";
            } else {
                radioGroup.check(botaoFeminino.getId());
                genero = "Feminino";
            }
        }
        super.onResume();
    }

    public void radioButtonApertadoMinhaConta(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        genero = (String) radioButton.getText();
    }

    public void clicarBotaoEditar(View view) {

        if (txtNome.getText().toString().isEmpty() || txtCpf.getText().toString().isEmpty() || txtDataNasc.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() || txtEndereco.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty() ||
                txtDescricaoTecnica.getText().toString().isEmpty() || txtSobrenome.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (!txtDataNasc.getText().toString().isEmpty()) {
                if (!validarData(txtDataNasc.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Data de nascimento inválida",
                            Toast.LENGTH_SHORT).show();
                    dataValida = false;
                } else {
                    dataValida = true;
                }
            }

            boolean podeGravar = verificarDataMenor();
            if (podeGravar == true && dataValida == true) {
                Voluntario dados = new Voluntario();
                if (voluntario != null) {
                    dados.setIdVoluntario(voluntario.getIdVoluntario());
                }

                dados.setNome(txtNome.getText().toString());
                dados.setSobrenome(txtSobrenome.getText().toString());
                dados.setCpf(txtCpf.getText().toString());
                dados.setDatanasc(txtDataNasc.getText().toString());
                dados.setEmail(txtEmail.getText().toString());
                dados.setEndereco(txtEndereco.getText().toString());
                dados.setTelefone(txtTelefone.getText().toString());
                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);
                genero = (String) radioButton.getText();
                dados.setGenero(genero);
                dados.setEspecialidade(txtDescricaoTecnica.getText().toString());

                if (listaVaga != null) {
                    listaVagaComVoluntario.clear();
                    acabou = false;
                    for (int i = 0; i < listaVaga.size(); i++) {
                        Vaga vaga;
                        vaga = listaVaga.get(i);

                        for (int j = 0; j < vaga.getVoluntarios().size(); j++) {
                            if (vaga.getVoluntarios().get(j).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                dados.setStatusVaga(vaga.getVoluntarios().get(j).getStatusVaga());
                                //vaga.getVoluntarios().remove(j);
                                vaga.getVoluntarios().get(j).setNome(dados.getNome());
                                vaga.getVoluntarios().get(j).setSobrenome(dados.getSobrenome());
                                //vaga.getVoluntarios().get(j).setCpf(dados.getCpf());
                                vaga.getVoluntarios().get(j).setDatanasc(dados.getDatanasc());
                                vaga.getVoluntarios().get(j).setEndereco(dados.getEndereco());
                                vaga.getVoluntarios().get(j).setTelefone(dados.getTelefone());
                                vaga.getVoluntarios().get(j).setEspecialidade(dados.getEspecialidade());
                                //vaga.getVoluntarios().add(dados);

                            }

                        }

                        listaVagaComVoluntario.add(vaga);
                    }
                    acabou = true;
                }
                if (listaVagaComVoluntario != null && acabou == true) {
                    VagaDao vagaDao = new VagaDao();
                    //dados.setStatusVaga(null);
                    vagaDao.atualizaVagaPerfilVoluntario(listaVagaComVoluntario, dados, getApplicationContext());
                    finish();
                } else if (listaVagaComVoluntario == null && acabou == true) {
                    controleCadastro = new ControleCadastro();
                    dados.setStatusVaga(null);
                    controleCadastro.atualizarDadosVoluntario(dados, tabelaVoluntario, getApplicationContext());
                    finish();
                }


            }

        }
    }

    public void clicarBotaoExcluir(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Excluir Conta");
        dialog.setMessage("Deseja excluir conta?");

        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listaVaga != null) {
                    listaVagaSemVoluntario.clear();
                    acabou = false;
                    for (int i = 0; i < listaVaga.size(); i++) {
                        Vaga vaga;
                        vaga = listaVaga.get(i);

                        for (int j = 0; j < vaga.getVoluntarios().size(); j++) {
                            if (vaga.getVoluntarios().get(j).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                                vaga.getVoluntarios().remove(j);
                                listaVagaSemVoluntario.add(vaga);
                            }

                        }


                    }
                    acabou = true;
                }


                if (listaVagaSemVoluntario != null && acabou == true) {
                    VagaDao vagaDao = new VagaDao();
                    vagaDao.removeListaVagaCandidaturas(listaVagaSemVoluntario, voluntario, getApplicationContext());
                } else if (listaVagaSemVoluntario == null && acabou == true) {
                    controleCadastro = new ControleCadastro();
                    controleCadastro.excluirDadosVoluntario(voluntario, tabelaVoluntario, getApplicationContext());
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

    public void clicarBotaoSair(View view) {
        Preferencias preferencias = new Preferencias(getApplicationContext());
        preferencias.salvarUsuarioPreferencias(null, null, null);
        this.finishAffinity();

    }

    public void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtCpf.setText("");
        txtDataNasc.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        //txtGenero.setText("");
        txtDescricaoTecnica.setText("");
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

            return true;
        } catch (ParseException ex) {
            return false;
        }

    }

    public boolean verificarDataMenor() {
        boolean podeGravar = false;
        boolean podeGravar2 = false;
        int diaNascimento = 0;
        int mesNascimento = 0;
        int anoNascimento = 0;

        String dataInicioConfig = txtDataNasc.getText().toString();
        if (dataInicioConfig.length() == 10) {
            diaNascimento = Integer.parseInt(dataInicioConfig.substring(0, 2));
            mesNascimento = Integer.parseInt(dataInicioConfig.substring(3, 5));
            anoNascimento = Integer.parseInt(dataInicioConfig.substring(6, 10));
        }

        if ((anoAtual - anoNascimento) < 18) {
            Toast.makeText(getApplicationContext(), "Proibido menor de idade ", Toast.LENGTH_SHORT).show();
            podeGravar = false;
        } else if ((anoAtual - anoNascimento) == 18) {
            if (mesNascimento > mesAtual) {
                Toast.makeText(getApplicationContext(), "Proibido menor de idade ", Toast.LENGTH_SHORT).show();
                podeGravar = false;
            } else if (mesNascimento == mesAtual) {
                if (diaNascimento > diaAtual) {
                    Toast.makeText(getApplicationContext(), "Proibido menor de idade ", Toast.LENGTH_SHORT).show();
                    podeGravar = false;
                } else {
                    podeGravar = true;
                }
            } else if (mesNascimento < mesAtual) {
                podeGravar = true;
            }

        } else if (anoNascimento < anoAtual) {

            podeGravar = true;
        }
        if (podeGravar == true) {
            if (anoNascimento < 1900) {
                Toast.makeText(getApplicationContext(), "Ano inválido ", Toast.LENGTH_SHORT).show();
                podeGravar = false;
            }
        }

        return podeGravar;
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