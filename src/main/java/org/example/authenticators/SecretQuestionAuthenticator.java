package org.example.authenticators;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.example.RequiredActions.SecretQuestionSetupRequiredAction;
import org.example.RequiredActions.SecretQuestionSetupRequiredActionFactory;
import org.example.credentials.SecretQuestionCredentialProvider;
import org.example.credentials.SecretQuestionCredentialProviderFactory;
import org.example.models.SecretQuestionCredentialModel;
import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SecretQuestionAuthenticator implements Authenticator, CredentialValidator<SecretQuestionCredentialProvider> {
    public static final String CREDENTIAL_TYPE = "SECRET_QUESTION";


    /**
     * This is the initial method that the flow invokes when the execution is first visited.
     * This method is not responsible for processing the secret question form.
     * Its sole responsibility is to render the page or to continue the flow.
     * @param context: AuthenticationFlowContext
     *
     *             <br>
     *  Application Specific Requirement: Authenticate should fetch the list of all available security questions, and send the list of available security questions as custom-attribute to the ftl. The Ftl should display the security questions as a drop down menu. The user chooses the question of their choice and then answers it. The question and the answer will be sent within the context to the action method.
     */
    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // fetch list of available security questions.
        SecretQuestionCredentialProvider credentialProvider = getCredentialProvider(context.getSession());
        // todo: exception handling : get without isPresent for Optional variable.
        HashMap<String,String> SECURITY_QUESTIONS = new HashMap<>();
        context.getUser().credentialManager()
                .getStoredCredentialsByTypeStream(CREDENTIAL_TYPE).toList()
                .forEach(credModel->{
                    String credentialId = credentialProvider.getCredentialFromModel(credModel).getId();
                    String credentialQuestion = credentialProvider.getCredentialFromModel(credModel)
                            .getSecretQuestionCredentialData().getQuestion();
                    SECURITY_QUESTIONS.put(credentialId, credentialQuestion);
                });

        /*List<String> SECURITY_QUESTIONS = context.getUser().credentialManager()
                .getStoredCredentialsByTypeStream(CREDENTIAL_TYPE)
                .map(credModel -> credentialProvider.getCredentialFromModel(credModel)
                        .getSecretQuestionCredentialData().getQuestion()).toList();*/

        System.out.println("Found these many Security Questions: ");
        SECURITY_QUESTIONS.keySet().forEach(key->{
            System.out.println(key + " " + SECURITY_QUESTIONS.get(key));
        });


        // SecretQuestionCredentialProvider credentialProvider = getCredentialProvider(context.getSession());
        // todo: exception handling : get without isPresent for Optional variable.
        //CredentialModel credentialModel = context.getUser().credentialManager()
        //        .getStoredCredentialsByTypeStream(CREDENTIAL_TYPE).findFirst().get();
        //SecretQuestionCredentialModel sqcm = credentialProvider
        //        .getCredentialFromModel(credentialModel);
        //String secret_question = sqcm.getSecretQuestionCredentialData().getQuestion();

        Response challenge = context.form()
                .setAttribute("custom_attribute", /*secret_question*/SECURITY_QUESTIONS)
                .createForm("secret-question.ftl");
        context.challenge(challenge);
        /*
        The form method initializesa Freemarker page builder
        This page builder is called LoginFormsProvider
        This provider loads a freemarker template file from your login theme
        We can also set some attributes
        .challenge will set the state to CHALLENGE, and the form will be rendered on the browser.
        When the user hits submit, the action() method will be invoked.
         */
    }

    /**
     * When the user hits submit button after entering credentials/secret question in our case.
     * @param context : AuthenticationFlowContext
     */
    @Override
    public void action(AuthenticationFlowContext context) {
        boolean validated = validateAnswer(context);
        if(!validated){
            Response challenge = context.form()
                    .setError("badSecret")
                    .createForm("secret-question.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }
        // setCookie(context); we can set a cookie here so that the user does not have to answer this question again.
        context.success();
    }

    /**
     * We will recieve the context from the user.
     * To validate the user's answer, we can use the isValid method that we wrote in SecretQuestionCredentialProvider. The isValid method is already setup such that it identifies the credential secret based on the credential id that is extracted from the authenticationFlowContext.
     * @param context
     * @return
     *
     * In case of multiple availabe credentials, the form also needs to contain the information of
     * credential id for the credential that is being used. If not specified, a default credential will
     * be used, which may or may not be as what is required of us.
     */
    protected boolean validateAnswer(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        System.out.println("These are all the available form Data keys: ");
        formData.keySet().forEach(System.out::println);
        String secret_answer_1 = formData.getFirst("secret_answer_1");
        String credentialId1 = formData.getFirst("secret_question_1"); // credentialId is not there i think
        String secret_answer_2 = formData.getFirst("secret_answer_2");
        String credentialId2 = formData.getFirst("secret_question_2");
        String secret_answer_3 = formData.getFirst("secret_answer_3");
        String credentialId3 = formData.getFirst("secret_question_3");

        System.out.println("The following form data was received: ");
        System.out.println(credentialId1 + "\n" +
                secret_answer_1 + "\n" +
                credentialId2 + "\n" +
                secret_answer_2 + "\n" +
                credentialId3 + "\n" +
                secret_answer_3 + "\n");


        // System.out.println("   Trying to fetch question by credentialId: ");
        // getCredentialProvider(context.getSession())


        /* I don't think this below if statement is ever called...*/
        /*if (credentialId == null || credentialId.isEmpty()) {
            System.out.println("This statement should not have been reachable.");
            credentialId = getCredentialProvider(context.getSession())
                    .getDefaultCredential(context.getSession(), context.getRealm(), context.getUser()).getId();
        }*/
        return  getCredentialProvider(context.getSession())
                .isValid(context.getRealm(),context.getUser(),
                        new UserCredentialModel(credentialId1,
                                getType(context.getSession()),secret_answer_1))
                &&
                getCredentialProvider(context.getSession())
                        .isValid(context.getRealm(),context.getUser(),
                        new UserCredentialModel(credentialId2,
                                getType(context.getSession()),secret_answer_2))
                &&
                getCredentialProvider(context.getSession())
                        .isValid(context.getRealm(),context.getUser(),
                        new UserCredentialModel(credentialId3,
                                getType(context.getSession()),secret_answer_3));





//        UserCredentialModel input = new UserCredentialModel(credentialId, getType(context.getSession()), secret_answer);
//        return getCredentialProvider(context.getSession()).isValid(context.getRealm(), context.getUser(), input);
    }

    /**
     * This is set to true when the authenticator requires the user to have already entered their correct login credentials.
     * @return
     */
    @Override
    public boolean requiresUser() {
        // this should always return true since, the user needs to be authenticated before being
        // challenged with the security question.
        return true;
    }

    /**
     * This method is responsible for determining if the user is configured for this particular
     * authenticator. In our case we can use the method we have written in the Secret Question Credential Provider.
     * @param keycloakSession
     * @param realm
     * @param user
     * @return
     *
     * Application use case:
     * Specific to our application we need to check if the total number of credentials of type SECURITY_QUESTION are equal to 3. I have made this change directly into the isConfiguredFor method in credentialProvdier
     */
    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realm, UserModel user) {
        System.out.println("Is the user configured for Security Question Authentication?: "
                + getCredentialProvider(keycloakSession).isConfiguredFor(realm, user, getType(keycloakSession)));
        return getCredentialProvider(keycloakSession).isConfiguredFor(realm, user, getType(keycloakSession));
    }

    /**
     * If configuredFor() returns false, and our authenticator is 'required' in the authentication flow, this method will be
     * called, but only if the corresponding authenticatorProviderFactory's 'isUserSetupAllowed()' returns true. \n
     * We can register any required actions that must be performed by the user. In our example, we need to register a required action
     * that will force the user to setup the answer to the secret question.
     * @param keycloakSession
     * @param realmModel
     * @param userModel
     */
    @Override
    public void setRequiredActions(KeycloakSession keycloakSession,
                                   RealmModel realmModel,
                                   UserModel userModel) {
        userModel.addRequiredAction(
                SecretQuestionSetupRequiredActionFactory.PROVIDER_ID);
    }

    @Override
    public List<RequiredActionFactory> getRequiredActions(KeycloakSession session) {
        return Authenticator.super.getRequiredActions(session);
    }

    @Override
    public boolean areRequiredActionsEnabled(KeycloakSession session, RealmModel realm) {
        return Authenticator.super.areRequiredActionsEnabled(session, realm);
    }

    /**
     * This comes from Credential Validator class.
     * This method allows the authenticator to retrieve SecretQuestionCredentialProvider.
     * provider provides the credentials that are stored in the database.
     * validator validates the user input against the credentials provided by the credentials provider.
     * @param keycloakSession
     * @return
     */
    @Override // this function comes from Credential Validator Class
    public SecretQuestionCredentialProvider getCredentialProvider(KeycloakSession keycloakSession) {
        return (SecretQuestionCredentialProvider) keycloakSession
                .getProvider(CredentialProvider.class,
                            SecretQuestionCredentialProviderFactory.PROVIDER_ID);
    }

    @Override
    public void close() {

    }


}
