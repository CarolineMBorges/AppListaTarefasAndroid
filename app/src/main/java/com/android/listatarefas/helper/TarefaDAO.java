package com.android.listatarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

//classe responsável por salvar os dados para tarefa
public class TarefaDAO implements InterfaceTarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        Log.i("INFO", "Tarefa salva com sucesso");

        try{
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);

        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{

            String[] args = {tarefa.getId().toString()};
            //"id=?" interrogação é um caracter "coringa" que é substituido pelo último parâmetro
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.i("INFO", "Tarefa atualizada com sucesso!");

        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] args = { tarefa.getId().toString() };
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args );
            Log.i("INFO", "Tarefa excluida com sucesso!");

        }catch (Exception e){
            Log.i("INFO", "Erro ao excluir tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        //seleciona todos os dados
        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;" ;

        //o parametro selectionArgs são parametros de seleção - por exemplo filtros
        Cursor c = le.rawQuery(sql, null);

        //percorrendo o cursor
        while( c.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong( c.getColumnIndex("id"));
            String nomeTarefa = c.getString( c.getColumnIndex("nome"));

            tarefa.setId( id );
            tarefa.setNomeTarefa( nomeTarefa );

            tarefas.add( tarefa );
        }
        return tarefas;
    }
}
