import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.OptionalLong;
import java.util.stream.*;

import IdentificadorUtilizadoException;
import TimeNaoEncontradoException;
import JogadorNaoEncontradoException;


public class DesafioMeuTimeApplication {

    private List<Time> times = new ArrayList<Time>();
    private List<Jogador> jogadores = new ArrayList<Jogador>();

//REPASSAR DAQUI PARA BAIXO, SEM ESQUECER DE ARRUMAR DEPOIS O THROW NEW
    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario){
        if (existeTime(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
        times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
    }


    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
        if (existeJogador(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
        if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
    }

    //PROBLEMA
    public void definirCapitao(Long idJogador) {
        if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");
        OptionalLong buscarIdTimeJogador = jogadores.stream().filter(x -> x.getId() == idJogador).mapToLong(Jogador::getIdTime).findFirst();
        //OptionalLong adicionarJogador = times.setbuscarIdTimeJogador;
    }


    public Long buscarCapitaoDoTime(Long idTime) {
        if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        return times.stream().filter(x -> x.getId() == idTime).mapToLong(Time::getCapitao);
        //.orElseThrow(CapitaoNaoInformadoException::new);
    }


    public String buscarNomeJogador(Long idJogador) {
        if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");
        return jogadores.stream().filter(x -> x.getId() == idJogador).map(Jogador::getNome).toString();
    }


    public String buscarNomeTime(Long idTime) {
        if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
       return times.stream().filter(x -> x.getId() == idTime).map(Time::getNome).toString();
    }


    public List<Long> buscarJogadoresDoTime(Long idTime) {
        if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        List<Long> jogadoresDoTime = jogadores.stream().filter(x -> x.getIdTime() == idTime).collect(Collectors.toList());
        return jogadoresDoTime;
    }


    public Long buscarMelhorJogadorDoTime(Long idTime) {
        if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        return jogadores.stream().filter(x -> x.getIdTime() == idTime).max(Comparator.comparingInt(Jogador::getNivelHabilidade)).map(Jogador::getId).get();
    }


    public Long buscarJogadorMaisVelho(Long idTime) {
        buscarJogadoresDoTime(idTime);
        return jogadoresDoTime.stream().max(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId)).mapToLong(Jogador::getId).findFirst();
    }


    public List<Long> buscarTimes() {
        return times.stream().sorted(Comparator.comparing(Time::getId)).map(Time::getId).collect(Collectors.toList());
    }


    public Long buscarJogadorMaiorSalario(Long idTime) {
        return buscarJogadoresDoTime(idTime).stream().sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId)).limit(1).mapToLong(Jogador::getId).findFirst();
    }

    //PROBLEMA
    public List<Long> buscarTopJogadores(Integer top) {
        return jogadores.stream().sorted(Comparator.comparingInt(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId)).limit(top).mapToLong(Jogador::getId).collect(Collectors.toList());
    }

    //PROBLEMA - RETURN CORDACAMISA
    public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
        if(!existeTime(timeDaCasa) || !existeTime(timeDeFora)) throw new TimeNaoEncontradoException("Time não encontrado");
        //if
    }

    public Boolean existeTime(Long id){
        return times.stream().anyMatch(x -> x.getId() == id);
    }

    public Boolean existeJogador(Long id){
        return jogadores.stream().anyMatch(x -> x.getId() == id);
    }


}
