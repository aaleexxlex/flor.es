#!/bin/bash

BASE_URL="http://localhost:8080"

echo "⏳ Poblando la base de datos..."

# Crear Clientes
echo "➡ Creando clientes..."
curl -X POST "$BASE_URL/clientes" -H "Content-Type: application/json" -d '{"nombre": "Juan Perez", "email": "juan@example.com", "direccion": "Calle 123"}'
curl -X POST "$BASE_URL/clientes" -H "Content-Type: application/json" -d '{"nombre": "Ana Gomez", "email": "ana@example.com", "direccion": "Calle 456"}'

# Crear Floricultores
echo "➡ Creando floricultores..."
curl -X POST "$BASE_URL/floricultores" -H "Content-Type: application/json" -d '{"nombre": "Luis Flores", "email": "luis@example.com", "ubicacion": "Madrid", "disponibilidad": true}'
curl -X POST "$BASE_URL/floricultores" -H "Content-Type: application/json" -d '{"nombre": "Marta Ramos", "email": "marta@example.com", "ubicacion": "Barcelona", "disponibilidad": true}'

# Crear Productos
echo "➡ Creando productos..."
curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Rosa Roja",
  "tipoFlor": "Rosa",
  "precio": 10.5,
  "floricultor": {"email": "luis@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Lirio Blanco",
  "tipoFlor": "Lirio",
  "precio": 15.0,
  "floricultor": {"email": "luis@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Tulipan Amarillo",
  "tipoFlor": "Tulipan",
  "precio": 12.0,
  "floricultor": {"email": "marta@example.com"}
}'

# Crear Pedidos (con relación a los productos creados)
echo "➡ Creando pedidos..."

# Pedido de Juan (Rosa Roja y Lirio Blanco)
curl -X POST "$BASE_URL/pedidos" -H "Content-Type: application/json" -d '{
  "cliente": {"email": "juan@example.com"},
  "floricultor": {"email": "luis@example.com"},
  "fecha": "2025-03-22T00:00:00",
  "estado": "pendiente",
  "destino": "Calle 789",
  "lineasPedido": [
    {
      "producto": {"idProducto": 1},
      "cantidad": 1,
      "precioUnitario": 10.5
    },
    {
      "producto": {"idProducto": 2},
      "cantidad": 1,
      "precioUnitario": 15.0
    }
  ]
}'

# Pedido de Ana (Tulipan Amarillo)
curl -X POST "$BASE_URL/pedidos" -H "Content-Type: application/json" -d '{
  "cliente": {"email": "ana@example.com"},
  "floricultor": {"email": "marta@example.com"},
  "fecha": "2025-03-23T00:00:00",
  "estado": "pendiente",
  "destino": "Calle 456",
  "lineasPedido": [
    {
      "producto": {"idProducto": 3},
      "cantidad": 1,
      "precioUnitario": 12.0
    }
  ]
}'



echo "✅ Base de datos poblada con éxito."

