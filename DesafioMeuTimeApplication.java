package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.*;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> times = new ArrayList<Time>();
	private List<Jogador> jogadores = new ArrayList<Jogador>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario){
		if (existeTime(id)) throw new br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException();
		times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
		if (existeJogador(id)) throw new br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException();
        if (!existeTime(idTime)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
        jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
        if (!existeJogador(idJogador)) throw new br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException();
		Long buscarIdTimeJogador = jogadores.stream().filter(x -> x.getId().equals(idJogador)).mapToLong(Jogador::idTime).findFirst();
		Long escolherCapitao = times.setIdCapitao(buscarIdTimeJogador);
        }

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		if (!existeTime(id)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
		Long buscarCapitao = times.stream().mapToLong(Times::idCapitao).findFirst().orElseThrow(CapitaoNaoInformadoException::new);
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
	    if (!existeJogador(idJogador)) throw new br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException();
        String nomeJogador = jogadores.stream().filter(x -> x.getId().equals(idJogador)).findFirst();
		return nomeJogador;
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
        if (!existeTime (idTime)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
		 String nomeTime = times.stream().filter(x -> x.getId().equals(idTime)).findFirst();
        return nomeTime;
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
	    if (!existeTime(idTime)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
        List<Long> jogadoresDoTime;
	    jogadoresDoTime = jogadores.stream().filter(x -> x.getIdTime().equals(idTime)).collect(Collectors.toList());
        return jogadoresDoTime;
    }

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		if (!existeTime (idTime)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
        Long melhorJogador = jogadores.stream().filter(x -> x.getIdTime().equals(idTime)).max(Comparator.comparingInt(Jogador::getNivelHabilidade)).mapToLong(Jogador::getId).findFirst();
        return melhorJogador;
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		buscarJogadoresDoTime(idTime);
		Long jogadorMaisVelho = jogadoresDoTime.stream().min(comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId)).mapToLong(Jogador::getId).findFirst();
		return jogadorMaisVelho;
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
	    return times.stream().sorted(comparing(Time::getId)).map(Time::getId).collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Long jogadorMaiorSalario = buscarJogadoresDoTime(idTime).stream().min(comparing(Jogador::getSalario).thenComparing(Jogador::getId)).mapToLong(Jogador::getId).findFirst();
		return jogadorMaiorSalario;
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
        return jogadores.stream().sorted(comparingInt(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId)).limit(top).mapToLong(Jogador::getId.collect(Collectors.toList());
    }

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		if(!existeTime(timeDaCasa) || !existeTime(timeDaCasa)) throw new br.com.codenation.desafio.exceptions.TimeNaoEncontradoException();
		Time timeCasa = buscarTimes(timeDaCasa);
		Time timeFora = buscarTimes(timeDeFora);
		return timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal() ? timeFora.getCorUniformeSecundario() : timeFora.getCorUniformePrincipal());
	}

	public Boolean existeTime(Long id){
		for (Time time : this.times) {
			if (time.getId() == id) {
				return true;
			}
			return false;
		}
	}

	public Boolean existeJogador(Long id){
		for (Jogador jogador : jogadores) {
			if (jogador.getId() == id) {
				return true;
			} else {
				return false;
			}
		}
	}


}
