package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs de depósito e retirada são obrigatórios");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Transferência entre mesma conta não é permitida");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Conta origem ou destino não encontrada");
        }
        if (from.getValor() == null || from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para a transferência");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }

    // Método auxiliares para uso local em backend sem dependência direta em EntityManager
    public void transfer(Beneficio from, Beneficio to, BigDecimal amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Conta origem e destino são obrigatórias");
        }
        if (from.getId().equals(to.getId())) {
            throw new IllegalArgumentException("Transferência para a mesma conta não é permitida");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }
        if (from.getValor() == null || from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para transferência");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}

