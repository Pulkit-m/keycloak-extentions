package org.example.actionTokens;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.authentication.actiontoken.DefaultActionToken;

/**
 * An action token is just a signed JWT with a few mandatory details/fields. It can be serialized and signed using Keycloak's JWSBuilder class. serialize method is already present in DefaultActionToken and can be leveraged by implementors by using that class for tokens instead of just plain JsonWebToken.
 */
public class ForgotUsernameActionToken extends DefaultActionToken {
    // DefaultActionToken is kind of a POJO with session and email related information.
    public static final String TOKEN_TYPE = "forgot-username-token";


    /**
     * IF the action token you are implementing, contains any custom fields that should be serializable to JSON fields, you should implement a descendent of
     */
    private static final String JSON_FIELD_DEMO_ID = "demo-id";
    @JsonProperty(value = JSON_FIELD_DEMO_ID)
    private String demoId;

    public ForgotUsernameActionToken(String userId, int absoluteExpirationInSecs,
                                     String compoundAuthenticationSessionId, String demoId){
        super(userId, TOKEN_TYPE, absoluteExpirationInSecs,
                null, compoundAuthenticationSessionId);
        this.demoId = demoId;
    }

    /**
     * The class must have a private constructor without any arguments. This is necessary to deserialize the token class from JWT.
     */
    private ForgotUsernameActionToken(){
        super();
    }

    public String getDemoId(){
        return demoId;
    }
}
