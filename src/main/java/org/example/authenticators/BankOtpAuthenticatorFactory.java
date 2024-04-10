package org.example.authenticators;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class BankOtpAuthenticatorFactory implements AuthenticatorFactory {
    public static final String PROVIDER_ID = "hdfc-bank-otp-authenticator";
    private static final BankOtpAuthenticator SINGLETON = new BankOtpAuthenticator();


    /**
     * @return
     */
    @Override
    public String getDisplayType() {
        return "HDFC Bank OTP Service";
    }

    /**
     * @return
     */
    @Override
    public String getReferenceCategory() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isConfigurable() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[0];
    }

    /**
     * @return
     */
    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getHelpText() {
        return "Uses HDFC Bank's Internal OTP Service for OTP Verification.";
    }

    /**
     * @return
     */
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    /**
     * @param keycloakSession
     * @return
     */
    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    /**
     * @param scope
     */
    @Override
    public void init(Config.Scope scope) {

    }

    /**
     * @param keycloakSessionFactory
     */
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    /**
     * NO need to implement this usually
     */
    @Override
    public void close() {

    }

    /**
     * @return
     */
    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
