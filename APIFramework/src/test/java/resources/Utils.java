package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	RequestSpecification reqSB;
	ResponseSpecification respSB;

	public RequestSpecification requestSpec() {
		//Build Request Spec
		reqSB = new RequestSpecBuilder()
										.setBaseUri("https://rahulshettyacademy.com")
										.addQueryParam("key" , "qaclick123")
										.setContentType(ContentType.JSON)
										.build();
		System.out.println("Request Spec is Build in Utils");
		return reqSB;

	}

	public ResponseSpecification responseSpec() {
		//Build Response Spec
		respSB = new ResponseSpecBuilder()
									.expectStatusCode(200)
									.expectContentType(ContentType.JSON)
									.build();
		System.out.println("Response Spec is Build in Utils");
		return respSB;
	}
}
