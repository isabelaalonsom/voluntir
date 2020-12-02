package br.com.voluntir.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AdapterAprovacao extends RecyclerView.Adapter<AdapterAprovacao.MyViewHolder> {
    private List<Voluntario> listaVoluntario;
    private List<Vaga> listaVaga;

    public AdapterAprovacao(List<Voluntario> listaVoluntario) {
        this.listaVoluntario = listaVoluntario;
    }

    @NonNull
    @Override
    public AdapterAprovacao.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_aprovacao2, parent, false);

        return new AdapterAprovacao.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAprovacao.MyViewHolder holder, int position) {
        //Vaga vaga = listaVaga.get(position);
        Voluntario voluntario = listaVoluntario.get(position);

        holder.candidato.setText("Victor Capel");
        //holder.candidato.setText(voluntario.getNome());

    }

    @Override
    public int getItemCount() {
        return listaVoluntario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView candidato;
        Button btnAprovar;

        public MyViewHolder(View itemView) {

            super(itemView);

            candidato = itemView.findViewById(R.id.txtCandidato);
            btnAprovar = itemView.findViewById(R.id.btnAprovar);

        }
    }
}
