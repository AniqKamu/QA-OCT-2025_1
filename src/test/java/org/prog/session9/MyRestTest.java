package org.prog.session9;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyRestTest {

    @Test
    public void myRestTest3() {
        ResponseDto responseDto = RestAssured.given()
                .baseUri("https://randomuser.me/")
                .basePath("/api")
                .queryParam("inc", "gender,name,nat")
                .queryParam("results", "5")
                .queryParam("noinfo")
                .get()
                .as(ResponseDto.class);
        Assert.assertTrue(responseDto.getResults()
                .stream().anyMatch(p -> p.getGender().equals("male")));
    }
}
