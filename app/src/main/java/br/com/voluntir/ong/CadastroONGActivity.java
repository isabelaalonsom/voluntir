package br.com.voluntir.ong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.voluntir.R;

public class CadastroONGActivity extends AppCompatActivity {
    private Button botaoConfirmar;
    private Ong ong;
    private EditText email, senha, cnpj, resumo, confirmarSenha;
    private EditText nome, telefone, endereco;
    private EditText causa, localizacao, site;
    private ControleCadastro controleCadastro;
    private String tabelaBanco = "ong";
    private DatabaseReference refenciaBanco;
    private boolean cnpjCadastrado = false;
    private List<String> listaCnpj = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);

        getSupportActionBar().hide();
        email = (EditText) findViewById(R.id.edtTextEmail);
        senha = (EditText) findViewById(R.id.edtTextSenha);
        cnpj = (EditText) findViewById(R.id.edtTextCpnj);
        nome = (EditText) findViewById(R.id.edtTextNome);
        telefone = (EditText) findViewById(R.id.edtTextTelefone);
        localizacao = (EditText) findViewById(R.id.edtTextLocalizacao);
        causa = (EditText) findViewById(R.id.edtTextCausas);
        site = (EditText) findViewById(R.id.edtTextSite);
        resumo = (EditText) findViewById(R.id.edtTextResumoOng);
        confirmarSenha = findViewById(R.id.edtTextConfirmarSenhaOng);

        refenciaBanco = BancoFirebase.getBancoReferencia();
        Query pesquisa = refenciaBanco.child(tabelaBanco).orderByChild("cnpj");
        pesquisa.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaCnpj.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String cnpj;
                    cnpj = dataSnapshot.getValue(Ong.class).getCpnj();
                    listaCnpj.add(cnpj);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        //limparDados();
        //mascara para o Cnpj
        SimpleMaskFormatter simpleMaskCnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher maskCnpj = new MaskTextWatcher(cnpj, simpleMaskCnpj);
        cnpj.addTextChangedListener(maskCnpj);

        //mascara para o Telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        Intent i = getIntent();
        Bundle parametros = i.getExtras();

        if (parametros != null) {
            String nomeOngPreenchido = parametros.getString("chave_nome_ong");
            String cnpjOngPreenchido = parametros.getString("chave_cnpj_ong");
            String localizacaoOngPreenchido = parametros.getString("chave_localizacao_ong");
            String causasOngPreenchido = parametros.getString("chave_causas_ong");
            String emailOngPreenchido = parametros.getString("chave_email_ong");
            String telefoneOngPreenchido = parametros.getString("chave_telefone_ong");
            String siteOngPreenchido = parametros.getString("chave_site_ong");
            String resumoOngPreenchido = parametros.getString("chave_resumo_ong");

            nome.setText(nomeOngPreenchido);
            cnpj.setText(cnpjOngPreenchido);
            localizacao.setText(localizacaoOngPreenchido);
            causa.setText(causasOngPreenchido);
            email.setText(emailOngPreenchido);
            telefone.setText(telefoneOngPreenchido);
            site.setText(siteOngPreenchido);
            resumo.setText(resumoOngPreenchido);

        }

            botaoConfirmar = findViewById(R.id.confirmarBtn);
            botaoConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    controleCadastro = new ControleCadastro();

                    //pegas os dados digitados
                    ong = new Ong();
                    ong.setEmailOng(email.getText().toString());
                    ong.setSenhaOng(senha.getText().toString());
                    ong.setCpnj(cnpj.getText().toString());
                    ong.setNome(nome.getText().toString());
                    ong.setCausas(causa.getText().toString());
                    ong.setTelefone(telefone.getText().toString());
                    ong.setLocalizacao(localizacao.getText().toString());
                    ong.setResumoOng(resumo.getText().toString());
                    ong.setSite(site.getText().toString());


                    //verifica se não tem campos em branco
                    if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() ||
                            cnpj.getText().toString().isEmpty() || nome.getText().toString().isEmpty() ||
                            causa.getText().toString().isEmpty() || telefone.getText().toString().isEmpty() ||
                            localizacao.getText().toString().isEmpty() || resumo.getText().toString().isEmpty() ||
                            site.getText().toString().isEmpty()) {

                        //exibe mensagem na tela
                        Toast.makeText(getApplicationContext(),
                                "Preencha todos os campos ",
                                Toast.LENGTH_SHORT).show();

                    } else if (!senha.getText().toString().equals(confirmarSenha.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "As senhas não conferem.", Toast.LENGTH_LONG).show();

                    } else {
                        if (listaCnpj != null) {
                            cnpjCadastrado = false;
                            for (int i = 0; i < listaCnpj.size(); i++) {
                                if (listaCnpj.get(i).equals(cnpj.getText().toString())) {
                                    cnpjCadastrado = true;
                                }
                            }
                        }
                        if (cnpjCadastrado == false) {
                            controleCadastro.cadastrarOng(ong, tabelaBanco, getApplicationContext());
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Cnpj já cadastrado ",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


                 }
            });
        }

        public void clicarBotaoConfirmar (View view){
            Toast.makeText(this, "Cadastro Criado!", Toast.LENGTH_SHORT).show();
            //aqui tem que jogar pro banco de dados os edit text preenchidos
        }

    public void limparDados(View v) {
        nome.setText("");
        cnpj.setText("");
        localizacao.setText("");
        causa.setText("");
        telefone.setText("");
        site.setText("");
        email.setText("");
        senha.setText("");
        confirmarSenha.setText("");
        resumo.setText("");
        }

    }

