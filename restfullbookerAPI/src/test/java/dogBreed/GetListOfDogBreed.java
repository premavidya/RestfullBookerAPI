package dogBreed;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.List;

public class GetListOfDogBreed {

	@Test
	public void getListOfDogBreeds()
	{
		Response resp= given()
		
		.when()
		.get("https://dog.ceo/api/breeds/list/all");
		 List<Object> ausBreeds = resp.jsonPath().getList("message.australian");
		for(Object s:ausBreeds)
		{
			System.out.println(s.toString());
		}
		resp.then()
		.log().all();
		
	}
}
