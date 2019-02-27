package com.android.listatarefas.helper;

import com.android.listatarefas.model.Tarefa;

import java.util.List;

public interface InterfaceTarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
