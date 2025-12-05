package com.pviniks.agenda_ifc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pviniks.agenda_ifc.controller.ContatoController;
import com.pviniks.agenda_ifc.model.Contato;

public class AddContatoActivity extends AppCompatActivity {
    private EditText editTextNome, editTextEmail, editTextTelefone;
    private Button btnSalvar;
    private ImageView imageView;
    private Contato contatoEdit;

    ContatoController contatoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contato);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contatoController = new ContatoController(this);
        inicializarViews();
        verificarEdicao();
    }

    public void inicializarViews(){
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        btnSalvar = findViewById(R.id.btnSalvar);
        imageView = findViewById(R.id.imageView);

        btnSalvar.setOnClickListener(v -> salvarContato());
    }

    public void salvarContato(){
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();

        if(nome.isEmpty() || email.isEmpty() || telefone.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }

        if(contatoEdit != null){
            contatoController.atualizarContato(contatoEdit.getId(), nome, email, telefone, "");
            Toast.makeText(this, "Contato atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            contatoController.adicionarContato(nome, email, telefone, "");
            Toast.makeText(this, "Contato salvo com sucesso", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void verificarEdicao(){
        if(getIntent().hasExtra("contato")){
            contatoEdit = (Contato) getIntent().getSerializableExtra("contato");

            editTextNome.setText(contatoEdit.getNome());
            editTextEmail.setText(contatoEdit.getEmail());
            editTextTelefone.setText(contatoEdit.getTelefone());

            btnSalvar.setText("Atualizar Contato");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoController.close();
    }
}
