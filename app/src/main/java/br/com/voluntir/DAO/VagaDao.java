package br.com.voluntir.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.MenuOngActivity;
import br.com.voluntir.voluntir.MinhasVagasActivity;

public class VagaDao implements DAO<Vaga> {
    //DatabaseReference bancoFirebase ;
    private DatabaseReference refenciaBanco;
    private FirebaseAuth autenticacao;
    private DatabaseReference tabela;
    Vaga vaga;
    OngDao ongDao;
    Ong ong;
    //Ong ong = new Ong();
    List<Vaga> listaVaga;
    private ValueEventListener valueEventListener;

    @Override
    public void adiciona(Vaga dado, String tabela, final Context appContext) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        String idVaga = refenciaBanco.push().getKey();
        dado.setIdVaga(idVaga);
        refenciaBanco.child(tabela).child(idVaga).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    /*ongDao = new OngDao();
                    ong = new Ong();
                    
                    String idDaOng = dado.getIdOng();

                    ong = ongDao.busca(idDaOng, "ong", appContext);*/

                    Toast.makeText(appContext,
                            "Vaga cadastrada com sucesso ",
                            Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(appContext.getApplicationContext(), MenuOngActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //i.putExtra("ong", ong1);
                    i.putExtra("objeto", ong);
                    appContext.startActivity(i);*/

                }
            }
        });

        return;
    }

    @Override
    public boolean remove(Vaga dado, String tabela, Context context) throws DatabaseException {
        return false;
    }

    @Override
    public void atualiza(Vaga dado, String tabela, Context context) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(dado.getIdVaga()).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(
                            context,
                            "Candidatura Enviada!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });


    }

    @Override
    public Vaga busca(String id, String tabela, Context context) throws DatabaseException {
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
