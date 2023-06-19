import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.Specifications;
import dto.UnSuccessfulDelete;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteUserTest extends BaseTest{

    public static String BASE_URL = "https://studio-api.softr.io/v1";

    @Test
    public void successfulRegistration() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest("Vlad T", EmailUser, "1256", "false");
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key", softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals("Vlad T", response.getFull_name());
        assertEquals(EmailUser, response.getEmail());
    }

    @Test
    public void successfulDeleteUser(){
    Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
            Specifications.response200Ok());
    Response response = (Response) given()
            .baseUri(BASE_URL)
            .header("Softr-Api-Key",softrApiKey)
            .header("Softr-Domain", softrDomain)
            .when().log().all()
            .delete("/api/users/"+EmailUser)
            .then().log().all()
            .extract();
}

    @Test
    public void UnSuccessfulDeleteUser(){
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response404NotFound());
        UnSuccessfulDelete response = (UnSuccessfulDelete) given()
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("/api/users/" + EmailUser)
                .then().log().all()
                .extract().body().as(UnSuccessfulDelete.class);
        assertEquals("Not Found",response.getCode());
        assertEquals("User with email:"+EmailUser+"not found",response.getMessage());
    }

}
