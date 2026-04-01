package com.example.backend.service;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.BeneficioEjbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository repository;
    private final BeneficioEjbService ejbService;

    public BeneficioService(BeneficioRepository repository, BeneficioEjbService ejbService) {
        this.repository = repository;
        this.ejbService = ejbService;
    }

    public List<Beneficio> findAll() {
        return repository.findAll();
    }

    public Beneficio findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado: " + id));
    }

    @Transactional
    public Beneficio create(Beneficio payload) {
        payload.setId(null);
        return repository.save(payload);
    }

    @Transactional
    public Beneficio update(Long id, Beneficio payload) {
        Beneficio existing = findById(id);
        existing.setNome(payload.getNome());
        existing.setDescricao(payload.getDescricao());
        existing.setValor(payload.getValor());
        existing.setAtivo(payload.getAtivo());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Beneficio existing = findById(id);
        repository.delete(existing);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Beneficio from = repository.findByIdForUpdate(fromId).orElseThrow(() -> new IllegalArgumentException("Benefício origem não encontrado: " + fromId));
        Beneficio to = repository.findByIdForUpdate(toId).orElseThrow(() -> new IllegalArgumentException("Benefício destino não encontrado: " + toId));

        ejbService.transfer(from, to, amount);

        repository.save(from);
        repository.save(to);
    }
}
