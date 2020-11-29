package br.com.voluntir.voluntir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.model.Ong;
import br.com.voluntir.model.Voluntario;


public class MainActivity extends AppCompatActivity {

    Voluntario voluntario = new Voluntario();
    Ong ong = new Ong();
    public String isVoluntario;
    public String isOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button btnONG = findViewById(R.id.btnONG);
        Button btnVoluntario = findViewById(R.id.btnVoluntario);

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