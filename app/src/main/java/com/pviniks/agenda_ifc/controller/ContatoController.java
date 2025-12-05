package com.pviniks.agenda_ifc.controller;

import android.content.Context;

import com.pviniks.agenda_ifc.model.Contato;
import com.pviniks.agenda_ifc.model.ContatoDAO;

import java.util.List;

public class ContatoController {
    private ContatoDAO contatoDAO;

    public ContatoController(Context context){
        contatoDAO = new ContatoDAO(context);
        contatoDAO.open();
    }

    public long adicionarContato(String nome, String email, String telefone, String foto){
        Contato contato = new Contato(nome, email, telefone, foto);
        return contatoDAO.adicionarContato(contato);
    }

    public List<Contato> listarContatos(){
        return contatoDAO.listarContatos();
    }

    public int atualizarContato(int id, String nome, String email, String telefone, String foto){
        Contato contato = new Contato(id, nome, email, telefone, foto);
        return contatoDAO.atualizarContato(contato);
    }

    public void apagarContato(int id){
        contatoDAO.apagarContato(id);
    }

    public void close(){
        contatoDAO.close();
    }
}
