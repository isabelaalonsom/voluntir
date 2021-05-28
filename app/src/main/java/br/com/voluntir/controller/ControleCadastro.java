package br.com.voluntir.controller;

import android.content.Context;

import java.util.List;

import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VoluntarioDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;

public class ControleCadastro {
    boolean retorno = false;
    private Voluntario voluntario;
    private VoluntarioDao voluntarioDao;
    private Ong ong;
    private OngDao ongDao;

    public void alterarSenha(String senha, String email, Context context) {
        ongDao = new OngDao();
        ongDao.atualizarSenha(senha, email, context);
    }

    public void alterarSenhaVoluntario(String senha, Context context) {
        voluntarioDao = new VoluntarioDao();
        voluntarioDao.atualizarSenha(senha, context);
    }


    public void alterarEmail(List<Vaga> listaVaga, Voluntario voluntario, Context context) {
        voluntarioDao = new VoluntarioDao();
        voluntarioDao.atualizarEmail(listaVaga, voluntario, context);
    }

    public void alterarEmailOng(Ong ong, Context context) {
        ongDao = new OngDao();
        ongDao.atualizarEmail(ong, context);
    }


    public void excluirDadosVoluntario(Voluntario dado, String tabela, Context context) {
        voluntarioDao = new VoluntarioDao();

        voluntarioDao.remove(dado, tabela, context);

    }

    public void excluirDadosOng(Ong dado, String tabela, Context context) {
        ongDao = new OngDao();
        try {
            ongDao.remove(dado, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscaOng(String email, String tabela, Context context) {
        ongDao = new OngDao();
        try {
            ongDao.busca(email, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void buscaVoluntario(String email, String tabela, Context context) {
        voluntarioDao = new VoluntarioDao();
        try {
            voluntarioDao.busca(email, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrarVoluntario(Voluntario dado, String tabela, Context context) {
        this.voluntario = dado;

        voluntarioDao = new VoluntarioDao();
        try {
            voluntarioDao.adiciona(voluntario, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public boolean cadastrarOng(Ong dado, String tabela, Context context) {
        this.ong = dado;

        ongDao = new OngDao();

        ongDao.adiciona(ong, tabela, context);

        return retorno;
    }


    public void atualizarDadosOng(Ong dado, String tabela, Context context) {
        this.ong = dado;

        ongDao = new OngDao();

        ongDao.atualiza(dado, tabela, context);

    }

    public void atualizarDadosVoluntario(Voluntario dado, String tabela, Context context) {
        this.voluntario = dado;

        voluntarioDao = new VoluntarioDao();
        voluntarioDao.atualiza(dado, tabela, context);


    }


}
