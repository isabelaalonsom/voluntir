package br.com.voluntir.DAO;

import android.content.Context;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.model.Vaga;

public class VagaDao implements DAO<Vaga> {
    BancoFirebase bancoFirebase = new BancoFirebase();
    private DatabaseReference refenciaBanco;
    private DatabaseReference tabelaVaga ;

    @Override
    public boolean adiciona(Vaga dado, String tabela, Context appContext) throws DatabaseException {
        refenciaBanco= bancoFirebase.getBancoReferencia();
        tabelaVaga=refenciaBanco.child(tabela);

        return false;
    }

    @Override
    public boolean remove(Vaga dado, String tabela) throws DatabaseException {
        return false;
    }

    @Override
    public boolean atualiza(Vaga dado, String tabela) throws DatabaseException {
        return false;
    }

    @Override
    public Vaga busca(Vaga dado, String tabela) throws DatabaseException {
        return null;
    }

    @Override
    public List<Vaga> listar(String criterio, String tabela) throws DatabaseException {
        return null;
    }
}
