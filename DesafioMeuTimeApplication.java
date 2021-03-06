package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> times = new ArrayList<>();
	private List<Jogador> jogadores = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario){
		if (existeTime(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
		times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
		if (existeJogador(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		if (id < 0 || idTime < 0 || nivelHabilidade < 0 || nivelHabilidade > 100) throw new IllegalArgumentException();
		jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");

		Long idTimeDoJogador = jogadores.stream()
				.filter(x -> x.getId() == idJogador)
				.map(Jogador::getIdTime)
				.findFirst().orElseThrow(JogadorNaoEncontradoException::new);

		times.forEach(time -> {
			if (time.getId() == idTimeDoJogador) {
				time.setCapitao(idJogador);
			}
		});

	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return times.stream()
				.filter(x -> x.getId() == idTime)
				.map(Time::getCapitao).findFirst()
				.orElseThrow(CapitaoNaoInformadoException::new);
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return jogadores.stream()
				.filter(x -> x.getId() == idJogador)
				.map(Jogador::getNome)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return times.stream()
				.filter(x -> x.getId() == idTime)
				.map(Time::getNome)
				.findFirst()
				.orElseThrow(TimeNaoEncontradoException::new);
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return jogadores.stream()
				.filter(x -> x.getIdTime().equals(idTime))
				.mapToLong(Jogador::getId)
				.sorted()
				.boxed()
				.collect(Collectors.toList());
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return jogadores.stream()
				.filter(x -> x.getIdTime().equals(idTime))
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade)
						.reversed()
						.thenComparing(Jogador::getId))
				.map(Jogador::getId)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		if(!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
		return jogadores.stream()
				.filter(x -> x.getIdTime().equals(idTime))
				.sorted(Comparator.comparing(Jogador::getDataNascimento)
						.thenComparing(Jogador::getId))
				.map(Jogador::getId)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return times.stream()
				.sorted(Comparator.comparingLong(Time::getId))
				.map(Time::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return jogadores.stream()
				.sorted(Comparator.comparing(Jogador::getSalario)
				.reversed()
				.thenComparingLong(Jogador::getId))
				.filter(x -> x.getIdTime().equals(idTime))
				.map(Jogador::getId).findFirst()
				.orElseThrow(TimeNaoEncontradoException::new);
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return jogadores.stream().filter(jogador -> jogador.getId() == idJogador)
				.map(Jogador::getSalario).findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		if (top == null || top < 0) throw new IllegalArgumentException("Digite um número válido");
		return jogadores.stream()
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade)
						.reversed()
						.thenComparing(Jogador::getId))
				.limit(top)
				.mapToLong(Jogador::getId)
				.boxed()
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
		if (id == null || id < 0) throw new IllegalArgumentException("Digite um número válido");
		return times.stream().anyMatch(x -> x.getId() == id);
	}

	public Boolean existeJogador(Long id){
		if (id == null || id < 0) throw new IllegalArgumentException("Digite um número válido");
		return jogadores.stream().anyMatch(x -> x.getId() == id);
	}


}
