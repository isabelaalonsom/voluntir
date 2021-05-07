package br.com.voluntir.controller;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VoluntarioDao;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.VisualizarPerfilOng;

public class ControleCadastro {
    private FirebaseAuth autenticacao;
    Voluntario voluntario;
    VoluntarioDao voluntarioDao;
    Ong ong;
    OngDao ongDao;
    VisualizarPerfilOng visualizarPerfilOng = new VisualizarPerfilOng();
    DatabaseReference bancoFirebase;
    boolean retorno = false;


    public void buscarOngTeste(Vaga vaga) {
        ongDao = new OngDao();
        /*ongDao.buscarOngTeste(new OngDao.FirebaseCallback() {
            @Override
            public void onCallback(Ong ong) {
                *//*VisualizarPerfilOng visualizarPerfilOng = new VisualizarPerfilOng();
                visualizarPerfilOng.exibirPerfil(ong);*//*
                retornaOng(ong);

            }
        }, vaga.getIdOng());*/

        ongDao.lerDados(vaga.getIdOng(), new OngDao.OnGetDataListener() {
            @Override
            public void onSucess(Ong ong) {

            }

            @Override
            public void onStart() {

            }
        });
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

    /*   public void buscaOngTeste(Context context, String id) {

            ongDao = new OngDao();
            ongDao.buscarOngTeste(new OngDao.FirebaseCallback() {
                @Override
                public void onCallback(Ong ong) {

                            Intent intent = new Intent(context, VisualizarPerfilOng.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("ong",ong);
                            context.startActivity(intent);


                        return;





                }
            },id);

        }*/
    public void buscaCompleta(Ong ong) {

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
