package br.com.codenation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Time {
    private long id;
    private String nome;
    private LocalDate dataCriacao;
    private String corUniformePrincipal;
    private String corUniformeSecundario;
    private long idCapitao;


    public Time(long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.corUniformePrincipal = corUniformePrincipal;
        this.corUniformeSecundario = corUniformeSecundario;
        this.idCapitao = getIdCapitao();
    }

    public long getid() {
        return this.id;
    }

    public void setid(long id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCorUniformePrincipal(){
        return this.corUniformePrincipal;
    }

    public void setCorUniformePrincipal( String corUniformePrincipal){
        this.corUniformePrincipal = corUniformePrincipal;
    }

    public String getCorUniformeSecundario(){
        return this.corUniformeSecundario;
    }

    public void setCorUniformeSecundario(String corUniformeSecundario){
        this.corUniformeSecundario = corUniformeSecundario;
    }

    public LocalDate getDataCriacao(){
        return this.dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    public Long getIdCapitao(){
        return this.idCapitao;
    }

    public void setIdCapitao(Long idCapitao){
        this.idCapitao = idCapitao;
    }
}