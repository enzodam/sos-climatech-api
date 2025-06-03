# SOS ClimaTech API 🌪️

## Projeto: Cadastro de Pessoas Afetadas por Eventos Climáticos

Este projeto desenvolve uma API REST utilizando Java e Spring Boot para registrar pessoas impactadas por eventos climáticos extremos (enchentes, secas, etc.) e os próprios eventos. A API serve como backend para uma aplicação que poderia ser usada por entidades de resposta a emergências para gerenciar dados de cidadãos afetados.

Esta versão foi refatorada para seguir as boas práticas de arquitetura REST, princípios SOLID, e os requisitos técnicos especificados, incluindo persistência com JPA, validações, documentação Swagger e preparação para deploy em nuvem.

---

## 🧩 Funcionalidades Implementadas

*   **Eventos Climáticos:**
    *   Cadastro (POST /api/eventos)
    *   Listagem (GET /api/eventos)
    *   Busca por ID (GET /api/eventos/{id})
    *   Atualização (PUT /api/eventos/{id})
    *   Remoção (DELETE /api/eventos/{id})
*   **Pessoas Afetadas:**
    *   Cadastro (POST /api/pessoas) - Associada a um evento existente.
    *   Listagem (GET /api/pessoas)
    *   Busca por ID (GET /api/pessoas/{id})
    *   Atualização (PUT /api/pessoas/{id})
    *   Remoção (DELETE /api/pessoas/{id})
    *   **Atualização de Status de Assistência (Inovação):** (PATCH /api/pessoas/{id}/status?status=NOVO_STATUS)
*   **Relacionamento:** Um evento pode ter várias pessoas afetadas (One-to-Many).
*   **Validações:** Uso de Bean Validation para garantir a integridade dos dados de entrada.
*   **Tratamento de Erros:** Respostas de erro padronizadas para recursos não encontrados e erros de validação.
*   **Documentação:** API documentada automaticamente via Swagger/OpenAPI.

---

## ✨ Proposta Inovadora: Status de Assistência

Para agregar valor e atender ao critério de inovação, foi implementado um campo `statusAssistência` na entidade `PessoaAfetada`. Este campo permite rastrear o progresso do atendimento a cada pessoa, utilizando os seguintes status:

*   `REGISTRADO`: Aguardando avaliação/contato.
*   `EM_ASSISTENCIA`: Recebendo suporte.
*   `ASSISTENCIA_CONCLUIDA`: Necessidades emergenciais atendidas.
*   `NECESSITA_ACOMPANHAMENTO`: Requer suporte contínuo.

Um endpoint `PATCH /api/pessoas/{id}/status` foi criado para permitir a atualização deste status pelas equipes de resposta.

---

## 🛠️ Tecnologias Utilizadas

*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3.x
*   **Persistência:** Spring Data JPA / Hibernate
*   **Banco de Dados (Desenvolvimento):** H2 Database (em memória)
*   **Banco de Dados (Produção):** Oracle (configurado para FIAP via variáveis de ambiente no Render)
*   **Validação:** Jakarta Bean Validation
*   **Documentação:** Springdoc OpenAPI (Swagger UI)
*   **Build:** Apache Maven
*   **Deploy:** Render (configurado para deploy como Web Service)

---

## 📄 Documentação da API (Swagger)

A documentação completa da API, com todos os endpoints, modelos de dados e exemplos, está disponível via Swagger UI.

*   **Após iniciar a aplicação localmente:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
*   **No ambiente de deploy (Render):** `{URL_DO_DEPLOY}/swagger-ui.html` (Substitua `{URL_DO_DEPLOY}` pela URL fornecida pelo Render após o deploy)

---

## 🚀 Executando Localmente

**Pré-requisitos:**

*   JDK 17 ou superior instalado.
*   Apache Maven instalado.

