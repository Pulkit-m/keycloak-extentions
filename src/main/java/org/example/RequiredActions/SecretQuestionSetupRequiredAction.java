package org.example.RequiredActions;

import jakarta.ws.rs.core.Response;
import org.example.authenticators.SecretQuestionAuthenticator;
import org.example.credentials.SecretQuestionCredentialProvider;
import org.example.credentials.SecretQuestionCredentialProviderFactory;
import org.example.models.SecretQuestionCredentialModel;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.stream.Stream;

public class SecretQuestionSetupRequiredAction implements RequiredActionProvider {

    @Override
    public InitiatedActionSupport initiatedActionSupport() {
        return RequiredActionProvider.super.initiatedActionSupport();
    }

    @Override
    public void initiatedActionCanceled(KeycloakSession session, AuthenticationSessionModel authSession) {
        RequiredActionProvider.super.initiatedActionCanceled(session, authSession);
    }

    /**
     * When should the user be prompted to re-do required action again.
     * @param requiredActionContext
     */
    @Override
    public void evaluateTriggers(RequiredActionContext requiredActionContext) {

    }

    /**
     * Initial call by the flow manager into the required action. This method is responsible for rendering the HTML form that will drive the required action.
     * @param requiredActionContext
     */
    @Override
    public void requiredActionChallenge(RequiredActionContext requiredActionContext) {
       Response challenge = requiredActionContext.form()
                /*
                Doubt: The Action url is preset by the call to this form() method. You just need
                to reference it within your HTML form.
                 */
                .setAttribute("custom-attribute", "This text was passed from my provider.")
                .createForm("secret-question-config.ftl");
        requiredActionContext.challenge(challenge);
    }

    /**
     * This method is responsible for processing the input from the HTML form of the required action. The Action URL of the form will be routed to this very processActio()method.
     * @param requiredActionContext
     */
    @Override
    public void processAction(RequiredActionContext requiredActionContext) {
        String answer = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_answer"));
        String question = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_question"));

        SecretQuestionCredentialProvider sqcp = (SecretQuestionCredentialProvider) requiredActionContext
                .getSession().getProvider(CredentialProvider.class,
                        SecretQuestionCredentialProviderFactory.PROVIDER_ID);
        sqcp.createCredential(requiredActionContext.getRealm(),
                requiredActionContext.getUser(),
                SecretQuestionCredentialModel.createSecretQuestion(question,answer));
        requiredActionContext.success();

        /*
        The documentation mentions: UserCredentialValueModel.
         */
        /*
        // this was as mentioned in the documentation. The above is the implementation by vedant.
        UserCredentialModel model = new UserCredentialModel();
        model.setValue(answer);
        model.setType(SecretQuestionAuthenticator.CREDENTIAL_TYPE);
        // model.setType(SecretQuestionAuthenticator.CREDENTIAL_TYPE);
        requiredActionContext.getUser().credentialManager().updateCredential(model);
        // requiredActionContext.getUser().updateCredentialDirectly(model);
        requiredActionContext.success();
        */
    }

    @Override
    public int getMaxAuthAge() {
        return RequiredActionProvider.super.getMaxAuthAge();
    }

    @Override
    public void close() {

    }
}
