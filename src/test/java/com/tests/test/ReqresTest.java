package com.tests.test;
import com.tests.helpers.ApiSpecifications;
import com.tests.pojo.SignupRequest;
import com.tests.pojo.SuccessSignupResponse;
import com.tests.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.tests.pojo.Error;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    @Test
    public void checkAvatarIdTest(){
        ApiSpecifications.installSpecification
                (ApiSpecifications.requestSpecification(),ApiSpecifications.responseSpecification(200));
        List<User> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", User.class);
        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(User::getAvatar).toList();
        List<String> ids = users.stream().map(x->x.getId().toString()).toList();
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegisterTest(){
        ApiSpecifications.installSpecification
                (ApiSpecifications.requestSpecification(),ApiSpecifications.responseSpecification(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        SignupRequest user = new SignupRequest("eve.holt@reqres.in","pistol");
        SuccessSignupResponse successRegister = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessSignupResponse.class);
        Assertions.assertNotNull(successRegister.getId());
        Assertions.assertNotNull(successRegister.getToken());

        Assertions.assertEquals(id, successRegister.getId());
        Assertions.assertEquals(token, successRegister.getToken());
    }

    @Test
    public void unSuccessRegisterTest(){
        ApiSpecifications.installSpecification
                (ApiSpecifications.requestSpecification(),ApiSpecifications.responseSpecification(400));
        SignupRequest user = new SignupRequest("sydney@fife","");
        Error unSuccessRegister = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(Error.class);
        Assertions.assertEquals("Missing password", unSuccessRegister.getError());
    }

    @Test
    public void userDeleteTest(){
        ApiSpecifications.installSpecification
                (ApiSpecifications.requestSpecification(),ApiSpecifications.responseSpecification(204));
        given().when().delete("api/users/2").then().log().all();
    }

    @Test
    public void singleUserNotFoundTest(){
        ApiSpecifications.installSpecification
                (ApiSpecifications.requestSpecification(),ApiSpecifications.responseSpecification(404));
        given().when().get("api/users/23").then().log().all();
    }
}
