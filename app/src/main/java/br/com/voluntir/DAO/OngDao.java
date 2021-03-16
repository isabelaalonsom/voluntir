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
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Ong;
import br.com.voluntir.voluntir.LoginActivityONG;
import br.com.voluntir.voluntir.MainActivity;

public class OngDao implements DAO<Ong> {
    private Ong ong;
    private FirebaseAuth autenticacao;
    private DatabaseReference bancoFirebase;
    private final static List<Ong> ongList = new ArrayList<>();
    boolean cadastrado;

    @Override
    public void adiciona(final Ong dado, final String tabela, final Context appContext) {
        ong = dado;

        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                dado.getEmailOng(),
                dado.getSenhaOng()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    cadastrado = true;
                    //recupera os dados do usuario
                    FirebaseUser ongFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    //ong.setIdOng( ongFirebase.getUid() );
                    dado.setIdOng(ongFirebase.getUid());
                    //voluntario.salvar();
                    DatabaseReference bancoReferencia = BancoFirebase.getBancoReferencia();
                    //seta o valor do proprio usuario
                    bancoReferencia.child(tabela).child(dado.getIdOng()).setValue(dado);
                    //desloga o usuário

                    Toast.makeText(appContext,
                            "Cadastrado com sucesso ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(appContext, LoginActivityONG.class);
                    appContext.startActivity(intent);


                }else{

                    cadastrado = false;
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado";
                    } catch (Exception e) {
                        erroExcecao = "Ao cadastrar usuário";
                        e.printStackTrace();
                    }

                    Toast.makeText(appContext,
                            "Erro: "+erroExcecao,
                            Toast.LENGTH_SHORT).show();
                    Log.w("CADASTRO ONG", "signInWithEmail:erro"+erroExcecao, task.getException());


                }
            }
        });

        return;
    }


    @Override
    public boolean remove(Ong dado, String tabela, final Context context) throws SQLException {
        final String id = dado.getIdOng();
        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        usuario.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            bancoFirebase = BancoFirebase.getBancoReferencia();
                            bancoFirebase.child("ong").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context,
                                                "Conta excluida com sucesso ",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(context, MainActivity.class);
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
    public void atualiza(Ong dado, String tabela, final Context appContext) {
        bancoFirebase = BancoFirebase.getBancoReferencia();
        bancoFirebase.child("ong").child(dado.getIdOng()).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(appContext,
                            "Dados atualizados com sucesso ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(appContext,
                            "Erro" + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public Ong busca(final String id, String tabela) {

        bancoFirebase = BancoFirebase.getBancoReferencia();
        bancoFirebase.child("ong").orderByKey().equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ong = dataSnapshot.getValue(Ong.class);
                    Log.i("FIREBASE", dataSnapshot.getValue().toString());
                    //listaVaga.add(vaga);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ong;

    }


    @Override
    public List<Ong> listar(String criterio, String tabela) throws SQLException {
        return null;
    }
}
