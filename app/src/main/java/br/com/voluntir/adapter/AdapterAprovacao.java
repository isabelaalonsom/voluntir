package br.com.voluntir.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AdapterAprovacao extends RecyclerView.Adapter<AdapterAprovacao.MyViewHolder> {
    private final List<Voluntario> listaVoluntario;

    public AdapterAprovacao(List<Voluntario> lista) {
        this.listaVoluntario = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_aprovacao, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Voluntario voluntario = listaVoluntario.get(position);
        if (voluntario != null) {
            holder.nome.setText(voluntario.getNome() + " " + voluntario.getSobrenome());
            holder.status.setText(voluntario.getStatusVaga());
            if (voluntario.getStatusVaga().equalsIgnoreCase("aprovado")) {
                holder.status.setTextColor(Color.GREEN);
            } else if (voluntario.getStatusVaga().equalsIgnoreCase("reprovado")) {
                holder.status.setTextColor(Color.RED);
            }
        }

    }

    @Override
    public int getItemCount() {
        return listaVoluntario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtCandidato);
            status = itemView.findViewById(R.id.txtStatusVoluntarioVariavel);
        }
    }
}
