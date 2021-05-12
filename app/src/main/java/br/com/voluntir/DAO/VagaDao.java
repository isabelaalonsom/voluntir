package br.com.voluntir.DAO;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.MainActivity;

public class VagaDao implements DAO<Vaga> {

    Vaga vaga;
    List<Vaga> listaVaga;
    private DatabaseReference refenciaBanco;

    public void atualizaVaga(Vaga dado, String tabela, Context context, final OnGetDataListener listener) {
        listener.onStart();
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(dado.getIdVaga()).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSucess(dado);

                }
            }
        });
    }

    public Vaga buscaVaga(String informacao, String tabela, Context context, final OnGetDataListener listener) {
        listener.onStart();
        refenciaBanco = BancoFirebase.getBancoReferencia();
        //Query pesquisa = refenciaBanco.child(tabela).orderByChild("idVaga").equalTo(id).orderByChild("idOng").equalTo();
        refenciaBanco.child(tabela).orderByChild("areaConhecimento").equalTo(informacao).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query pesquisa = refenciaBanco.child(tabela).orderByChild("areaConhecimento").equalTo(informacao);
        pesquisa.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    listener.onSucess(null);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        vaga = dataSnapshot.getValue(Vaga.class);
                        if (vaga != null) {
                            listener.onSucess(vaga);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        return vaga;

    }

    @Override
    public void adiciona(Vaga dado, String tabela, final Context appContext) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        String idVaga = refenciaBanco.push().getKey();
        dado.setIdVaga(idVaga);
        refenciaBanco.child(tabela).child(idVaga).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(appContext,
                            "Vaga cadastrada com sucesso ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return;
    }

    @Override
    public boolean remove(Vaga dado, String tabela, Context context) throws DatabaseException {
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(dado.getIdVaga()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,
                        "Vaga excluída com sucesso ",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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
                            "Dados atualizados!",
                            Toast.LENGTH_SHORT
                    ).show();


                }
            }
        });

    }

    public void cadastrarVoluntarioVaga(Vaga dado, String tabela, Context context, final OnGetDataListener listener) {
        listener.onStart();
        refenciaBanco = BancoFirebase.getBancoReferencia();
        refenciaBanco.child(tabela).child(dado.getIdVaga()).setValue(dado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSucess(dado);

                }
            }
        });
    }

    public interface OnGetDataListener {
        void onSucess(Vaga vaga);

        void onStart();

    }


    @Override
    public Vaga busca(String id, String tabela, Context context) throws DatabaseException {

        refenciaBanco = BancoFirebase.getBancoReferencia();
        Query pesquisa = refenciaBanco.child(tabela).orderByChild("idVaga").equalTo(id);
        pesquisa.addValueEventListener(new ValueEventListener() {
            //recuperar os dados sempre que for mudado no banco
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);
                }

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

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    vaga = dataSnapshot.getValue(Vaga.class);
                    if (vaga.getNomeOng().equals(criterio)) {

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
