import files.payload;
import io.restassured.path.json.JsonPath;
public class ComplexJsonParse {
	
	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int count = js.getInt("courses.size()");
		int totalprice=0;
		
//		System.out.println("The Count is :"+count);
//		
//		System.out.println((js.getInt("dashboard.purchaseAmount")));
//		
//		System.out.println((js.getInt("courses[1].price")));
//	String title =js.get("courses[1].title");
//		System.out.println(title);
//		
		for (int i =0; i<count ;i++) {
//			String title =js.get("courses["+i+"].title");
//			System.out.println(title);
			String  title = js.get("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			totalprice =totalprice + price *copies;
		//	System.out.println(price *copies);
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println("RPA price is "+price *copies);
			}
		
			
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
