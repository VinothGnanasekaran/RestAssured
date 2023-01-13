package basics;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class CrudBasics_04_Delete_Place {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured
			.given()
				.log()
				.all()
				.body("{\r\n"
						+ "\"place_id\":\"dc8b236101b0179008b1b7934c0b220d\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}\r\n"
						+ " ")
			.when()
				.delete("maps/api/place/delete/json")
			.then()
			 	.log()
			 	.all()
			 	.assertThat()
			 		.body("status", equalTo("OK"))
			 		.statusCode(200);
	}
	
}
	
