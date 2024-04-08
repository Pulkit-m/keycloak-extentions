package org.example.RequiredActions;

import jakarta.ws.rs.core.Response;
import org.example.authenticators.SecretQuestionAuthenticator;
import org.example.authenticators.SecretQuestionAuthenticatorFactory;
import org.example.credentials.SecretQuestionCredentialProvider;
import org.example.credentials.SecretQuestionCredentialProviderFactory;
import org.example.models.SecretQuestionCredentialModel;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.HashMap;
import java.util.stream.Stream;

public class SecretQuestionSetupRequiredAction implements RequiredActionProvider {

    public static final HashMap<String,String> SECURITY_QUESTIONS = new HashMap<>();
    static{
        // label-question
        SECURITY_QUESTIONS.put("question-1", "SECURITY_QUESTION_NUMBER 1");
        SECURITY_QUESTIONS.put("question-2", "SECURITY_QUESTION_NUMBER 2");
        SECURITY_QUESTIONS.put("question-3", "SECURITY_QUESTION_NUMBER 3");
        SECURITY_QUESTIONS.put("question-4", "SECURITY_QUESTION_NUMBER 4");
        SECURITY_QUESTIONS.put("question-5", "SECURITY_QUESTION_NUMBER 5");
        SECURITY_QUESTIONS.put("question-6", "SECURITY_QUESTION_NUMBER 6");
    }

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
     * @param requiredActionContext <br>
     * As per our use case requirement, we have to pass the the availabe six question to the user. As per good application design these questions should also be configurable from the User interface.
     */
    @Override
    public void requiredActionChallenge(RequiredActionContext requiredActionContext) {
        AuthenticatorConfigModel config = requiredActionContext.getRealm()
                .getAuthenticatorConfigById(SecretQuestionAuthenticatorFactory.PROVIDER_ID);
        int numQuestions = 10; // three questions should exist if no config property set.
        if (config != null) {
            numQuestions = Integer.valueOf(config.getConfig().get("min.required.questions"));
        }
        System.out.println("The Authenticator Config says that " + String.valueOf(numQuestions)
                + " number of security questions are required to be setup.");


       Response challenge = requiredActionContext.form()
                /*
                Doubt: The Action url is preset by the call to this form() method. You just need
                to reference it within your HTML form.
                 */
                .setAttribute("available_questions", SECURITY_QUESTIONS)
                .createForm("secret-question-config.ftl");
        requiredActionContext.challenge(challenge);
    }

    /**
     * This method is responsible for processing the input from the HTML form of the required action. The Action URL of the form will be routed to this very processActio()method.
     * @param requiredActionContext
     */
    @Override
    public void processAction(RequiredActionContext requiredActionContext) {

        String answer1 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_answer_1"));
        String question1 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_question_1"));
        String answer2 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_answer_2"));
        String question2 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_question_2"));
        String answer3 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_answer_3"));
        String question3 = (requiredActionContext.getHttpRequest()
                .getDecodedFormParameters().getFirst("secret_question_3"));

        SecretQuestionCredentialProvider sqcp = (SecretQuestionCredentialProvider) requiredActionContext
                .getSession().getProvider(CredentialProvider.class,
                        SecretQuestionCredentialProviderFactory.PROVIDER_ID);
        sqcp.createCredential(requiredActionContext.getRealm(),
                requiredActionContext.getUser(),
                SecretQuestionCredentialModel.createSecretQuestion(question1,answer1));
        sqcp.createCredential(requiredActionContext.getRealm(),
                requiredActionContext.getUser(),
                SecretQuestionCredentialModel.createSecretQuestion(question2,answer2));
        sqcp.createCredential(requiredActionContext.getRealm(),
                requiredActionContext.getUser(),
                SecretQuestionCredentialModel.createSecretQuestion(question3,answer3));
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
