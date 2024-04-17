package org.example.actionTokens;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.authentication.actiontoken.ActionTokenHandler;
import org.keycloak.common.VerificationException;
import org.keycloak.events.EventType;
import org.keycloak.representations.JsonWebToken;
import org.keycloak.sessions.AuthenticationSessionModel;

public class ForgotUsernameActionTokenHandler implements ActionTokenHandler {
    /**
     * @param jsonWebToken
     * @param actionTokenContext
     * @return
     */
    @Override
    public Response handleToken(JsonWebToken jsonWebToken, ActionTokenContext actionTokenContext) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Class getTokenClass() {
        return null;
    }

    /**
     * @param jsonWebToken
     * @param actionTokenContext
     * @param authenticationSessionModel
     * @return
     */
    @Override
    public String getAuthenticationSessionIdFromToken(JsonWebToken jsonWebToken, ActionTokenContext actionTokenContext, AuthenticationSessionModel authenticationSessionModel) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public EventType eventType() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getDefaultEventError() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getDefaultErrorMessage() {
        return null;
    }

    /**
     * @param jsonWebToken
     * @param actionTokenContext
     * @return
     * @throws VerificationException <br>
     * "If the action token url was opened in the browser with existing authentication session, and the token contains authentication session ID matching the authentication session from teh browser, action token validation and handling will attach this ongoing authentication session. Otherwise, action token hanlder creates a fresh authentication session that replaces any other authentication session present at that time in the browser.  "
     */
    @Override
    public AuthenticationSessionModel startFreshAuthenticationSession(JsonWebToken jsonWebToken, ActionTokenContext actionTokenContext) throws VerificationException {
        return null;
    }

    /**
     * @param jsonWebToken
     * @param actionTokenContext
     * @return boolean
     * specifies whether the user will be able to reuse the token ever again. By Default and by logic this should always return false;
     */
    @Override
    public boolean canUseTokenRepeatedly(JsonWebToken jsonWebToken, ActionTokenContext actionTokenContext) {
        return false;
    }

    /**
     * Usually left un-implemented.
     */
    @Override
    public void close() {

    }
}
