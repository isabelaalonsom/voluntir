package br.com.voluntir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AdapterAprovacao extends RecyclerView.Adapter<AdapterAprovacao.MyViewHolder> {
    private List<Voluntario> listaVoluntario;

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
        holder.nome.setText(voluntario.getNome() + " " + voluntario.getSobrenome());
        holder.status.setText("Status: " + voluntario.getStatusVaga());
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
            status = itemView.findViewById(R.id.txtStatusVoluntario);
        }
    }
}
