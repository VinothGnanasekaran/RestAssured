package basics;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;

public class OAuth2_Deserialization_using_POJO {
	
	public static void main(String[] args) throws InterruptedException {

	// TODO Authorization process is skipped as Google restricts automation, hence directly passing the Auth URL

	/*
	 * This URL is the Authentication URL, obtained after entering the google creds
		As Google have blocked automation bots /scripts to fetch this, directly passing the Authorization url here and 
		deriving the code from url
	*/
	String Authurl ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

	//To split the URL into fragments and obtain only the CODE in the URL

	String partialcode=Authurl.split("code=")[1];
			/*When using split using delimiter [code=], the url get broken and stored as array in index like below
			 * split[0] = https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss
		    	split[1]= 4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#
			 */
	String authCode=partialcode.split("&scope=")[0];
			/*When using Second split using delimiter[&scope=], the url get broken further and stored as array in index like below
			 * split[0] = 4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M
		    	split[1]= email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#
			 */
	System.out.println("The Authorization code from URL is : "+authCode);

	String response =
	                given() 
	                .urlEncodingEnabled(false)
	        //This is used for passing the code with spl characters(@%!,etc),else RESTAssured will encode while passing
	                       .queryParams("code", authCode)
	                       .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
	                        .queryParams("grant_type", "authorization_code")
	                        .queryParams("state", "verifyfjdss")
	                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
	                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
	                     // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
	                 .when().log().all()
	                        .post("https://www.googleapis.com/oauth2/v4/token")
	                        .asString();
	// System.out.println(response);
	//Exacting the Access TOKEN 
	JsonPath jsonPath = new JsonPath(response);
	    String accessToken = jsonPath.getString("access_token");
	    System.out.println("The Access Token from Auth Server is : "+accessToken);
	
	    //A. Actual Request to be sent to obtain the get course from the Rahul Shetty Website
	    String r2=given().contentType("application/json")
	    						.queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
	    			.when()
	    				.get("https://rahulshettyacademy.com/getCourse.php")
	    				.asString();
	    	System.out.println(r2);

	//B. Actual Request to RSA site with POJO, to be used either A or B
	GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
	.when()
	.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
	
	System.out.println(gc.getLinkedIn());
	System.out.println(gc.getInstructor());
	System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
	
	//To Get the price of the course-SoapUI , and print its price.
	List<Api> apiCourses=gc.getCourses().getApi();
	for(int i=0;i<apiCourses.size();i++)
	{
		if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
				{
			System.out.println(apiCourses.get(i).getPrice());
				}
	}
	
	//Get the course names of WebAutomation
	String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
	ArrayList<String> a= new ArrayList<String>();
	List<pojo.WebAutomation> w=gc.getCourses().getWebAutomation();
		for(int j=0;j<w.size();j++)
	{
		a.add(w.get(j).getCourseTitle());
	}
		List<String> expectedList=	Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));
	
	
	
	}

}
