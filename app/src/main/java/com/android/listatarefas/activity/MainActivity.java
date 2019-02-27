package com.android.listatarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.listatarefas.R;
import com.android.listatarefas.adapter.TarefaAdapter;
import com.android.listatarefas.helper.DbHelper;
import com.android.listatarefas.helper.RecyclerItemClickListener;
import com.android.listatarefas.helper.TarefaDAO;
import com.android.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //configurar recyclerView
        recyclerView = findViewById(R.id.recyclerViewId);


        //adicionar um evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            //usaremos para a edição de itens
                            @Override
                            public void onItemClick(View view, int position) {

                                //recuperar tarefa para edição
                                Tarefa tarefaSelecionada = listaTarefas.get( position );

                                //Envia tarefa para a tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefasActivity.class);

                                //para abrir a tela com o texto da tarefa selecionada
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity( intent );
                            }

                            //usaremos para deletar itens
                            @Override
                            public void onLongItemClick(View view, int position) {

                                //recupera tarefa para deletar
                                tarefaSelecionada = listaTarefas.get( position );

                                //usamos a caixa de alerta para a pessoa confirmar ou cancelar a exclusão
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //configura título e mensagem
                                dialog.setTitle("Confirmar Exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + " ?");

                                //botão de exclusão
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TarefaDAO tarefaDAO =  new TarefaDAO(getApplicationContext());

                                        if( tarefaDAO.deletar( tarefaSelecionada )){

                                            //para que lista seja atualizada sem a tarefa que foi deletada
                                            carregarListaTarefas();

                                            Toast.makeText(getApplicationContext(), "Sucesso ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();

                                        }else{

                                            Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                                //se a pessoa não quiser deletar a tarefa, nenhuma ação será tomada
                                dialog.setNegativeButton("Não", null);

                                //exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        // o FloatingActionButton será usado para adicionar tarefa
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefasActivity.class);
                startActivity(intent);
            }
        });

    }

    public void carregarListaTarefas(){
            //listar tarefas
            TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext());
            listaTarefas = tarefaDAO.listar();

            //exibir lista de tarefas

            //configurar um adapter
            tarefaAdapter = new TarefaAdapter( listaTarefas );

            //configurar um recyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
            recyclerView.setLayoutManager( layoutManager );
            recyclerView.setHasFixedSize( true );
            //divisor
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            recyclerView.setAdapter( tarefaAdapter );
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
