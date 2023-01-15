package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class RSA_PlaceValidations {

	RequestSpecification reqSB;
	ResponseSpecification respSB;
	RequestSpecification request;
	Response post;
	Response response;

	@Given("User to add all Request Builder Spec for the AddPlace request")
	public void user_to_add_all_request_builder_spec_for_the_add_place_request() {
	
		AddPlace a = new AddPlace();
		a.setAccuracy(50);
		a.setName("Love house");
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("Tamil-IN");
		a.setPhone_number("\"(+91) 983 893 3937");
		a.setWebsite("http://google.com");

		Location loc = new Location();
		loc.setLat(-38.483494);
		loc.setLng(33.527362);
		a.setLocation(loc);

		List<String> list = new ArrayList<String>();
		list.add("shoe park");
		list.add("shop");
		a.setTypes(list);

		//Build Request Spec
		reqSB = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
															.addQueryParam("key" , "qaclick123")
															.setContentType(ContentType.JSON)
															.build();
		System.out.println("Request Spec is Build");

		//Build Response Spec
		respSB = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		System.out.println("Response Spec is Build");

		request = given().log().all().spec(reqSB).body(a); //Here 'Spec(reqSB)' passed all the header info in it
		System.out.println("Request is Sent");
	}

	@When("User calls for {string} Api call")
	public void user_calls_for_api_call(String string) {
	
		post = request.when().post("maps/api/place/add/json");
	}

	@Then("User should be successful with request status code {int}")
	public void user_should_be_successful_with_request_status_code(Integer int1) {

		response = post.then().spec(respSB).extract().response();
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
