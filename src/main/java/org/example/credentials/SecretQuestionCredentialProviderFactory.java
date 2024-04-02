package org.example.credentials;

import org.keycloak.Config;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.credential.OTPCredentialProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class SecretQuestionCredentialProviderFactory implements CredentialProviderFactory<SecretQuestionCredentialProvider> {
    public static final String PROVIDER_ID =  "secret-question";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public int order() {
        return CredentialProviderFactory.super.order();
    }

    @Override
    public List<ProviderConfigProperty> getConfigMetadata() {
        return CredentialProviderFactory.super.getConfigMetadata();
    }

    @Override
    public void init(Config.Scope config) {
        CredentialProviderFactory.super.init(config);
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        CredentialProviderFactory.super.postInit(factory);
    }

    @Override
    public void close() {
        CredentialProviderFactory.super.close();
    }

    @Override
    public CredentialProvider create(KeycloakSession session) {
        return new SecretQuestionCredentialProvider(session);
    }
}
