package com.challenge.desafio;

import com.challenge.interfaces.Calculavel;
import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;


public class CalculadorDeClasses implements Calculavel {


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

    private BigDecimal fazerCalculoBase(Object classe, Class annotation) {
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
        return fazerCalculoBase(classe, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object classe) throws IllegalAccessException {
        return fazerCalculoBase(classe, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object classe) throws IllegalAccessException {
        return somar(classe).subtract(subtrair (classe));
    }
}
