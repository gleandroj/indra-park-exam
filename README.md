# indra-park-exam
Desafio técnico – Desenvolvedor Web

### Database

* [Postgres] 

### Config Database

Configurar o banco de dados em application.properties.

# Getting Started Api

- run `mvn clean install`
- run `mvn test`
- run `mvn spring-boot:run`

Note: Jacoco Code Coverage is located in `/target/site/jacoco/index.html` 

# Getting Started Frontend

Configurar URL da API em environments/environments.ts

- run `ng test`
- run `ng serve`

Note: 
- Foi utilizado a biblioteca Highcharts no lugar da ng2-charts (chart.js) por ser uma biblioteca mais completa e customizável.
- Só sera possível executar o `ng serve` na ultima versão estável do nodejs  (>= v10.16.0) devido ter realizado atualização do framework Angular e seus componentes.