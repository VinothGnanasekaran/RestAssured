package mockCode_API;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawStringToJson(String response) {
		JsonPath js = new JsonPath(response);
				return js;
	}

}
