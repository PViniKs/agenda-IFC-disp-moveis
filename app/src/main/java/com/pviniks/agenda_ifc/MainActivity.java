package com.pviniks.agenda_ifc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pviniks.agenda_ifc.controller.ContatoController;
import com.pviniks.agenda_ifc.model.Contato;
import com.pviniks.agenda_ifc.model.ContatoDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton fabAdd;
    private List<Contato> contatos;
    private ArrayAdapter<Contato> adapter;
    private ContatoController contatoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicializarViews();
        carregarContatos();
        configurarListeners();
    }

    private void inicializarViews() {
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContatoActivity.class);
            startActivity(intent);
        });
    }

    private void carregarContatos() {
        contatoController = new ContatoController(this);
        contatos = contatoController.listarContatos();

        adapter = new ArrayAdapter<>(this, R.layout.list_item, contatos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
                }
                Contato contato = getItem(position);

                TextView txtNomeLista = convertView.findViewById(R.id.txtNomeLista);
                TextView txtTelLista = convertView.findViewById(R.id.txtTelLista);
                TextView txtEmailLista = convertView.findViewById(R.id.textEmailLista);

                txtNomeLista.setText(contato.getNome());
                txtTelLista.setText(contato.getTelefone());
                txtEmailLista.setText(contato.getEmail());

                return convertView;
            }
        };

        listView.setAdapter(adapter);
    }

    private void configurarListeners() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contato contato = contatos.get(position);

            Intent intent = new Intent(MainActivity.this, AddContatoActivity.class);
            intent.putExtra("contato", contato);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Contato contato = contatos.get(position);

            new AlertDialog.Builder(this).
                    setTitle("Excluir contato").
                    setMessage("Deseja excluir este contato " + contato.getNome() + "?").
                    setPositiveButton("Sim", (dialog, wich) -> {
                        contatoController.apagarContato(contato.getId());
                        carregarContatos();
                        Toast.makeText(this, "Contato excluído com sucesso", Toast.LENGTH_SHORT).show();
                    }).
                    setNegativeButton("Não", null).
                    show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarContatos();
    }
}