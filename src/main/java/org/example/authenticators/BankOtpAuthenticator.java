package org.example.authenticators;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.OTPFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class BankOtpAuthenticator extends OTPFormAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext authenticationFlowContext) {
        Response challenge = authenticationFlowContext.form()
                .setAttribute("custom_attribute","value-of-custom-attribute")
                .createForm("verify-otp.ftl");
        authenticationFlowContext.challenge(challenge);
    }

    /**
     * @param authenticationFlowContext
     */
    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
        MultivaluedMap<String, String> formData = authenticationFlowContext
                .getHttpRequest().getDecodedFormParameters();
        String otp_input = formData.getFirst("otp");

        if(otp_input.equalsIgnoreCase("123456")){
            authenticationFlowContext.success();
        }else{
            Response challenge = authenticationFlowContext.form()
                    .setError("Invalid OTP")
                    .createForm("verify-otp.ftl");
            authenticationFlowContext.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
        }
    }

    /**
     * @return
     */
    @Override
    public boolean requiresUser() {
        return true;
    }

    /**
     * @param keycloakSession
     * @param realmModel
     * @param userModel
     * @return
     * configuredFor Method always needs to return true if no configuration is required from the user's end.
     */
    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    /**
     * @param keycloakSession
     * @param realmModel
     * @param userModel
     */
    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    /**
     *
     */
    @Override
    public void close() {

    }
}
