package basics;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class Serialization_POJO_GoogleApi_addPlace {

	public static void main(String[] args) {
				// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Serialization - Java objects(value setting) & Then convert the javaobjects to JSON payload.
		
		AddPlace a = new AddPlace();
		
		//As long/lat are inside the Location classes, create obj to Location class and access it to set values and 
		//pass the obj of location class to SetLocation in AddPlace class.
			Location loc = new Location();
			loc.setLat(-38.383494);
			loc.setLng(33.427362);
		a.setLocation(loc);
		a.setAccuracy(50);
		a.setName("Love house");
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("Tamil-IN");
		a.setPhone_number("\"(+91) 983 893 3937");
		a.setWebsite("http://google.com");
		
			List<String> list = new ArrayList<String>();
			list.add("shoe park");
			list.add("shop");
		a.setTypes(list);
		
		//Actual Request
				String respData = given()
					.log().all()
					.queryParam("key", "qaclick123")
					.body(a)
				.when()
					.post("maps/api/place/add/json")
				.then()
					.assertThat().statusCode(200)
					.extract().response().asString();
		
				System.out.println(respData);
		
	}

}
