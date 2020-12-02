package br.com.voluntir.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Ong implements Serializable {
    private String idOng;
    private String nome;
    private String emailOng;
    private String senhaOng;
    private String cpnj;
    private String site;
    private String localizacao;
    private String causas;
    private String resumoOng;
    private String telefone;

    public Ong() {
    }

    public String getIdOng() {
        return idOng;
    }

    public void setIdOng(String idOng) {
        this.idOng = idOng;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailOng() {
        return emailOng;
    }

    public void setEmailOng(String emailOng) {
        this.emailOng = emailOng;
    }

    @Exclude
    public String getSenhaOng() {
        return senhaOng;
    }

    public void setSenhaOng(String senhaOng) {
        this.senhaOng = senhaOng;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getCausas() {
        return causas;
    }

    public void setCausas(String causas) {
        this.causas = causas;
    }

    public String getResumoOng() {
        return resumoOng;
    }

    public void setResumoOng(String resumoOng) {
        this.resumoOng = resumoOng;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isOng() {
        return true;
    }
}
