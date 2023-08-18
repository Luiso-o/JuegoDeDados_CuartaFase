# JuegoDeDados_segundaFase
ApiRest Crud, Juego de dados  persistiendo MysqlWorkbench como base de datos, @Maven @Workbench @Swuagger 

El juego de dados se juega con dos dados. En caso de que el resultado de la suma de ambos dados sea menor o igual a 7, la partida será ganada, si no es perdida. Un jugador/a puede ver un listado de todas las tiradas que ha realizado y el porcentaje de éxito.

Para poder jugar al juego y realizar una tirada, un usuario debe registrarse con un nombre no repetido. Al crearse, se le asigna un identificador numérico único y una fecha de registro. Si el usuario así lo desea, puedes no añadir ningún nombre y se llamará “ANÓNIMO”. Puede haber más de un jugador “ANÓNIMO”.
Cada jugador puede ver un listado de todas las tiradas que ha hecho, con el valor de cada dado y si se ha ganado o no la partida. Además, puede saber su porcentaje de éxito por todas las tiradas que ha realizado.

No se puede eliminar una partida en concreto, pero sí se puede eliminar todo el listado de tiradas por un jugador/a.

El software debe permitir listar a todos los jugadores/as que hay en el sistema, el porcentaje de éxito de cada jugador/a y el porcentaje de éxito medio de todos los jugadores/as en el sistema.

El software debe respetar los principales patrones de diseño.

NOTAS

Tienes que tener en cuenta los siguientes detalles de construcción:

URL's:
POST: /players: crea un jugador/a.
PUT /players: modifica el nombre del jugador/a.
POST /players/{id}/games/ : un jugador/a específico realiza un tirón de los dados.
DELETE /players/{id}/games: elimina las tiradas del jugador/a.
GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje medio de éxitos.
GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a.
GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de logros.
GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.
GET /players/ranking/winner: devuelve al jugador con peor porcentaje de éxito.

- Fase 3
Añade seguridad: incluye autenticación por JWT en todos los accesos a las URL's del microservicio.

1)-OPENAPI SWAGGER
![Captura1](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/aac97443-c315-45ed-a261-64f556691404)

2)-REGISTER
![Captura2](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/4c6842f5-2cd7-4967-afc7-c2366ff498d8)

![Captura3](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/ec95d44e-9ecb-4e76-94f0-8e30c38c36a1)

3)-LOGIN 
![Captura4](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/dd075a5e-1d84-428b-9612-44714e13f0e4)

4)-AUTHORITATION
![Captura5](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/db79c841-8ff0-409b-9096-4f51746ed70a)

5)-UPDATE USER AUTHENTICATION
![Captura6](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/dcfcfcfb-ee65-41ba-898c-e858f2257eb8)

![Captura7](https://github.com/Luiso-o/JuegoDeDado_CuartaFase/assets/128043647/f16410b4-4ee4-4c01-954f-d77a09f5375d)


