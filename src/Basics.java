import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//validate if the Add place API is working as expected
		
		//given - all input details
		//when -submit the API
		//Then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	//	Response output = 
				given().log().all().queryParam("key", "qaclick123")
			//	given().queryParam("key", "qaclick123")
						.header("Content-Type","application/json")
						.body("{\n" + 
								"  \"location\": {\n" + 
								"    \"lat\": -38.383494,\n" + 
								"    \"lng\": 33.427362\n" + 
								"  },\n" + 
								"  \"accuracy\": 50,\n" + 
								"  \"name\": \"Frontline house\",\n" + 
								"  \"phone_number\": \"(+91) 983 893 3937\",\n" + 
								"  \"address\": \"29, side layout, cohen 09\",\n" + 
								"  \"types\": [\n" + 
								"    \"shoe park\",\n" + 
								"    \"shop\"\n" + 
								"  ],\n" + 
								"  \"website\": \"http://google.com\",\n" + 
								"  \"language\": \"French-IN\"\n" + 
								"}\n" + 
								"").when().post("/maps/api/place/add/json")
						.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
						.header("server","Apache/2.4.52 (Ubuntu)");
//						.then().log().all().extract().response();
//				System.out.println(output.asString());
//				JsonPath js = new JsonPath(output.asString());
//				System.out.println(js.getString("place_id"));
				
				//Add place -> U[pdate with new address -> get place to validate of new address is present 
	}

}
