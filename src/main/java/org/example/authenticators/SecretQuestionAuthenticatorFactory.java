package org.example.authenticators;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Factory is responsible for instantiating an authenticator.
 * Also provides deployment and configuration metadata about the authenticator.
 */
public class SecretQuestionAuthenticatorFactory implements AuthenticatorFactory {
    public static final String PROVIDER_ID = "secret-question-authenticator";
    public static final Integer TOTAL_SECURITY_QUESTIONS_REQUIRED = 3;
    private static final SecretQuestionAuthenticator SINGLETON = new SecretQuestionAuthenticator();
    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    public static final Logger logger = Logger.getLogger(SecretQuestionAuthenticatorFactory.class);

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();
    static{
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName("cookie.max.age");
        property.setLabel("Biscuit Ki Umar");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Max age in seconds of the SECRET_QUESTION_COOKIE. If the user logs in again within this time-frame, he won't have to answer the security question again");
        configProperties.add(property);

        ProviderConfigProperty property1 = new ProviderConfigProperty();
        property1.setName("min.required.questions");
        property1.setLabel("Number of Required Questions");
        property1.setType(ProviderConfigProperty.STRING_TYPE);
        property1.setHelpText("The user trying to login must have to answer these many security questions for logging in.");
        configProperties.add(property1);
    }

    @Override
    public String getDisplayType() {
        return "Security Question";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    /**
     * flag that specifies whether or not the Authenticator can be configured within the flow. This configuration is setup by the admin when he clicks on the settings button to the right side of the execution action in the flow. The configuration that the admin sees over there are the same configuration that we return in the getConfiguration method defined below.
     * @return
     */
    @Override
    public boolean isConfigurable() {
        return true; // false by default
    }

    /**
     * Specify the allowed requirement switches. ALTERNATIVE, REQUIRED, CONDITIONAL, DISABLED.
     * CONDITIONAL should only be used for subflows.
     * requirement on an authenticator should always be  REQUIRED, DISABLED OR ALTERNATIVE.
     * @return
     */
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    /**
     * this is just a flag that tells the flow manager whether or nto Authenticator.setRequiredActions() method
     * will be called or not. If an authenticator is not configured for a user, the flow manager checks isUserSetupAllowed().
     * If it is false, the the flow aborts with an error. If it returns true, then the flow manager will invoke Authenticator.setRequiredActions().
     * Note that if some requiredAction is set on a user, it is not permanent. Once the user completes the required action,
     * the required action will be removed from the user, and the user won't have to perform the required action again, unless
     * specifically the required action is set again.
     * @return
     */
    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return "The user Sets up his own security Questions and answers to them. " +
                "The user needs to remember the answers character by character to be able to login again.";
    }

    /**
     * returns a list of ProviderConfigProperty objects. Every object defines a specific configuration attribute.
     * @return
     */
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    /**
     * called by the runtime to allocate and process the authenticator.
     * @param keycloakSession
     * @return
     */
    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        logger.debug("Initiating authenticator");
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

    /**
     * This is simply just a unique name for the component.
     * @return
     */
    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
