import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestResponse {
    ResponseSpecification responseSpecification;
    RequestSpecification requestSpecification1,requestSpecification2;


    @BeforeClass
    public void setup() {
        RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
        requestSpecBuilder1.setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("Content-Type", "application/json");
        requestSpecification1 = RestAssured.with().spec(requestSpecBuilder1.build());

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri("https://reqres.in/api").
                addHeader("Content-Type", "application/json");
        requestSpecification2 = RestAssured.with().spec(requestSpecBuilder2.build());

        responseSpecification = RestAssured.expect().
                contentType(ContentType.JSON);
    }


    @Test(priority = 1)
    public void get_data_call() {
        Response response=with().
                spec(requestSpecification1).
                when().
                get("/posts").
                then().
                spec(responseSpecification).statusCode(200).log().status().log().headers().
                extract().response();
        assertThat(response.path("[39].userId"), is(equalTo(4)));
        int temp=1;
        JSONArray j=new JSONArray(response.asString());
        for(int i=0;i<j.length();i++){
            Object obj = j.getJSONObject(i).get("title");
            if( !(obj instanceof String) ) {
                temp = 0;
                break;
            }
        }
        assertThat(temp,is(equalTo(1)));
    }


    @Test(priority = 2)
    public void put_data_call(){
        File jsonData = new File("src//test//resources//putData.json");
        given().
                spec(requestSpecification2).
                body(jsonData).
                when().
                put("/users").
                then().
                spec(responseSpecification).statusCode(200).log().status().log().body().log().headers();
    }
}