LINK:
https://github.com/Jaime1999l/Jaime_Lopez_Novelas_con_Fragmentos_Widgets.git

JAIME LÓPEZ DÍAZ

# Novelas App - Gestión de Novelas con Widgets y Fragmentos

Esta App permite gestionar una lista de novelas, añadirlas a favoritos, crear reseñas, y aplicar una visualización en modo oscuro. La app utiliza `Firebase Firestore` como base de datos en la nube y `SQLite` para almacenamiento local. Los widgets proporcionan accesos rápidos a las novelas favoritas y los fragments permiten mostrar una novela especifica para poder añadirla a favorita o generar una reseña sobre ella.

## Estructura del Proyecto

### 1. **Actividades Principales**

   - **`PantallaPrincipalActivity`**  
     La actividad principal que carga todas las novelas y las organiza en una lista visual. Permite la navegación hacia otras actividades y carga el tema oscuro según las preferencias guardadas. También coordina la actualización de los widgets.

   - **`AddNovelActivity`**  
     Permite al usuario añadir o editar una novela. Incluye la selección de imagen para la portada y guarda la novela en `Firebase` y `SQLite`.

   - **`FavoritesActivity`**  
     Muestra todas las novelas que se han marcado como favoritas. Se sincroniza en tiempo real con Firebase para reflejar los cambios.

   - **`AddReviewActivity`**  
     Permite añadir reseñas a una novela. Guarda la reseña en Firebase y SQLite y utiliza `ReviewViewModel` para manejar la lógica de datos.

   - **`SettingsActivity`**  
     Controla las preferencias de la aplicación, incluyendo el modo oscuro. Guarda la configuración en `SharedPreferences`.

### 2. **Fragments**

   - **`NovelListFragment`**  
     Muestra la lista de todas las novelas usando un `RecyclerView`. Permite la interacción con elementos de cada novela, como marcarlos como favoritos o añadir una reseña.

   - **`NovelDetailFragment`**  
     Muestra los detalles de una novela seleccionada, con opciones para marcarla como favorita o añadir una reseña. Sincroniza el estado de favorito con Firebase.

### 3. **Adaptadores**

   - **`NovelAdapter`**  
     Adaptador de `RecyclerView` que muestra cada novela en la lista. Permite manejar eventos de clic en los elementos para marcar favoritos o añadir reseñas.

### 4. **ViewModels**

   - **`NovelViewModel`**  
     Gestiona la lista de novelas y sincroniza datos en tiempo real desde Firebase, aplicando un `SnapshotListener`.

   - **`ReviewViewModel`**  
     Maneja las operaciones relacionadas con las reseñas, incluyendo la creación y obtención de todas las reseñas de Firebase.

### 5. **Widget**

   - **`NovelWidgetProvider`**  
     Proporciona un widget que muestra los títulos de las novelas favoritas. Se actualiza en tiempo real en la pantalla principal y permite abrir la app desde el widget.

### 6. **Modelos de Dominio**

   - **`Novel`**  
     Representa una novela con propiedades como título, autor, año, sinopsis, URI de imagen y estado de favorito.

   - **`Review`**  
     Representa una reseña de una novela con campos como ID de novela, revisor, comentario, calificación y nombre de la novela.

### 7. **SQLiteHelper**

   - `SQLiteHelper` es una clase de ayuda para la gestión de la base de datos local SQLite. Contiene:
     - **`novelas`**: Almacena información sobre cada novela.
     - **`reseñas`**: Almacena reseñas de las novelas.
   - Métodos incluyen:
     - `addNovel` y `updateNovel`: Agrega y actualiza novelas.
     - `getAllNovels` y `getFavoriteNovels`: Obtiene todas las novelas o solo las favoritas.
     - `addReview`: Agrega reseñas.
     - `getAllReviews`: Recupera todas las reseñas.

## Explicaciones Importantes

### Almacenamiento de Datos
   - **Firebase Firestore**: Almacena y sincroniza datos en tiempo real, como novelas y reseñas. Los datos de las novelas incluyen el título, autor, año y estado de favorito.
   - **SQLite**: Almacenamiento local para acceder a datos offline. Se sincroniza con Firebase para asegurar que los datos se mantengan actualizados.

### Widgets y Actualización en Tiempo Real
   - El widget de favoritos, gestionado por `NovelWidgetProvider`, muestra los favoritos y se actualiza cada vez que se cambian los favoritos desde la actividad principal o fragmento de detalles.

### Fragmentos y Navegación
   - La `PantallaPrincipalActivity` permite la navegación hacia los fragmentos y actividades mediante eventos de clic y navegación de menú. El `NovelListFragment` y `NovelDetailFragment` se integran para mejorar la modularidad y UX.

### Preferencias del Usuario
   - La configuración de tema se guarda en `SharedPreferences` en `SettingsActivity`. Se aplica al inicio de la app, permitiendo al usuario cambiar entre tema claro y oscuro de forma persistente.
