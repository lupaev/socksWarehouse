# Тестовое задание SkyPro
### Приложение для автоматизации учёта носков на складе магазина
### Функционал:
- учёт прихода и отгрузки носков;
- проверка общего количества носков определенного цвета и состава в данный момент времени.

### Используемые технологии:
- Java 11
- Spring 5.3.26
- Spring Boot 2.7.10
- PostgreSQL 15.2
- Liquibase
- MupStruct 1.5.3
- Lombok 1.18.26

### Установка и запуск Docker 

``git clone https://github.com/lupaev/socksWarehous.git && cd socksWarehous && docker-compose up -d``

для выключения команда:

``docker-compose stop``






Api через swagger: http://localhost:8080/swagger-ui/index.html

PostgreSQL работает на стандартном порте 5432