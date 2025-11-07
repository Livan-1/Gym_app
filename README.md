#  Proyecto GYM COLEMAN – Aplicación Android con Jetpack Compose

##  Descripción general
Este proyecto corresponde a la aplicación **GYM COLEMAN**, desarrollada en **Android Studio con Jetpack Compose**.  
El objetivo fue crear una **app moderna, visual y animada** que represente la identidad de un gimnasio, permitiendo mostrar productos, registrar pedidos y ofrecer una experiencia fluida al usuario.  

Durante el desarrollo se implementaron animaciones, navegación dinámica, validaciones de formularios y un sistema de almacenamiento de pedidos usando `ViewModel`.

---

##  Estructura general del proyecto
La aplicación está compuesta por varias pantallas principales:

### `LoginScreen.kt`
Pantalla inicial de la app.  
Incluye animaciones con una **pesa en movimiento**, un fondo con **degradado azul animado**, y campos de texto personalizados para usuario y contraseña.  
Valida las credenciales y redirige al menú principal en caso de éxito.  

**Credenciales de prueba:**  
```
usuario: usuario  
contraseña: 1234
```

---

###  `DrawerMenu.kt`
Corresponde al **menú lateral** o Drawer.  
Tiene un fondo animado con **degradado en movimiento** y una **pesa que sube y baja rotando**, reforzando la temática deportiva.  
Incluye opciones como “Inicio”, “Productos”, “Entrenamientos” y “Cerrar sesión”, todas con **animaciones progresivas de entrada** (fade y slide).  
Desde aquí se accede a la lista de productos.

---

###  `ProductListScreen.kt`
Muestra todos los productos del gimnasio con sus **nombres, precios e imágenes**.  
Cada tarjeta aparece con una **animación individual de entrada** y un **efecto de rebote** para hacerlo más visual.  
El banner superior también tiene animación con `fadeIn` y `slideInVertically`.  
Al seleccionar un producto, se navega a la pantalla de detalle correspondiente.

---

###  `ProductoFormScreen.kt`
Pantalla donde el usuario puede ver los detalles del producto seleccionado y realizar un pedido.  
Incluye un formulario para ingresar **cantidad, dirección y método de pago** (efectivo o débito).  
El pedido se guarda con un **ViewModel**, y los pedidos confirmados se muestran en una lista dinámica.  
También se usó una **animación de entrada** para la imagen del producto.

---

###  `AppNav.kt`
Controla toda la **navegación de la aplicación**.  
Define las rutas entre las pantallas y permite pasar datos dinámicos (como nombre, precio o imagen del producto) a través del `NavController`.

---

##  Tecnologías utilizadas
- **Kotlin**
- **Jetpack Compose**
- **Material Design 3**
- **ViewModel / State Management**
- **Animaciones (fade, slide, scale, rebote, transiciones infinitas)**
- **Navegación con `NavHost` y `NavController`**

---

##  Diseño y experiencia de usuario
Se trabajó con una **paleta de colores inspirada en el gimnasio**:
- Azul: energía y confianza.  
- Negro: fuerza.  
- Dorado: superación y motivación.  

Además, todas las pantallas mantienen coherencia visual, validaciones de campos y animaciones suaves que aportan dinamismo sin distraer al usuario.

---

##  Aprendizaje obtenido
Durante el desarrollo de este proyecto aprendí a:
- Implementar **animaciones en Compose** combinando `fadeIn`, `slideIn`, `scaleIn` y animaciones infinitas.
- Utilizar **LazyColumn** para mostrar listas dinámicas.
- Manejar la **navegación entre pantallas con argumentos**.
- Aplicar **principios de diseño visual y usabilidad** en una app real.
- Controlar el estado de la aplicación con **ViewModel y Compose State**.

---

##  Resultado final
La aplicación GYM COLEMAN combina **funcionalidad, diseño y movimiento**, logrando una experiencia de usuario fluida y atractiva.  
Representa visualmente la energía y dinamismo de un gimnasio moderno, usando animaciones para destacar la marca y guiar la interacción del usuario.
