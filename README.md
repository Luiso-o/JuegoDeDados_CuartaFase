# JuegoDeDados_Cuarta Fase
ApiRest Crud, Juego de dados, Implementación de Test Unitarios y de Integración @JUnit 5 @Mockito @Maven @Workbench @Swuagger 

El juego de dados se juega con dos dados. En caso de que el resultado de la suma de ambos dados sea menor o igual a 7, la partida será ganada, si no es perdida. Un jugador/a puede ver un listado de todas las tiradas que ha realizado y el porcentaje de éxito.

Para poder jugar al juego y realizar una tirada, un usuario debe registrarse con un nombre no repetido. Al crearse, se le asigna un identificador numérico único y una fecha de registro. Si el usuario así lo desea, puedes no añadir ningún nombre y se llamará “ANÓNIMO”. Puede haber más de un jugador “ANÓNIMO”.
Cada jugador puede ver un listado de todas las tiradas que ha hecho, con el valor de cada dado y si se ha ganado o no la partida. Además, puede saber su porcentaje de éxito por todas las tiradas que ha realizado.

No se puede eliminar una partida en concreto, pero sí se puede eliminar todo el listado de tiradas por un jugador/a.

El software debe permitir listar a todos los jugadores/as que hay en el sistema, el porcentaje de éxito de cada jugador/a y el porcentaje de éxito medio de todos los jugadores/as en el sistema.

El software debe respetar los principales patrones de diseño.

NOTAS

Tienes que tener en cuenta los siguientes detalles de construcción:

URL's:
-POST: /players: crea un jugador/a.

-PUT /players: modifica el nombre del jugador/a.

-POST /players/{id}/games/ : un jugador/a específico realiza un tirón de los dados.

-DELETE /players/{id}/games: elimina las tiradas del jugador/a.

-GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje medio de éxitos.

-GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a.

-GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de logros.

-GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.

-GET /players/ranking/winner: devuelve al jugador con peor porcentaje de éxito.

- Fase 4
Añade tests unitarios, de componente y de integración al proyecto con librerías jUnit, AssertJ o Hamcrest.
Añade Mocks al testing del proyecto (Mockito) y Contract Tests (https://docs.pact.io/)

-DEPARTAMENTOS
![CapturaDto](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/74d88778-4cdb-4cb5-a412-5503b1f55b23)

ENTIDADES
![CapturaEntity](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/a3dd7dab-52ee-4079-95bd-24ade070c1fa)

SERVICIOS
![CapturaServices](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/e907e314-2a6c-4df7-9a7b-8f22843202cb)

REPOSITORIOS
![CapturaRepository](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/c189195f-5d84-4df3-8013-be62351ae438)

CONTROLADORES
![CapturaControllers](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/b9249983-fb2b-4b93-97b1-159f15bbd027)

SEGURIDAD
![CapturaSecurity](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/e89c2dc0-01da-4275-aa42-d711a279912c)

EXCEPCIONES
![CapturaExcepciones](https://github.com/Luiso-o/JuegoDeDados_Cuarta_Fase_ApiRest/assets/128043647/9d9aeb2f-5bf0-41d0-a10d-d6db3788ae8c)

