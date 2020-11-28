package br.com.voluntir.voluntario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.voluntir.voluntir.R;


public class MinhaContaVoluntarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta_voluntario);

        getActionBar().hide();
    }


}