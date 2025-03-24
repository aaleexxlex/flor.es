#!/bin/bash

# URL base de la API
BASE_URL="http://localhost:8080/clientes"  # Cambia el puerto si es necesario

# Crear un cliente
echo "Creando un nuevo cliente..."
curl -X POST "$BASE_URL" \
     -H "Content-Type: application/json" \
     -d '{
           "email": "cliente1@example.com",
           "nombre": "Juan Perez",
           "direccion": "Calle Falsa 123"
         }'

echo -e "\n-------------------------------------------------\n"

# Leer todos los clientes
echo "Obteniendo todos los clientes..."
curl -X GET "$BASE_URL"

echo -e "\n-------------------------------------------------\n"

# Leer un cliente por email
echo "Obteniendo el cliente con email cliente1@example.com..."
curl -X GET "$BASE_URL/cliente1@example.com"

echo -e "\n-------------------------------------------------\n"

# Actualizar un cliente
echo "Actualizando el cliente con email cliente1@example.com..."
curl -X PUT "$BASE_URL/cliente1@example.com" \
     -H "Content-Type: application/json" \
     -d '{
           "email": "cliente1@example.com",
           "nombre": "Juan Perez Actualizado",
           "direccion": "Calle Nueva 456"
         }'

echo -e "\n-------------------------------------------------\n"

# Actualizar parcialmente un cliente
echo "Actualizando parcialmente el cliente con email cliente1@example.com..."
curl -X PATCH "$BASE_URL/cliente1@example.com" \
     -H "Content-Type: application/json" \
     -d '{
           "direccion": "Calle Modificada 789"
         }'

echo -e "\n-------------------------------------------------\n"

# Eliminar un cliente
echo "Eliminando el cliente con email cliente1@example.com..."
curl -X DELETE "$BASE_URL/cliente1@example.com"

echo -e "\n-------------------------------------------------\n"

# Verificar que el cliente ha sido eliminado
echo "Intentando obtener el cliente eliminado..."
curl -X GET "$BASE_URL/cliente1@example.com"

