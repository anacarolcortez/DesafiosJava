package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class Jogador {
    private Long id;
    private Long idTime;
    private String nome;
    private LocalDate dataNascimento;
    private Integer nivelHabilidade;
    private BigDecimal salario;

    public Jogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        this.id = id;
        this.idTime = idTime;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nivelHabilidade = nivelHabilidade;
        this.salario = salario;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }
    public String getNome(){
        return this.nome;
    }

    public void setNome(){
        this.nome = nome;
    }

    public LocalDate getDataNascimento(){
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public Integer getNivelHabilidade(){
        return this.nivelHabilidade;
    }

    public void setNivelHabilidade(Integer nivelHabilidade){
        this.nivelHabilidade = nivelHabilidade;
    }

    public BigDecimal getSalario(){
        return this.salario;
    }

    public void setSalario(BigDecimal salario){
        this.salario = salario;
    }

}