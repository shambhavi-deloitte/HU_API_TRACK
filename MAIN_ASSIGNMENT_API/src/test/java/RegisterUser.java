import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RegisterUser {
    File JsonData;
    String TokenG;

    @BeforeClass
    public void GET_POST()
    {
        JsonData = new File("src//test//resources//register.json");
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com/user";

    }

    @Test(priority = 1)
    public void CreateUser()
    {
        given()
                .contentType("application/json")
                .body(JsonData)
                .when()
                .post("/register")
                .then()
                .statusCode(201);
    }

    @Test(priority = 2)
    public void login() throws IOException {
        baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String loginCredentials="{\n" +
                "  \n" +
                "  \"email\":\"shambhavi12@gmail.com\",\n" +
                "  \"password\":\"12345678\"\n" +
                "  \n" +
                "}";
        request.header("Content-Type", "application/json");
        Response re = request.body(loginCredentials).post("/user/login");
        re.prettyPrint();
        String jsonString=re.getBody().asString();


        TokenG = JsonPath.from(jsonString).get("token");
        System.out.println(TokenG);
    }
}