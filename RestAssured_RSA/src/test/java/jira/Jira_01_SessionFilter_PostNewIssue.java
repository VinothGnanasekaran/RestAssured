package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import mockCode_API.Payload;
import mockCode_API.ReusableMethods;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class Jira_01_SessionFilter_PostNewIssue {
	@Test
	public void loginToJira() {
		//Usage of this SessionFilter class to obtain the entire session and parse it to other request
		SessionFilter session = new SessionFilter();
		
		RestAssured.baseURI="http://localhost:8080";
		
		//Post - Login to JIRA
		RequestSpecification loginRequest = given()
																.log().all().header("Content-type", "application/json")
																.body(Payload.JiraLoginCreds()).filter(session);
											 loginRequest.when().post("rest/auth/1/session")
																.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Login to JIRA is successful");	
											 
	
		//Post - Create an issue to JIRA
		RequestSpecification createIssueRequest = given()
																			.log().all().header("Content-type", "application/json")
																			.body(Payload.CreateIssue()).filter(session);
		String createIssueResponse = createIssueRequest.when().post("rest/api/2/issue")
																	  				.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawStringToJson(createIssueResponse);
		String issueKey = js.getString("key");
		System.out.println("The New issue is created, and its ID : " +issueKey);
		

		
	}

}
