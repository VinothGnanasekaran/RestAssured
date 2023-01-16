package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataPayload {
	
	public AddPlace addPlace_Payload(String name, String lang, String Addr) {
		
		AddPlace a = new AddPlace();
		a.setAccuracy(50);
		a.setName(name);
		a.setAddress(Addr);
		a.setLanguage(lang);
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
		return a;
	}

}
