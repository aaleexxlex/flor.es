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

# Crear Pedido
echo "➡ Creando pedido..."
pedido_id=$(curl -s -X POST "$BASE_URL/pedidos" -H "Content-Type: application/json" -d '{
  "cliente": {"email": "juan@example.com"},
  "floricultor": {"email": "luis@example.com"},
  "fecha": "2025-03-22",
  "estado": "PENDIENTE",
  "total": 21.0,
  "destino": "Calle 789"
}' | jq -r '.idPedido')

echo "✅ Pedido creado con ID: $pedido_id"

# Crear Producto
echo "➡ Creando producto..."
producto_id=$(curl -s -X POST "$BASE_URL/productos" -H "Content-Type: application/json" -d '{
  "nombre": "Rosa Roja",
  "tipoFlor": "Rosa",
  "precio": 10.5,
  "floricultor": {"email": "luis@example.com"}
}' | jq -r '.idProducto')

echo "✅ Producto creado con ID: $producto_id"

# Crear DetallePedido
echo "➡ Creando detalle de pedido..."
curl -X POST "$BASE_URL/detallesPedido" -H "Content-Type: application/json" -d "{
  \"cantidad\": 2,
  \"subtotal\": 21.0,
  \"pedido\": { \"idPedido\": $pedido_id },
  \"producto\": { \"idProducto\": $producto_id }
}"

# Crear Valoraciones
echo "➡ Creando valoraciones..."
curl -X POST "$BASE_URL/valoraciones" -H "Content-Type: application/json" -d '{
  "tipoValoracion": "Servicio",
  "calificacion": 5,
  "comentario": "Excelente servicio y entrega rápida.",
  "fecha": "2025-03-23",
  "pedido": {"idPedido": 1}
}'



echo "✅ Base de datos poblada con éxito."

