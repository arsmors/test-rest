package testDefs;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.RestUser;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser {
    private Response response;
    private String jsonResponseString;
    private RequestSpecification request;
    private ValidatableResponse jsonResponse;

    String API = "wMFQtp27-QrHHF7wdQM7AHZzVcsP_CZOVEdd";

    @When("^the user wants to create a record with given credentials")
    public void theUserWantsToCreateARecordWithFirstNameAs(Map<String, String> requestFields) {
        RestUser user = new RestUser();
        user.setFirstName(requestFields.get("first_name"));
        user.setLastName(requestFields.get("last_name"));
        user.setEmail(requestFields.get("email"));
        user.setGender(requestFields.get("gender"));

        request = given().contentType(ContentType.JSON).auth().oauth2(API).body(user);

    }

    @And("^user submits the user data in \"([^\"]*)\"$")
    public void userSubmitsTheUserDataIn(String url) {
        response = request.when().post(url);

    }

    @Then("^you should receive a \"([^\"]*)\" status code$")
    public void youShouldReceiveAStatusCode(int statusCode) {
        jsonResponseString = response.then().body("_meta.code", equalTo(statusCode)).extract().jsonPath().getString("result.id");
    }


    @And("^response includes following body with info$")
    public void responseIncludesFollowingBodyWithInfo(Map<String, String> responseFields) {
        RestUser user = new RestUser();
        user.setFirstName(responseFields.get("first_name"));
        user.setLastName(responseFields.get("last_name"));
        user.setEmail(responseFields.get("email"));
        user.setGender(responseFields.get("gender"));

        response.then().body("result.first_name", equalTo(user.getFirstName())).extract().jsonPath().getString("result.id");
        response.then().body("result.last_name", equalTo(user.getLastName())).extract().jsonPath().getString("result.id");
        response.then().body("result.email", equalTo(user.getEmail())).extract().jsonPath().getString("result.id");
        response.then().body("result.gender", equalTo(user.getGender())).extract().jsonPath().getString("result.id");
    }


    @And("^delete test user$")
    public void deleteTestUser() {
        RestAssured.given()
                .auth()
                .oauth2(API)
                .when()
                .delete("https://gorest.co.in/public-api/users/" + jsonResponseString)
                .then()
//                .log().all()
                .body("_meta.code", equalTo(204));
    }


    @When("^the user wants to get a record of existing user id \"([^\"]*)\"$")
    public void theUserWantsToGetARecordOfExistingUserId(String id) {
        response = given().auth().oauth2(API).when()
                .get("https://gorest.co.in/public-api/users/"+id);
    }


    @When("^patch existing user with info$")
    public void patchExistingUserWithInfo(Map<String, String> requestFields) {

        request = given().contentType(ContentType.JSON).auth().oauth2(API).with().body(requestFields);
        response = request.when().patch("https://gorest.co.in/public-api/users/14217");

    }
}
