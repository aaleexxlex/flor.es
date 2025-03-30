#!/bin/bash

BASE_URL="http://localhost:8080"

echo "⏳ Poblando la base de datos..."

# Clientes
curl -X POST "$BASE_URL/clientes" -H "Content-Type: application/json" -d '{"nombre": "Juan Perez", "email": "juan@example.com", "direccion": "Calle 123"}'
curl -X POST "$BASE_URL/clientes" -H "Content-Type: application/json" -d '{"nombre": "Ana Gomez", "email": "ana@example.com", "direccion": "Calle 456"}'

# Floricultores
curl -X POST "$BASE_URL/floricultores" -H "Content-Type: application/json" -d '{"nombre": "Luis Flores", "email": "luis@example.com", "ubicacion": "Madrid", "disponibilidad": true}'
curl -X POST "$BASE_URL/floricultores" -H "Content-Type: application/json" -d '{"nombre": "Marta Ramos", "email": "marta@example.com", "ubicacion": "Barcelona", "disponibilidad": true}'

# Productos individuales (no son ramos)
curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Rosa Roja",
  "tipoFlor": "Rosa",
  "color": "Rojo",
  "precio": 10.5,
  "cantidad": 20,
  "imagen": "/images/rosa.png",
  "esRamo": false,
  "floricultor": {"email": "luis@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Lirio Blanco",
  "tipoFlor": "Lirio",
  "color": "Blanco",
  "precio": 15.0,
  "cantidad": 15,
  "imagen": "/images/lirio.png",
  "esRamo": false,
  "floricultor": {"email": "luis@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Tulipán Amarillo",
  "tipoFlor": "Tulipan",
  "color": "Amarillo",
  "precio": 12.0,
  "cantidad": 25,
  "imagen": "/images/tulipan.png",
  "esRamo": false,
  "floricultor": {"email": "marta@example.com"}
}'

# Productos tipo ramo
curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Ramo de Rosas",
  "tipoFlor": "Ramo",
  "color": "Rojo",
  "precio": 39.99,
  "cantidad": 10,
  "imagen": "/images/ramoRosas.jpg",
  "esRamo": true,
  "floricultor": {"email": "luis@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Ramo de Tulipanes",
  "tipoFlor": "Ramo",
  "color": "Multicolor",
  "precio": 34.50,
  "cantidad": 8,
  "imagen": "/images/ramoTulipanes.jpg",
  "esRamo": true,
  "floricultor": {"email": "marta@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Ramo de Margaritas",
  "tipoFlor": "Ramo",
  "color": "Blanco",
  "precio": 29.00,
  "cantidad": 6,
  "imagen": "/images/ramoMargaritas.jpg",
  "esRamo": true,
  "floricultor": {"email": "marta@example.com"}
}'

curl -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Ramo de Lirios",
  "tipoFlor": "Ramo",
  "color": "Blanco",
  "precio": 37.00,
  "cantidad": 5,
  "imagen": "/images/ramoLirios.jpg",
  "esRamo": true,
  "floricultor": {"email": "luis@example.com"}
}'

# Pedidos de prueba (sin ramos)
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
