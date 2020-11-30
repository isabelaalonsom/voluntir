package br.com.voluntir.voluntario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.voluntir.voluntir.R;

public class CandidaturaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatura);

        getSupportActionBar().hide();
    }
}