package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.TestDataPayload;
import resources.Utils;

public class RSA_PlaceValidations extends Utils {
	
	RequestSpecification request;
	Response post;
	Response response;
	TestDataPayload testdata = new TestDataPayload();
	
	
	 @Given("User to add all Request Builder Spec for the AddPlace request with {string} {string} {string}")
	public void user_to_add_all_request_builder_spec_for_the_add_place_request_with(String name, String language, String address) throws IOException {
					
		System.out.println("Request Spec is Build is invoked in SD class");
		request = given().log().all().spec(requestSpec()).body(testdata.addPlace_Payload(name, language, address)); //Here instead of 'Spec(reqSB)' passed, due to inheritance(Utils), we access its method directly without obj
		System.out.println("Request is Sent");
	}

	@When("User calls for {string} Api call with {string} method")
	public void user_calls_for_api_call_with_method(String Api, String method) {
	
		post = request.when().post("maps/api/place/add/json");
	}

	@Then("User should be successful with request status code {int}")
	public void user_should_be_successful_with_request_status_code(Integer int1) {
		
		System.out.println("Response Spec is Build is invoked in SD class");
		response = post.then().spec(responseSpec()).extract().response(); //Here instead of 'Spec(respSB)' passed earlier, due to inheritance(Utils), we access its method directly without obj
		
		assertEquals(response.getStatusCode(), 200);
		System.out.println("Response is extracted");
	}

	@Then("User can validate the {string} in the response body is  {string}")
	public void user_can_validate_the_in_the_response_body_is(String key, String value) {
		
		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		assertEquals(js.get(key).toString(), value);
		System.out.println("Response converted to String : "+responseString);
	}

}
