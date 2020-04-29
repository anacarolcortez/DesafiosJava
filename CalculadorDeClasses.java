package com.challenge.desafio;

import com.challenge.interfaces.Calculavel;
import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;

import java.lang.reflect.Field;
import java.math.BigDecimal;


public class CalculadorDeClasses implements Calculavel {

    public BigDecimal acumulador(Object classe, Class annotation) {
        BigDecimal somaBigDecimal = BigDecimal.ZERO;
        try {
            Class parametros = classe.getClass();
            for (Field parametro : parametros.getFields()) {
                parametro.setAccessible(true);
                if (parametro.isAnnotationPresent(annotation) && parametro.getType().equals(BigDecimal.class)){
                    somaBigDecimal = somaBigDecimal.add((BigDecimal) parametro.get(classe));
                }
            }
        }catch (IllegalArgumentException | IllegalAccessException e) {
            System.out.println("Não é possível acessar atributos");
        }
        return somaBigDecimal;
    }

    @Override
    public BigDecimal somar(Object classe) throws IllegalAccessException {
        return acumulador(classe, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object classe) throws IllegalAccessException {
        return acumulador(classe, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object classe) throws IllegalAccessException {
        return somar(classe).subtract(subtrair (classe));
    }
}
