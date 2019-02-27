package com.android.listatarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {

    /* SQLiteOpenHelper também gerencia versões do banco de dados
     * */
    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";


    public DbHelper(Context context ) {
        super(context, NOME_DB, null, VERSION);
    }

    //onCreate utilizamos para criar pela primeira vez nosso banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS +
                      " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                      " nome TEXT NOT NULL); ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");


        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }

    }

    /* onUpgrade utilizamos por exemplo, quando criamos uma outra versão do app
    *  e precisarmos criar mais tabelas ou fazer alterações nas tabelas que já existem
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //para alterar a tabela


        //para deletar a tabela
        String sql = "DROP TABLE IF  EXISTS " + TABELA_TAREFAS + " ;";


        //para alterar a tabela - no caso adicionamos uma nova coluna
        //String sql = "ALTER TABLE " + TABELA_TAREFAS +  " ADD COLUMN status VARCHAR(2) " ;

        try {
            db.execSQL(sql);

            //para criar novamente a tabela
            onCreate(db);

            Log.i("INFO DB", "Sucesso ao atualizar tabela");


        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar a tabela" + e.getMessage());
        }

    }
}
