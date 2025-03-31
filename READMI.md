# 🌸 flor.es — Plataforma de venta y entrega de flores a domicilio

flor.es es una aplicación web que conecta a floricultores locales con clientes particulares para ofrecer flores frescas y ramos personalizados con entrega rápida y sostenible.

---

## 🚀 Funcionalidades principales

- 🔍 Catálogo con filtros por tipo de flor, color, precio y disponibilidad
- 💐 Compra de ramos predefinidos o creación personalizada
- 🛒 Carrito y gestión de pedidos en tiempo real
- 🌍 Entregas urgentes o programadas según localización
- 🧑‍🌾 Gestión de inventario por parte de los floricultores
- ⭐ Valoraciones de pedidos y logística
- 🧾 Vista de floricultores con productos y puntuación media

---

## ⚙️ Tecnologías utilizadas

- **Frontend:** Spring MVC + Thymeleaf
- **Backend:** Java + Spring Boot
- **Base de datos:** H2 (en memoria)
- **Consumo REST:** RestTemplate
- **Testing:** cURL scripts para pruebas de poblamiento
- **Otros:** Bootstrap, HTML5, CSS3

---

## 🧪 Cómo probar la aplicación

### 1. Clonar el repositorio

```bash
git clone https://github.com/usuario/flor.es.git
cd flor.es
```

### 2. Iniciar el backend

```bash
cd backend
./mvnw spring-boot:run
```

### 3. Iniciar el frontend

```bash
cd ../frontend
./mvnw spring-boot:run
```

### 4. Poblar la base de datos (opcional)

```bash
chmod +x poblar.sh
./poblar.sh
```

---

## 🔑 Usuarios de prueba

- Clientes: `juan@example.com`, `ana@example.com`, `laura@example.com`, `pedro@example.com`
- Floricultores: `marta@example.com`, `luis@example.com`, `carlos@example.com`, `sofia@example.com`

---

## 📦 Estructura del proyecto

```
/backend
/frontend
README.md
```

---

## 🧠 Autores

- 👤 Alejandro Garcia
- 👤 Alejandro Gomez
- 👤 Jaime Gonzalez
- 👤 Mariano Lorenzo Kayser

---

## 📄 Licencia

Este proyecto es de uso académico y no se distribuye comercialmente.
