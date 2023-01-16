package resources;

//Enum is a special class in JAVA which is a collection of constants or Methods.
public enum APIresources {

	addPlace ("/maps/api/place/add/json"),
	getPlace("/maps/api/place/get/json"),
	updatePlace("/maps/api/place/update/json"),
	deletePlace("/maps/api/place/delete/json");
	
	private String resource;

	APIresources(String apiResource) {
		
		this.resource = apiResource;
	}

	public String getResource() {

		return resource;
	}


}
