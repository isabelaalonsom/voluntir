package br.com.voluntir.controller;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.DAO.VoluntarioDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;
import br.com.voluntir.voluntir.MenuOngActivity;

public class ControleCadastro {
    private FirebaseAuth autenticacao;
    Voluntario voluntario;
    VoluntarioDao voluntarioDao;
    Ong ong;
    Ong ongRetorno;
    OngDao ongDao;
    Vaga vaga;
    VagaDao vagaDao;
    DatabaseReference bancoFirebase;
    boolean retorno = false;

    public void excluirDadosVoluntario(Voluntario dado, String tabela, Context context) {
        voluntarioDao = new VoluntarioDao();
        try {
            retorno = voluntarioDao.remove(dado, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirDadosOng(Ong dado, String tabela, Context context) {
        ongDao = new OngDao();
        try {
            retorno = ongDao.remove(dado, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrarVoluntario(Voluntario dado, String tabela, Context context) {
        this.voluntario = dado;

        voluntarioDao = new VoluntarioDao();
        try {
            voluntarioDao.adiciona(voluntario, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public boolean cadastrarOng(Ong dado, String tabela, Context context) {
        this.ong = dado;

        ongDao = new OngDao();

        ongDao.adiciona(ong, tabela, context);

        return retorno;
    }

    public void atualizarDadosOng(Ong dado, String tabela, Context context) {
        this.ong = dado;

        ongDao = new OngDao();

        ongDao.atualiza(dado, tabela, context);

    }

    public void atualizarDadosOng(Voluntario dado, String tabela, Context context) {
        this.voluntario = dado;

        voluntarioDao = new VoluntarioDao();

        voluntarioDao.atualiza(dado, tabela, context);

    }

    public boolean cadastrarVaga(Vaga dado, String tabela, Context context) {
        this.vaga = dado;

        vagaDao = new VagaDao();

        try {
            vagaDao.adiciona(vaga, tabela, context);
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


    public void validarLoginOng(final Ong dado, final String nomeTabela, final Context context) {
        ongDao = new OngDao();

        Task taskretorno;

        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                dado.getEmailOng(), dado.getSenhaOng()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    retorno = true;
                    //recupera os dados do usuario
                    FirebaseUser ongFirebase = autenticacao.getCurrentUser();

                    bancoFirebase = BancoFirebase.getBancoReferencia();
                    bancoFirebase.child("ong").orderByKey().equalTo(ongFirebase.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ong = dataSnapshot.getValue(Ong.class);
                                Log.i("FIREBASE", dataSnapshot.getValue().toString());

                            }
                            if (ong != null) {
                                Toast.makeText(context,
                                        "Sucesso ao fazer Login ",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context.getApplicationContext(), MenuOngActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("objeto", ong);
                                context.startActivity(intent);
                            } else {
                                String erroExcecao = "";
                                try {
                                    throw task.getException();
                                } catch (Exception e) {
                                    erroExcecao = "E-mail não cadastrado como ONG";
                                }

                                Log.w("Login", "erro ao fazer login", task.getException());
                                Toast.makeText(context,
                                        "Erro: " + erroExcecao,
                                        Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcecao = "E-mail não cadastrado ou desativado ";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Senha inválida";
                    } catch (Exception e) {
                        erroExcecao = e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(context,
                            "Erro: " + erroExcecao,
                            Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    public Voluntario validarLoginVoluntario(final Voluntario dado, final String nomeTabela, final Context context) {
        voluntarioDao = new VoluntarioDao();


        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                dado.getEmail(), dado.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //recupera os dados do usuario
                    FirebaseUser voluntarioFirebase = autenticacao.getCurrentUser();


                    bancoFirebase = BancoFirebase.getBancoReferencia();
                    bancoFirebase.child("voluntario").orderByKey().equalTo(voluntarioFirebase.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                voluntario = dataSnapshot.getValue(Voluntario.class);
                                Log.i("FIREBASE", dataSnapshot.getValue().toString());

                            }

                            if (voluntario != null) {
                                Toast.makeText(context,
                                        "Sucesso ao fazer Login ",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context.getApplicationContext(), MenuVoluntarioActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("objeto", voluntario);
                                context.startActivity(intent);
                            } else {
                                String erroExcecao = "";
                                try {
                                    throw task.getException();
                                } catch (Exception e) {
                                    erroExcecao = "E-mail não cadastrado como Voluntário";
                                }

                                Log.w("Login", "erro ao fazer login", task.getException());
                                Toast.makeText(context,
                                        "Erro: " + erroExcecao,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcecao = "E-mail não cadastrado ou desativado ";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Senha inválida";
                    } catch (Exception e) {
                        erroExcecao = e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(context,
                            "Erro: " + erroExcecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });


        return voluntario;
    }

}
