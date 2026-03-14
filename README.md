# Proyecto: Procesamiento de Transacciones Bancarias con RabbitMQ

Este proyecto implementa un sistema distribuido para el procesamiento de transacciones bancarias utilizando el patrón **Producer–Consumer** con **RabbitMQ** como sistema de mensajería.

El sistema obtiene transacciones desde una API externa, las distribuye en colas según el banco destino y posteriormente las procesa para almacenarlas mediante otro servicio API.

El objetivo principal es desacoplar el procesamiento de transacciones utilizando colas de mensajería.

---

# Tecnologías Utilizadas

* Java 17
* Maven
* RabbitMQ
* Docker
* Jackson (JSON)
* HttpClient (Java)
* Eclipse IDE
* Postman

---

# Arquitectura del Sistema

El sistema sigue una arquitectura basada en colas de mensajería:

API GET → Producer → RabbitMQ → Consumer → API POST

1. El **Producer** consume el endpoint GET de transacciones.
2. Cada transacción se envía a una cola cuyo nombre corresponde al banco destino.
3. **RabbitMQ** almacena y distribuye los mensajes.
4. El **Consumer** escucha múltiples colas.
5. Cada transacción es enviada al endpoint POST para ser almacenada.

---

# Principios de Diseño Aplicados

## Single Responsibility Principle

Cada clase tiene una única responsabilidad:

* **TransaccionService** → enviar transacciones al API
* **RabbitConsumer** → consumir mensajes desde RabbitMQ
* **Modelos (Transaccion, Detalle, Referencias)** → representar datos

## Open/Closed Principle

El sistema permite agregar nuevos bancos sin modificar la lógica del consumidor.

Solo es necesario agregar el nombre del banco en la lista de colas.

---

# Estructura del Proyecto

## Producer

```
producer
 ├─ model
 ├─ rabbit
 ├─ service
 └─ Main
```

Responsabilidades:

* Consumir el API GET
* Parsear JSON
* Enviar cada transacción a RabbitMQ

---

## Consumer

```
consumer
 ├─ model
 ├─ rabbit
 ├─ service
 └─ Main
```

Responsabilidades:

* Escuchar múltiples colas
* Convertir JSON a objetos
* Enviar transacciones al API POST
* Confirmar mensajes con ACK manual

---

# Flujo del Sistema

1. Producer obtiene transacciones desde la API.
2. Cada transacción se publica en una cola según el banco destino.
3. RabbitMQ almacena los mensajes.
4. Consumer recibe las transacciones.
5. Consumer envía cada transacción al endpoint POST.
6. Si el POST es exitoso, se confirma el mensaje con ACK.

---

# Ejecución del Proyecto

## 1. Ejecutar RabbitMQ con Docker

```
docker run -d --hostname rabbitmq --name rabbitmq \
-p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Panel de administración:

```
http://localhost:15672
```

Usuario:

```
guest
guest
```

---

## 2. Ejecutar Consumer

Ejecutar la clase:

```
Main (Consumer)
```

---

## 3. Ejecutar Producer

Ejecutar la clase:

```
Main (Producer)
```

---

# Resultado esperado

* Se crean colas dinámicas por banco.
* El Consumer procesa las transacciones.
* Cada transacción se envía al API de almacenamiento.
* El mensaje se confirma con **ACK manual**.

---

# Video

https://drive.google.com/file/d/1N02OVX59qXNVX7JjLQvJLCOEVTfV1vWD/view?usp=sharing
