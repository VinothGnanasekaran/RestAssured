package basics;

import io.restassured.RestAssured;

public class CrudBasics_02_Get_Place {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured
				.given()
					.log().all()
					.queryParam("key", "qaclick123")
					.queryParam("place_id", "a24a168fd06ebb5e8517bec8229c80a0")
				.when()
					.get("maps/api/place/get/json")
				.then()
					.log()	.all()
					.assertThat().statusCode(200);
	}

}
