package br.com.voluntir.model;

public class Candidatura {

    private int idCandidatura;
    private boolean aprovado;

    //não sei como entra as foreign keys
    //foreign key (idVaga) references vaga(idVaga),
    //foreign key (cpfVoluntario) references voluntario(cpf)


    public int getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(int idCandidatura) {
        this.idCandidatura = idCandidatura;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
