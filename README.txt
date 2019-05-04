############# MovieFlix ##############
############ CAPA PRINCIPAL 
##### activities: Esta Carpeta contiene las clases Activity
AppCompatPrefenceActivity: Clase tipo Activity donde se cargan las funciones de la barra de accion.
FavoritesActivity: Clase tipo Activity en la cual se muestra la base de datos sqlite de pel�culas agregadas a favoritos.
InfoActivity: Clase tipo Activity en la cual se muestra informaci�n de contacto del desarrollador.
MovieDetailActivity: Clase tipo Activity en la cual se muestra el detalle de la pel�cula seleccionada de las listas.
ProximasActivity: Clase tipo Activity donde se muestra el listado de pel�culas pr�ximas a estreno.
SearchActivity: Clase tipo Activity donde se muestra los generos y tambi�n se consumen los servicios web para el buscador online.
SearchResultActivity: Clase tipo Activity en la cual se obtienen los resultados de la b�squeda.
SplashActivity: Clase tipo Activity que se carga cuando inicia la app.
TopActivity: Clase tipo Activity donde se muestra el listado de pel�culas mejor calificadas.
TrendingActivity: Clase tipo Activity donde se muestra el listado de pel�culas populares.
##### adapters: En esta carpeta se encuentra las clases tipo Adapter. 
CastRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de actores en el cast_item.
CrewRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de equipo en el crew_item.
GenresRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de generos o categorias en el cardview_item_genres.
MovieRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de peliculas populares en el cardview_item_movie.
ProximasRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de pel�culas pr�ximas a estreno en el cardview_ cardview_item_proximas.
ProximasRecyclerViewAdapter: Clase tipo RecyclerView.Adapter que maneja la lista de pel�culas mejor calificadas a estreno en el cardview_item_top.
##### data: 
	DBModels: Lineamientos de variables para las columnas de la base de datos de favoritos.
	FavoritesDbHelper: Creacion base de datos sqlite de favoritos.
	NetworkUtils: Hace llamados y construye peticion al servicio.
##### models: Esta carpeta contiene las clases de Modelos
BasicResponse: Clase donde se definen los atributos y metodos de una respuesta basica del servicio web.
Cast: Clase donde se definen los atributos y metodos de un �tems del reparto de la lista obtenida del servicio.
Casts: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el vector de reparto y equipo.
Crew: Clase donde se definen los atributos y metodos de un items del equipo de la lista obtenida del servicio.
GenreResponde: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener la lista de generos o categorias.
Genres: Clase donde se definen los atributos y metodos de un genero o categor�a de la lista obtenida del servicio.
Movie: Clase donde se definen los atributos y metodos de un pelicula de la lista obtenida del servicio.
MovieResponde: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener la lista de peliculas.
Person: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el image de reparto y equipo.
ProductionCompanies: Clase donde se definen los atributos y metodos de un productor de la lista obtenida del servicio.
Videos: Clase donde se definen los atributos y metodos de la respuesta del servicio web de obtener el vector de trailers.
##### services: Esta carpeta contiene las clases de servicios
CallbackListenerRetrofit: Identifica respuesta del servicio si OK o existe algun error
EndpointRetrofit: Servicio que llama la app
##### util: Esta carpeta contiene la clase de utilidades del sistema
Utils: Utilidades de sistema en este caso para verificar conexi�n a internet


############ En que consiste el principio de responsabilidad unica? Cu�l es su propusito?
Este principio consiste en que una clase debe de tener una unica responsabilidad, con el proposito de no alterar otros servicios cuando hay fallos.
############ Qu� caracter�sticas tiene, seg�n su opini�n, un �buen� c�digo o c�digo limpio?
Segun mi opinion un codigo limpio debe dar a entender claramente los procesos realizados para su facil compresi�n y futuros cambios.
