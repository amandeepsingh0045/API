package test;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import test.pojo.*;
public class EcommerceAPITest {
	
	@Test
	public void EndToEndTest() {
		
		
		//Login into URL
	
	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	//given
	
	LoginRequest login = new LoginRequest();
	login.setUserEmail("testpostman@gmail.com");
	login.setUserPassword("Test@123");
	
	RequestSpecification reslogin = given().log().all().spec(req).body(login);
	//when
	
	//then 
	//ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON). build();
	
	
	LoginResponse loginResponse =reslogin.when().post("/api/ecom/auth/login") .then().log().all().extract().response().as(LoginResponse.class);
	//
	System.out.println(loginResponse.getToken());
	String token = loginResponse.getToken();
	System.out.println(loginResponse.getUserId());
	String userid =loginResponse.getUserId();
	
	// Add Product to Site
	RequestSpecification addProductBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).build();
	
	RequestSpecification reqAddProdct =given().log().all().spec(addProductBasereq).param("productName", "Laptop").param("productAddedBy", userid).param("productCategory", "faishon").
	param("productSubCategory","shirts").param("productPrice", 83993).param("productDescription", "randomproduct").param("productFor", "male")
	.multiPart("productImage",new File("C:\\Users\\AMANDEEP SINGH\\Downloads\\download.png"));
	
	CreateProduct create = reqAddProdct.when().post("/api/ecom/product/add-product").
	then().log().all().extract().response().as(CreateProduct.class);
	
	System.out.println(create.getProductId());
	String productId = create.getProductId();
	
	// Create Order 
	
	RequestSpecification CreateBaseSepc = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
	//Pass Orders
	OrderDetails orderDetails = new OrderDetails();
	orderDetails.setCountry("India");
	orderDetails.setProductOrderedId(productId);
	
	List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
	 orderDetailsList.add(orderDetails);
	 
	Orders order = new Orders();
	order.setOrders(orderDetailsList);
	
	RequestSpecification createOrder = given().log().all().spec(CreateBaseSepc).body(order);
	
	String responseOrder =createOrder.when().post("/api/ecom/order/create-order").
	then().log().all().extract().response().asString();
	
	System.out.println(responseOrder);
	//Delete Orders
	
	RequestSpecification deleteSpecBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
	
	RequestSpecification deletSpec = given().log().all().spec(deleteSpecBase).pathParams("productId",productId);
	
	String deleted =deletSpec.when().delete("/api/ecom/product/delete-product/{productId}").then().assertThat().log().all().extract().response().asString();
	JsonPath js = new JsonPath(deleted);
	String msg = js.get("message");
	
	System.out.println(msg);
	
	//Pass Orders
	
	
	// View Order Details
//	RequestSpecification viewOrderSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWU5MzcwM2E4NmY4Zjc0ZGM5M2I1YTEiLCJ1c2VyRW1haWwiOiJ0ZXN0cG9zdG1hbkBnbWFpbC5jb20iLCJ1c2VyTW9iaWxlIjo4OTc2Nzg5MDk3LCJ1c2VyUm9sZSI6ImN1c3RvbWVyIiwiaWF0IjoxNzA5ODM1Mzg3LCJleHAiOjE3NDEzOTI5ODd9.actUjUcQvdIUJW6ceIB241vQGJCPRsVN8ngbRZrnS3g").setContentType(ContentType.JSON).build();
//	
//	
//	RequestSpecification viewOrder = given().log().all().spec(viewOrderSpec).queryParam("id", "65ea047ca86f8f74dc94f331");
//	
//	String orderDetailsFor = viewOrder.when().get("/api/ecom/order/get-orders-details")
//			.then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	
//	System.out.println(orderDetailsFor);

	}
}
