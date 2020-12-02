package br.com.voluntir.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.R;

public class AdapterVaga extends RecyclerView.Adapter<AdapterVaga.MyViewHolder> {
    private List<Vaga> listaVaga;

    public AdapterVaga(List<Vaga> lista) {
        this.listaVaga = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vaga, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vaga vaga = listaVaga.get(position);
        holder.nomeOng.setText(vaga.getNomeOng());
        holder.areaConhecimento.setText(vaga.getAreaConhecimento());
        holder.vaga.setText("5");

        holder.txtViewStatus.setVisibility(View.INVISIBLE);
        holder.txtViewStatusVariavel.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return listaVaga.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descricao;
        TextView nomeOng;
        TextView areaConhecimento;
        TextView vaga;
        TextView dataFinal;
        TextView horario;
        //TextView categoria;
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
