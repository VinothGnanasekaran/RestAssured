package jira;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import mockCode_API.Payload;
import mockCode_API.ReusableMethods;

public class Jira_02_Post_Modify_CommentToIssue {
	
	SessionFilter session = new SessionFilter();
	String commentID;
	
	//Post - Login to JIRA
	@BeforeTest
	public void LoginToJira() {
			RestAssured.baseURI="http://localhost:8080";
			
			RequestSpecification loginRequest = given()
																	.log().all().header("Content-type", "application/json")
																	.body(Payload.JiraLoginCreds()).filter(session);
			
												 loginRequest.when().post("rest/auth/1/session")
																	.then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println("Login to JIRA is successful");
			
	}
	
	//Post - Add Comment to an issue to JIRA
	@Test
	public void AddComment() {
			RequestSpecification createIssueRequest = given()
																			.log().all().filter(session)
																			.header("Content-type", "application/json")
																			.body(Payload.AddCommentToIssue());
			String addCommentResponse = createIssueRequest.when().post("rest/api/2/issue/AJR-1/comment")
														.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
			System.out.println("New Comment  is added to the issue successfully");
			JsonPath js1 = ReusableMethods.rawStringToJson(addCommentResponse);
			commentID = js1.getString("id");
			
			System.out.println("Comment ID Created is : "+commentID);
			}
	
	//PUT - Update the comment in the Issue 
	@Test (dependsOnMethods = "AddComentwithSession")
	public void UpdateComment() {
			RequestSpecification updateCommentReq = given()
																					.log().all()
																					.filter(session)
																					.header("Content-type", "application/json")
																					.body("{\r\n"
																							+ "    \"body\": \"Modified comment from Rest Assured code\",\r\n"
																							+ "    \"visibility\": {\r\n"
																							+ "        \"type\": \"role\",\r\n"
																							+ "        \"value\": \"Administrators\"\r\n"
																							+ "    }\r\n"
																							+ "}");
			updateCommentReq.when()
												.put("rest/api/2/issue/AJR-1/comment/"+commentID+"")
											.then()
												.log().all()
												.assertThat().statusCode(200);
												//.body("body", equalTo("Modified comment from Rest Assured code)")
				
			System.out.println("Comment is Updated now" );
	}
	
}

