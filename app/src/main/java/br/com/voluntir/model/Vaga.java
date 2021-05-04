package br.com.voluntir.model;

import java.io.Serializable;
import java.util.List;

public class Vaga implements Serializable {

    //private String idVaga;
    private String idVaga;
    private int qtdCandidaturas;
    private String areaConhecimento;
    private String descricaoVaga;
    private String dataInicio;
    private String dataTermino;
    private String periodicidade;
    private String nomeOng;
    private String idOng;
    private String cargaHoraria;
    private String idVoluntario;
    private List<Voluntario> voluntarios;


    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }


    public Vaga() {
    }

    public String getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(String idVaga) {
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

    public String getIdOng() {
        return idOng;
    }

    public void setIdOng(String idOng) {
        this.idOng = idOng;
    }

    public List<Voluntario> getVoluntarios() {
        //voluntario.getSobrenome();
        //voluntario.getNome();
        return voluntarios;
    }

    public void setVoluntarios(List<Voluntario> voluntarios) {
        this.voluntarios = voluntarios;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

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


}
