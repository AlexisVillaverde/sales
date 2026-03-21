# Sales - Sistema de Gestión de Ventas 🛒

Este es un proyecto de gestión de ventas desarrollado para la materia de Móviles Avanzados en el **Instituto Tecnológico del Valle de Oaxaca (ITVO)**. La aplicación está construida bajo los principios de **Clean Architecture** y las últimas tecnologías de desarrollo nativo en Android.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una estructura de **Clean Architecture** dividida en capas para garantizar un código modular, escalable y fácil de testear:

### 1. Presentation Layer (Interfaz de Usuario)
* **Jetpack Compose**: UI declarativa y moderna.
* **MVVM (Model-View-ViewModel)**: Separación clara de la lógica de vista y el estado.
* **Navigation Compose**: Gestión de rutas y flujo entre pantallas (Products, Customers).

### 2. Domain Layer (El Corazón del Negocio)
* **Models**: Clases de datos puras.
* **Use Cases**: Acciones específicas del usuario (`CreateProductUseCase`, `ListCustomerUseCase`).
* **Validation**: Lógica de control para asegurar datos limpios.
* **Repository**: Aquí defines funciones como saveCustomer(customer: Customer)

### 3. Data Layer (Gestión de Datos)
* **Local**: Implementación de base de datos persistente con **Room**.
* **Remote**: Sincronización en la nube mediante **Firebase Firestore**.
* **Mapper**: Traductores encargados de convertir entidades de base de datos a modelos de dominio.
* **Repository Pattern**: Abstracción que decide si obtener datos de memoria, disco o la nube.

### 4. DI (Inyección de Dependencias)
* **Hilt (Dagger)**: El "pegamento" que une todas las capas de forma limpia, facilitando la modularidad y el intercambio de piezas.

## 🛠️ Tecnologías Utilizadas

* **Kotlin**: Lenguaje de programación principal.
* **Jetpack Compose**: Toolkit moderno para construir interfaces.
* **Hilt**: Dependency Injection framework.
* **Room**: Abstracción sobre SQLite para persistencia local.
* **Firebase Firestore**: Base de datos NoSQL en tiempo real.
* **Coroutines & Flow**: Manejo de operaciones asíncronas y reactividad de datos.
