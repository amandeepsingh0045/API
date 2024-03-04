package test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.ResuableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	//@Test(dataProvider="BooksData")
	public void addbook(String isbn , String aisle) throws IOException {
		// TODO Auto-generated method stub
		// validate if the Add place API is working as expected

		// given - all input details
		// when -submit the API
		// Then - validate the response

		RestAssured.baseURI = "http://216.10.245.166/";
		String  resp = given().header("Content-Type", "application/json")
				       .body(payload.Addbook(isbn,aisle))
				       .when().post("/Library/Addbook.php")
				       .then().assertThat().statusCode(200).extract().response().asString();
	
		
		
		JsonPath js = ResuableMethods.rawToJson(resp);
		String id = js.get("ID");
		System.out.println(id);
		
		
		// delete book scenario
	
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object [][] {{"49440", "kdkdk"},{"40949","jffjfkf"},{"49450","dnjcio"}};
	}
	
	@Test
	public void addbookthroughPath() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166/";

		String  resp = given().header("Content-Type", "application/json")
			       .body(GenerateStringFromResource("C:\\Users\\AMANDEEP SINGH\\eclipse-workspace\\Demo Project\\src\\files\\addplace.json"))
			       .when().post("/Library/Addbook.php")
			       .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ResuableMethods.rawToJson(resp);
		String id = js.get("ID");
		System.out.println(id);
		
		
		// delete book scenario
	
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
	    return new String(Files.readAllBytes(Paths.get(path)));

	}
}
