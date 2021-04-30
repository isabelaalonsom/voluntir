package br.com.voluntir.controller;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.model.Vaga;

public class ControleVaga {

    Vaga vaga;
    VagaDao vagaDao;
    DatabaseReference bancoFirebase;
    boolean retorno = false;

    public boolean cadastrarVaga(Vaga dado, String tabela, Context context) {

        vagaDao = new VagaDao();

        vagaDao.adiciona(dado, tabela, context);

        return retorno;
    }

    public void atualizaVagaVoluntario(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();

        vagaDao.atualiza(dado, tabela, context);

    }
}
