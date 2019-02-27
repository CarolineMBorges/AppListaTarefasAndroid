package com.android.listatarefas.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.listatarefas.R;
import com.android.listatarefas.helper.TarefaDAO;
import com.android.listatarefas.model.Tarefa;

public class AdicionarTarefasActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefas);

        editTarefa = findViewById(R.id.textTarefaId);

        //recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar tarefa na caixa de texto
        if( tarefaAtual != null ){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){

            //executa ação para o itemSalvar
            case R.id.itemSalvar:

               TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

               if( tarefaAtual!= null ){ //edicao

                   String nomeTarefa = editTarefa.getText().toString();

                   if (!nomeTarefa.isEmpty()) {

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa( nomeTarefa );
                        tarefa.setId( tarefaAtual.getId());

                        //atualizando banco de dados
                       if( tarefaDAO.atualizar(tarefa) ){
                           finish();
                           Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!",
                                   Toast.LENGTH_SHORT).show();

                       }else{
                           Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa!",
                                   Toast.LENGTH_SHORT).show();

                       }
                   }


                }else { //salvar

                   String nomeTarefa = editTarefa.getText().toString();

                   if (!nomeTarefa.isEmpty()) {

                       Tarefa tarefa = new Tarefa();
                       tarefa.setNomeTarefa(nomeTarefa);

                       if( tarefaDAO.salvar( tarefa )) {
                           finish();

                           Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!",
                                   Toast.LENGTH_SHORT).show();
                       } else{
                           Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!",
                                   Toast.LENGTH_SHORT).show();
                       }
                   }
               }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
