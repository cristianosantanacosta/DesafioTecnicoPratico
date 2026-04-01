package com.example.backend.controller;

import com.example.backend.model.Beneficio;
import com.example.backend.service.BeneficioService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Beneficio> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Beneficio get(@PathVariable @Min(1) Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Beneficio> create(@Valid @RequestBody Beneficio beneficio) {
        return ResponseEntity.ok(service.create(beneficio));
    }

    @PutMapping("/{id}")
    public Beneficio update(@PathVariable @Min(1) Long id, @Valid @RequestBody Beneficio beneficio) {
        return service.update(id, beneficio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest request) {
        service.transfer(request.getFromId(), request.getToId(), request.getAmount());
        return ResponseEntity.ok().build();
    }

    public static class TransferRequest {
        @NotNull
        private Long fromId;

        @NotNull
        private Long toId;

        @NotNull
        private BigDecimal amount;

        public Long getFromId() {
            return fromId;
        }

        public void setFromId(Long fromId) {
            this.fromId = fromId;
        }

        public Long getToId() {
            return toId;
        }

        public void setToId(Long toId) {
            this.toId = toId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
