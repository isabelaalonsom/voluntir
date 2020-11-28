package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EsqueceuASenhaActivity extends AppCompatActivity {

    EditText edtTextEmail = findViewById(R.id.edtTextEmail);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_a_senha);

        getSupportActionBar().hide();
    }

    public void clicarEnviar(View view) {
        //se for igual a algum cadastrado no sistema
        String email = edtTextEmail.getText().toString();

        //push creates a unique id in database
        //demoRef.push().setValue(value);

        Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();
    }



}