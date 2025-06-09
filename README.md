# Colección de Proyectos Android SOLID

Este repositorio contiene una colección de proyectos Android que implementan los principios SOLID y otras buenas prácticas de desarrollo. Cada proyecto se enfoca en diferentes aspectos del desarrollo de aplicaciones Android, desde gestión de permisos hasta consumo de APIs.

## Principios SOLID

Los principios SOLID son un conjunto de directrices de diseño orientado a objetos que promueven la creación de software mantenible, extensible y robusto:

- **S**: Single Responsibility Principle (Principio de Responsabilidad Única)
- **O**: Open/Closed Principle (Principio Abierto/Cerrado)
- **L**: Liskov Substitution Principle (Principio de Sustitución de Liskov)
- **I**: Interface Segregation Principle (Principio de Segregación de Interfaces)
- **D**: Dependency Inversion Principle (Principio de Inversión de Dependencias)

## Proyectos Incluidos

### SOLIDPermission

Un sistema de gestión de permisos para Android que implementa los principios SOLID. Permite manejar diferentes tipos de permisos (cámara, almacenamiento, ubicación, micrófono) de manera modular y extensible.

[Ver detalles del proyecto](./SOLIDPermission/README.md)

### SolidAndroid

Sistema de autenticación para Android siguiendo los principios SOLID y arquitectura MVVM. Implementa funcionalidades de login y registro con almacenamiento seguro de credenciales.

[Ver detalles del proyecto](./SolidAndroid/README.md)

### Rickandmorty

Aplicación Android que consume la API de Rick and Morty para mostrar información sobre personajes, episodios y ubicaciones de la serie. Implementa arquitectura moderna y patrones de diseño recomendados.

### Cartoons

Aplicación Android para explorar información sobre series de dibujos animados. Utiliza conexión a API externa y muestra los datos en una interfaz de usuario moderna.

### HarryPotter

Aplicación Android que muestra información sobre el universo de Harry Potter, incluyendo personajes, hechizos y más. Implementa navegación entre actividades y visualización detallada de elementos.

### FakeApi

Proyecto Android que demuestra el consumo de una API ficticia (posiblemente FakeStore API) para mostrar productos y sus detalles. Incluye navegación entre lista de productos y vista detallada.

### AndroidFirebase

Proyecto Android que integra servicios de Firebase como autenticación, base de datos en tiempo real, almacenamiento y notificaciones push.

### AndroidJavaApp

Aplicación Android desarrollada en Java que implementa funcionalidades básicas y sirve como ejemplo de buenas prácticas de desarrollo.

### BDAndroid

Proyecto Android enfocado en la implementación de bases de datos locales, posiblemente utilizando Room o SQLite.

### NoteKeep

Aplicación de notas para Android que permite crear, editar y eliminar notas con almacenamiento local.

## Características Comunes

Estos proyectos comparten varias características:

- Implementación de principios SOLID
- Arquitecturas modernas (MVVM, Clean Architecture)
- Patrones de diseño recomendados
- Buenas prácticas de desarrollo Android
- Pruebas unitarias e instrumentadas
- Consumo de APIs externas
- Almacenamiento local de datos