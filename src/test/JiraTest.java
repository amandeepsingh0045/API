package test;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {
	@Test
	public void AutomateJira() {
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		//Login Scenario 
	String response =given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{ \"username\": \"amandeep\", \"password\": \"Singh@0045\" }").
		log().all().filter(session).
		when().post("/rest/auth/1/session").
		then().log().all().extract().response().asString();
		
		//Add comment through Rest Api
	String expectedMessage ="I am adding new comment";
	
		String addCommentResponse =given().pathParam("id", "10105").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).post("/rest/api/2/issue/{id}/comment").
		then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");
		
		// Add attachment 
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", "10105").
		header("Content-Type", "multipart/form-data").
		multiPart("file",new File("C:\\Users\\AMANDEEP SINGH\\eclipse-workspace\\Demo Project\\Jira.txt")).
		when().post("/rest/api/2/issue/{id}/attachments").
		then().log().all().assertThat().statusCode(200);
		
		// get issue details from jira
		String issueDetails =given().filter(session).pathParam("id", "10105").
		                    queryParam("fields", "comment").
							log().all().
							when().get("/rest/api/2/issue/{id}").
							then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		JsonPath js1 =new JsonPath(issueDetails);

		int commentsCount=js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentsCount;i++){

		String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId)){
				String message= js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
	        	Assert.assertEquals(message, expectedMessage);

				}
			}
	}
}
