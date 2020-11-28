package br.com.voluntir.DAO;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

public interface DAO <ValueObjects> {

    public boolean adiciona(ValueObjects dado, String tabela, Context appContext);


    public boolean remove(ValueObjects dado, String tabela)
            throws SQLException;
    public boolean atualiza(ValueObjects dado, String tabela)
            throws SQLException;

    public ValueObjects busca(ValueObjects dado, String tabela)
            throws SQLException;

    public List<ValueObjects> listar(String criterio, String tabela)
            throws SQLException;

}
