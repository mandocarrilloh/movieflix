############# MovieFlix ##############
############ CAPA PRINCIPAL 
##### activities: Esta Carpeta contiene las clases Activity
AppCompatPrefenceActivity: Clase tipo Activity donde se cargan las funciones de la barra de accion.
FavoritesActivity: Clase tipo Activity en la cual se muestra la base de datos sqlite de películas agregadas a favoritos.
InfoActivity: Clase tipo Activity en la cual se muestra información de contacto del desarrollador.
MovieDetailActivity: Clase tipo Activity en la cual se muestra el detalle de la película seleccionada de las listas.
ProximasActivity: Clase tipo Activity donde se muestra el listado de películas próximas a estreno.
SearchActivity: Clase tipo Activity donde se muestra los generos y también se consumen los servicios web para el buscador online.
SearchResultActivity: Clase tipo Activity en la cual se obtienen los resultados de la búsqueda.
SplashActivity: Clase tipo Activity que se carga cuando inicia la app.
TopActivity: Clase tipo Activity donde se muestra el listado de películas mejor calificadas.
TrendingActivity: Clase tipo Activity donde se muestra el listado de películas populares.
##### adapters: En esta carpeta se encuentra las clases tipo Adapter. 
CastRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de actores en el cast_item.
CrewRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de equipo en el crew_item.
GenresRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de generos o categorias en el cardview_item_genres.
MovieRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de peliculas populares en el cardview_item_movie.
ProximasRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de películas próximas a estreno en el cardview_ cardview_item_proximas.
ProximasRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de películas mejor calificadas a estreno en el cardview_item_top.
##### data: 
	DBModels: Lineamientos de variables para las columnas de la base de datos de favoritos.
	FavoritesDbHelper: Creacion base de datos sqlite de favoritos.
	NetworkUtils: Hace llamados y construye peticion al servicio.
##### models: Esta carpeta contiene las clases de Modelos
BasicResponse: Clase donde se definen los atributos y metodos de una respuesta basica del servicio web.
Cast: Clase donde se definen los atributos y metodos de un ítems del reparto de la lista obtenida del servicio.
Casts: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el vector de reparto y equipo.
Crew: Clase donde se definen los atributos y metodos de un items del equipo de la lista obtenida del servicio.
GenreResponde: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener la lista de generos o categorias.
Genres: Clase donde se definen los atributos y metodos de un genero o categoría de la lista obtenida del servicio.
Movie: Clase donde se definen los atributos y metodos de un pelicula de la lista obtenida del servicio.
MovieResponde: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener la lista de peliculas.
Person: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el image de reparto y equipo.
ProductionCompanies: Clase donde se definen los atributos y metodos de un productor de la lista obtenida del servicio.
Videos: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el vector de trailers.
##### services: Esta carpeta contiene las clases de servicios
CallbackListenerRetrofit: Identifica respuesta del servicio si OK o existe algun error
EndpointRetrofit: Servicio que llama la app
##### util: Esta carpeta contiene la clase de utilidades del sistema
Utils: Utilidades de sistema en este caso para verificar conexión a internet


############ En que consiste el principio de responsabilidad unica? Cuál es su propusito?
Este principio consiste en que una clase debe de tener una unica responsabilidad, con el proposito de no alterar otros servicios cuando hay fallos.
############ Qué características tiene, según su opinión, un “buen” código o código limpio?
Segun mi opinion un codigo limpio debe dar a entender claramente los procesos realizados para su facil compresión y futuros cambios.
