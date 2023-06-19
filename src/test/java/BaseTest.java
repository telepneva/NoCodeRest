import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

public class BaseTest {

    public static String BASE_URL = "https://studio-api.softr.io/v1";

    public static String EmailUser = "vladT1@gmail.com";

    public static String FullName = "Vlad Teacher";

    public static String email = UUID.randomUUID().toString() + "@example.com";

    public static String password = "125halo125";

    public static String softrApiKey = "khIbAyJIU5CIuh1oDuBRx1s49";

    public static String softrDomain = "jere237.softr.app";

    public static String contentType = "application/json";


    static RequestSpecification specification = new RequestSpecBuilder()
            .setUrlEncodingEnabled(false)
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addHeader("Softr-Api-Key", softrApiKey)
            .addHeader("Softr-Domain",softrDomain )
            .build();

    public static Response getRequest(String endPointUri, Integer responseCode) {
        Response response = RestAssured.given()
                .spec(specification)
                .when()
                .log().all()
                .get(endPointUri)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response postRequest(String endPointUri, Integer responseCode, Object bodyRequest) {
        Response response = RestAssured.given()
                .spec(specification)
                .body(bodyRequest)
                .when()
                .log().all()
                .post(endPointUri)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }


    public Response deleteRequest(String endPointDelete, Integer responseCode) {

        Response response = RestAssured.given()
                .spec(specification)
                .when()
                .log().all()
                .delete(endPointDelete)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

}
