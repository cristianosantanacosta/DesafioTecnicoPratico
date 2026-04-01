package com.example.ejb;

import com.example.backend.model.Beneficio;
import jakarta.ejb.Stateless;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Stateless
@Component
public class BeneficioEjbService {

    public void transfer(Beneficio from, Beneficio to, BigDecimal amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Conta origem e destino são obrigatórias");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }
        if (from.getId().equals(to.getId())) {
            throw new IllegalArgumentException("Transferência para a mesma conta não é permitida");
        }

        if (from.getValor() == null || from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para transferência");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}
