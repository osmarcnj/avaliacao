# PROVA PARA DESENVOLVEDOR BACKEND JAVA - ERP

## Descrição

Essa prova tem como objetivo avaliar três possíveis níveis de conhecimento usando a mesma
problemática, sendo cada nível mais complexo do que o outro.
• Você pode escolher o nível de dificuldade que está mais de acordo com o seu grau de conhecimento
para resolver a prova.
• Cada nível incrementa as tecnologias mínimas que devem ser utilizadas, os requisitos que devem ser
implementados e os critérios de aceitação.
• Não é necessário fazer a parte do front-end desse desafio, apenas back-end; se você for um
programador full stack você receberá uma prova diferente para front-end.
• O resultado da prova deve ser publicado no GitHub (fornecer a URL) ou enviado em um arquivo ZIP.

## 1 - Configuração

Banco de Dados MySql:
* Criar uma base com o nome `avaliacao`;
* No arquivo `application.yml` (`/src/main/resources/application.yml`) estão as configurações de `Usuário` e `Senha` as mesmas deverão ser alteradas para a configuração informada no BD.
* As Tabelas são criadas automaticamente toda vez que o aplicação é recarregado.

## 2 - Rodando a Aplicação
Para rodar a aplicação utilizar o comando Maven abaixo:
* mvn spring-boot:run

## 3 - Consumindo a Aplicação

## 3.1 - Produto / Serviço
Para consumir os Serviços de Produto/Servico utiliza-se os EndPoits abaixo:
* Para adicionar uma `Novo Produto/Servico` Method: `POST` localhost:8080/produtoServico informando o JSON
  {  
  "name":"String",
  "tipo_dado": "String", //PRODUTO ou SERVICO
  "ativo": "boolean",
  "valor": "BigDecimal"
  }
* Para consultar os Pedidos/Servicos Method: `GET` localhost:8080/produtoServico, retornará o seguinte JSON
  [
  {
  "id": "UUID"
  "name":"String",
  "ativo": "boolean",
  "valor": "BigDecimal",
  "tipo_dado": "String"
  }
  ]
  * Ainda tem consultas personalizadas de Produto / Servico nas URL 'GET'
    ****localhost:8080/produtoServico/listPage**** (para listagem paginada)
    **** localhost:8080/produtoServico/{id} ****(para listagem por id)
    **** localhost:8080/produtoServico/listName/{name}**** (para listagem filtrada por nome essa usando QueryDSL)

* Para alterar os Pedidos/Servicos Method: `PUT` localhost:8080/produtoServico/alterar, passando o seguinte JSON
  [
  {
  "id": "UUID"
  "name":"String",
  "ativo": "boolean",
  "valor": "BigDecimal",
  "	tipo_dado": "String"
  }
  ]

* Para Deletar um Pedido/Servico Method: `DELETE` localhost:8080/produtoServico/deletar/{id}, passando o id na URL.

## 3.2 - Pedido

* Para adicionar uma `Novo Produto/Servico` Method: `POST` localhost:8080/pedidos informando o JSON
  {
  "desconto": "BigDecimal",
  "ativo": "Boolean",
  "item_pedido": [
  {
  "quantidade": "int",
  "valor": "BigDecimal",
  "valor_total": "BigDecimal",
  "produto_servico": {
  "id": "UUID",
  "name": "String",
  "ativo": "boolean",
  "valor": "BigDecimal",
  "tipo_dado": "String"
  }
  }
  ]
  }

* Para consultar os Pedidos Method: `GET` localhost:8080/pedidos, retornará o seguinte JSON
  [
  {
  "id": "UUID",
  "valor": "BigDecimal",
  "ativo": "boolan",
  "data_pedido": "Calendar",
  "valor_desconto": "BigDecimal",
  "valor_total": "BigDecimal",
  "item_pedido": [
  {
  "id": UUID",
  "quantidade": "int",
  "valor": "BigDecimal",
  "valor_total": "BigDecimal",
  "produto_servico": {
  "id": "UUID",
  "name": "String",
  "ativo": "boolean",
  "valor": "BigDecimal",
  "tipo_dado": "String"
  }
  }
  ]
  }
  ]

* Ainda tem consultas personalizadas de Produto / Servico nas URL 'GET'
  ****localhost:8080/pedidos/listPage**** (para listagem paginada)
  **** localhost:8080/pedidos/{id} ****(para listagem por id)

* Para alterar os Pedidos Method: `PUT` localhost:8080/pedidos/alterar, passando o seguinte JSON
  {
  "id": "UUID",
  "data_pedido": "Calendar", //Format dd/MM/yyyy HH:mm:ss
  "ativo": "boolean"
  }

* Para Deletar um Pedido/Servico Method: `DELETE` localhost:8080/pedidos/deletar/{id}, passando o id na URL.

* Para adicionar um Item Pedido ao Pedido Method: `PUT` localhost:8080/pedidos/addItem, enviando o seguinte JSON
  {
  "id": "UUID",
  "desconto": "BigDecimal",
  "ativo": "boolean",
  "data_pedido": "Calendar",
  "item_pedido": [
  {
  "quantidade": "int",
  "valor": "BigDecimal",
  "valor_total": "BigDecimal",
  "produto_servico": {
  "id": "UUID",
  "name": "String",
  "ativo": "boolean",
  "valor": "BigDecimal",
  "tipo_dado": "String"
  }
  }
  ]
  }

* Para remover um Item Pedido ao Pedido Method: `DELETE` localhost:8080/pedidos/addItem, enviando o seguinte JSON


		[
			{
        "id": "UUID",
        "valor": "BigDecimal",
        "ativo": "boolan",
        "data_pedido": "Calendar",
        "valor_desconto": "BigDecimal",
        "valor_total": "BigDecimal",
        "item_pedido": [
            {
                "id": UUID",
                "quantidade": "int",
                "valor": "BigDecimal",
                "valor_total": "BigDecimal",
                "produto_servico": {
                    "id": "UUID",
                    "name": "String",
                    "ativo": "boolean",
                    "valor": "BigDecimal",
                    "tipo_dado": "String"
                }
            }
        ]
   	 }
		]


