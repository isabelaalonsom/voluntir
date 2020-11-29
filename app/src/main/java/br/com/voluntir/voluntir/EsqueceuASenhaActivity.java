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

public class EsqueceuASenhaActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private EditText email;
    private String emailEnviar;
    private Button botaoEnviar;

    EditText edtTextEmail = findViewById(R.id.edtTextEmail);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_a_senha);

        getSupportActionBar().hide();
        botaoEnviar = (Button) findViewById(R.id.btnEnviar);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailEnviar = email.getText().toString();

                if (emailEnviar.isEmpty() ){
                    Toast.makeText(getApplicationContext(),
                            "Preencha o campo ",
                            Toast.LENGTH_SHORT).show();
                }else{
                    enviarEmail(emailEnviar);
                }

            }
        });
    }

    private void enviarEmail(String email){
        autenticacao = BancoFirebase.getFirebaseAutenticacao();
        autenticacao.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao enviar email ",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("EMAIL ENVIADO", "Email enviado.");
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao enviar email ",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("EMAIL NÃO ENVIADO", "Email não enviado.");
                        }
                    }
                });
    }

}