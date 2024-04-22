package org.example.actionTokens;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.authentication.actiontoken.ActionTokenHandler;
import org.keycloak.common.VerificationException;
import org.keycloak.events.EventType;
import org.keycloak.representations.JsonWebToken;
import org.keycloak.sessions.AuthenticationSessionModel;

public class ForgotUsernameActionTokenHandler extends AbstractActionTokenHandler<ForgotUsernameActionToken> {

    public ForgotUsernameActionTokenHandler(String id, Class<ForgotUsernameActionToken> tokenClass, String defaultErrorMessage, EventType defaultEventType, String defaultEventError) {
        super(id, tokenClass, defaultErrorMessage, defaultEventType, defaultEventError);
    }

    /**
     * @param forgotUsernameActionToken
     * @param actionTokenContext
     * @return
     */
    @Override
    public Response handleToken(ForgotUsernameActionToken forgotUsernameActionToken, ActionTokenContext<ForgotUsernameActionToken> actionTokenContext) {
        return null;
    }
}
