package booking;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoutility.BookingPOJO;
import pojoutility.Bookingdates;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

public class BookingTest {

	static String token="";
	static int id=0;
	@Test
	public void generateTokenTest()
	{
		HashMap<String, String> mobj = new HashMap<String, String>();
		mobj.put("username","admin");
		mobj.put("password","password123");
		Response resp= given()
				.contentType(ContentType.JSON)
				.body(mobj)
				.log().all()
				.when()
				.post("https://restful-booker.herokuapp.com/auth");
		 token=resp.jsonPath().get("token");
		resp.then().statusCode(200).contentType(ContentType.JSON).log().all();
		System.out.println(token);
		
	}
	
	@Test(dependsOnMethods = "generateTokenTest")
	public void createBookingTest()
	{
		Bookingdates date=new Bookingdates("2026-01-01","2026-01-01");
		
		BookingPOJO bookobj=new BookingPOJO("mango","apple",121,true,date,"Breakfast");
		Response resp= given()
		.contentType(ContentType.JSON)
		.auth().oauth2(token)
		.body(bookobj)
		.when()
		.post("https://restful-booker.herokuapp.com/booking");
		
		resp.then().statusCode(200).contentType(ContentType.JSON).log().all();
		 id=resp.jsonPath().get("bookingid");
		 System.out.println(id);
	}
	@Test(dependsOnMethods={"createBookingTest","generateTokenTest"})
	public void getBookingByIdTest()
	{
		given()
		.auth().oauth2(token)
		.pathParam("id", id)
		.when()
		.get("https://restful-booker.herokuapp.com/booking/{id}")
		.then()
		.contentType(ContentType.JSON)
		.statusCode(200).log().all();
	}
	@Test(enabled=true)
	public void getAllBookingIdsTest()
	{
		given()
		.log().all()
		.auth().oauth2(token)
		
		.when()
		.get("https://restful-booker.herokuapp.com/booking")
		.then()
		.contentType(ContentType.JSON)
		.statusCode(200).log().all();
	}
	
}
