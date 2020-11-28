package br.com.voluntir.voluntir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VagasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagas);

        getSupportActionBar().hide();
    }


}