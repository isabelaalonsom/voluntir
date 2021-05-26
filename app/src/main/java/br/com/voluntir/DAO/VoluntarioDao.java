package br.com.voluntir.DAO;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;
import br.com.voluntir.voluntir.Carregamento;
import br.com.voluntir.voluntir.LoginActivityVoluntario;

public class VoluntarioDao implements DAO<Voluntario> {
    private Voluntario voluntario;
    private FirebaseAuth autenticacao;
    private Boolean cadastrado = false;
    private DatabaseReference bancoFirebase;

    @Override
    public void adiciona(Voluntario dado, final String tabela, final Context appContext) {
        voluntario = dado;
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                voluntario.getEmail(),
                voluntario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    cadastrado = true;
                    //recupera os dados do usuario
                    FirebaseUser voluntarioFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    voluntario.setIdVoluntario(voluntarioFirebase.getUid());

                    DatabaseReference bancoReferencia = BancoFirebase.getBancoReferencia();
                    //seta o valor do proprio usuario
                    bancoReferencia.child(tabela).child(voluntario.getIdVoluntario()).setValue(voluntario);
                    //desloga o usuário
                    autenticacao.signOut();
                    Toast.makeText(appContext,
                            "Cadastrado com sucesso ",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(appContext, LoginActivityVoluntario.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("objeto", voluntario);
                    appContext.startActivity(intent);


                } else {

                    cadastrado = false;
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado";
                    } catch (Exception e) {
                        erroExcecao = "Ao cadastrar usuário";
                        e.printStackTrace();
                    }

                    Toast.makeText(appContext,
                            "Erro: " + erroExcecao,
                            Toast.LENGTH_SHORT).show();
                    Log.w("CADASTRO", "signInWithEmail:erro" + erroExcecao, task.getException());

                }

            }
        });

    }

    @Override
    public boolean remove(final Voluntario dado, final String tabela, final Context context) {
        final String id = dado.getIdVoluntario();
        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        usuario.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            bancoFirebase = BancoFirebase.getBancoReferencia();
                            bancoFirebase.child(tabela).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context,
                                                "Conta excluida com sucesso ",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(context, Carregamento.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    } else {
                                        Toast.makeText(context,
                                                "Erro" + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
        return false;
    }

    @Override
    public void atualiza(Voluntario dado, String tabela, final Context context) {
        bancoFirebase = BancoFirebase.getBancoReferencia();
        bancoFirebase.child(tabela).child(dado.getIdVoluntario()).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context,
                            "Dados atualizados com sucesso ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), Carregamento.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tela", "contaVoluntario");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context,
                            "Erro" + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public Voluntario busca(final String email, final String tabela, Context context) {
        bancoFirebase = BancoFirebase.getBancoReferencia();
        Query pesquisa = bancoFirebase.child(tabela).orderByChild("email").equalTo(email);
        pesquisa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    voluntario = dataSnapshot.getValue(Voluntario.class);

                }
                if (voluntario != null) {
                    Intent intent = new Intent(context.getApplicationContext(), MenuVoluntarioActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("objeto", voluntario);
                    context.startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return voluntario;
    }

    @Override
    public List<Voluntario> listar(String criterio, String tabela) {
        return null;
    }
}
