package org.example.authenticators;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class BankOtpAuthenticator implements Authenticator {
    
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

    }

    /**
     * @return
     */
    @Override
    public boolean requiresUser() {
        return false;
    }

    /**
     * @param keycloakSession
     * @param realmModel
     * @param userModel
     * @return
     */
    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
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
