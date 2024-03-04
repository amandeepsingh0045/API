package test;
import pojo.AddPlace;
import pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.ArrayList;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class SpecBuilderTest {
	@Test
	public void Searilizetestmetod() {
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName("Business Addrres");
		p.setPhone_number("8976r787793");
		p.setAddress("29, side layout, cohen 09333");
		p.setLanguage("English-EN");
		p.setWebsite("www.yahoo.com");
			
		List<String >list = new ArrayList<String>();
		list.add("shofffe");
		list.add("parffk ");
		
		p.setTypes(list);
		
		Location l = new Location();
		l.setLat(38.3939);
		l.setLng(-83.930);
		
		p.setLocation(l);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
				.build();
		//given
		RequestSpecification res = given().spec(reqspec).body(p);
		//when
		
		//then 
		ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON). build();
		
		
		Response response =res.when().post("/maps/api/place/add/json") .then().spec(resspec).extract().response();
		// Add Place API
		
		  String responseString = response.asString();
		 
		  System.out.println(responseString);
	}
}
