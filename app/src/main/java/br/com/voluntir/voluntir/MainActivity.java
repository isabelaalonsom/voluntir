package br.com.voluntir.voluntir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.Preferencias;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntario.MenuVoluntarioActivity;


public class MainActivity extends AppCompatActivity {
    private final String tabelaOng = "ong", tabelaVoluntario = "voluntario";
    private ControleCadastro controleCadastro;
    private DatabaseReference bancoFirebase;
    private Ong ong;
    private Voluntario voluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        Context context = this.getApplicationContext();
        Preferencias preferencias = new Preferencias(context);
        Button btnONG = findViewById(R.id.btnONG);
        Button btnVoluntario = findViewById(R.id.btnVoluntario);
        if (preferencias.getEmailUsuarioLogado() != null || preferencias.getSenhaUsuarioLogado() != null) {
            controleCadastro = new ControleCadastro();
            if ((preferencias.getUsuarioLogado().equals("ong"))) {
                bancoFirebase = BancoFirebase.getBancoReferencia();
                Query pesquisa = bancoFirebase.child(tabelaOng).orderByChild("emailOng").equalTo(preferencias.getEmailUsuarioLogado());
                pesquisa.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ong = dataSnapshot.getValue(Ong.class);

                        }
                        if (ong != null) {

                            Intent intent = new Intent(context.getApplicationContext(), MenuOngActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("objeto", ong);
                            context.startActivity(intent);
                        } else {

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else if ((preferencias.getUsuarioLogado().equals("voluntario"))) {
                bancoFirebase = BancoFirebase.getBancoReferencia();
                Query pesquisa = bancoFirebase.child(tabelaVoluntario).orderByChild("email").equalTo(preferencias.getEmailUsuarioLogado());
                pesquisa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            voluntario = dataSnapshot.getValue(Voluntario.class);

                        }
                        if (voluntario != null) {
                            Intent intent = new Intent(context.getApplicationContext(), MenuVoluntarioActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("objeto", voluntario);
                            context.startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }

        btnONG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivityONG.class);
                startActivity(i);
            }
        });

        btnVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivityVoluntario.class);
                startActivity(i);
            }
        });
    }


}