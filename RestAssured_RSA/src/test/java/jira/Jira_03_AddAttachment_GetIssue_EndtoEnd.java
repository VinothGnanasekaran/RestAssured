package jira;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import mockCode_API.Payload;
import mockCode_API.ReusableMethods;

public class Jira_03_AddAttachment_GetIssue_EndtoEnd {
	SessionFilter session = new SessionFilter();
	String issueKey;
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
	
	//Post - Create an NEW issue to JIRA
	@Test
	 public void createIssue() {
			RequestSpecification createIssueRequest = given()
																				.log().all().header("Content-type", "application/json")
																				.body(Payload.CreateIssue()).filter(session);
			String createIssueResponse = createIssueRequest.when().post("rest/api/2/issue")
																		  				.then().log().all().assertThat().statusCode(201).extract().response().asString();
			
			JsonPath js = ReusableMethods.rawStringToJson(createIssueResponse);
			issueKey = js.getString("key");
			System.out.println("The New issue is created, and its ID : " +issueKey);
			
	 }
	
	//Post - Add Comment to an issue to JIRA
	@Test (dependsOnMethods = "createIssue")
	public void AddComment() {
			RequestSpecification createIssueRequest = given()
																			.log().all().filter(session)
																			.header("Content-type", "application/json")
																			.pathParam("BugID",issueKey)
																			.body(Payload.AddCommentToIssue());
			String addCommentResponse = createIssueRequest.when().post("rest/api/2/issue/{BugID}/comment")
														.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
			System.out.println("New Comment  is added to the issue successfully");
			JsonPath js1 = ReusableMethods.rawStringToJson(addCommentResponse);
			commentID = js1.getString("id");
			
			System.out.println("Comment ID Created is : "+commentID);
			}
	
	//PUT - Update the comment in the Issue 
	String updatedComment= "Modified comment from Rest Assured code";
	@Test (dependsOnMethods = "AddComment")
		public void UpdateComment() {
			RequestSpecification updateCommentReq = given()
																					.log().all()
																					.filter(session)
																					.header("Content-type", "application/json")
																					.pathParam("BugID", issueKey)
																					.body("{\r\n"
																							+ "    \"body\": \""+updatedComment+"\",\r\n"
																							+ "    \"visibility\": {\r\n"
																							+ "        \"type\": \"role\",\r\n"
																							+ "        \"value\": \"Administrators\"\r\n"
																							+ "    }\r\n"
																							+ "}");
			updateCommentReq.when()
												.put("rest/api/2/issue/{BugID}/comment/"+commentID+"")
											.then()
												.log().all()
												.assertThat().statusCode(200);
												//.body("body", equalTo("Modified comment from Rest Assured code)")
				
			System.out.println("Comment is Updated now" );
	}
	//Add an attachment to JIRA Issue from Code
	@Test (dependsOnMethods = "createIssue")
	public void addAttachment() {
			given()
				.log().all().filter(session)
				.pathParam("BugID", issueKey)
				.header("X-Atlassian-Token", "no-check")
				.header("Content-type", "multipart/form-data")
				.multiPart(new File("TestAttachment.txt"))
			.when()
				.post("rest/api/2/issue/{BugID}/attachments")
			.then()
				.log().all()
				.assertThat().statusCode(200);
			
		
	}


}
