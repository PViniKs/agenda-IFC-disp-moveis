package com.pviniks.agenda_ifc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public ContatoDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public long adicionarContato(Contato contato){
        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("foto", contato.getFoto());

        return database.insert("contato",null, values);
    }

    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = database.query("contato", null, null, null, null, null, "nome ASC");

        if(cursor.moveToFirst()) {
            do {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
                contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
                contatos.add(contato);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contatos;
    }

    public int atualizarContato(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("foto", contato.getFoto());

        return database.update("contato", values, "id=?", new String[]{String.valueOf(contato.getId())});
    }

    public void apagarContato(int id){
        //delete na tabela "contato"
        database.delete("contato", "id=?", new String[]{String.valueOf(id)});
    }

    public Contato buscaContatoPorId(int id){
        Cursor cursor = database.query("contato", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            Contato contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
            contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
            cursor.close();
            return contato;
        }
        return null;
    }
}
