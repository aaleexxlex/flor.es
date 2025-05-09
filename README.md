
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
git clone https://github.com/aaleexxlex/flor.es
cd flor.es
```

### 2. Iniciar el backend

```bash
cd florapi
./mvnw spring-boot:run
```

### 3. Iniciar el frontend

```bash
cd ../florcliente
./mvnw spring-boot:run
```

### 4. Poblar la base de datos en florapi/src/main/resources/requests (opcional)

```bash

bash
chmod +x poblar.sh
./poblar.sh
```
### 5. Configurar la API de Google Maps
Para que la funcionalidad de mapas y autocompletado de direcciones funcione correctamente en la aplicación, es necesario obtener una clave de API de Google Maps y activarla para varios servicios de Google.

¿Por qué es necesaria?
La plataforma flor.es utiliza:

Maps JavaScript API: para mostrar mapas en el frontend.

Places API: para autocompletar direcciones en los formularios.

Geocoding API: para convertir direcciones en coordenadas geográficas (latitud y longitud).

Pasos para obtener y configurar tu clave API
Ve a https://console.cloud.google.com/.

Crea un nuevo proyecto (o selecciona uno ya creado).

En el menú lateral izquierdo, accede a:

API y servicios → Biblioteca

Busca y activa estas 3 APIs:

✅ Maps JavaScript API

✅ Places API

✅ Geocoding API

Luego ve a API y servicios → Credenciales

Haz clic en Crear credencial → Clave de API

Copia la clave generada.

Opcional pero recomendado: puedes configurar restricciones para que solo funcione desde tu dominio o localhost.

¿Dónde poner la API Key?
Debes pegar tu clave en los siguientes archivos del cliente:

florcliente/src/main/resources/templates/fragments/script.html

florcliente/src/main/resources/templates/registro.html

En ambos casos, reemplaza API_KEY por tu clave real:

<script src="https://maps.googleapis.com/maps/api/js?key=API_KEY&libraries=places"></script>
### 6. Abrir en el navegador

```bash
http://localhost:8083/home
```

---

## 🔑 Usuarios de prueba

- Clientes: `juan@example.com`, `ana@example.com`, `laura@example.com`, `pedro@example.com`
- Floricultores: `marta@example.com`, `luis@example.com`, `carlos@example.com`, `sofia@example.com`

---

## 📦 Estructura del proyecto

```
/florapi
/florliente
README.md
```

---

## 🧠 Autores

- 👤 Alejandro Garcia
- 👤 Alejandro Gomez
- 👤 Jaime Gonzalez
- 👤 Mariano Lorenzo Kayser
- 👤 Victor Garcia

---

## 📄 Licencia

Este proyecto es de uso académico y no se distribuye comercialmente.
