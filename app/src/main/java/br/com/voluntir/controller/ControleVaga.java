package br.com.voluntir.controller;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import br.com.voluntir.DAO.OngDao;
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

    public void deletarVaga(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();
        vagaDao.remove(dado, tabela, context);
    }

    public void atualizaVagaVoluntario(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();

        vagaDao.atualiza(dado, tabela, context);


    }

    public void atualizaVagaOng(Vaga dado, String tabela, Context context) {

        vagaDao = new VagaDao();
        vagaDao.atualizaVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess() {
                Toast.makeText(
                        context,
                        "Vaga editada com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onStart() {

            }
        });

    }


    public void buscaOng(String id, String tabela, Context context) {
        vagaDao = new VagaDao();
        try {
            vagaDao.busca(id, tabela, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cadastrarVoluntarioVaga(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();

        vagaDao.cadastrarVoluntarioVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess() {
                Toast.makeText(
                        context,
                        "Candidatado com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onStart() {

            }
        });
    }

    public void cancelarCandidatura(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();
        vagaDao.cadastrarVoluntarioVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess() {
                Toast.makeText(
                        context,
                        "Candidatura cancelada!",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onStart() {

            }
        });
    }

    public void reprovarCandidato(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();
        vagaDao.cadastrarVoluntarioVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess() {
                Toast.makeText(
                        context,
                        "Candidato reprovado!",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onStart() {

            }
        });

    }

    public void aprovarCandidato(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();
        vagaDao.cadastrarVoluntarioVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess() {
                Toast.makeText(
                        context,
                        "Candidato aprovado!",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onStart() {

            }
        });

    }
}
