package org.prog.session9;

import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

//TODO: 0. Install Docker and run these tests
//TODO: 1. Create table Phones: Model name and Model Price
//TODO: 2. Load allo.ua page, and store 3 phone price and model to DB

public class MyDBTests {

    @SneakyThrows
    @Test
    public void dbTest() {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db", "root", "password");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Persons (FirstName, LastName, Gender, Title, Nat) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ResponseDto responseDto = getPersons(10);
            for (PersonDto personDto : responseDto.getResults()) {
                insertPerson(ps, personDto);
            }
//            insertPerson(ps, "John", "Doe", "male", "Mr", "US");
//            insertPerson(ps, "Jane", "Doe", "female", "Mrs", "US");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @SneakyThrows
    private void insertPerson(PreparedStatement ps, PersonDto personDto) {
        try {
            ps.setString(1, personDto.getName().getFirst());
            ps.setString(2, personDto.getName().getLast());
            ps.setString(3, personDto.getGender());
            ps.setString(4, personDto.getName().getTitle());
            ps.setString(5, personDto.getNat());
            ps.execute();
        } catch (Exception e) {
            System.out.println("Failed to insert person: " + personDto);
        }
    }

    @SneakyThrows
    private void insertPerson(PreparedStatement ps,
                              String firstName,
                              String lastName,
                              String gender,
                              String title,
                              String nat) {
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, gender);
        ps.setString(4, title);
        ps.setString(5, nat);
        ps.execute();
    }

    public ResponseDto getPersons(int count) {
        return RestAssured.given()
                .baseUri("https://randomuser.me/")
                .basePath("/api")
                .queryParam("inc", "gender,name,nat")
                .queryParam("results", count)
                .queryParam("noinfo")
                .get()
                .prettyPeek()
                .as(ResponseDto.class);
    }
}