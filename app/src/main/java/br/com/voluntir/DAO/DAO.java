package br.com.voluntir.DAO;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

public interface DAO <ValueObjects> {

    void adiciona(ValueObjects dado, String tabela, Context appContext);


    boolean remove(ValueObjects dado, String tabela, Context context)
            throws SQLException;

    void atualiza(ValueObjects dado, String tabela, Context appContext)
            throws SQLException;

    ValueObjects busca(String id, String tabela, Context context)
            throws SQLException;

    List<ValueObjects> listar(String criterio, String tabela)
            throws SQLException;

}
