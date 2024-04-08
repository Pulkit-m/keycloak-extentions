package org.example.authenticators.conditional;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Optional;

public class FirstTimeUserLoginConditional implements ConditionalAuthenticator {
    @Override
    public boolean matchCondition(AuthenticationFlowContext authenticationFlowContext) {
        UserModel user = authenticationFlowContext.getUser();
        Optional<CredentialModel> optionalCredentialModel = user.credentialManager()
                .getStoredCredentialsByTypeStream("Password").findFirst();
        CredentialModel creds = optionalCredentialModel.get();
        // TODO: REMOVE THIS CONDITIONAL AUTHENTICATOR, THIS IS ALREADY BUILT-INTO THE KEYCLOAK SYSTEM.
        return false;
    }

    /**
     * No need to override the below methods. Also there are other methods available from Authenticator Class as well, but we do not need to implement or override any mroe methods for creating a conditional authenticator.
     * @param context
     */
    @Override
    public void authenticate(AuthenticationFlowContext context) {
        ConditionalAuthenticator.super.authenticate(context);
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return ConditionalAuthenticator.super.configuredFor(session, realm, user);
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
