package org.example.RequiredActions;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class SecretQuestionSetupRequiredActionFactory implements RequiredActionFactory {

    public static final String PROVIDER_ID = "secret-question-setup";

    private static final SecretQuestionSetupRequiredAction SINGLETON = new SecretQuestionSetupRequiredAction();

    /**
     * What you write here is what will actually be visible to the Server Administrator while setting up your authenticator in his flow.
     * @return
     */
    @Override
    public String getDisplayText() {
        return "Secret Question Configuration Setup";
    }

    @Override
    public boolean isOneTimeAction() {
        return RequiredActionFactory.super.isOneTimeAction();
    }

    @Override
    public RequiredActionProvider create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public int order() {
        return RequiredActionFactory.super.order();
    }

    @Override
    public List<ProviderConfigProperty> getConfigMetadata() {
        return RequiredActionFactory.super.getConfigMetadata();
    }
}
