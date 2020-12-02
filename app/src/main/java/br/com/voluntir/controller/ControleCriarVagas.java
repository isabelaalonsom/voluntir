package br.com.voluntir.controller;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.model.Vaga;

public class ControleCriarVagas {
    private FirebaseAuth autenticacao;
    Vaga vaga;
    VagaDao vagaDao;
    List<Vaga> listaVaga;
    String tabela="vaga";
    public List<Vaga> listarVaga(String criterio, String tabela){
        listaVaga = new ArrayList<>();
        listaVaga = vagaDao.listar(null,tabela);
        return listaVaga;
    }

    public void candidatarVaga(Vaga vaga){
        vagaDao.atualiza(vaga,tabela);
    }
}
