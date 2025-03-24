#!/bin/bash

# URL base de la API
BASE_URL="http://localhost:8080/productos"

# 1. Crear un nuevo producto
echo "Creando un nuevo producto..."
curl -X POST $BASE_URL \
    -H "Content-Type: application/json" \
    -d '{"nombre": "Rosa Roja", "tipoFlor": "Rosa", "precio": 10.50, "floricultor": {"email": "marta@example.com"}}' \
    

# 2. Obtener todos los productos
echo "Obteniendo todos los productos..."
curl -X GET $BASE_URL \
    

# 3. Obtener un producto por ID
echo "Obteniendo producto por ID (idProducto=1)..."
curl -X GET $BASE_URL/3 \
    

# 4. Actualizar un producto
echo "Actualizando producto con ID 1..."
curl -X PUT $BASE_URL/3 \
    -H "Content-Type: application/json" \
    -d '{"nombre": "Rosa Roja Actualizada", "tipoFlor": "Rosa", "precio": 12.00, "floricultor": {"email": "marta@example.com"}}' \
    

# 5. Actualizar parcialmente un producto
echo "Actualizando parcialmente producto con ID 1..."
curl -X PATCH $BASE_URL/3 \
    -H "Content-Type: application/json" \
    -d '{"precio": 15.00}' \
    

# 6. Eliminar un producto
echo "Eliminando producto con ID 1..."
curl -X DELETE $BASE_URL/3 \
    

echo "Pruebas de productos completadas."