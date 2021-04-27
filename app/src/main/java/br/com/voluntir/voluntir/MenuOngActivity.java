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

        //-------- PDF ----------//

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {

            String permissao[] = new String[] {
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

/*    public void clicarVagas(View view) {
        Intent intent = new Intent(this, VagaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }*/

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

    public void gerarPDF(View view) throws IOException, DocumentException {

        OutputStream outputStream;
        File pdf = null;
        Uri uri = null;
        double numeroRandom = Math.random();
        ParcelFileDescriptor descriptor = null;

        //Se o celular utilizado for da versão 10 do android ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"application/pdf");
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Candidatos"+numeroRandom);
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DOWNLOADS+"/Pedido/");

            ContentResolver resolver = getContentResolver();

            uri = resolver.insert(MediaStore.Downloads.getContentUri("external"), contentValues);
            descriptor = resolver.openFileDescriptor(uri, "rw");
            FileDescriptor fileDescriptor = descriptor.getFileDescriptor();

            outputStream = new FileOutputStream(fileDescriptor);

            //Se o celular utilizado for da versão 9 do android ou inferior
        } else {

            File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File diretorio = new File(diretorioRaiz.getPath()+"/Aprovados/");

            //Se o diretório não existe, então cria o diretório Aprovados
            if (!diretorio.exists()) {
                diretorio.mkdir();
            }

            //Dando nome ao PDF que é Candidatos e um número randomico
            String nomeArquivo = diretorio.getPath()+"/Candidatos"+numeroRandom+".pdf";
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

        String id = "ID Candidatos: " + numeroRandom;

        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

        //Primeira info
        Paragraph paragraph = new Paragraph("Candidatos Aprovados", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);

        //Segunda info
        paragraph = new Paragraph(id, font);
        document.add(paragraph);

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
        if(lista.size() > 0) {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri uri = FileProvider.getUriForFile(getBaseContext(), "br.com.voluntir.voluntir",pdf);

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
        if(lista.size() > 0) {
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

        for (int result: grantResults) {
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