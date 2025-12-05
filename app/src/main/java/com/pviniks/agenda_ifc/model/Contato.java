package com.pviniks.agenda_ifc.model;
import java.io.Serializable;

public class Contato implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String foto;

    public Contato() {
    }

    public Contato(String nome, String email, String telefone, String foto) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.foto = foto;
    }

    public Contato(int id, String nome, String email, String telefone, String foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
