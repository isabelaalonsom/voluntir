package br.com.voluntir.voluntir;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import br.com.voluntir.controller.ControleCadastro;
import br.com.voluntir.model.Voluntario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginVoluntarioTest {

    @Test
    public void testeDeLoginVoluntario() {

        Context contexto = ApplicationProvider.getApplicationContext();
        ControleCadastro controleCadastro = new ControleCadastro();

        Voluntario voluntario;
        voluntario = mockLoginVoluntarioExistente();

        controleCadastro.validarLoginVoluntario(voluntario, "voluntario", contexto);

        assertTrue(voluntario.isVoluntario());
        //assertThat(EmailValidator.isValidEmail("name@email.com")).isTrue();

    }

    @Test
    public void ummaisum() {
        assertEquals(2, 1+1);
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public Voluntario mockLoginVoluntarioExistente() {

        Voluntario voluntario = new Voluntario();

        voluntario.setNome("Thais");
        voluntario.setSobrenome("Gomes");
        voluntario.setEmail("thaisgomes@gmail.com");
        voluntario.setGenero("Feminino");
        voluntario.setCpf("123.456.789-11");
        voluntario.setTelefone("(11)92222-4444");
        voluntario.setSenha("thais123");
        voluntario.setConfirmarSenha("thais123");
        voluntario.setEndereco("Rua do Café 154");
        voluntario.setDatanasc("18/04/1995");
        voluntario.setEspecialidade("Conhecimento em informática");
        voluntario.setIdVoluntario("1234567890");


        return voluntario;

    }


}