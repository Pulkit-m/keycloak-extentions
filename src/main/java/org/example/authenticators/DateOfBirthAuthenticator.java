package org.example.authenticators;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateOfBirthAuthenticator implements Authenticator {
    @Override
    public void authenticate(AuthenticationFlowContext authenticationFlowContext) {

        Response challenge = authenticationFlowContext.form()
                .setAttribute("custom_attribute","value-of-custom-attribute")
                .createForm("verify-dob.ftl");
        authenticationFlowContext.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
            MultivaluedMap<String, String> formData = authenticationFlowContext
                    .getHttpRequest().getDecodedFormParameters();
        String secret_answer = formData.getFirst("secret_answer");
        String secret_question = formData.getFirst("secret_question");
        String credentialId = formData.getFirst("credentialId");
        System.out.println("This is what the user Entered: " +
                "Secret Question: " + secret_question + "\n" +
                "Secret Answer: " + secret_answer + "\n" +
                "credentialId" + credentialId);

        UserModel user = authenticationFlowContext.getUser();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean validated = false;
        String errorMessage = null;
        AuthenticationFlowError authFlowError = null;
        try {
            Date systemDate = sdf.parse(user.getAttributes().get("date-of-birth").get(0));
            Date userInputDate = sdf.parse(secret_answer);
            System.out.println("System Date: " + systemDate);
            System.out.println("User Input Date: " + sdf.parse(secret_answer));
            if(systemDate.equals(userInputDate)){
                authenticationFlowContext.success();
                validated = true;
            }else{
                errorMessage = "Invalid Credentials";
                authFlowError = AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR;
            }
        } catch (ParseException e) {
            System.out.println("Bad Date Format.");
            errorMessage = "Bad Date Format";
            authFlowError = AuthenticationFlowError.INVALID_CREDENTIALS;
        }
        if(!validated){
            Response challenge = authenticationFlowContext.form()
                    .setError(errorMessage)
                    .createForm("verify-dob.ftl");
            authenticationFlowContext.failureChallenge(authFlowError, challenge);
        }
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        Map<String, List<String>> userAttributes = userModel.getAttributes();
        boolean configured = userAttributes.containsKey("date-of-birth");
        System.out.println("Does date of birth exist for this user?: "+ configured);
        return configured;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        // no required actions if we have to check against user-attributes only.
    }

    @Override
    public void close() {

    }
}
