package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.voluntir.controller.ControleCadastro;

public class MudarSenha extends AppCompatActivity {
    private EditText senha, confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);


        senha = (EditText) findViewById(R.id.edtTextRepetirSenhaContaTela);
        confirmarSenha = (EditText) findViewById(R.id.edtTextTelaSenha);


    }

    public void confirmar(View view) {
        if (senha.getText().toString().isEmpty() || confirmarSenha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos ",
                    Toast.LENGTH_SHORT).show();
        } else if (!senha.getText().toString().equals(confirmarSenha.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    "Senhas não conferem ",
                    Toast.LENGTH_SHORT).show();
        } else {
            String senha = this.senha.getText().toString();
            ControleCadastro controleCadastro = new ControleCadastro();
            controleCadastro.alterarSenha(senha, getApplicationContext());
        }

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