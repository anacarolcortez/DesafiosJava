import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;


public class DesafioMeuTimeApplication {

    private List<Time> times = new ArrayList<>();
    private List<Jogador> jogadores = new ArrayList<>();

    
    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario){
        if (existeTime(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
        times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
    }

    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
        if (existeJogador(id)) throw new IdentificadorUtilizadoException("Id já cadastrado");
        if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
    }

    public void definirCapitao(Long idJogador) {
        if (!existeJogador(idJogador)) throw new JogadorNaoEncontradoException("Jogador não cadastrado");
        for (Time time : times) {
            for (Jogador jogador : jogadores) {
                if (jogador.getId() == idJogador) {
                    time.setCapitao(jogador.getId());
                }
            }
        }
    }

    public Long buscarCapitaoDoTime(Long idTime) {
        if (!existeTime(idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        return times.stream().filter(x -> x.getId() == idTime).mapToLong(Time::getCapitao).findFirst().orElseThrow(CapitaoNaoInformadoException::new);
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
        List<Long> jogadoresDoTime = jogadores.stream()
                .filter(x -> x.getIdTime() == idTime)
                .mapToLong(Jogador::getId)
                .boxed()
                .collect(Collectors.toList());
        return jogadoresDoTime;
    }

    public Long buscarMelhorJogadorDoTime(Long idTime) {
        if (!existeTime (idTime)) throw new TimeNaoEncontradoException("Time não encontrado");
        return jogadores.stream().filter(x -> x.getIdTime() == idTime).max(Comparator.comparingInt(Jogador::getNivelHabilidade)).map(Jogador::getId).get();
    }

    public Long buscarJogadorMaisVelho(Long idTime) {
        return buscarJogadoresDoTime(idTime).stream().max(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId)).mapToLong(Jogador::getId).findFirst();
    }

    public List<Long> buscarTimes() {
        return times.stream().sorted(Comparator.comparing(Time::getId)).map(Time::getId).collect(Collectors.toList());
    }

    public Long buscarJogadorMaiorSalario(Long idTime) {
        return buscarJogadoresDoTime(idTime).stream().sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId)).mapToLong(Jogador::getId).findFirst();
    }

    public List<Long> buscarTopJogadores(Integer top) {
        return jogadores.stream().sorted(Comparator.comparingInt(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId)).limit(top).mapToLong(Jogador::getId).collect(Collectors.toList());
    }

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
