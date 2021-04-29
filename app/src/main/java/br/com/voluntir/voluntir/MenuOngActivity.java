package br.com.voluntir.voluntir;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.ong.MinhaContaONGActivity;
import br.com.voluntir.util.PdfCreator;
import br.com.voluntir.util.Util;


public class MenuOngActivity extends AppCompatActivity {
    Voluntario voluntario;
    Ong ong;
    Button botaoCriarVaga;
    TextView txtEmailOng, txtNomeOng, txtIdOng, txtTituloPDF;
    private FirebaseAuth autenticacao;
    ControleCadastro controleCadastro;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference bancoFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ong);

        getSupportActionBar().hide();

        Bundle dados = getIntent().getExtras();

        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
        }
        /*Toast.makeText(getApplicationContext(),
                "Id " + ong.getIdOng(),
                Toast.LENGTH_SHORT).show();*/


//        new Thread(() -> {
//            txtNomeOng = (TextView) findViewById(R.id.txtViewNomeOng);
//            txtNomeOng.setText(ong.getNome());
//        });

        runOnUiThread(new Thread((Runnable) () -> {
            try {
                txtNomeOng = (TextView) findViewById(R.id.txtViewNomeOng);
                txtNomeOng.setText(ong.getNome());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "erro: " + e.getCause(), Toast.LENGTH_LONG).show();
            }
        }));

        autenticacao = BancoFirebase.getFirebaseAutenticacao();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void preencheTextViewEmail() {
        txtEmailOng.setText(ong.getEmailOng());
        txtNomeOng.setText(ong.getNome());
    }

    private void preencheOng() {
        String email = txtEmailOng.getText().toString();
        String nome = txtNomeOng.getText().toString();
        ong.setEmailOng(email);
        ong.setNome(nome);

    }

    public void clicarMinhaConta(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhaContaONGActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",ong);
        startActivity(intent);
    }

    public void clicarCriarVaga(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastroVagaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto",ong);
        startActivity(intent);

    }

    public void clicarMinhasVagas(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhasVagasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }

}