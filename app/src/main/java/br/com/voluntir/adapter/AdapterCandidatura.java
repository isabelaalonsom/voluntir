package br.com.voluntir.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AdapterCandidatura extends RecyclerView.Adapter<AdapterCandidatura.MyViewHolder> {
    private final List<Vaga> listaVaga;
    private final Voluntario voluntario;
    private String status;

    public AdapterCandidatura(List<Vaga> lista, Voluntario voluntario) {
        this.listaVaga = lista;
        this.voluntario = voluntario;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_candidatura, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vaga vaga = listaVaga.get(position);
        if (vaga.getVoluntarios() != null) {
            for (int i = 0; i < vaga.getVoluntarios().size(); i++) {
                if (vaga.getVoluntarios().get(i).getIdVoluntario().equals(voluntario.getIdVoluntario())) {
                    status = vaga.getVoluntarios().get(i).getStatusVaga();

                }

            }

        }

        holder.nomeOng.setText(vaga.getNomeOng());
        holder.areaConhecimento.setText(vaga.getAreaConhecimento());
        holder.vaga.setText("Vagas:" + (vaga.getQtdCandidaturas() - vaga.getVoluntarios().size()));
        if (status.equalsIgnoreCase("aprovado")) {
            holder.txtViewStatusVariavel.setTextColor(Color.GREEN);
        } else if (status.equalsIgnoreCase("reprovado")) {
            holder.txtViewStatusVariavel.setTextColor(Color.RED);
        }
        holder.txtViewStatus.setVisibility(View.VISIBLE);
        holder.txtViewStatusVariavel.setText(status);
//        if (holder.txtViewStatusVariavel.getText() == "APROVADO") {
//            holder.txtViewStatusVariavel.setTextColor(Integer.parseInt("GREEN"));
//        }


    }

    @Override
    public int getItemCount() {
        return listaVaga.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeOng;
        TextView areaConhecimento;
        TextView vaga;
        TextView txtViewStatus, txtViewStatusVariavel;


        public MyViewHolder(View itemView) {

            super(itemView);

            nomeOng = itemView.findViewById(R.id.textNomeOng);
            vaga = itemView.findViewById(R.id.txtVagas);
            areaConhecimento = itemView.findViewById(R.id.textAreaConhecimento);

            txtViewStatus = itemView.findViewById(R.id.txtStatus);
            txtViewStatusVariavel = itemView.findViewById(R.id.txtStatusVariavel);

        }
    }

}
