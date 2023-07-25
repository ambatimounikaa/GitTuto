package com.pack1;

import static io.restassured.RestAssured.given;

import java.util.Scanner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GetWeather {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI= "https://samples.openweathermap.org";
		
		//Storing the response as string
		String getResponse = given().log().all().queryParam("q","London,us")
		.queryParam("appid", "b6907d289e10d714a6e88b30761fae22")
		.when().get("data/2.5/forecast/hourly")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//Converting raw data to JSON
		JsonPath js = new JsonPath(getResponse);
		
		int listSize = js.getInt("list.size()");
		
		//Taking input from user
		Scanner sc = new Scanner(System.in);
		
		while(true) {
		System.out.println("Press any number between 0 and 3");
		int userSpecification = Integer.parseInt(sc.nextLine());
		if(userSpecification==0) {
			System.out.println("Exit and Terminated");
			break;
		}
		else if (userSpecification<0 || userSpecification>3) {
			System.out.println("You should choose a number from 0-3 only");
			continue;
		}
		else {
		System.out.println("Enter the date");
		String date = sc.nextLine();
		
		for(int i=0;i<listSize;i++) {
			String d = js.getString("list["+i+"].dt_txt");
			if(d.equals(date)) {
				if(userSpecification==1)
				System.out.println(js.getString("list["+i+"].weather"));
				else if(userSpecification==2){
					System.out.println(js.getString("list["+i+"].wind.speed"));	
				}
				else if (userSpecification==3){
					System.out.println(js.getString("list["+i+"].main.pressure"));	
				}
				else {
					break;
				}
			}
		}
		}
	}
	}

}
