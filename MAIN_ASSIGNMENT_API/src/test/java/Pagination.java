import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;

public class Pagination {

    public boolean pagination(String TokenG,int limit){

        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + TokenG)
                .header("Content-Type", "application/json");

        Response Response_Get_Task = null;

        Response_Get_Task = request.get("/task?limit="+limit);
        Response_Get_Task.prettyPrint();

        String jsonString=Response_Get_Task.getBody().asPrettyString();

        int count = JsonPath.from(jsonString).get("count");
        ArrayList<String> data = JsonPath.from(jsonString).get("data");

        if(limit == data.size()){
            return true;
        }

        else {
            return false;
        }
    }
}