# Stratton Oakmont API

Микросервисная система управления для компании Stratton Oakmont.

## Запуск проекта

### Требования
- Docker и Docker Compose
- Java 17 (для локальной сборки)

### Запуск через Docker Compose

```bash
docker-compose up --build -d
```

Проект запустится на портах:
- **8081** - первый инстанс приложения
- **8082** - второй инстанс приложения (для балансировки)
- **5432** - PostgreSQL

## Доступ к API

### Swagger UI

После запуска проекта откройте в браузере:

**http://localhost:8081/api/swagger-ui/index.html**

или

**http://localhost:8081/api/swagger-ui.html** (редирект на index)

### API Endpoints

Все endpoints доступны по адресу: `http://localhost:8081/api/`

#### Аутентификация
- `POST /api/login` - получение JWT токена

#### Заявки (требует роль HR)
- `GET /api/application` - получить список заявок
- `POST /api/application` - создать заявку
- `DELETE /api/application/{id}?reason={true|false}` - одобрить/отклонить заявку

#### Курсы (требует роль SELLER или ANDREW)
- `GET /api/courses` - получить список курсов
- `POST /api/courses` - опубликовать курсы (только ANDREW)

#### Отчёты (требует роль SELLER)
- `POST /api/reports` - отправить отчёт

#### Предпочтения Джордана (требует роль JORDAN или ANDREW)
- `GET /api/jordan-preferences/cocaine`
- `GET /api/jordan-preferences/whiskey`
- `GET /api/jordan-preferences/gong`

## Тестовые учётные данные

### HR
- Логин: `hr`
- Пароль: `hr123`

### SELLER
- Логин: `seller`
- Пароль: `seller123`

## Использование Swagger UI

1. Откройте Swagger UI: http://localhost:8081/api/swagger-ui.html
2. Найдите endpoint `/api/login`
3. Нажмите "Try it out"
4. Введите логин и пароль (например, `{"login": "hr", "password": "hr123"}`)
5. Нажмите "Execute"
6. Скопируйте полученный токен
7. Нажмите кнопку "Authorize" вверху страницы
8. Вставьте токен в поле "Value" (формат: `Bearer <token>` или просто `<token>`)
9. Теперь можно тестировать все endpoints, требующие авторизации

## Структура проекта

- `app-boot-service` - основной сервис с конфигурацией
- `app-person-service` - сервис для работы с персонами и заявками
- `app-user-service` - сервис аутентификации и работы с токенами
- `app-position-service` - сервис для работы с должностями
- `app-courses-service` - сервис курсов
- `app-commons` - общие модели и классы

## Остановка проекта

```bash
docker-compose down
```

Для полной очистки (включая данные БД):

```bash
docker-compose down -v
```


