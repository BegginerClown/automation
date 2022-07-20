import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestAssured {


    //часть 1
    @Order(1)
    @Test
    @DisplayName("1. Прощай Буся!")
    void addPet() {
        JSONObject bodyUno = new JSONObject()
                .put("id", 669)
                .put("name", "Буся")
                .put("status", "available");


        given()
                .when()
                .log().all()
                .body(bodyUno.toString())
                .contentType(ContentType.JSON)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(669))
                .body("name", equalTo("Буся"))
                .body("status", equalTo("available"))
                .extract()
                .body()
                .asString();

    }


    //часть 2
    @Order(2)
    @Test
    void updatePet() {

        JSONObject bodyDos = new JSONObject()
                .put("id", 720)
                .put("name", "Муся")
                .put("status", "available");

        given()
                .when()
                .log().all()
                .body(bodyDos.toString())
                .contentType(ContentType.JSON)
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(720))
                .body("name", equalTo("Муся"))
                .body("status", equalTo("available"))
                .log().all();


    }


    //часть 3
    @Order(3)
    @Test
    void checkPet() {

        given()
                .when()
                .log().all()
                .pathParam("petId", 720)
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("available"))
                .log().all();
    }


    //часть 4
    @Order(4)
    @Test
    void deletePet() {

        given()

                .when()
                .log().all()
                .pathParam("petId", 720)
                .delete("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Order(5)
    @Test
    void checkNoPet() {

        given()
                .when()
                .log().all()
                .pathParam("petId", 420)
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(404)
                .log().all();
    }
    public static Logger LOGGER = LoggerFactory.getLogger(RestAssured.class);
    @Test
    public void LogExample() {
        LOGGER.info("Это лог");
    }
    @Test
    public void LogExampleError() {
        LOGGER.error("Это лог об ошибке");
    }
}


