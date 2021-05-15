package br.com.voluntir;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private static final String NOME_ARQUIVO = "app.preferencias";
    private final String EMAIL_USUARIO_LOGADO = "email_usuario_logado", SENHA_USUARIO_LOGADO = "senha_usuario_logado", USUARIO_LOGADO = "usuario_logado";
    private final Context context;
    private final SharedPreferences preferences;
    private final int mode = 0;
    private final SharedPreferences.Editor editor;

    public Preferencias(Context contextoParametro) {
        context = contextoParametro;

        preferences = context.getSharedPreferences(NOME_ARQUIVO, mode);

        editor = preferences.edit();

    }

    public void salvarUsuarioPreferencias(String email, String senha, String usuario) {
        editor.putString(EMAIL_USUARIO_LOGADO, email);
        editor.putString(SENHA_USUARIO_LOGADO, senha);
        editor.putString(USUARIO_LOGADO, usuario);

        editor.commit();
    }

    public String getEmailUsuarioLogado() {
        return preferences.getString(EMAIL_USUARIO_LOGADO, null);
    }

    public String getSenhaUsuarioLogado() {
        return preferences.getString(SENHA_USUARIO_LOGADO, null);
    }

    public String getUsuarioLogado() {
        return preferences.getString(USUARIO_LOGADO, null);
    }

}
