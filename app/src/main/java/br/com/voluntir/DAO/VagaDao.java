package br.com.voluntir.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Vaga;

public class VagaDao implements DAO<Vaga> {
    //DatabaseReference bancoFirebase ;
    private DatabaseReference refenciaBanco;
    private DatabaseReference tabela;
    Vaga vaga;
    List<Vaga> listaVaga;
    private ValueEventListener valueEventListener;

    @Override
    public boolean adiciona(Vaga dado, String tabela, Context appContext) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).push().setValue(dado);

        return false;
    }

    @Override
    public boolean remove(Vaga dado, String tabela, Context context) throws DatabaseException {
        return false;
    }

    @Override
    public void atualiza(Vaga dado, String tabela, Context context) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(String.valueOf(dado.getIdVaga())).setValue(dado);


    }

    @Override
    public Vaga busca(String id, String tabela) throws DatabaseException {
        //vaga = new Vaga();
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(id).addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //DataSnapshot é o retorno do firebase

                    vaga = snapshot.getValue(Vaga.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return vaga;

    }

    @Override
    public List<Vaga> listar(final String criterio, String tabela) throws DatabaseException {
        listaVaga = new ArrayList<>();

        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();
                //DataSnapshot é o retorno do firebase
                Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    vaga = dataSnapshot.getValue(Vaga.class);
                    if (vaga.getNomeOng().equals(criterio)){

                    }
                    listaVaga.add(vaga);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return listaVaga;
    }
}
