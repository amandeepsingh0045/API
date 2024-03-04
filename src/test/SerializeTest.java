package test;
import pojo.AddPlace;
import pojo.Location;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.ArrayList;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class SerializeTest {
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
		// Add Place API
		
		  String response = given().log().all().queryParam("key", "qaclick123")
		  .header("Content-Type","application/json") .body(p)
		  .when().post("/maps/api/place/add/json")
		  .then().assertThat().statusCode(200).extract().response().asString();
		  System.out.println(response);
	}
}
