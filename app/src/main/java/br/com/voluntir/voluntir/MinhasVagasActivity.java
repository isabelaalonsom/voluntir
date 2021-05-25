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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
import java.util.ArrayList;
import java.util.List;

import br.com.voluntir.RecyclerItemClickListener;
import br.com.voluntir.adapter.AdapterVaga;
import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.ong.AprovacaoCandidatoActivity;
import br.com.voluntir.util.PdfCreator;
import br.com.voluntir.util.Util;

public class MinhasVagasActivity extends AppCompatActivity {
    private final List<Vaga> listaVaga = new ArrayList<>();
    private final List<Voluntario> listaVoluntario = new ArrayList<>();
    private final DatabaseReference bancoReferencia = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference tabelaVaga = bancoReferencia.child("vaga");
    private RecyclerView recyclerView;
    private Vaga vaga = new Vaga();
    private String informacoes = "";
    private Ong ong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Voluntir");
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerViewVaga);
        Bundle dados = getIntent().getExtras();
        ong = (Ong) dados.getSerializable("objeto");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        Query teste = tabelaVaga.orderByChild("idOng").equalTo(ong.getIdOng());
        teste.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVaga.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    vaga = dataSnapshot.getValue(Vaga.class);

                    if (vaga.getIdOng().equals(ong.getIdOng())) {
                        listaVaga.add(vaga);

                    }

                }
                AdapterVaga adapterVaga = new AdapterVaga(listaVaga);
                recyclerView.setAdapter(adapterVaga);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Vaga vaga = listaVaga.get(position);

                                if (vaga.getVoluntarios() == null) {
                                    Toast.makeText(getApplicationContext(),
                                            "Nenhum candidato cadastrado ",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), AprovacaoCandidatoActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("objeto", vaga);
                                    intent.putExtra("ong", ong);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onLongItemClick(View view, int position) throws IOException, DocumentException {
                                Vaga vaga = listaVaga.get(position);
                                if (vaga.getVoluntarios() == null) {
                                    Toast.makeText(getApplicationContext(),
                                            "Nenhum candidato cadastrado ",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Vaga vaga2 = listaVaga.get(position);
                                    gerarPDF(view, position, vaga2);
                                }
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {

            String[] permissao = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };

            Util.validate(this, 17, permissao);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voltar:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void gerarPDF(View view, int position, Vaga vaga) throws IOException, DocumentException {

        OutputStream outputStream;
        File pdf = null;
        Uri uri = null;
        double numeroRandom = Math.random();
        ParcelFileDescriptor descriptor = null;

        //Se o celular utilizado for da versão 10 do android ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Candidatos" + numeroRandom);
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Pedido/");

            ContentResolver resolver = getContentResolver();

            uri = resolver.insert(MediaStore.Downloads.getContentUri("external"), contentValues);
            descriptor = resolver.openFileDescriptor(uri, "rw");
            FileDescriptor fileDescriptor = descriptor.getFileDescriptor();

            outputStream = new FileOutputStream(fileDescriptor);

            //Se o celular utilizado for da versão 9 do android ou inferior
        } else {

            File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File diretorio = new File(diretorioRaiz.getPath() + "/Aprovados/");

            //Se o diretório não existe, então cria o diretório Aprovados
            if (!diretorio.exists()) {
                diretorio.mkdir();
            }

            //Dando nome ao PDF que é Candidatos e um número randomico
            String nomeArquivo = diretorio.getPath() + "/Candidatos" + numeroRandom + ".pdf";
            pdf = new File(nomeArquivo);

            //Gravar informações dentro do arquivo PDF
            outputStream = new FileOutputStream(pdf);

        }

        Rectangle rectangle = new Rectangle(200, 200);

        //Folha que insere as informações
        Document document = new Document(rectangle, 5, 5, 5, 5);
        PdfCreator pdfCreator = new PdfCreator();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);

        pdfWriter.setBoxSize("box", new Rectangle(0, 0, 0, 0));
        pdfWriter.setPageEvent(pdfCreator);

        document.open();


        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);


        //Primeira info
        Paragraph paragraph = new Paragraph("Candidatos Aprovados", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
        listaVoluntario.clear();

        //Segunda info
        int qtdAprovados = 0;
        if (vaga.getVoluntarios() == null) {
            Toast.makeText(getBaseContext(), "Não há nenhum candidato cadastrado nesta vaga", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                Voluntario voluntario = vaga.getVoluntarios().get(i);
                if (voluntario.getStatusVaga().equalsIgnoreCase("APROVADO")) {
                    qtdAprovados++;
                    String nome = "Nome: " + voluntario.getNome() + " " + voluntario.getSobrenome();
                    String telefone = "Telefone: " + voluntario.getTelefone();
                    String cidade = "Endereço: " + voluntario.getEndereco();
                    informacoes = nome + " | " + telefone + " | " + cidade;
                    paragraph = new Paragraph(informacoes, font);
                    document.add(paragraph);
                }
            }
        }
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
        Paragraph paragraph1 = new Paragraph("Quantidade de vagas oferecidas pela ONG:" + vaga.getQtdCandidaturas(), font2);
        paragraph1.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph1);
        listaVoluntario.clear();

        Paragraph paragraph2 = new Paragraph("Quantidade de vagas preenchidas:" + qtdAprovados, font2);
        paragraph2.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph2);
        listaVoluntario.clear();
        //Segunda info

        document.close();
        outputStream.close();

        //Se o celular utilizado for da versão 10 do android ou superior

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                descriptor.close();
                visualizarPdfUri(uri);

            } else {

                visualizarPdfFile(pdf);
            }


    }

    //Versões antigas
    private void visualizarPdfFile(File pdf) {

        PackageManager packageManager = getPackageManager();
        //Visualização
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Tipo de arquivo que quer visualizar
        intent.setType("application/pdf");

        //Verificar quais aplicativos que podem ler PDF
        List<ResolveInfo> lista = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        //Se tem um ou mais aplicativo que lê pdf, se não dá a mensagem de que não possui nenhum PDF
        if (lista.size() > 0) {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri uri = FileProvider.getUriForFile(getBaseContext(), "br.com.voluntir.voluntir", pdf);

            intent1.setDataAndType(uri, "application/pdf");

            startActivityForResult(intent1, 1234);
        } else {
            Toast.makeText(getBaseContext(), "Você não possui nenhum leitor PDF no seu dispositivo.", Toast.LENGTH_LONG).show();

        }

    }

    //Versões novas
    private void visualizarPdfUri(Uri uri) {

        PackageManager packageManager = getPackageManager();
        //Visualização
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Tipo de arquivo que quer visualizar
        intent.setType("application/pdf");

        //Verificar quais aplicativos que podem ler PDF
        List<ResolveInfo> lista = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        //Se tem um ou mais aplicativo que lê pdf, se não dá a mensagem de que não possui nenhum PDF
        if (lista.size() > 0) {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent1.setDataAndType(uri, "application/pdf");

            startActivityForResult(intent1, 1234);
        } else {
            Toast.makeText(getBaseContext(), "Você não possui nenhum leitor PDF no seu dispositivo.", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissao = true;

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                permissao = false;
                break;
            }
        }

        if (!permissao) {

            Toast.makeText(getBaseContext(), "Aceite as permissões necessárias para o aplicativo funcionar", Toast.LENGTH_LONG).show();
            finish();


        }

    }
}