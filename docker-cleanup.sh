#!/bin/bash

echo "Остановка и удаление существующих контейнеров..."
docker-compose down -v

echo "Удаление образов..."
docker-compose down --rmi all

echo "Очистка неиспользуемых ресурсов Docker..."
docker system prune -f

echo "Запуск контейнеров заново..."
docker-compose up --build -d

echo "Ожидание готовности PostgreSQL..."
sleep 10

echo "Проверка статуса контейнеров..."
docker-compose ps

echo "Проверка логов PostgreSQL..."
docker-compose logs postgres

echo "Подключение к базе данных для проверки таблиц..."
docker-compose exec postgres psql -U cassidal -d todoist_mp_db -c "\dt" 