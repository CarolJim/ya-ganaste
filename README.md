********************************************************************************************************************************
**`YA GANASTE README`**
********************************************************************************************************************************

Para poder mejorar el proceso de documentación de código así como las referencias y notas que cada versión contienen se ocupará
éste documento como guía de la Aplicación para presentes y futuros desarrolladores integrados en el equipo.
Las intrucciones de uso son las siguientes:

    1. El README solo puede ser editado en master (IMPORTANTE):  Para evitar problemas con la documentación y notas de las versiones el README solo deberá ser actualizado una vez que se haga un merge al branch master. (De preferencia que solo sea editado por el encargado del equipo)
    3. Versión como título:  Cada que se haga merge al master de un branch se debe poner como título la versión con la que se subirá a la Play Store dentro de éste documento, seguido de la fecha en que se realizó el cambio en el documento.
       3.1 Anexar ID de cambio en plataforma SOS.
    4. Descripciones breves:  Una vez que el código de un branch ya sea autorizado para subirse a master se deberá crear una descripción breve de los cambios que se realizaron en el código.
    5. Funcionalidad: Agregar una descripción breve de las nuevas funcionalidades implementadas para el Usuario.
    6. Fechas de desarrollo: Al final de las descripciones se deberá poner las fechas de inicio y fin de desarrollo.
  
**_VERSION 1.7.2 - 5 de Diciembre del 2017 al 2 de Enero del 2018 (ID:)_**

Descripción al desarrollador:

    * Cambio en focos de EditText's al saltar entre ellos.
    * Mantener el Scrollview solo para formularios de pagos, no afectando a los círculos de imágenes de usuario y carrier.
    * Limitar carrusel a 10 elementos y ordenas por carriers en específico.
    * Corrección bug de seguridad para uso de huella digital en Autorización de Transferencia.
    * Solución de aprovisionamiento en FrejaMass para desarrollo.
    * Limpieza de código ejemplo que hacía detener la Aplicación en versiones Android menores a 5.
    * Prellenar el nombre del contacto en el formulario al seleccionarlo directamnte de la agenda nativa de contactos.
    * Correción de coachmarks.
    * Cambio de gif's para flujos de Dongle Adquiriente.
    * Habilitar notificaciones y correción de clases duplicadas para registro en Firebase.
    * Cambio en velocidad de flips para tarjta Emisor y Dongle Adquiriente así como tamaño de los mismos.
    * Implementación de contraseña a 6 dígitos y correción de flujos correspondientes.
    * Correcion de incidencias.
    
Funcionalidad:

    * Implementación de Notificaciones.
    * Mejorar flujo en carrusel.
    * Implementación de contraseña a 6 dígitos.
  
**_VERSION 1.7.1 - 16 de Noviembre al 4 de Diciembre del 2017 (ID:3930)_**

Descripción al desarrollador:

    * Limpieza de Memoria Caché para actualización de versión y desvinculamiento de dispositivo.
    * Implementación de nuevo PIN_DIGESTO para conexión segura a servidores productivos.
    * Implementación de eventos de Fabric para análisis de Big Data (solamente productivo).
    * Lector de Huellas Digitales para secciones:
        + Autorización de Envío (empleando SHA256)
        + Bloquear Temporalmente Tarjeta fuera de sesión (empleando algoritmo de (des)cifrrado de cadena con contraseña y otros elementos aleatorios)
        + Generar Código de Seguridad (empleando SHA256)
    * Funcionalidad de coachmakrs sin tiempo y avance de flujo por medio de clicks en pantalla.
    
Funcionalidad:

    * Implementación del uso de la Huella Digital para accesos rápidos.

**_VERSION 1.7.0.1 - 15 de Noviembre del 2017 (HOT FIX)_**

Descripción al desarrollador:

    * Hardcoded de version de catálogo de comercios para actualización completa de los componentes visuales.
    
Funcionalidad:

    * Corrección logos de comercios para todos los dispositivos.

**_VERSION 1.7.0 - 30 de Octubre al 14 de Noviembre del 2017_**

Descripción al desarrollador:

    * Token Freja para agregar y editar favoritos.
    * Resolver incidencias.
    * Compartir ticket Adquiriente por medio de correo.
    * Envíos automáticos de Tickets.
    * Corrección de coachmarks.
    * Corrección de logos.
    * Nueva pantalla de carga de documentos.
    * Nueva pantalla en formulario de registro adquiriente (PLD)
    * Obtención de países por servicio en registro adquiriente.
    * Clave de rastreo para envíos bancarios.
    * Compartir Screenshot y texto plano como descripción.
    * Preferencias de Lector unificado a pantalla Mi Lector.
    * Preferencias de Tarjeta unificado a pantalla Mi Tarjeta.
    
Funcionalidades:
    
    * Nuevos tutoriales.
    * Screenshot con texto.
    * Nueva vista de estatus de documentos.

**_VERSION 1.6.8 - 25 de Septiembre a 26 de Octubre del 2017_**

Descripción al desarrollador:

    * Pantalla para encuadrar fotos tomadas o imagenes de galería.
    * Cambios en diseño de flujo emisor y adquiriente.
    * Cambios en logos de carrusel.
    * Cambios en mensajes de Error.
    * Nuevos coachmarks.
    * Cobros de Adquiriente.
    * Mejoras flip card y dongle.
    * Ayuda y Legales juntos en preferencias de Usuarios.
    * Imagenes cambiadas en QuickBalance.
    * Compartir por medio de Screenshots.

Funcionalidades:

    * Editar y Eliminar Favoritos
    * Cambio en la pantalla de Login
    * Visualización diferente del diseño de tarjeta (Bloqueada/Desbloqueada)
    * Batería de Lector