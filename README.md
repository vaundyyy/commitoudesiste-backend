http://localhost:8080/swagger-ui/index.html#/

## 📚 API Reference

### 🧑‍💼 Usuários

| Método | Endpoint               | Descrição                     | Corpo da Requisição     | Resposta            |
|--------|------------------------|-------------------------------|--------------------------|---------------------|
| GET    | `/api/usuarios`        | Lista todos os usuários       | –                        | Lista de usuários   |
| GET    | `/api/usuarios/{id}`   | Retorna um usuário específico| –                        | Usuário com ID      |
| POST   | `/api/usuarios`        | Cria um novo usuário          | JSON com dados do usuário | Usuário criado      |
| PUT    | `/api/usuarios/{id}`   | Atualiza um usuário existente| JSON com dados atualizados | Usuário atualizado |
| DELETE | `/api/usuarios/{id}`   | Remove um usuário             | –                        | Status de sucesso   |

#### Exemplo: Criar Usuário (POST `/api/usuarios`)

**Request:**
json
{
  "nome": "Maia Silva",
  "email": "maia.silva@example.com",
  "cpf": "123.456.789-00",
  "senha": "senhaSegura123"
}

Reponse - 
{
  "id": 1,
  "nome": "Maia Silva",
  "email": "maia.silva@example.com",
  "cpf": "123.456.789-00",
  "dataCriacao": "2025-06-06T14:30:00Z"
}

Transações
| Método | Endpoint               | Descrição                        | Corpo da Requisição                   | Resposta                     |
| ------ | ---------------------- | -------------------------------- | ------------------------------------- | ---------------------------- |
| GET    | `/api/transacoes`      | Lista todas as transações        | –                                     | Lista de transações          |
| GET    | `/api/transacoes/{id}` | Detalha uma transação específica | –                                     | Dados da transação           |
| POST   | `/api/transacoes`      | Cria uma nova transação          | JSON com valor, origem, destino, etc. | Transação criada e analisada |
| DELETE | `/api/transacoes/{id}` | Deleta uma transação             | –                                     | Status de sucesso            |

Exemplo: Criar Transação (POST /api/transacoes)
Request:

json
Copiar
Editar
{
  "valor": 500.00,
  "contaOrigem": "12345678901",
  "contaDestino": "10987654321",
  "descricao": "Pagamento Pix"
}

json
{
  "id": 101,
  "valor": 500.00,
  "contaOrigem": "12345678901",
  "contaDestino": "10987654321",
  "statusAnalise": "APROVADA",
  "dataTransacao": "2025-06-06T15:00:00Z"
}

Denúncias
| Método | Endpoint              | Descrição                       | Corpo da Requisição                       | Resposta            |
| ------ | --------------------- | ------------------------------- | ----------------------------------------- | ------------------- |
| GET    | `/api/denuncias`      | Lista todas as denúncias        | –                                         | Lista de denúncias  |
| GET    | `/api/denuncias/{id}` | Detalha uma denúncia específica | –                                         | Dados da denúncia   |
| POST   | `/api/denuncias`      | Cria uma denúncia               | JSON com idTransacao, idMotivo, idUsuario | Denúncia registrada |

Exemplo: Criar Denúncia (POST /api/denuncias)
Request:

json
{
  "idTransacao": 101,
  "idMotivo": 3,
  "idUsuario": 1,
  "descricaoDetalhada": "Transação suspeita de fraude"
}
Response:

json
{
  "id": 501,
  "idTransacao": 101,
  "idMotivo": 3,
  "idUsuario": 1,
  "dataDenuncia": "2025-06-06T16:00:00Z",
  "status": "PENDENTE"
}

Motivos de Denúncia
| Método | Endpoint                | Descrição                    | Corpo da Requisição | Resposta         |
| ------ | ----------------------- | ---------------------------- | ------------------- | ---------------- |
| GET    | `/api/motivos-denuncia` | Lista os motivos disponíveis | –                   | Lista de motivos |
| POST   | `/api/motivos-denuncia` | Adiciona um novo motivo      | JSON com descrição  | Motivo criado    |

Exemplo: Adicionar Motivo (POST /api/motivos-denuncia)
Request:

json
{
  "descricao": "Transação não autorizada"
}
Response:

json
{
  "id": 3,
  "descricao": "Transação não autorizada"
}

Alertas Suspeitos
| Método | Endpoint                      | Descrição                    | Corpo da Requisição | Resposta         |
| ------ | ----------------------------- | ---------------------------- | ------------------- | ---------------- |
| GET    | `/api/alertas-suspeitos`      | Lista alertas suspeitos      | –                   | Lista de alertas |
| GET    | `/api/alertas-suspeitos/{id}` | Detalha um alerta específico | –                   | Dados do alerta  |

Exemplo: Resposta alerta suspeito (GET /api/alertas-suspeitos/{id})
Response:

json
{
  "id": 9001,
  "idTransacao": 101,
  "tipoAlerta": "Valor elevado",
  "descricao": "Transação acima do limite usual",
  "dataAlerta": "2025-06-06T15:10:00Z"
}
