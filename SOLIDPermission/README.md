# SOLID Permission Manager

Este proyecto implementa un sistema de gestión de permisos para Android siguiendo los principios SOLID, lo que permite una arquitectura modular, extensible y fácil de mantener.

## Principios SOLID aplicados

### S - Principio de Responsabilidad Única (Single Responsibility Principle)
Cada clase tiene una única responsabilidad:
- `PermissionStrategy`: Define el contrato para las estrategias de permisos
- `BasePermissionStrategy`: Proporciona implementación base común
- Estrategias específicas: Cada una maneja un tipo de permiso específico
- `PermissionManager`: Coordina las estrategias y maneja los callbacks
- `PermissionProvider`: Crea las estrategias de permisos (Factory)

### O - Principio Abierto/Cerrado (Open/Closed Principle)
El sistema está abierto para extensión pero cerrado para modificación:
- Se pueden añadir nuevos tipos de permisos creando nuevas estrategias sin modificar el código existente
- El `PermissionProvider` facilita la integración de nuevas estrategias

### L - Principio de Sustitución de Liskov (Liskov Substitution Principle)
Las implementaciones de `PermissionStrategy` son intercambiables:
- Todas las estrategias implementan la misma interfaz
- El `PermissionManager` trabaja con la abstracción, no con implementaciones concretas

### I - Principio de Segregación de Interfaces (Interface Segregation Principle)
Las interfaces son específicas y cohesivas:
- `PermissionStrategy` define solo los métodos necesarios para gestionar permisos
- `PermissionCallback` define solo los métodos necesarios para notificar eventos de permisos

### D - Principio de Inversión de Dependencias (Dependency Inversion Principle)
Las clases de alto nivel no dependen de las de bajo nivel, ambas dependen de abstracciones:
- `PermissionManager` depende de la interfaz `PermissionStrategy`, no de implementaciones concretas
- `PermissionProvider` facilita la creación de estrategias, desacoplando la creación de su uso

## Tipos de permisos implementados

1. **Almacenamiento**: Acceso a archivos del dispositivo
2. **Ubicación**: Acceso a la ubicación del dispositivo (precisa y aproximada)
3. **Cámara**: Acceso a la cámara del dispositivo
4. **Micrófono**: Acceso al micrófono para grabación de audio

## Cómo usar el sistema de permisos

```java
// 1. Crear una instancia del gestor de permisos
PermissionManager permissionManager = new PermissionManager(this);

// 2. Establecer el callback para recibir notificaciones
permissionManager.setCallback(new PermissionManager.PermissionCallback() {
    @Override
    public void onPermissionRequested(int requestCode, String permissionName) {
        // Manejar la solicitud de permiso
    }

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, String message) {
        // Manejar el resultado del permiso
    }
});

// 3. Solicitar un permiso específico
permissionManager.requestPermission(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE);

// 4. Procesar el resultado en la actividad
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

## Extensibilidad

Para añadir un nuevo tipo de permiso:

1. Crear una nueva clase que extienda `BasePermissionStrategy`
2. Añadir el nuevo tipo de permiso en el enum `PermissionType` de `PermissionProvider`
3. Implementar la creación del nuevo tipo en `PermissionProvider.createPermissionStrategy()`
4. Añadir una constante para el código de solicitud en `PermissionManager`
5. Registrar la nueva estrategia en `PermissionManager.initializePermissionStrategies()`

Este diseño permite añadir nuevos tipos de permisos con cambios mínimos en el código existente.

## Pruebas Unitarias

El proyecto incluye un conjunto completo de pruebas unitarias que validan el funcionamiento del sistema de permisos siguiendo las mejores prácticas de desarrollo:

### Pruebas Unitarias (JUnit + Mockito + Robolectric)

- **BasePermissionStrategyTest**: Valida el funcionamiento de la clase base abstracta para todas las estrategias.
- **CameraPermissionStrategyTest**: Prueba la implementación específica para permisos de cámara.
- **StoragePermissionStrategyTest**: Prueba la implementación de permisos de almacenamiento con lógica específica para diferentes versiones de Android.
- **PermissionProviderTest**: Valida que el Factory Pattern crea correctamente las estrategias adecuadas.
- **PermissionManagerTest**: Prueba la coordinación entre las diferentes estrategias y el manejo de callbacks.

### Pruebas Instrumentadas (Espresso)

- **MainActivityTest**: Prueba la integración del sistema de permisos con la interfaz de usuario en un entorno real de Android.

### Cómo ejecutar las pruebas

Para ejecutar las pruebas unitarias:
```bash
./gradlew test
```

Para ejecutar las pruebas instrumentadas (requiere un dispositivo o emulador):
```bash
./gradlew connectedAndroidTest
```

Las pruebas unitarias utilizan Robolectric para simular el entorno de Android sin necesidad de un emulador, mientras que las pruebas instrumentadas utilizan Espresso para interactuar con la interfaz de usuario en un entorno real.
