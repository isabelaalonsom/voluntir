package br.com.voluntir.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.voluntir.model.Voluntario;
import br.com.voluntir.voluntir.R;

public class AdapterPerfil extends RecyclerView.Adapter<AdapterPerfil.MyViewHolder> {
    Voluntario voluntario;

    public AdapterPerfil(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    @NonNull
    @Override
    public AdapterPerfil.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_perfil, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Voluntario voluntario = this.voluntario;
        holder.nome.setText(voluntario.getNome());
        holder.cpf.setText(voluntario.getCpf());
        holder.sobrenome.setText(voluntario.getSobrenome());
        holder.dataNascimento.setText(voluntario.getDatanasc());
        holder.genero.setText(voluntario.getGenero());
        holder.telefone.setText(voluntario.getTelefone());
        holder.email.setText(voluntario.getEmail());
        holder.endereco.setText(voluntario.getEndereco());
        holder.descricao.setText(voluntario.getEspecialidade());
        holder.status.setText(voluntario.getStatusVaga());
        if (voluntario.getStatusVaga() != null) {
            if (voluntario.getStatusVaga().equalsIgnoreCase("aprovado")) {
                holder.status.setTextColor(Color.GREEN);
            } else if (voluntario.getStatusVaga().equalsIgnoreCase("reprovado")) {
                holder.status.setTextColor(Color.RED);
            }
        }

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView cpf;
        TextView sobrenome;
        TextView dataNascimento;
        TextView genero;
        TextView telefone;
        TextView email;
        TextView endereco;
        TextView descricao;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtViewNomeDoVoluntarioVariavelAdapter);
            cpf = itemView.findViewById(R.id.txtViewCpfVariavelAdapter);
            sobrenome = itemView.findViewById(R.id.txtViewSobrenomeVariavelAdapter);
            dataNascimento = itemView.findViewById(R.id.txtViewDataNascVariavelAdapter);
            genero = itemView.findViewById(R.id.txtViewGeneroVariavelAdapter);
            telefone = itemView.findViewById(R.id.txtViewTelefoneVariavelAdapter);
            email = itemView.findViewById(R.id.txtViewEmailVariavelAdapter);
            endereco = itemView.findViewById(R.id.txtViewEnderecoVariavelAdapter);
            descricao = itemView.findViewById(R.id.txtViewDescricaoTecnicaVariavelAdapter);
            status = itemView.findViewById(R.id.txtViewStatusVoluntarioVagaAdapter);

        }
    }
}
