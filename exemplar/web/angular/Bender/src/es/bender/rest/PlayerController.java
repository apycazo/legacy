package es.bender.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import Elements.Player;

//	VER CONFIGURACIÓN EN WebContent/web.xml !

	//	Declaramos que los recursos que se instancian en esta clase son accesibles si la ruta es /Player.
@Path("/Player")
public class PlayerController {

		//	Declaramos el método por el que es accesible (get/post/put/delete)
	@GET
		//	Declaramos que generará un resultado que presentar como respuesta. En este caso texto plano
	@Produces(MediaType.TEXT_PLAIN)
		//	Declaramos que el path para acceder a este recurso es ./getRandomName.
		//	En este caso quedará como http://nombre-del-dominio/rest/Player/getRandomName
	@Path("/getRandomName")
		//	Definimos la función a la que se está llamando.
	public String getRandomName (){
		return "randomName";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRandomPlayer")
	public Player getRandomPlayer (){
		Player player = new Player();
		return player;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String playerHello (){
		return "Hello man!";
	}
	
	@POST
		//	Para poder recibir un JSON y que lo tranforme a POJO (Player) necesito una libreria que lo haga (Jackson)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/completePlayer")
		// Como el elemento que recibe 'player' tiene los mismos atributos que la clase/POJO 'Player' lo matchea* y genera el objeto
	public Response completePlayer(Player player){
			// Hago lo que tenga que hacer
		Player myPlayer = new Player ();
		myPlayer.setName(myPlayer.getName() + ".." + player.getName());
		if (player.getType() != "loaded")
			myPlayer.setType("newItem");
		
			// Genero una respuesta utilizando el objeto Reponse.
			// Le pongo un status que será lo que le llegue a la web
			// Le adjunto la entidad myPlayer que quiero enviar como respuesta.
			// Finalmente ejecuto la construcción del bicho :)
		return Response.status(Status.CREATED).entity(myPlayer).build();
	}
	
}
