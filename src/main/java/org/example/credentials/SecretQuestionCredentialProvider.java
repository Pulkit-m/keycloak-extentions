package org.example.credentials;

import org.example.authenticators.SecretQuestionAuthenticatorFactory;
import org.example.models.SecretQuestionCredentialModel;
import org.jboss.logging.Logger;
import org.keycloak.common.util.Time;
import org.keycloak.credential.*;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;

public class SecretQuestionCredentialProvider implements CredentialProvider<SecretQuestionCredentialModel>, CredentialInputValidator{

    private static final Logger logger = Logger.getLogger(SecretQuestionCredentialProvider.class);

    protected KeycloakSession session;

    public SecretQuestionCredentialProvider(KeycloakSession session) {
        this.session = session;
    }
    @Override
    public boolean supportsCredentialType(String credentialType) {
        return getType().equals(credentialType);
    }

    /**
     * If there exist at least 3(for example) credentials of credentialType, then we are sure that the user is configured for this authenticator.
     * @param realm
     * @param user
     * @param credentialType
     * @return
     */
    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if (!supportsCredentialType(credentialType)) return false;
        /*if(user.credentialManager().getStoredCredentialsByTypeStream(credentialType)
                .count() >= SecretQuestionAuthenticatorFactory.TOTAL_SECURITY_QUESTIONS_REQUIRED){
            return true;
        }else{
            System.out.println("Number of Security Questions are still less than 3, hence No good");
            return false;
        }*/
         return user.credentialManager().getStoredCredentialsByTypeStream(credentialType).findAny().isPresent();
    }

    // this method comes from CredentialInputValidator.
    // this method is called by the authenticator when it seeks to validate the user's input
    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        if (!(credentialInput instanceof UserCredentialModel)) {
            logger.debug("Expected instance of UserCredentialModel for CredentialInput");
            return false;
        }
        if (!credentialInput.getType().equals(getType())) {
            return false;
        }
        String challengeResponse = credentialInput.getChallengeResponse();
        if (challengeResponse == null) {
            return false;
        }
        CredentialModel credentialModel = user.credentialManager()
                .getStoredCredentialById(credentialInput.getCredentialId());
        SecretQuestionCredentialModel sqcm = getCredentialFromModel(credentialModel);

        System.out.println("Called the Credential Provider to look at the secret Question: " +
                sqcm.getSecretQuestionCredentialData().getQuestion());

        return sqcm.getSecretQuestionSecretData().getAnswer().equals(challengeResponse);
    }

    @Override
    public String getType() {
        return SecretQuestionCredentialModel.TYPE;
    }


    // below two methdos are to create and delete credentials from the database.
    @Override
    public CredentialModel createCredential(RealmModel realm, UserModel user, SecretQuestionCredentialModel credentialModel) {
        // note that we also have realm information if we wish to use it
        if(credentialModel.getCreatedDate()==null){
            credentialModel.setCreatedDate(Time.currentTimeMillis());
        }
        return user.credentialManager().createStoredCredential(credentialModel);
    }

    @Override
    public boolean deleteCredential(RealmModel realmModel, UserModel user, String credentialId) {
        return user.credentialManager().removeStoredCredentialById(credentialId);
    }

    @Override
    //The second method is to create a SecretQuestionCredentialModel from a CredentialModel.
    public SecretQuestionCredentialModel getCredentialFromModel(CredentialModel credentialModel) {
        return SecretQuestionCredentialModel.createFromCredentialModel(credentialModel);
    }

    /**
     * this is an abstract method of the credential provider interface. Each Credential Provider
     * has to provide and implement this method. This method returns an instance of the
     * CredentialTypeMetadata, which should atleast include <u>type</u> & <u>category</u> of the authenticator
     * , display name and removable item. Type can be obtained from getType(), category is TwoFactor and removable, which
     * is set up to False (user can't remove previously registered credentials). <br>
     * Other items include helpText(), createAction(), updateAction()
     * <ul>
     *     <li>helptext: to show to the user on various screens</li>
     *     <li>createAction: the provider id of the required action, which will be used by the user to create new credential</li>
     *     <li>updateAction: same as create action, but instead of create, it will be update</li>
     * </ul>
     * @param credentialTypeMetadataContext
     * @return
     */
    @Override
    public CredentialTypeMetadata getCredentialTypeMetadata(CredentialTypeMetadataContext credentialTypeMetadataContext) {
        return CredentialTypeMetadata.builder()
                .type(getType())
                .category(CredentialTypeMetadata.Category.TWO_FACTOR)
                .displayName(SecretQuestionCredentialProviderFactory.PROVIDER_ID)
                .helpText("help-for-secret-question-will-be-provided-soon")
                .createAction(SecretQuestionCredentialProviderFactory.PROVIDER_ID)
                .removeable(false)
                .build(session);
    }
}
