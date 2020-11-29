package br.com.voluntir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import br.com.voluntir.model.Vaga;
import br.com.voluntir.voluntir.R;

public class AdapterVaga extends RecyclerView.Adapter<AdapterVaga.MyViewHolder>{

    private List<Vaga> listaServico;

    public AdapterVaga() {

    }

    public AdapterVaga(List<Vaga> lista) {
        this.listaServico = lista;
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
        Vaga vaga = listaServico.get(position);
        holder.descricao.setText(vaga.getDescricaoVaga());
        holder.vaga.setText(vaga.getAreaConhecimento());
        //holder.vaga.setText(Integer.toString(vaga.getIdServico()));
        //holder.dataInicio.setText("5");

        //holder.dataFinal.setText(vaga.getServicoVoluntarioModel());
    }

    @Override
    public int getItemCount() {
        return listaServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descricao;
        TextView vaga;
        TextView dataFinal;
        TextView horario;
        //TextView categoria;

        public MyViewHolder(View itemView) {

            super(itemView);

            descricao = itemView.findViewById(R.id.textDescricao);
            vaga = itemView.findViewById(R.id.textVaga);
            dataFinal = itemView.findViewById(R.id.textDataFinal);

        }
    }
}
