package org.example.authenticators;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordFormFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;


/**
 * It is recommended that your providerFactory returns a unique getId(). However there can be some exceptions to this rule
 * as mentioned in the Overriding Providers section in server developers' guide.
 */
public class UsernamePasswordCaptchaAuthenticatorFactory extends UsernamePasswordFormFactory {
    // public static final String PROVIDER_ID = "username-password-form-with-captcha";

    public static final UsernamePasswordCaptchaAuthenticator SINGLETON = new UsernamePasswordCaptchaAuthenticator();
    public static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES;
    static {
        REQUIREMENT_CHOICES = new AuthenticationExecutionModel
                .Requirement[]{
                        AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    /**
     * Default Constructor: Note that constructor is not overridden.
     */
    public UsernamePasswordCaptchaAuthenticatorFactory() {
        super();
    }

    /**
     * @param session
     * @return
     */
    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }

    /**
     * @returns the Provider id for the Provider, This is unique for now.
     */
    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    /**
     * This is what will be displayed in the Authentication Flow Diagram in Keycloak UI
     * @return String
     */
    @Override
    public String getDisplayType() {
        return "Username Password Form with Captcha";
    }

    /**
     * Help Text for the Authenticator:
     * @return
     */
    @Override
    public String getHelpText() {
        return "Validates a username and password from login form. " +
                "The login form should also display a captcha challenge " +
                "that the user shall have to complete.";
    }

    @Override
    public int order() {
        return super.order()+1;
    }
}
