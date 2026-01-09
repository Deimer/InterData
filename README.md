# 🧩 Technical Test – InterData

Android App – Framework Parameters, Security & Localities

---

## 📱 Descripción / Description

**ES:**
Aplicación Android desarrollada como prueba técnica para **Interrapidisimo**, denominada **InterData**.
La aplicación gestiona la seguridad del usuario mediante autenticación, controla las versiones del aplicativo cruzando datos locales con una API y permite la sincronización de esquemas de datos y localidades en una base de datos local **SQLite (Room)** para garantizar la disponibilidad de la información.

---

## 🧱 Arquitectura y Modularización / Architecture & Modularization

El proyecto sigue los principios de **Clean Architecture** y desarrollo **SOLID** , separando responsabilidades en módulos independientes para asegurar que el código sea funcional, mantenible y escalable.

### 🔄 Flujo de dependencias (Clean Architecture)

app ---> presentation ---> usecase ---> repository ---> datasource
↘
database / network

### ⚙️ Ventajas del diseño modular

- 🚀 **Compilación más rápida:** cada módulo se compila de forma independiente.  
- 🧩 **Reutilización:** módulos como `network` o `database` pueden ser usados en otros proyectos.  
- 🔄 **Escalabilidad:** facilita agregar nuevas features o fuentes de datos sin afectar otras capas.  
- 🧠 **Clean Architecture real:** separación estricta entre lógica de negocio, datos y UI.  

### 🧩 Estructura de módulos

```text
app/
presentation/ → UI (Jetpack Compose), ViewModels y manejo de estados
usecase/      → Lógica de negocio (Validación de versiones, autenticación)
repository/   → Coordinación entre datos remotos (API) y locales (Room)
datasource/   → Implementaciones concretas de fetching y persistencia
network/      → Retrofit, DTOs y manejo de errores de API Rest
database/     → Persistencia local con Room (Entidades y DAOs)
Gradle Scripts/

```

* **presentation:** Implementa las 3 pantallas principales: Home, Tablas y Localidades.

* **usecase:** Contiene la lógica para comparar versiones (Same, Lower, Greater) y procesar la autenticación.

* **repository:** Gestiona el flujo de datos; por ejemplo, si el login es exitoso (200 OK), coordina el almacenamiento local.

* **network:** Gestiona los endpoints de Parametros Framework, Seguridad y Sincronizador de Datos.

* **database:** Almacena la información del usuario y los esquemas de tablas retornados por el API.


---

## 🧠 Tecnologías / Tech Stack

| Tipo | Librerías / Tecnologías |
|------|---------------------------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose, Material3 |
| Arquitectura | Clean Architecture, MVVM, Repository Pattern |
| Persistencia | Room Database |
| Inyección de dependencias | Hilt |
| Conectividad | Retrofit + OkHttp |
| Manejo de estados | Kotlin Flow, StateFlow |
| Asincronismo | Coroutines |

---

## 🌐 Flujo de Capas / Layer Flow

1. **Capa Seguridad:** Control de versiones cruzado (API vs Local) y autenticación de usuario mediante consumo de API Rest con manejo de headers y body específicos.

2. **Capa Datos:** Si la respuesta es exitosa (HTTP 200), se extraen y almacenan localmente los datos de identificación y esquemas de tablas.

3. **Capa Presentación:**
* **Home:** Visualiza datos del usuario y accesos a módulos.

* **Tablas:** Exhibe la información de esquemas sincronizados.

* **Localidades:** Lista `cityAbbreviation` y `fullName` obtenidas del endpoint de parámetros.

---

## 🧩 Estrategia de Persistencia y Errores

* **Manejo de Errores:** Implementación de bloques Try/Catch y validación de códigos de respuesta HTTP (alertas si el código es diferente de 200).

* **Persistencia Offline:** Los datos de tablas y el perfil del usuario se consultan desde la base de datos local una vez sincronizados, permitiendo la visualización de la información sin re-consumir el API constantemente.

---

## ⚙️ Instrucciones de compilación y ejecución / Build & Run Instructions

### 1️⃣ Clonar el repositorio / Clone the repository

```bash
git clone https://github.com/TuUsuario/InterData_Technical_Test.git
cd InterData_Technical_Test

```

### 2️⃣ Ejecución

* Asegúrese de tener conexión a internet para la primera sincronización y validación de versión.


* Utilice el perfil de usuario proporcionado en la documentación técnica para las pruebas de autenticación.



## Decisiones de diseño / Design Decisions

* Se implementó una extensión de comparación de versiones para manejar formatos `String` (ej: "10.2") sin conversiones manuales a `Int`.
* El uso de `IntrinsicSize.Max` en la UI asegura que los indicadores visuales (barras/círculos de estado de versión) mantengan consistencia con el tamaño del texto.
* Se modularizó el mapeo de datos (`DTO` -> `Entity` -> `Domain`) para proteger la lógica de negocio de cambios en la API externa.
* Manejo robusto de excepciones mediante operadores de `Flow` para capturar fallos de conexión de forma centralizada.



¿Deseas que añada la sección de pruebas unitarias al README para documentar la validación de la lógica de comparación de versiones?
