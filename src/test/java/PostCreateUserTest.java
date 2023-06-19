import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.Specifications;
import dto.UnSuccessfulRegistrationResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PostCreateUserTest extends BaseTest{

    @Test
    public void successfulRegistration() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "false");
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals(FullName, response.getFull_name());
        assertEquals(email, response.getEmail());
        String emailFurDelete = response.getEmail();
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response200Ok());
         given()
                .baseUri(BASE_URL)
                 .header("Softr-Api-Key",softrApiKey)
                 .header("Softr-Domain", softrDomain)
                 .header("Content-Type", contentType)
                .when().log().all()
                .delete("/api/users/" + emailFurDelete)
                .then().log().all()
                .extract();
    }

    String endpointUri = "/users";
    @Test
    public void successfulCreateUser() {

        CreateUserRequest requestBody = CreateUserRequest.builder()
                .email(email)
                .full_name(FullName)
                .password(password)
                .generate_magic_link("false")
                .build();

        Response response = postRequest(endpointUri, 201, requestBody);
    }

    @Test
    public void UnSuccessfulRegistrationWithOutFullName() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest(null, email, password, "false");
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals("", response.getFull_name());
        String emailFurDelete = response.getEmail();
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response200Ok());
        given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .when().log().all()
                .delete("/api/users/" + emailFurDelete)
                .then().log().all()
                .extract();
    }

    @Test
    public void UnSuccessfulRegistrationInvalidEmail() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest(FullName, "emailgmail.com", password, "false");
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals(FullName, response.getFull_name());
        assertEquals("emailgmail.com", response.getEmail());
        String emailFurDelete = response.getEmail();
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response200Ok());
        given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .when().log().all()
                .delete("/api/users/" + emailFurDelete)
                .then().log().all()
                .extract();
    }

    @Test
    public void UnSuccessfulRegistrationWithOutEmail() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response400BadRequest());
        CreateUserRequest request = new CreateUserRequest(FullName, null, password, "false");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("Bad Request", response.getCode());
    }

    @Test
    public void UnSuccessfulRegistrationWithOutPassword() {
            Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                    Specifications.response201Ok());
            CreateUserRequest request = new CreateUserRequest(FullName, email, null,"false");
            CreateUserResponse response = (CreateUserResponse) given()
                    .baseUri(BASE_URL)
                    .header("Softr-Api-Key",softrApiKey)
                    .header("Softr-Domain", softrDomain)
                    .header("Content-Type", contentType)
                    .body(request)
                    .when().log().all()
                    .post("/api/users")
                    .then().log().all()
                    .extract().body().as(CreateUserResponse.class);
            assertEquals(FullName, response.getFull_name());
            assertEquals(email, response.getEmail());

            String emailFurDelete = response.getEmail();
            Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                    Specifications.response200Ok());
            given()
                    .baseUri(BASE_URL)
                    .header("Softr-Api-Key",softrApiKey)
                    .header("Softr-Domain", softrDomain)
                    .header("Content-Type", contentType)
                    .when().log().all()
                    .delete("/api/users/" + emailFurDelete)
                    .then().log().all()
                    .extract();
        }

    @Test
    public void UnSuccessfulRegistrationWithTrueGenerate_magic_link() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest(FullName, email,password,"true" );
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals(FullName, response.getFull_name());
        assertEquals(email, response.getEmail());
        assertTrue(response.getMagic_link().contains("magic-token"));
        String emailFurDelete = response.getEmail();
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response200Ok());
        given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .when().log().all()
                .delete("/api/users/" + emailFurDelete)
                .then().log().all()
                .extract();
    }

    @Test
    public void UnSuccessfulRegistrationWithOutGenerate_magic_link() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response201Ok());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, null);
        CreateUserResponse response = (CreateUserResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(CreateUserResponse.class);
        assertEquals(FullName, response.getFull_name());
        assertEquals(email, response.getEmail());
        assertNull(null,response.getMagic_link());
        String emailFurDelete = response.getEmail();
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response200Ok());
        given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .when().log().all()
                .delete("/api/users/" + emailFurDelete)
                .then().log().all()
                .extract();
    }

    @Test
    public void UnSuccessfulRegistrationInvalidDataGenerate_magic_link() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response400BadRequest());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "ghjyuii");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("Bad Request", response.getCode());
        assertEquals("Something went wrong, please try again.", response.getMessage());
    }

    @Test
    public void unsuccessfulRegistrationWithMethodGet() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response405MethodNotAllowed());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "false");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .get("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("Method Not Allowed", response.getMessage());
    }

    @Test
    public void unsuccessfulRegistrationWithOutSoftrApiKey() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response401BadRequest());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "false");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("UNAUTHORIZED", response.getCode());
        assertEquals("Wrong apiKey", response.getMessage());
    }

    @Test
    public void unsuccessfulRegistrationWithOutSoftrDomain() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response400BadRequest());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "false");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("Bad Request", response.getCode());
        assertEquals("Something went wrong, please try again.", response.getMessage());
    }

    @Test
    public void unsuccessfulRegistrationWithOutContentType() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL),
                Specifications.response400BadRequest());
        CreateUserRequest request = new CreateUserRequest(FullName, email, password, "false");
        UnSuccessfulRegistrationResponse response = (UnSuccessfulRegistrationResponse) given()
                .baseUri(BASE_URL)
                .header("Softr-Api-Key",softrApiKey)
                .header("Softr-Domain", softrDomain)
                .header("Content-Type", contentType)
                .body(request)
                .when().log().all()
                .post("/api/users")
                .then().log().all()
                .extract().body().as(UnSuccessfulRegistrationResponse.class);
        assertEquals("Bad Request", response.getCode());
        assertEquals("Something went wrong, please try again.", response.getMessage());
    }
}