**Passos:**

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd sos-climatech_refactored
    ```
2.  **Compile e execute a aplicação usando Maven:**
    ```bash
    mvn spring-boot:run
    ```
    *   A aplicação iniciará usando o perfil `dev` por padrão, conectando-se ao banco H2 em memória.
    *   A API estará acessível em `http://localhost:8080`.
    *   O console do H2 estará acessível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:sosclimatechdb`, User: `sa`, Password: em branco).

---

## ☁️ Deploy na Nuvem (Render) com Oracle FIAP

A aplicação está configurada para deploy na plataforma Render como um "Web Service", utilizando o banco de dados Oracle da FIAP.

**Instruções para Deploy no Render:**

1.  **Crie uma conta no Render:** Se ainda não tiver, crie uma conta em [https://render.com/](https://render.com/).
2.  **Crie um novo "Web Service" no Render:** No dashboard, clique em "New +" e selecione "Web Service".
3.  **Conecte seu repositório Git:** Escolha a opção para conectar seu repositório do GitHub, GitLab ou Bitbucket onde está o código do projeto.
4.  **Configurações do Serviço:**
    *   **Name:** Dê um nome para seu serviço (ex: `sos-climatech-api`).
    *   **Region:** Escolha uma região (ex: `Frankfurt`).
    *   **Branch:** Selecione a branch que deseja fazer deploy (ex: `main` ou `master`).
    *   **Root Directory:** Deixe em branco se o `pom.xml` estiver na raiz do repositório.
    *   **Runtime:** Selecione `Java` (O Render deve detectar o `pom.xml`).
    *   **Build Command:** `mvn clean install -DskipTests` (O `-DskipTests` acelera o build).
    *   **Start Command:** `java -jar target/sos-climatech-0.0.1-SNAPSHOT.jar` (Verifique se o nome do JAR gerado em `target/` corresponde. O perfil `prod` será ativado pelas variáveis de ambiente).
    *   **Instance Type:** Escolha o plano desejado (o plano gratuito pode ser suficiente para testes).
5.  **Variáveis de Ambiente (Environment Variables):** Esta é a parte crucial para conectar ao Oracle FIAP.
    *   Clique em "Advanced" ou procure a seção "Environment".
    *   Adicione as seguintes variáveis de ambiente **uma por uma**, clicando em "Add Environment Variable" para cada:
        *   `SPRING_PROFILES_ACTIVE` = `prod`
        *   `SPRING_DATASOURCE_URL` = `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`
        *   `SPRING_DATASOURCE_USERNAME` = `SEU_RM_AQUI` (Substitua pelo seu RM da FIAP)
        *   `SPRING_DATASOURCE_PASSWORD` = `SUA_SENHA_AQUI` (Substitua pela sua senha do Oracle FIAP)
        *   `SPRING_DATASOURCE_DRIVER_CLASS_NAME` = `oracle.jdbc.OracleDriver`
        *   `SPRING_JPA_HIBERNATE_DDL_AUTO` = `update` (Ou `validate` ou `none`. `update` tentará criar/atualizar tabelas automaticamente, use com cuidado em produção real).
6.  **Deploy:** Clique em "Create Web Service". O Render irá clonar seu repositório, executar o comando de build e, se bem-sucedido, executar o comando de start.
7.  **Acompanhe o Deploy:** Monitore os logs na aba "Logs" para verificar se a aplicação iniciou corretamente e se conectou ao banco Oracle.
8.  **Acesse a Aplicação:** Após o deploy bem-sucedido, o Render fornecerá uma URL pública (ex: `https://seu-servico.onrender.com`). Acesse a documentação Swagger em `{URL_DO_DEPLOY}/swagger-ui.html` para testar.

**Observação:** O deploy pode levar alguns minutos. Se ocorrerem erros, verifique os logs do Render cuidadosamente.

---

## 🔗 Links Importantes

*   **Repositório GitHub:** `[LINK_DO_SEU_REPOSITORIO_AQUI]` (Substitua pelo link real do seu repositório)
*   **Deploy no Render:** `[LINK_DO_DEPLOY_NO_RENDER_AQUI]` (Substitua pelo link real após o deploy bem-sucedido)

---

## 🎬 Vídeos de Entrega

Lembre-se que a entrega final requer dois vídeos:

1.  **Vídeo Demonstração (máx. 10 minutos):** Apresente a solução completa, mostrando a API funcionando (use o Swagger UI da aplicação no Render), explique a arquitetura, as funcionalidades implementadas (incluindo a inovação do status) e como os requisitos técnicos foram atendidos.
2.  **Vídeo Pitch (máx. 3 minutos):** Apresente a proposta de valor do projeto de forma concisa, focando no problema que ele resolve (gestão de dados em crises climáticas) e como a solução (a API) ajuda a mitigar os impactos.

---

## Avaliação

Este projeto busca atender aos seguintes critérios de avaliação:

*   **Cumprimento dos requisitos técnicos e boas práticas (70 pontos):** API REST, JPA, Relacionamentos, Bean Validation, Swagger, Deploy.
*   **Viabilidade e Inovação (10 pontos):** Proposta de Status de Assistência.
*   **Documentação e Apresentação (20 pontos):** Código fonte, links, instruções, vídeos.

