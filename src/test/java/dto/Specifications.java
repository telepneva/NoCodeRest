package dto;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

        public static RequestSpecification requestSpecification(String URL){
            return new RequestSpecBuilder().setBaseUri(URL)
                    .setContentType(ContentType.JSON)
                    .build();
        }

        public static ResponseSpecification response201Ok(){
            return new ResponseSpecBuilder()
                    .expectStatusCode(201)
                    .build();
        }
    public static ResponseSpecification response200Ok(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

        public static ResponseSpecification response401BadRequest(){
            return new ResponseSpecBuilder()
                    .expectStatusCode(401)
                    .build();
        }

    public static ResponseSpecification response405MethodNotAllowed(){
        return new ResponseSpecBuilder()
                .expectStatusCode(405)
                .build();
    }

    public static ResponseSpecification response400BadRequest(){
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification response404NotFound(){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

        public static void installSpecification(RequestSpecification req, ResponseSpecification resp){
            RestAssured.requestSpecification =req;
            RestAssured.responseSpecification = resp;
        }
    }


