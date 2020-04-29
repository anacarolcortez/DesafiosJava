package challenge;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Estacionamento {

    private Set<Carro> carros = new LinkedHashSet<>();

    public void estacionar(Carro carro) {
        validarCarro(carro);
        if (carrosEstacionados() >= 10) {
            for (Iterator<Carro> it = carros.iterator(); it.hasNext(); ) {
                Carro carroNoEstacionamento = it.next();
                Motorista motor = carroNoEstacionamento.getMotorista();
                if (motor.getIdade() < 55) {
                    carros.remove(carroNoEstacionamento);
                    carros.add(carro);
                    return;
                }
            }
            throw new EstacionamentoException("Não há vagas disponíveis");
        }
        carros.add(carro);
    }

    public Boolean carroEstacionado(Carro carro) {
        return carros.contains(carro);
    }

    public int carrosEstacionados() {
        return carros.size();
    }
    
    public void validarCarro(Carro carro) {
        if (carro.getMotorista().getIdade() < 18) {
            throw new EstacionamentoException("Motorista é menor de idade e não pode dirigir");
        }
        if (carro.getMotorista().getPontos() > 20) {
            throw new EstacionamentoException("Habilitação inválida");
        }
    }
    
}
