package br.com.voluntir.adapter;

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

public class AdapterPerfil extends RecyclerView.Adapter<AdapterPerfil.MyViewHolder> {
    private List<Voluntario> listaVoluntario;
    private List<Vaga> listaVaga;
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
        //Vaga vaga = listaVaga.get(position);
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
