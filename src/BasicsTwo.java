import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;

public class BasicsTwo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//validate if the Add place API is working as expected
		
		//given - all input details
		//when -submit the API
		//Then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
				// Add Place API
				String response = given().log().all().queryParam("key", "qaclick123")
						.header("Content-Type","application/json")
						.body(payload.AddPlace())
						.when().post("/maps/api/place/add/json")
						.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
						.header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
				
				System.out.println(response);
				
				JsonPath js = new JsonPath(response);
				String PlaceId =js.getString("place_id");
				
				System.out.println("place id is "+ PlaceId);
				
				//Add place -> U[pdate with new address -> get place to validate of new address is present 
				//Update Place 
				String NewAddress = "Patna , Bihar, India";
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body("{\r\n"
						+ "        \"place_id\": \""+PlaceId+"\",\r\n"
						+ "         \"address\": \""+NewAddress+"\",\r\n"
						+ "         \"key\":\"qaclick123\"\r\n"
						+ "}")
				.when().put("/maps/api/place/update/json")
				.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
				
				
				// get place 
				String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
						.queryParam("place_id", PlaceId)
						.when().get("/maps/api/place/get/json")
						.then().assertThat().log().all().statusCode(200).extract().response().asString();;
				
	
						JsonPath js2 = new JsonPath(getPlaceResponse);
						String Actualaddress =js2.getString("address");
						
						System.out.println("The New Address is "+ Actualaddress);
						//TestNg
						Assert.assertEquals(Actualaddress, NewAddress);
	}

}
