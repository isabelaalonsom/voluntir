package br.com.voluntir.model;

import java.io.Serializable;

public class Vaga implements Serializable {

    //private String idVaga;
    private int idVaga;
    private int qtdCandidaturas;
    private String areaConhecimento;
    private String descricaoVaga;
    private String dataInicio;
    private String dataTermino;
    private String periodicidade;
    private String horario;
    private String nomeOng;
    private String idOng;
    Voluntario voluntario;

    public Vaga() {
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }


    public String getDescricaoVaga() {
        return descricaoVaga;
    }

    public void setDescricaoVaga(String descricaoVaga) {
        this.descricaoVaga = descricaoVaga;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getIdOng() {
        return idOng;
    }

    public void setIdOng(String idOng) {
        this.idOng = idOng;
    }
    //public String getIdVaga() {
    //     return idVaga;
    //}

    //public void setIdVaga(String idVaga) {
    //    this.idVaga = idVaga;
    //}

    public int getQtdCandidaturas() {
        return qtdCandidaturas;
    }

    public void setQtdCandidaturas(int qtdCandidaturas) {
        this.qtdCandidaturas = qtdCandidaturas;
    }

    public String getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
}
