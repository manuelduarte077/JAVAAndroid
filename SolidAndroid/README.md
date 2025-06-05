# SOLID Android - Sistema de Login

Este proyecto implementa un sistema de autenticación para Android siguiendo los principios SOLID y buenas prácticas de desarrollo. 
La aplicación permite a los usuarios iniciar sesión o registrarse, almacenando sus credenciales de forma segura en el almacenamiento local del dispositivo.

## Arquitectura

La solución sigue una arquitectura limpia con tres capas bien definidas:

### Capa de Datos
- **SharedPreferencesManager**: Implementación concreta para almacenar credenciales
- **UserPreferences**: Interfaz para abstraer el almacenamiento local
- **AuthRepositoryImpl**: Implementación del repositorio de autenticación

### Capa de Dominio
- **User**: Modelo de datos para el usuario
- **AuthRepository**: Interfaz que define las operaciones de autenticación
- **Casos de uso**: 
  - `LoginUseCase`
  - `RegisterUseCase`
  - `GetCurrentUserUseCase`
  - `LogoutUseCase`

### Capa de Presentación
- **LoginActivity**: Actividad principal para la autenticación
- **LoginViewModel**: ViewModel para manejar la lógica de presentación
- **Fragmentos**: 
  - `LoginFragment`
  - `RegisterFragment`
- **Adaptador**: `LoginPagerAdapter`

## Principios SOLID Aplicados

### Principio de Responsabilidad Única (S)
Cada clase tiene una única responsabilidad:
- `EmailValidator`: Solo valida emails
- Cada caso de uso maneja una única operación

### Principio Abierto/Cerrado (O)
Las implementaciones pueden extenderse sin modificar el código existente:
- Uso de interfaces para permitir múltiples implementaciones
- Repositorios extensibles para añadir nuevas fuentes de datos

### Principio de Sustitución de Liskov (L)
Las implementaciones pueden sustituir a sus interfaces sin afectar el comportamiento:
- `SharedPreferencesManager` implementa `UserPreferences`
- `AuthRepositoryImpl` implementa `AuthRepository`

### Principio de Segregación de Interfaces (I)
Interfaces específicas para cada funcionalidad:
- `AuthRepository` define solo métodos relacionados con la autenticación
- Casos de uso específicos para cada operación

### Principio de Inversión de Dependencias (D)
Dependencias inyectadas mediante constructores:
- Uso de `LoginViewModelFactory` para inyectar dependencias al ViewModel
- Inyección de repositorios en los casos de uso

## Características de Seguridad

- Almacenamiento seguro de credenciales con SharedPreferences
- Hash de contraseñas con SHA-256 antes de almacenarlas
- Validación de campos de entrada para prevenir datos incorrectos

## Flujo de la Aplicación

1. Al iniciar, la app verifica si hay una sesión activa
2. Si no hay sesión, muestra la pantalla de login/registro
3. Tras autenticación exitosa, navega a la pantalla principal
4. Permite cerrar sesión desde la pantalla principal

## Pruebas Unitarias

El proyecto incluye pruebas unitarias exhaustivas para garantizar la calidad y robustez del código:

### Pruebas de Utilidades
- **EmailValidatorTest**: Verifica la correcta validación de direcciones de email
  - Casos de prueba para emails válidos, inválidos, vacíos y nulos

### Pruebas de Casos de Uso
- **LoginUseCaseTest**: Verifica el correcto funcionamiento del caso de uso de login
  - Pruebas con credenciales válidas e inválidas
- **RegisterUseCaseTest**: Verifica el correcto funcionamiento del caso de uso de registro
  - Pruebas con datos válidos y errores de registro

### Pruebas de Repositorio
- **AuthRepositoryImplTest**: Verifica la implementación del repositorio de autenticación
  - Pruebas de login con credenciales válidas e inválidas
  - Pruebas de registro de nuevos usuarios
  - Pruebas de cierre de sesión
  - Pruebas de obtención del usuario actual

### Pruebas de ViewModel
- **LoginViewModelTest**: Verifica la lógica de presentación
  - Pruebas de verificación de usuario actual
  - Pruebas de login con credenciales válidas e inválidas
  - Pruebas de validación de campos
  - Pruebas de registro con datos válidos e inválidos

### Herramientas de Testing
- **JUnit**: Framework de pruebas unitarias
- **Mockito**: Framework para la creación de mocks y stubs
- **InstantTaskExecutorRule**: Regla para probar LiveData de forma síncrona

## Extensibilidad

Esta implementación proporciona una base sólida para un sistema de autenticación que puede extenderse fácilmente para incluir más funcionalidades como:
- Autenticación con servicios externos
- Recuperación de contraseña
- Perfiles de usuario más complejos
- Autenticación biométrica

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Componentes de Android**:
  - LiveData
  - ViewModel
  - ViewPager2
  - Material Design Components
- **Almacenamiento**: SharedPreferences
