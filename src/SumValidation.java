

import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	@Test
	public void VerifySumofAllProducts() {
	JsonPath js = new JsonPath(payload.CoursePrice());
		
		int count = js.getInt("courses.size()");
		int totalprice=0;
		for (int i =0; i<count ;i++) {
//			
			String  title = js.get("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			totalprice =totalprice + price *copies;			
		}
		System.out.println("the total price is "+ totalprice);
		if (totalprice ==js.getInt("dashboard.purchaseAmount"))
		{
			System.out.println("The total price is qual to purchase amount");
		}
		else {
			System.out.println("Error message");
		}
	}
}
