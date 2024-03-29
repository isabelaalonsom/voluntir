package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Ong;
import br.com.voluntir.ong.CadastroVagaActivity;
import br.com.voluntir.ong.MinhaContaONGActivity;


public class MenuOngActivity extends AppCompatActivity {
    private Ong ong;
    private TextView txtNomeOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ong);

        Bundle dados = getIntent().getExtras();

        if (dados != null) {
            ong = (Ong) dados.getSerializable("objeto");
        }

        runOnUiThread(new Thread((Runnable) () -> {
            try {
                txtNomeOng = (TextView) findViewById(R.id.txtViewNomeOng);
                txtNomeOng.setText(ong.getNome());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "erro: " + e.getCause(), Toast.LENGTH_LONG).show();
            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void clicarMinhaConta(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhaContaONGActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }

    public void clicarCriarVaga(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastroVagaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);

    }

    public void clicarMinhasVagas(View view) {
        Intent intent = new Intent(getApplicationContext(), MinhasVagasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }

    public void clicarEditarVagas(View view) {
        Intent intent = new Intent(getApplicationContext(), EditarMinhasVagas.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("objeto", ong);
        startActivity(intent);
    }

}