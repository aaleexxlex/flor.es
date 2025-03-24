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

# Crear Pedidos
#echo "➡ Creando pedidos..."
#curl -X POST "http://localhost:8080/pedidos" -H "Content-Type: application/json" -d '{
 # "cliente": {"email": "juan@example.com"},
  #"floricultor": {"email": "luis@example.com"},
  #"fecha": "2025-03-22",
  #"estado": "Pendiente",
  #"total": 21.0,
  #"destino": "Calle 789"
#}'

#echo "➡ Creando productos..."
#curl -X POST "http://localhost:8080/productos" -H "Content-Type: application/json" -d '{
 # "nombre": "Rosa Roja",
  #"tipoFlor": "Rosa",
  #"precio": 10.5,
  #"floricultor": {"email": "luis@example.com"}
#}'

echo "✅ Base de datos poblada con éxito."

