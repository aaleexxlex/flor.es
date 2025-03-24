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
  "fecha": "2025-03-22",
  "estado": "Pendiente",
  "total": 25.5,
  "destino": "Calle 789"
}'

# Pedido de Ana (solo Tulipan Amarillo)
curl -X POST "$BASE_URL/pedidos" -H "Content-Type: application/json" -d '{
  "cliente": {"email": "ana@example.com"},
  "floricultor": {"email": "marta@example.com"},
  "fecha": "2025-03-23",
  "estado": "Pendiente",
  "total": 12.0,
  "destino": "Calle 456"
}'

# Relacionar productos con pedidos a través de DetallePedido (suponiendo que el pedido tiene idPedido 1 y 2)
echo "➡ Relacionando productos con pedidos..."

# Relacionar Rosa Roja (Producto 1) y Lirio Blanco (Producto 2) con Pedido 1
curl -X POST "$BASE_URL/detallesPedido" -H "Content-Type: application/json" -d '{
  "cantidad": 1,
  "subtotal": 10.5,
  "pedido": {"idPedido": 1},
  "producto": {"idProducto": 1}
}'

curl -X POST "$BASE_URL/detallesPedido" -H "Content-Type: application/json" -d '{
  "cantidad": 1,
  "subtotal": 15.0,
  "pedido": {"idPedido": 1},
  "producto": {"idProducto": 2}
}'

# Relacionar Tulipan Amarillo (Producto 3) con Pedido 2
curl -X POST "$BASE_URL/detallesPedido" -H "Content-Type: application/json" -d '{
  "cantidad": 1,
  "subtotal": 12.0,
  "pedido": {"idPedido": 2},
  "producto": {"idProducto": 3}
}'

echo "✅ Base de datos poblada con éxito."

