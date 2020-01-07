package br.ce.wcaquino.rest;
//********** FORMAS DE VALIDAR O JSON


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;import sun.security.jca.GetInstance;

public class OlaMundo2 {

	@Test
	public void validarBody() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
	
	@Test
	public void JsonPrimeiroNivel() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
			
	}
	
	@Test
	public void JsonPrimeiroNivelOutraForma() {
		Response response = request(Method.GET,"http://restapi.wcaquino.me/users/1");
		//path
		Assert.assertEquals(1,response.path("id"));
		
		//jsonpath
		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals("1", jsonPath.getString("id"));
		
		//from
		int id = JsonPath.from(response.asString()).getInt("id");
	}
	
	@Test
	public void JsonSegundoNivel() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body("endereco.rua", containsString("bobos"));
	}
	
	@Test
	public void jsonLista() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("filhos", hasSize(2))
			.body("filhos[0].name",is("Zezinho"))
			.body("filhos.name",hasItems("Zezinho","Luizinho"));
	}
	
	@Test
	public void jsonErro() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users/4")
		.then()
			.statusCode(404)
			.body("error",is("Usuário inexistente"));
	}
	
	@Test
	public void listaRaiz() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.statusCode(200)
			.body("$", hasSize(3)) //qtd de itens na lista
			.body("name", hasItems("Ana Júlia")) // posso passar todos os itens da lista tbm
			.body("age[2]", is(20))
			.body("filhos.name",hasItem(Arrays.asList("Zezinho","Luizinho")))
			.body("salary",contains(1234.5678f,2500,null));	
	}
	
	@Test
	public void verificacaoAvancada() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.statusCode(200)
			.body("findAll{it.age <= 25}.size()", is(2));
//			.body("findAll{it.name.contains('n')}.name", is("Maria Joaquina, Ana Júlia"));
			
	}
	
	@Test
	public void jsonPathWithJava() {
		ArrayList<String> names = 
			given()
			.when()
				.get("http://restapi.wcaquino.me/users")
			.then()
				.statusCode(200)
				.extract().path("name.findAll{it.startsWith('Maria')}") //obtenho os nomes q inicia com maria no meu array na request
			;
			Assert.assertEquals(1, names.size());
			Assert.assertTrue(names.get(0).equalsIgnoreCase("maria Joaquina"));
			Assert.assertEquals(names.get(0).toUpperCase(),"Maria Joaquina".toUpperCase());
	}
}
	
	

