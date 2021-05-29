package br.com.voluntir.controller;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;

public class ControleVaga {

    private final boolean retorno = false;
    private VagaDao vagaDao;
    private DatabaseReference bancoFirebase;
    private boolean nomeVaga = false;


    public void atualizaNomeOng(List<Vaga> listaVaga, Ong ong, String tabela, Context context) {
        vagaDao = new VagaDao();
        vagaDao.atualizaNomeOng(listaVaga, ong, tabela, context);
    }

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
            public void onSucess(Vaga vaga) {
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


    public void buscarVagaNome(String informacao, String idOng, String tabela, Context context, final VagaDao.OnGetDataListener listener) {

        vagaDao = new VagaDao();
        vagaDao.buscaVaga(informacao, idOng, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess(Vaga vagas) {
                if (vagas != null) {
                    nomeVaga = true;
                    listener.onSucess(vagas);
                }

            }

            @Override
            public void onStart() {

            }
        });

    }

    public void cadastrarVoluntarioVaga(Vaga dado, String tabela, Context context) {
        vagaDao = new VagaDao();

        vagaDao.cadastrarVoluntarioVaga(dado, tabela, context, new VagaDao.OnGetDataListener() {
            @Override
            public void onSucess(Vaga vaga) {
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
            public void onSucess(Vaga vaga) {
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
            public void onSucess(Vaga vaga) {
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
            public void onSucess(Vaga vaga) {
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

    public interface OnGetDataListener {
        void onSucess(Vaga vaga);

        void onStart();
    }
}
