package br.com.voluntir.DAO;

import android.content.Context;
import android.content.ContextWrapper;
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
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.LoginActivityVoluntario;

public class VoluntarioDao implements DAO<Voluntario> {
    private Voluntario voluntario;
    private FirebaseAuth autenticacao;
    Boolean cadastrado=false;
    DatabaseReference bancoFirebase;

    @Override
    public boolean adiciona(Voluntario dado, final String tabela, final Context appContext){
        voluntario=dado;
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                voluntario.getEmail(),
                voluntario.getSenha()
        ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    cadastrado=true;
                    //recupera os dados do usuario
                    FirebaseUser voluntarioFirebase = task.getResult().getUser();

                    //recupera o uid do usuario
                    voluntario.setIdVoluntario( voluntarioFirebase.getUid() );

                    DatabaseReference bancoReferencia = BancoFirebase.getBancoReferencia();
                    //seta o valor do proprio usuario
                    bancoReferencia.child(tabela).child(voluntario.getIdVoluntario()).setValue(voluntario);
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
                    Log.w("CADASTRO", "signInWithEmail:erro"+erroExcecao, task.getException());

                }



            }
        });

        return cadastrado;
    }

    @Override
    public boolean remove(Voluntario dado, String tabela) {
        return false;
    }

    @Override
    public boolean atualiza(Voluntario dado, String tabela) {
        return false;
    }

    @Override
    public Voluntario busca(final String id, final String tabela) {
        bancoFirebase = BancoFirebase.getBancoReferencia();
        bancoFirebase.child("voluntario").orderByKey().equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    voluntario = dataSnapshot.getValue(Voluntario.class);
                    Log.i("FIREBASE", dataSnapshot.getValue().toString());
                    //listaVaga.add(vaga);
                }
                Log.i("FIREBASE", voluntario.getNome());
                Log.i("FIREBASE", voluntario.getIdVoluntario());
                Log.i("FIREBASE", voluntario.getEmail());
                Log.i("FIREBASE", voluntario.getCpf());
                Log.i("FIREBASE", voluntario.getDatanasc());
                Log.i("FIREBASE", voluntario.getEndereco());
                Log.i("FIREBASE", voluntario.getEspecialidade());
                Log.i("FIREBASE", voluntario.getGenero());
                Log.i("FIREBASE", voluntario.getTelefone());
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
