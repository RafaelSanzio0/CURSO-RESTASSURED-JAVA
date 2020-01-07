
//********** AQUI ESTAMOS APLICANDO FORMAS DE SE FAZER UM TESTE
package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.hamcrest.Matchers;


public class OlaMundo {
	
	@Test
	public void formaRobusta() {
		Response response = request(Method.GET,"http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.statusCode() == 200);
//		Assert.assertTrue("status code deveria ser 200",response.statusCode() == 201);
//		Assert.assertEquals(201,response.statusCode());

		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		
		ValidatableResponse validatableResponse = response.then();
		
		validatableResponse.statusCode(200);
	}
	
	@Test
	public void formaEnxuta() {		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200); 	
	}
	
	@Test
	public void formaLegivel() {
		given()
		//pre condições da request
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void matchersWithHamcrest() {
		Assert.assertThat("maria", Matchers.is("maria"));
		assertThat(128, Matchers.is(128));
		assertThat(128, isA(Integer.class));
		assertThat(130, greaterThan(120));
		assertThat(125, lessThan(130));

		List<Integer> numeros = Arrays.asList(1,2,3,4,5);
		assertThat(numeros, hasSize(5));
		assertThat(numeros, contains(1,2,3,4,5)); //tem que ser exatamente o que contem na lista in ordem
		assertThat(numeros, containsInAnyOrder(1,4,5,3,2));
		assertThat(numeros, hasItems(5));
		
		assertThat("maria", is(not("joao")));
		assertThat("maria", not("joao"));
		assertThat("bruna", anyOf(is("joao"),is("bruna")));

	}	
}

