package com.pviniks.agenda_ifc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;

import com.pviniks.agenda_ifc.controller.ContatoController;
import com.pviniks.agenda_ifc.model.Contato;

public class AddContatoActivity extends AppCompatActivity {
    private EditText editTextNome, editTextEmail, editTextTelefone;
    private Button btnSalvar;
    private ImageView imageView;
    private Contato contatoEdit;

    private String caminhoFoto = "";

    ContatoController contatoController;

    private final ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(
        new CropImageContract(), result -> {
            if (result.isSuccessful()) {
                Uri uriContent = result.getUriContent();
                if (uriContent != null) {
                    caminhoFoto = uriContent.toString();
                    imageView.setImageURI(uriContent);
                }
            } else {
                Exception error = result.getError();
                if (error != null) {
                    Toast.makeText(this, "Erro ao cortar a imagem", Toast.LENGTH_SHORT).show();
                }
            }
        }
    );


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
        Button btnSelecionarFoto = findViewById(R.id.btnSelecionarFoto);

        btnSelecionarFoto.setOnClickListener(v -> {
            CropImageOptions options = new CropImageOptions();
            options.imageSourceIncludeGallery = true;
            options.imageSourceIncludeCamera = true;
            options.aspectRatioX = 1;
            options.aspectRatioY = 1;
            options.fixAspectRatio = true;

            CropImageContractOptions contractOptions = new CropImageContractOptions(null, options);
            cropImage.launch(contractOptions);
        });

        btnSalvar.setOnClickListener(v -> salvarContato());
    }

    public void salvarContato(){
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();

        if(nome.isEmpty() || email.isEmpty() || telefone.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(contatoEdit != null){
            if (caminhoFoto.isEmpty() && contatoEdit.getFoto() != null) {
                caminhoFoto = contatoEdit.getFoto();
            }

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

            if (contatoEdit.getFoto() != null && !contatoEdit.getFoto().isEmpty()) {
                caminhoFoto = contatoEdit.getFoto();
                imageView.setImageURI(Uri.parse(caminhoFoto));
            }

            btnSalvar.setText("Atualizar Contato");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoController.close();
    }
}
