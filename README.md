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
![TestUnitariosDTO](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/ac09ff06-d942-4b91-8b09-c1e9fd01cc4c)

ENTIDADES
![TestUnitariosEntidades](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/b6f66467-8552-48e1-9885-f22ba837f796)

SERVICIOS
![TestUnitariosServicios](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/b9f343a9-04a7-467d-a648-27674d0a1e76)

REPOSITORIOS
![TestUnitariosRepositorios](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/e8c1fa83-8636-4885-b7c5-45af1a4b8159)

CONTROLADORES
![TestDeIntegracionControladores](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/25693703-e3eb-4c22-acf6-39b20685df6e)

SEGURIDAD
![TestUnitariosSeguridad](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/7db4be49-56ab-4e30-9971-2b4dec35d58d)

EXCEPCIONES
![TestUnitariosExcepciones](https://github.com/Luiso-o/JuegoDeDados_CuartaFase/assets/128043647/589194b6-9df7-47a6-8e15-7ecd470fb55b)


