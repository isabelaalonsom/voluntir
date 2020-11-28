package br.com.voluntir;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BancoFirebase {
    private static DatabaseReference bancoReferencia ;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getBancoReferencia() {
        if ( bancoReferencia == null){
            bancoReferencia = FirebaseDatabase.getInstance().getReference();
        }
        return bancoReferencia;
    }

    //retorna a instancia da autenticação para que possa fazer os processos de autenticação
    public static FirebaseAuth getFirebaseAutenticacao(){
        if ( autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
