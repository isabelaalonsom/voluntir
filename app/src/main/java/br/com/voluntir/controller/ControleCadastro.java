package br.com.voluntir.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.DAO.OngDao;
import br.com.voluntir.DAO.VagaDao;
import br.com.voluntir.DAO.VoluntarioDao;
import br.com.voluntir.Preferencias;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;
import br.com.voluntir.voluntir.MenuOngActivity;

public class ControleCadastro {
    private FirebaseAuth autenticacao;
    Voluntario voluntario;
    VoluntarioDao voluntarioDao;
    Ong ong;
    Ong ongRetorno;
    OngDao ongDao;

    DatabaseReference bancoFirebase;
    boolean retorno = false;

    public void excluirDadosVoluntario(Voluntario dado, String tabela, Context context) {
        voluntarioDao = new VoluntarioDao();
        try {
            retorno = voluntarioDao.remove(dado, tabela, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirDadosOng(Ong dado, String tabela, Context context) {
        ongDao = new OngDao();
        try {
            retorno = ongDao.remove(dado, tabela, context);

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
