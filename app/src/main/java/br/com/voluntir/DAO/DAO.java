package br.com.voluntir.DAO;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

public interface DAO <ValueObjects> {

    public void adiciona(ValueObjects dado, String tabela, Context appContext);


    public boolean remove(ValueObjects dado, String tabela, Context context)
            throws SQLException;

    public void atualiza(ValueObjects dado, String tabela, Context appContext)
            throws SQLException;

    public ValueObjects busca(String id, String tabela, Context context)
            throws SQLException;

    public List<ValueObjects> listar(String criterio, String tabela)
            throws SQLException;

}
