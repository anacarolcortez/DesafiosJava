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
		if (existeTime(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
		times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
		if (existeJogador(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");
		for (Time time : times) {
			for (Jogador jogador : jogadores) {
				if (jogador.getId() == idJogador) {
					time.setCapitao(idJogador);
				}
			}
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		for (Time time : times) {
			if (time.getCapitao() == 0)  throw new CapitaoNaoInformadoException("Capitão não informado");
			if (time.getId() == idTime) {
				return time.getCapitao();

			}
		}
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");
		return jogadores.stream()
				.filter(x -> x.getId() == idJogador)
				.map(Jogador::getNome)
				.toString();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return times.stream()
				.filter(x -> x.getId() == idTime)
				.map(Time::getNome)
				.toString();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		List<Long> jogadoresDoTime = jogadores.stream()
				.filter(x -> x.getIdTime() == idTime)
				.mapToLong(Jogador::getId)
				.boxed()
				.collect(Collectors.toList());
		return jogadoresDoTime;
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return jogadores.stream()
				.filter(x -> x.getIdTime() == idTime)
				.max(Comparator.comparingInt(Jogador::getNivelHabilidade))
				.map(Jogador::getId).get();
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		if(!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return jogadores.stream().sorted(Comparator.comparing(Jogador::getDataNascimento).reversed().thenComparing(Jogador::getId)).map(Jogador.getId()).findFirst().get();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return times.stream()
				.sorted(Comparator.comparing(Time::getId))
				.map(Time::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return buscarJogadoresDoTime(idTime).stream()
				.sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId))
				.mapToLong(Jogador::getId)
				.findFirst();

	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime){

	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		return jogadores.stream()
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
				.limit(top)
				.mapToLong(Jogador::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		if(!existeTime(timeDaCasa) || !existeTime(timeDeFora)) throw new TimeNaoEncontradoException("Time não encontrado");

		Time timeCasa = new Time();
		Time timeConvidado = new Time();

		for (Time time : times) {
			if (time.getId() == timeDaCasa) {
				timeCasa = time;
			} else if (time.getId() == timeDeFora) {
				timeConvidado = time;
			}
		}

		if (!timeCasa.getCorUniformePrincipal().equals(timeConvidado.getCorUniformePrincipal())) {
			return timeConvidado.getCorUniformePrincipal();
		} else {
			return timeConvidado.getCorUniformeSecundario();
		}
	}

	public Boolean existeTime(Long id){
		return times.stream().anyMatch(x -> x.getId() == id);
	}

	public Boolean existeJogador(Long id){
		return jogadores.stream().anyMatch(x -> x.getId() == id);
	}


}
