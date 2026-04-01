# 🏗️ Desafio Fullstack Integrado
🚨 Instrução Importante (LEIA ANTES DE COMEÇAR)
❌ NÃO faça fork deste repositório.

Este repositório é fornecido como modelo/base. Para realizar o desafio, você deve:
✅ Opção correta (obrigatória)
  Clique em “Use this template” (se este repositório estiver marcado como Template)
OU
  Clone este repositório e crie um NOVO repositório público em sua conta GitHub.
📌 O resultado deve ser um repositório próprio, independente deste.

## 🎯 Objetivo
Criar solução completa em camadas (DB, EJB, Backend, Frontend), corrigindo bug em EJB e entregando aplicação funcional.

## 📦 Estrutura
- db/: scripts schema e seed
- ejb-module/: serviço EJB com bug a ser corrigido
- backend-module/: backend Java 8+
- frontend/: app Angular
- docs/: instruções e critérios
- .github/workflows/: CI

## ✅ Tarefas do candidato
1. Executar db/schema.sql e db/seed.sql
2. Corrigir bug no BeneficioEjbService
3. Implementar backend CRUD + integração com EJB
4. Desenvolver frontend Angular consumindo backend
5. Implementar testes
6. Documentar (Swagger, README)
7. Enviar link para recrutadora com seu repositório para análise

## 🐞 Bug no EJB
- Transferência não verifica saldo, não usa locking, pode gerar inconsistência
- Espera-se correção com validações, rollback, locking/optimistic locking

## 📊 Critérios de avaliação
- Arquitetura em camadas (20%)
- Correção EJB (20%)
- CRUD + Transferência (15%)
- Qualidade de código (10%)
- Testes (15%)
- Documentação (10%)
- Frontend (10%)

## Como rodar a solução

1. Compilar e executar backend (Spring Boot + H2 in-memory):
   - `cd backend-module`
   - `mvn clean package`
   - `mvn spring-boot:run`
2. API disponível em `http://localhost:8080/api/v1/beneficios`
3. OpenAPI JSON: `http://localhost:8080/v3/api-docs`
4. Swagger UI: `http://localhost:8080/swagger-ui.html`
5. Frontend Angular:
   - `cd frontend`
   - `npm install`
   - `npm start`
   - Acessar `http://localhost:4200`

## 🧪 Testes

- `cd backend-module`
- `mvn test`

## 🐞 Correção do bug EJB

Implementado em `ejb-module/src/main/java/com/example/ejb/BeneficioEjbService.java`:
- validações de ids, valor, mesma conta, saldo insuficiente
- bloqueio PESSIMISTIC_WRITE para evitar lost update / concorrência
- rollback automático em exceções Runtime

## 🛠️ Integração

- `backend-module` utiliza `BeneficioEjbService` para regras de transferência.
- Camadas:
  - DB: `backend-module/src/main/resources/schema.sql` e `data.sql`
  - EJB: `ejb-module` com entidade e serviço de negócio
  - Backend: Spring REST + JPA
  - Frontend: Angular HTTP consumer

