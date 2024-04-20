package org.example.authenticators;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.credential.CredentialInput;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

public class UsernamePasswordCaptchaAuthenticator extends UsernamePasswordForm {


    /**
     * I took this method from Parent class, which is UsernamePasswordForm.
     * @param context: Authentication Flow Context
     * @param formData: Hashmap of key value pairs
     * @return Response which is just a page that the user shall see.
     */
    @Override
    protected Response challenge(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
        LoginFormsProvider forms = context.form();
                //.setAttribute("custom_attribute","XYZ987");
        if (!formData.isEmpty()) {
            forms.setFormData(formData);
        }
        forms.setAttribute("custom_attribute", "ABC123");
        return forms.createLoginUsernamePassword();
    }

    /**
     * I took this method from grandfather class, which is AbstractUsernameFormAuthenticator
     * @param context: Authentication Flow Context
     * @param error: String format, either wrong username or password
     * @param field: dunno
     * @return Response.
     */
    @Override
    protected Response challenge(AuthenticationFlowContext context, String error, String field) {
        LoginFormsProvider form = context.form().setExecution(context.getExecution().getId());
        if (error != null) {
            if (field != null) {
                form.addError(new FormMessage(field, error));
            } else {
                form.setError(error, new Object[0]);
            }
        }

        form.setAttribute("custom_attribute","NEWCAP");
        return this.createLoginForm(form);
    }

    /**
     * Taken from the parent class, and it only adds a new kind of validation which is Captcha Validation to the Picture.
     * @param context: Authentication Flow Context
     * @param formData: Hash map of key-value pairs
     * @return Boolean value, whether the username password and the captcha entered by the user checks out or not.
     */
    @Override
    protected boolean validateForm(AuthenticationFlowContext context,
                                   MultivaluedMap<String, String> formData){
        return validateUserAndPassword(context, formData) &&
                this.validateCaptcha(context, formData);
    }

    /**
     * This method is unique to this class. It validates the Captcha code.
     * //todo: Implement this method, and also handle BadCaptchaHandler Method
     * For reference BadPasswordHandler is already available in the
     * AbstractUsernamePasswordAuthenticator Class.
     * @param context: Authentication Flow Context: Fetches you everything related to current login flow.
     * @param formData: HashMap of values
     * @return: Boolean whether the captcha entered by the user checks out or not.
     */
    protected boolean validateCaptcha(AuthenticationFlowContext context,
                                   MultivaluedMap<String, String> formData){
        System.out.println("Checking Captcha:   Currently return true no matter what.");
        return true;
    }
}
