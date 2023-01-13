package mockCode_API;

public class Payload {
	
	public static String JiraLoginCreds() {
		return "{\r\n"
				+ "        \"username\": \"VinothGM\",\r\n"
				+ "        \"password\": \"P@ssw0rd-1\"\r\n"
				+ "    }";
	}
	
	public static String CreateIssue() {
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"AJR\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Created issue from RESTAssured Code\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        },\r\n"
				+ "        \"labels\": [\r\n"
				+ "            \"RESTAPI\",\r\n"
				+ "            \"RestAssured\"\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "}";
	}

	public static String AddCommentToIssue() {
		return "{\r\n"
				+ "	    \"body\": \"Adding new comment via the RestAssured Code\",\r\n"
				+ "	    \"visibility\": {\r\n"
				+ "	        \"type\": \"role\",\r\n"
				+ "	        \"value\": \"Administrators\"\r\n"
				+ "	    }\r\n"
				+ "	}";
	}
	
	
	public static String CoursePrice()	{
		
		return "{\r\n" + 
				"  \"dashboard\": {\r\n" + 
				"    \"purchaseAmount\": 1162,\r\n" + 
				"    \"website\": \"rahulshettyacademy.com\"\r\n" + 
				"  },\r\n" + 
				"  \"courses\": [\r\n" + 
				"    {\r\n" + 
				"      \"title\": \"Selenium Python\",\r\n" + 
				"      \"price\": 50,\r\n" + 
				"      \"copies\": 6\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"title\": \"Cypress\",\r\n" + 
				"      \"price\": 40,\r\n" + 
				"      \"copies\": 4\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"title\": \"RPA\",\r\n" + 
				"      \"price\": 45,\r\n" + 
				"      \"copies\": 10\r\n" + 
				"    },\r\n" + 
				"     {\r\n" + 
				"      \"title\": \"Appium\",\r\n" + 
				"      \"price\": 36,\r\n" + 
				"      \"copies\": 7\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    \r\n" + 
				"    \r\n" + 
				"  ]\r\n" + 
				"}\r\n" + 
				"";
		}
	
	public static String addBook() {
		String addBookBody = "{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\"bcd\",\r\n"
				+ "\"aisle\":\"2926\",\r\n"
				+ "\"author\":\"John foer\"\r\n"
				+ "}";
		return addBookBody;
	}
	
	public static String addBookParam(String isbn, String aisle) {
		String addBookBody = "{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\"John foer\"\r\n"
				+ "}";
		return addBookBody;
	}
	
}
