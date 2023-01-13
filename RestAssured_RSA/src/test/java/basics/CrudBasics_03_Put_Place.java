package basics;

import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;

public class CrudBasics_03_Put_Place {

	public static void main(String[] args) {
		File bodyPath = new File("./src/test/resources/updateplace_body.json");
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		/*Since i have imported <-- import static io.restassured.RestAssured.*; -->
				I dont have to start as RestAssured.given() - directly it can start from given method
			*/
			given()
					.log().all()
					.queryParam("key", "qaclick123")
					.body(bodyPath)
				.when()
					.put("maps/api/place/update/json")
				.then()
					.log().all()
					.assertThat()
						.statusCode(200)
						.body("msg", equalTo("Address successfully updated"));
		//equalTo is a static method from Hamcrest, hence no auto-suggestion, and we have to import <-- import static org.hamcrest.Matchers.*; -->
			
					
								

	}

}
