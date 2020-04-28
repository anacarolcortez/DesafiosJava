package desafio;

import interfaces.Calculavel;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.lang.reflect.*;
import java.util.ArrayList;

public abstract class CalculadorDeClasses implements Calculavel {


    public ArrayList<Object> acumuladorBigDecimal(Object classe) {
        ArrayList<Object> todosBigDecimal = new ArrayList<>();
        try {
            Field[] parametros = Object.class.getDeclaredFields();
            for (Field parametro: parametros){
                if (parametro.getType().isAssignableFrom(BigDecimal.class)){
                    todosBigDecimal.add(parametro);
                }
            }
        } catch (Throwable e) {
            System.out.println("Erro ao acessar atributos da classe");
        }
        return todosBigDecimal;
    }

    private BigDecimal fazerCalculoBase(Object classe) {
        BigDecimal somaBigDecimal = new BigDecimal(0);
        try {
            Annotation[] annotations = Object.class.getDeclaredAnnotations();
            if (annotations.length == 0) {
                somaBigDecimal =  BigDecimal.ZERO;
            } else {
                ArrayList<Object> todosBigDecimal = acumuladorBigDecimal(classe);
                for(int i = 0; i < todosBigDecimal.size(); i++){
                    somaBigDecimal = (BigDecimal)todosBigDecimal.get(i);
                }
            }
        } catch (Throwable e) {
            System.out.println("Erro ao acessar atributos da classe");
        }
        return somaBigDecimal;
    }

    @Override
    public BigDecimal somar(Object classe) throws IllegalAccessException {
        BigDecimal soma = fazerCalculoBase(classe);
        return soma;
    }

    @Override
    public BigDecimal subtrair(Object classe) throws IllegalAccessException {
        BigDecimal subtracao = fazerCalculoBase(classe);
        return subtracao;
    }

    @Override
    public BigDecimal totalizar(Object classe) throws IllegalAccessException {
        BigDecimal resultadoFinal = somar(classe).subtract(subtrair (classe));
        return resultadoFinal;
    }



}