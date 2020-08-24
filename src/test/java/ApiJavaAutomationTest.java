import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

public class ApiJavaAutomationTest {
    final static String firstUrl = "http://dummy.restapiexample.com";
    final static String secondUrl = "https://reqres.in";

    /*
        Hallo ini adalah initial state terkait java rest assured kita.
        Coba run class ini, dan pastikan program berjalan dan menghasilkan
        output "The response status is 200".
        Segera kontak tim Atlas jika menemui kendala!

        Nantikan berbagai latihan dan problem set di kelas nanti!
     */

    @Test
    public void firstTrial(){
        Response response = given().baseUri(firstUrl).basePath("/api/v1").contentType(ContentType.JSON)
                .get("/employees");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
        System.out.println("The response status is " +statusCode);
//        System.out.println("Response body:" + response.asString());
//        System.out.println("Response body:" + response.getBody().prettyPrint());
        System.out.println("First name employee:" + response.path("data.employee_name[0]"));
//        Assert.assertEquals("Adit", response.path("data.employee_name[0]"));
    }

    @Test
    public void getResponseBody(){
//        lebih rapi nomer 1 yang response
//        Response response =
                given().
//                        log().all().
                        baseUri(secondUrl).
                        basePath("/api/").
                        contentType(ContentType.JSON).
                        param("page" , 2).
                        param("per_page" , 4).
                        get("/users").then().statusCode(200);
//        Response secondResponse =
//                given().
//                        log().all().
//                        baseUri(secondUrl).
//                        basePath("/api/").
//                        contentType(ContentType.JSON).
//                        get("/usﬁers?page=1&per_page=2");
//        secondResponse.body().prettyPrint();
    }

//    @Test
//    public void login(){
//        Response response=
//                given().auth().oauth();
//    }

    @Test
    public void getFirstEmployeeName(){

    }

    @Test
    public void tryQueryParameters(){

    }

    @Test
    public void tryPathParameters(){
        Response response = given().log().all().
                baseUri(secondUrl).basePath("/api/").
                contentType(ContentType.JSON).
                pathParam("id", 1).
                get("users/{id}");
        Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < 2000);
        System.out.println("response time:" +response.getTimeIn(TimeUnit.MILLISECONDS));
        response.body().prettyPrint();
    }

    @Test
    public void tryVerifyEmployee(){

    }

    @Test
    public void postCreateEmployee(){
        String bodyRequest = "{\"name\":\"Atlas\",\"salary\":\"123\",\"age\":\"23\"}";
        Response response =
                given().baseUri(firstUrl).basePath("/api/v1")
                .contentType(ContentType.JSON).body(bodyRequest).post("/create");
        Assert.assertEquals(response.path("status") ,"success");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("data.name"),"Atlas");
        int idEmployee = response.path("data.id");
        System.out.println("id = " +idEmployee);

    }

    @Test
    public void postCreateUser(){
        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        Response response =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON).body(bodyRequest).post("/users");
        response.body().prettyPrint();
        Assert.assertEquals("morpheus", response.path("name"));
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertNotEquals(404, response.getStatusCode());
    }

    @Test
    public void updateUser(){
        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        Response response =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON).body(bodyRequest).post("/users");
        response.body().prettyPrint();
        Assert.assertEquals("morpheus", response.path("name"));
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertNotEquals(404, response.getStatusCode());

//        String idUser = response.path("id");
//        String updateRequest = "{\n" +
//                "    \"name\": \"morpheus\",\n" +
//                "    \"job\": \"CEO\"\n" +
//                "}";
//        Response updateResponse =
//                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON).body(updateRequest).pathParam("id",idUser).
//                        put("/users/{id}");
//        updateResponse.body().prettyPrint();
//        Assert.assertEquals("morpheus", updateResponse.path("name"));
//        Assert.assertEquals(200, updateResponse.getStatusCode());
//        Assert.assertNotEquals(404, updateResponse.getStatusCode());
        String idUser = response.path("id");
        String updateRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        Response updateResponse =
                given().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON).body(updateRequest).pathParam("id",idUser).
                        patch("/users/{id}");
        updateResponse.body().prettyPrint();
        Assert.assertEquals("morpheus", updateResponse.path("name"));
        Assert.assertEquals(200, updateResponse.getStatusCode());
        Assert.assertNotEquals(404, updateResponse.getStatusCode());

    }

    @Test
    public void deleteUser(){
        String bodyRequest = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        Response response =
                given().log().all().baseUri(secondUrl).basePath("/api").contentType(ContentType.JSON).body(bodyRequest).post("/users");
//        response.body().prettyPrint();
        Assert.assertEquals("morpheus", response.path("name"));
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertNotEquals(404, response.getStatusCode());

        String idUser = response.path("id");

        Response deleteResponse =
                given().baseUri(secondUrl).basePath("/api").
                        contentType(ContentType.JSON).
                        pathParam("id",idUser).
                        delete("/users/{id}");
        deleteResponse.getBody().prettyPrint();
        Assert.assertEquals(204, deleteResponse.getStatusCode());

    }
}
