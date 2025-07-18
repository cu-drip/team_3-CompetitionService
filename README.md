# About
Репозиторий для **CompetitionService**

* **Управляет** соревнованиям
* **Регистрирует** команды и участников
* **Уведомляет** участников о скором начале соревнования (не реализовано)

А также по совместительству **работает с командами**:
* Team CRUD
* Добавляет/удаляет участников в команду
* Смотрит всех участников команды

## Контракт
Для просмотра **эндпоинтов** и схем откройте 
`contract.yaml` и посмотрите его [тут](https://editor.swagger.io/)
для удобства.

### Деплой / тестирование
Если вам нужен сервис для теста своих компонентов:
1) Склонируйте себе на машину
2) Впишите контейнер в свой `docker compose`
3) Настройте нужные порты (+ тк сервис работает с UserService с командными запросами, нужно добавить его и в файле `UserClient` поменять пути)
4) Также загляните в склонированный docker compose, он может понадобиться, тк там есть необходимые таблицы (ну и user_service бд не нужна)
5) И не забудьте `application.properties`, данные могут измениться
