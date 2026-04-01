package com.example.backend.service;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.BeneficioEjbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BeneficioService.class, BeneficioEjbService.class})
public class BeneficioServiceTest {

    @Autowired
    private BeneficioRepository repository;

    @Autowired
    private BeneficioService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.save(new Beneficio("Beneficio A", "Desc A", new BigDecimal("1000.00"), true));
        repository.save(new Beneficio("Beneficio B", "Desc B", new BigDecimal("500.00"), true));
    }

    @Test
    void transferShouldMoveAmount() {
        Beneficio origem = repository.findAll().stream().filter(b -> "Beneficio A".equals(b.getNome())).findFirst().orElseThrow();
        Beneficio destino = repository.findAll().stream().filter(b -> "Beneficio B".equals(b.getNome())).findFirst().orElseThrow();

        service.transfer(origem.getId(), destino.getId(), new BigDecimal("200.00"));

        Beneficio atualOrigem = repository.findById(origem.getId()).orElseThrow();
        Beneficio atualDestino = repository.findById(destino.getId()).orElseThrow();

        assertThat(atualOrigem.getValor()).isEqualByComparingTo("800.00");
        assertThat(atualDestino.getValor()).isEqualByComparingTo("700.00");
    }

    @Test
    void transferShouldFailOnInsufficientBalance() {
        Beneficio origem = repository.findAll().stream().filter(b -> "Beneficio B".equals(b.getNome())).findFirst().orElseThrow();
        Beneficio destino = repository.findAll().stream().filter(b -> "Beneficio A".equals(b.getNome())).findFirst().orElseThrow();

        assertThrows(IllegalStateException.class, () -> service.transfer(origem.getId(), destino.getId(), new BigDecimal("1000.00")));
    }
}
