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
import resources.APIresources;
import resources.TestDataPayload;
import resources.Utils;

public class RSA_PlaceValidations extends Utils {

	RequestSpecification request;
	Response reqMethod;
	static Response response;
	TestDataPayload testdata = new TestDataPayload();


	@Given("User to add all Request Builder Spec for the AddPlace request with {string} {string} {string}")
	public void user_to_add_all_request_builder_spec_for_the_add_place_request_with(String name, String language, String address) throws IOException {

		System.out.println("Request Spec is Build for AddPlace is invoked in SD class");
		request = given().log().all().spec(requestSpec()).body(testdata.addPlace_Payload(name, language, address)); //Here instead of 'Spec(reqSB)' passed, due to inheritance(Utils), we access its method directly without obj
		System.out.println("Request is Sent");
	}

	@When("User calls for {string} Api call with {string} method")
	public void user_calls_for_api_call_with_method(String apiResource, String method) {

		//Usage of Enum class and valueOf method to call the required API resource
		//Command to fetch the API Resource
		APIresources callApi = APIresources.valueOf(apiResource); 
		System.out.println("The API resource called for place validation is : " +callApi.getResource());

		//Logic to find the method from CRUD actions
		if(method.equalsIgnoreCase("POST")){
			reqMethod = request.when().post(callApi.getResource());
		} else if (method.equalsIgnoreCase("GET")){
			reqMethod = request.when().get(callApi.getResource());
		} else if (method.equalsIgnoreCase("PUT")){
			reqMethod = request.when().put(callApi.getResource());
		} else {
			reqMethod = request.when().delete(callApi.getResource());
		}
	}

	@Then("User should be successful with request status code {int}")
	public void user_should_be_successful_with_request_status_code(Integer int1) {

		System.out.println("Response Spec is Build is invoked in SD class");
		response = reqMethod.then().spec(responseSpec()).extract().response(); //Here instead of 'Spec(respSB)' passed earlier, due to inheritance(Utils), we access its method directly without obj

		assertEquals(response.getStatusCode(), int1);
		System.out.println("Response is extracted");
	}

	@Then("User validates the {string} in the response body is  {string}")
	public void user_can_validates_the_in_the_response_body_is(String key1, String value) {

		assertEquals(getJsonPath(response, key1), value);
		System.out.println("Condition Matched: " +key1+ " to : " +value );
		}

	@Then("User to validate the {string} in the response body when {string} api is called")
	public void user_to_validate_the_in_the_response_body_when_api_is_called(String expectedName, String getResource) throws IOException {
			System.out.println("The Json path is called for extracting the PlaceID from prev response");
		String placeId=getJsonPath(response, "place_id");
			System.out.println("Request Spec for GetPlace is invoked in SD class");
			
		request = given().log().all().spec(requestSpec()).queryParam("place_id", placeId);
			System.out.println("GetPlace Request is parsed without resource");
			
		user_calls_for_api_call_with_method(getResource, "get");
			System.out.println("GetPlace Request is Sent completely");
		response = reqMethod.then().spec(responseSpec()).extract().response();
		String actualName = getJsonPath(response, "name");
			System.out.println(actualName);
		assertEquals( actualName, expectedName);
			System.out.println("The Name of the get place matched");
				
	}

		
			
	}
