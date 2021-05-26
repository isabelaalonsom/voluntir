package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.voluntir.controller.ControleLogin;

import static br.com.voluntir.voluntir.R.layout.activity_esqueceu_a_senha;

public class EsqueceuASenhaActivity extends AppCompatActivity {

    private EditText email;
    private String emailEnviar;
    private Button botaoEnviar;
    private ControleLogin controleLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_esqueceu_a_senha);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);


        botaoEnviar = (Button) findViewById(R.id.btnEnviar);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.edtTextEmail);
                emailEnviar = email.getText().toString();

                if (emailEnviar.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Preencha o campo ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    controleLogin = new ControleLogin();
                    controleLogin.enviarEmailRecuperarSenha(emailEnviar, getApplicationContext());
                }

            }
        });
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


}