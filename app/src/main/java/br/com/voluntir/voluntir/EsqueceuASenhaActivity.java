package br.com.voluntir.voluntir;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.com.voluntir.BancoFirebase;
import br.com.voluntir.controller.ControleLogin;

import static br.com.voluntir.voluntir.R.layout.activity_esqueceu_a_senha;

public class EsqueceuASenhaActivity extends AppCompatActivity {

    private EditText email;
    private String emailEnviar;
    private Button botaoEnviar;
    private ControleLogin controleLogin;
    public EsqueceuASenhaActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_esqueceu_a_senha);

        getSupportActionBar().hide();

        botaoEnviar = (Button) findViewById(R.id.btnEnviar);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.edtTextEmail);
                emailEnviar = email.getText().toString();

                if (emailEnviar.isEmpty() ){
                    Toast.makeText(getApplicationContext(),
                            "Preencha o campo ",
                            Toast.LENGTH_SHORT).show();
                }else {
                    controleLogin = new ControleLogin();
                    controleLogin.enviarEmailRecuperarSenha(emailEnviar, getApplicationContext());
                    //enviarEmail(emailEnviar);
                }

            }
        });
    }


}