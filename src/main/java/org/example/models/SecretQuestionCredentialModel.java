package org.example.models;

import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;

/**
 * Credentials are stored as follows:
 * ID is the primary key of the credential.
 *
 * user_ID is the foreign key linking the credential to a user.
 *
 * credential_type is a string set during the creation that must reference an existing credential type.
 *
 * created_date is the creation timestamp (in long format) of the credential.
 *
 * user_label is the editable name of the credential by the user
 *
 * secret_data contains a static json with the information that cannot be transmitted outside of Keycloak
 *
 * credential_data contains a json with the static information of the credential that can be shared in the Admin Console or via the REST API.
 *
 * priority defines how "preferred" a credential is for a user, to determine which credential to present when a user has multiple choices.
 */
public class SecretQuestionCredentialModel extends CredentialModel {
    public static final String TYPE = "SECRET_QUESTION";
    private static final String USER_LABEL = "question";

    /**
     * the secret_data and credential_data fields are designed to contain json, it is up to you to determine how to structure, read and write into these fields, allowing you a lot of flexibility.
     */
    private final SecretQuestionCredentialData credentialData; // represents the question to be asked to be stored as a credential.
    private final SecretQuestionSecretData secretData;// represents the answer to be stored as a credential

    private SecretQuestionCredentialModel(SecretQuestionCredentialData credentialData, SecretQuestionSecretData secretData) {
        this.credentialData = credentialData;
        this.secretData = secretData;
    }

    public SecretQuestionCredentialData getSecretQuestionCredentialData() {
        return credentialData;
    }

    public SecretQuestionSecretData getSecretQuestionSecretData() {
        return secretData;
    }

    // another constructor
    private SecretQuestionCredentialModel(String question, String answer) {
        credentialData = new SecretQuestionCredentialData(question);
        secretData = new SecretQuestionSecretData(answer);
    }

    // this  method is to read data from Credential Model table and parse it into our SecretQuestion... 's representation.
    public static SecretQuestionCredentialModel createFromCredentialModel(CredentialModel credentialModel){
        try {
            // to get the credentials from database
            SecretQuestionCredentialData credentialData = JsonSerialization.readValue(credentialModel.getCredentialData(), SecretQuestionCredentialData.class);
            SecretQuestionSecretData secretData = JsonSerialization.readValue(credentialModel.getSecretData(), SecretQuestionSecretData.class);

            // mapping
            SecretQuestionCredentialModel secretQuestionCredentialModel = new SecretQuestionCredentialModel(credentialData, secretData);
            secretQuestionCredentialModel.setUserLabel(credentialModel.getUserLabel());
            secretQuestionCredentialModel.setCreatedDate(credentialModel.getCreatedDate());
            secretQuestionCredentialModel.setType(TYPE);
            secretQuestionCredentialModel.setId(credentialModel.getId());
            secretQuestionCredentialModel.setSecretData(credentialModel.getSecretData());
            secretQuestionCredentialModel.setCredentialData(credentialModel.getCredentialData());
            return secretQuestionCredentialModel;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static SecretQuestionCredentialModel createSecretQuestion(String question, String answer) {
        SecretQuestionCredentialModel credentialModel = new SecretQuestionCredentialModel(question, answer);
        credentialModel.fillCredentialModelFields();
        return credentialModel;
    }
    private void fillCredentialModelFields(){
        try {
            setCredentialData(JsonSerialization.writeValueAsString(credentialData));
            setSecretData(JsonSerialization.writeValueAsString(secretData));
            setType(TYPE);
            setUserLabel(USER_LABEL);
            setCreatedDate(Time.currentTimeMillis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
