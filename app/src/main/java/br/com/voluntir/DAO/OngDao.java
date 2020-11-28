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
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Ong;

public class OngDao implements DAO<Ong> {
    private Ong ong;
    private FirebaseAuth autenticacao;
    private DatabaseReference bancoFirebase;
    Boolean cadastrado=false;

    @Override
    public boolean adiciona(Ong dado, final String tabela, final Context appContext) {
        ong=dado;

        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                ong.getEmailong(),
                ong.getSenhaong()
        ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    cadastrado=true;
                    //recupera os dados do usuario
                    FirebaseUser ongFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    ong.setIdOng( ongFirebase.getUid() );
                    //voluntario.salvar();
                    DatabaseReference bancoReferencia = BancoFirebase.getBancoReferencia();
                    //seta o valor do proprio usuario
                    bancoReferencia.child(tabela).child(ong.getIdOng()).setValue(ong);
                    //desloga o usuário
                    autenticacao.signOut();
                    Toast.makeText(appContext,
                            "Cadastrado com sucesso ",
                            Toast.LENGTH_SHORT).show();

                    //encerra a activity
                    //finish();

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

        return cadastrado;
    }

    @Override
    public boolean remove(Ong dado, String tabela) throws SQLException {
        return false;
    }

    @Override
    public boolean atualiza(Ong dado, String tabela) throws SQLException {
        return false;
    }

    @Override
    public Ong busca(final Ong dado, String tabela) {
        ong = dado;
        bancoFirebase = BancoFirebase.getBancoReferencia();
        bancoFirebase.child(tabela).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean usuario=false;

                //laço para percorrer o banco
                for (DataSnapshot objSnapshot:snapshot.getChildren()){
                    ong = objSnapshot.getValue(Ong.class);
                    //busca o email no banco
                    if (ong.getEmailong().equals(dado.getEmailong())){
                        break;

                    }

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
