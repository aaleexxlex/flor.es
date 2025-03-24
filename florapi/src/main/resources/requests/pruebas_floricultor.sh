#!/bin/bash

# URL base de la API
BASE_URL="http://localhost:8080/floricultores"

# 1. Crear un nuevo floricultor
echo "Creando un nuevo floricultor..."
curl -X POST $BASE_URL \
    -H "Content-Type: application/json" \
    -d '{"email": "floricultor1@example.com", "nombre": "Floricultor 1", "ubicacion": "Madrid", "disponibilidad": true}' \
    

# 2. Obtener todos los floricultores
echo "Obteniendo todos los floricultores..."
curl -X GET $BASE_URL \
    

# 3. Obtener un floricultor por email (ID)
echo "Obteniendo floricultor por email (floricultor1@example.com)..."
curl -X GET $BASE_URL/floricultor1@example.com \
    

# 4. Actualizar un floricultor
echo "Actualizando floricultor con email floricultor1@example.com..."
curl -X PUT $BASE_URL/floricultor1@example.com \
    -H "Content-Type: application/json" \
    -d '{"email": "floricultor1@example.com", "nombre": "Floricultor 1 Actualizado", "ubicacion": "Barcelona", "disponibilidad": false}' \
    

# 5. Actualizar parcialmente un floricultor
echo "Actualizando parcialmente floricultor con email floricultor1@example.com..."
curl -X PATCH $BASE_URL/floricultor1@example.com \
    -H "Content-Type: application/json" \
    -d '{"ubicacion": "Valencia"}' \
    

# 6. Eliminar un floricultor
echo "Eliminando floricultor con email floricultor1@example.com..."
curl -X DELETE $BASE_URL/floricultor1@example.com \
    

echo "Pruebas de floricultores completadas."
