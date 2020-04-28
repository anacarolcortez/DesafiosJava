package com.challenge.desafio;

import com.challenge.interfaces.Calculavel;
import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;

import java.lang.reflect.Field;
import java.math.BigDecimal;


public class CalculadorDeClasses implements Calculavel {

    public BigDecimal acumuladorSoma(Object classe, Class annotation) {
        BigDecimal somaBigDecimal = BigDecimal.ZERO;
        try {
            Field[] parametros = Object.class.getFields();
            for (Field parametro : parametros) {
                parametro.setAccessible(true);
                if (parametro.isAnnotationPresent(Somar.class) && parametro.getType().isAssignableFrom(BigDecimal.class)) {
                    somaBigDecimal = somaBigDecimal.add(new BigDecimal(parametro.getDouble(BigDecimal.class)));
                }
            }
        }catch (Throwable e) {
            System.out.println("Erro ao acessar atributos da classe");
        }
        return somaBigDecimal;
    }

    public BigDecimal acumuladorSubtracao(Object classe, Class annotation) {
        BigDecimal somaBigDecimal = BigDecimal.ZERO;
        try {
            Field[] parametros = Object.class.getFields();
            for (Field parametro : parametros) {
                parametro.setAccessible(true);
                if (parametro.isAnnotationPresent(Subtrair.class) && parametro.getType().isAssignableFrom(BigDecimal.class)) {
                    somaBigDecimal = somaBigDecimal.add(new BigDecimal(parametro.getDouble(BigDecimal.class)));
                }
            }
        }catch (Throwable e) {
            System.out.println("Erro ao acessar atributos da classe");
        }
        return somaBigDecimal;
    }


    @Override
    public BigDecimal somar(Object classe) throws IllegalAccessException {
        return acumuladorSoma(classe, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object classe) throws IllegalAccessException {
        return acumuladorSubtracao(classe, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object classe) throws IllegalAccessException {
        return somar(classe).subtract(subtrair (classe));
    }
}
