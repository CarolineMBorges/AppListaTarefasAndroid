package com.android.listatarefas.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.listatarefas.R;
import com.android.listatarefas.model.Tarefa;

import java.util.List;

//Com isso estamos acessando a classe MyViewHolder
//Tarefa adapter precisa receber uma lista de tarefas para ser exibida dentro dos métodos
public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    //o construtor irá receber a lista de tarefas
    public TarefaAdapter(List<Tarefa> lista) {
            this.listaTarefas = lista;
    }

    @NonNull
    //retorna os itens de lista que é passado para o MyViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*
        * Dentro do método getView precisamos transformar numa View o xml contendo o layout do item da lista,
        * para isso usamos o método inflate do LayoutInflater.
        * */
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_tarela_adapter, viewGroup, false);

        return new MyViewHolder( itemLista );
    }

    /*
    *  Dentro do onBindViewHolder colocamos ações de clicks das funções do recyclerview
    * */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        myViewHolder.tarefa.setText( tarefa.getNomeTarefa() );

    }

    //quantos itens estão sendo exibidos
    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tarefa;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefaId);

        }
    }
}
