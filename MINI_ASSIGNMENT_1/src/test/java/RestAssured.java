import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssured {

    @Test
    public void test_get_call() {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json").
                when().
                get("/posts").
                then().
                statusCode(200);
    }
    public void validateUserId(){
        Response response = given().
                when().
                get("https://jsonplaceholder.typicode.com/posts").
                then().extract().response();
        assertThat(response.path("[39].userId"),is(equalTo(4)));
    }
    
    @Test
    public void test_put_call() {
        File JsonData = new File("src//test//resources//putdata.json");
        given().
                baseUri("https://reqres.in/api").
                body(JsonData).
                header("Content-Type", "application/json").
                when().
                put("/users").
                then().
                statusCode(200);
    }

}
