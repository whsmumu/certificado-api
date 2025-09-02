# Gerenciamento de certificados digitais no varejo

A **Certificado API** é uma aplicação Spring Boot desenvolvida para gerenciar certificados digitais de lojas no ramo varejo. O sistema permite controlar o ciclo de vida dos certificados, incluindo instalação, monitoramento de prazos de expiração e histórico de instalações.

## 🚀 Funcionalidades

- **Gestão de Lojas**: CRUD completo para lojas com controle de status de certificados
- **Histórico de Instalações**: Registro detalhado de instalações de certificados por técnicos
- **Monitoramento de Prazos**: Acompanhamento automático de prazos de expiração
- **Sistemas Multi-certificado**: Suporte para instalação em múltiplos sistemas por loja
- **Validações de Negócio**: Prevenção de duplicações e validações de integridade
- **Autenticação LDAP**: Integração com Active Directory (ambiente produção)
- **Perfis de Ambiente**: Configurações específicas para desenvolvimento e produção

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Security**
- **Spring LDAP**
- **PostgreSQL**
- **MapStruct** (mapeamento de objetos)
- **Lombok** (redução de boilerplate)
- **Docker & Docker Compose**
- **Maven**

## 🐳 Docker

O projeto inclui configuração completa com Docker Compose:

- **PostgreSQL**: Banco de dados principal
- **pgAdmin**: Interface web para administração do banco
- **Aplicação**: Backend Spring Boot
- **Frontend**: Aplicação Node.js (referência externa)

### Executando com Docker

```bash
# Subir a aplicação
docker-compose -p certificadonm --profile dev up --build -d

# Derrubar a aplicação
docker-compose -p certificadonm --profile dev down
```

## ⚙️ Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
# Database
DB_NAME=certificadodb
DB_USERNAME=postgres
DB_PASSWORD=postgres

# pgAdmin
PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=admin

# LDAP (Produção)
LDAP_URLS=ldap://seu-servidor:389
LDAP_BASE_DN=DC=exemplo,DC=local
LDAP_MANAGER_DN=CN=manager,DC=exemplo,DC=local
LDAP_MANAGER_PASSWORD=senha
```

# 📡 Endpoints da API

## 🏪 Lojas

| Método | Endpoint       | Descrição              |
|--------|----------------|------------------------|
| GET    | `/lojas`       | Listar todas as lojas  |
| GET    | `/lojas/{id}`  | Buscar loja por ID     |
| POST   | `/lojas`       | Criar nova loja        |
| PUT    | `/lojas/{id}`  | Atualizar loja         |
| DELETE | `/lojas/{id}`  | Deletar loja           |

### Exemplo de Requisição - Criar Loja
```json
POST /lojas
{
  "nomeLoja": "Loja Centro",
  "codigoLoja": "LC001",
  "prazoExpiracaoCertificado": "2024-12-31",
  "lojaEnviado": "ENVIADO",
  "certificadoRecebido": "RECEBIDO",
  "enviadoFiscal": "ENVIADO"
}
```
---

## 📝 Históricos

| Método | Endpoint            | Descrição                  |
|--------|---------------------|----------------------------|
| GET    | `/historicos`       | Listar todos os históricos |
| GET    | `/historicos/{id}`  | Buscar histórico por ID    |
| POST   | `/historicos`       | Criar novo histórico       |
| PUT    | `/historicos/{id}`  | Atualizar histórico        |
| DELETE | `/historicos/{id}`  | Deletar histórico          |

### Exemplo de Requisição - Criar Histórico
```json
POST /historicos
{
  "tecnicoResponsavel": "João Silva",
  "acompanhantes": ["Maria Santos", "Pedro Costa"],
  "sistemasParaInstalarCertificado": [
    {
      "nomeSistema": "Sistema Fiscal",
      "status": "CONCLUIDO"
    },
    {
      "nomeSistema": "Sistema PDV",
      "status": "PENDENTE"
    }
  ],
  "dataInstalacao": "2024-01-15",
  "statusInstalacao": "EM_ANDAMENTO",
  "idLoja": "uuid-da-loja",
  "prazoExpiracaoCertificado": "2024-12-31"
}
```
---

## 🔐 Autenticação (apenas no perfil `prod`)

| Método | Endpoint   | Descrição               |
|--------|------------|-------------------------|
| POST   | `/login`   | Autenticação via LDAP   |
---

## 🔍 Validações de Negócio

**Lojas**
- Nome e código **únicos**

**Históricos**
- Data de instalação **não pode ser futura**
- Não permite duplicação por **loja + data + prazo de expiração**

**Certificados**
- Cálculo automático de status baseado na **data de expiração**

**Processo**
- Status automático baseado no **fluxo completo**
---

## 🚨 Tratamento de Erros

A API retorna erros padronizados no formato:

```json
{
  "tituloErro": "Recurso Não Encontrado",
  "codigoStatusHTTP": 404,
  "detalhesExcecao": "Loja não encontrada com o ID: uuid"
}
```

