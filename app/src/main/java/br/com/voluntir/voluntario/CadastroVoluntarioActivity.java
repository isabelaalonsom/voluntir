package br.com.voluntir.voluntario;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;


@RequiresApi(api = Build.VERSION_CODES.O)
public class CadastroVoluntarioActivity extends AppCompatActivity {

    private Button botaoConfirmar;
    private Voluntario voluntario;
    private EditText email, senha, cpf, data, nome, telefone, confirmarSenha;
    private EditText endereco, especialidade, sobrenome;
    private ControleCadastro controleCadastro;
    private String tabelaBanco = "voluntario";
    private RadioGroup radioGroup;
    RadioButton radioButton;
    private RadioButton botaoFeminino;
    private RadioButton botaoMasculino;
    int mesAtual = LocalDate.now().getMonth().getValue();
    int diaAtual = LocalDate.now().getDayOfMonth();
    int anoAtual = LocalDate.now().getYear();
    String genero;
    boolean grava = false;
    boolean mesdiaok = false;
    private DatabaseReference refenciaBanco;
    private List<String> listaCpf = new ArrayList<>();
    private boolean cpfCadastrado = false;
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
        botaoFeminino = findViewById(R.id.rdBtnFeminino);
        botaoMasculino = findViewById(R.id.rdBtnMasculino);
        confirmarSenha = findViewById(R.id.edtTextConfirmarSenha);

        radioGroup = findViewById(R.id.rdBtnGrpGenero);

        refenciaBanco = BancoFirebase.getBancoReferencia();
        Query pesquisa = refenciaBanco.child(tabelaBanco).orderByChild("cpf");
        pesquisa.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaCpf.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String cpf;
                    cpf = dataSnapshot.getValue(Voluntario.class).getCpf();
                    listaCpf.add(cpf);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        //clicarBotaoLimpar();
        //mascara para o Cpf
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(cpf, simpleMaskCpf);
        cpf.addTextChangedListener(maskCpf);

        //mascara para a Data
        SimpleMaskFormatter simpleMaskData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskData = new MaskTextWatcher(data, simpleMaskData);
        data.addTextChangedListener(maskData);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        botaoConfirmar = findViewById(R.id.btnConfirmarVoluntario);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dataValida = false;
                voluntario = new Voluntario();
                controleCadastro = new ControleCadastro();
                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);

                voluntario.setEmail(email.getText().toString());
                voluntario.setSenha(senha.getText().toString());
                voluntario.setCpf(cpf.getText().toString());
                voluntario.setNome(nome.getText().toString());
                voluntario.setEspecialidade(especialidade.getText().toString());
                voluntario.setTelefone(telefone.getText().toString());
                voluntario.setEndereco(endereco.getText().toString());
                voluntario.setDatanasc(data.getText().toString());
                voluntario.setSobrenome(sobrenome.getText().toString());




                //verifica se todos os campos foram preenchidos
                if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() ||
                        cpf.getText().toString().isEmpty() || nome.getText().toString().isEmpty() ||
                        especialidade.getText().toString().isEmpty() || telefone.getText().toString().isEmpty() ||
                        endereco.getText().toString().isEmpty() || data.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os campos ",
                            Toast.LENGTH_SHORT).show();
                } else if (radioButton == null) {
                    Toast.makeText(getApplicationContext(), "Preencha o gênero ", Toast.LENGTH_SHORT).show();
                } else if (!senha.getText().toString().equals(confirmarSenha.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "As senhas não conferem.", Toast.LENGTH_LONG).show();
                }
                if (!data.getText().toString().isEmpty()) {
                    if (!validarData(data.getText().toString())) {
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
                    if (listaCpf != null) {
                        cpfCadastrado = false;
                        for (int i = 0; i < listaCpf.size(); i++) {
                            if (listaCpf.get(i).equals(cpf.getText().toString())) {
                                cpfCadastrado = true;
                            }
                        }
                    }
                    if (cpfCadastrado == false) {
                        voluntario.setGenero((String) radioButton.getText());
                        Boolean retorno = controleCadastro.cadastrarVoluntario(voluntario, tabelaBanco, getApplicationContext());
                    } else {
                        Toast.makeText(getApplicationContext(), "CPF já cadastrado ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }


    public void clicarBotaoLimpar(View view) {

        nome.setText("");
        sobrenome.setText("");
        cpf.setText("");
        data.setText("");
        email.setText("");
        senha.setText("");
        botaoFeminino.setChecked(false);
        botaoMasculino.setChecked(false);
        especialidade.setText("");
        endereco.setText("");
        telefone.setText("");
        confirmarSenha.setText("");
        radioGroup.clearCheck();

    }

    public void radioButtonApertado(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        genero = (String) radioButton.getText();
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

        String dataInicioConfig = data.getText().toString();
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
}