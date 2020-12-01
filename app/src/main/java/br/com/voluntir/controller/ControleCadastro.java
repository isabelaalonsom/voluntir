package br.com.voluntir.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.DAO.VoluntarioDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class ControleCadastro {
    private FirebaseAuth autenticacao;
    Voluntario voluntario;
    VoluntarioDao voluntarioDao;
    Ong ong;
    OngDao ongDao;
    Vaga vaga;
    VagaDao vagaDao;
    boolean entrou;
    private Boolean retorno;
    boolean usuario = false;

    public boolean cadastrarVoluntario(Voluntario dado, String tabela, Context context) {
        this.voluntario = dado;

        voluntarioDao = new VoluntarioDao();
        try {
            retorno = voluntarioDao.adiciona(voluntario, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public boolean cadastrarOng(Ong dado, String tabela, Context context) {
        this.ong = dado;

        ongDao = new OngDao();
        try {
            retorno = ongDao.adiciona(ong, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public boolean cadastrarVaga(Vaga dado, String tabela, Context context) {
        this.vaga = dado;

        vagaDao = new VagaDao();

        try {
            retorno = vagaDao.adiciona(vaga, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public void enviarEmailRecuperarSenha(String email, final Context context) {
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context,
                                    "Sucesso ao enviar email ",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("EMAIL ENVIADO", "Email enviado.");
                        } else {
                            Toast.makeText(context,
                                    "Erro ao enviar email ",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("EMAIL NÃO ENVIADO", "Email não enviado.");
                        }
                    }
                });
    }

    public Ong validarLoginOng(final Ong dado, final String nomeTabela, final Context context) {
        ongDao = new OngDao();
        this.ong = dado;
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                ong.getEmailOng(), ong.getSenhaOng()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //recupera os dados do usuario
                    FirebaseUser ongFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    ong.setIdOng(ongFirebase.getUid());

                    ongDao.busca(ong.getIdOng(), nomeTabela);

                    Toast.makeText(context,
                            "Sucesso ao fazer Login ",
                            Toast.LENGTH_SHORT).show();


                } else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcecao = "E-mail não cadastrado ou desativado ";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Senha inválida";
                    } catch (Exception e) {
                        erroExcecao = "Ao fazer login";
                        e.printStackTrace();
                    }

                    Log.w("Login", "erro ao fazer login", task.getException());
                    Toast.makeText(context,
                            "Erro: " + erroExcecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return ong;
    }

    public Voluntario validarLoginVoluntario(final Voluntario dado, final String nomeTabela, final Context context) {
        voluntarioDao = new VoluntarioDao();

        this.voluntario = dado;
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                voluntario.getEmail(), voluntario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //recupera os dados do usuario
                    FirebaseUser voluntarioFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    voluntario.setIdVoluntario(voluntarioFirebase.getUid());


                    //voluntario=voluntarioDao.busca(voluntario.getIdVoluntario(),nomeTabela);


                    Toast.makeText(context,
                            "Sucesso ao fazer Login ",
                            Toast.LENGTH_SHORT).show();

                    //chamar proxima tela
                    //Intent intent = new Intent(context, VagaActivity.class);
                    //startActivity(intent);

                } else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcecao = "E-mail não cadastrado ou desativado ";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Senha inválida";
                    } catch (Exception e) {
                        erroExcecao = "Ao fazer login";
                        e.printStackTrace();
                    }

                    Log.w("Login", "erro ao fazer login", task.getException());
                    Toast.makeText(context,
                            "Erro: " + erroExcecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        return voluntario;
    }

}
