package org.example.authenticators;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.forms.login.LoginFormsPages;
import org.keycloak.forms.login.LoginFormsProvider;

public class UsernamePasswordCaptchaAuthenticator extends UsernamePasswordForm {


    @Override
    protected Response challenge(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
        LoginFormsProvider forms = context.form();
        if (formData.size() > 0) {
            forms.setFormData(formData);
        }


        return forms.createLoginUsernamePassword();
    }

    @Override
    protected boolean validateForm(AuthenticationFlowContext context,
                                   MultivaluedMap<String, String> formData){
        return validateUserAndPassword(context, formData) &&
                this.validateCaptcha(context, formData);
    }

    public boolean validateCaptcha(AuthenticationFlowContext context,
                                   MultivaluedMap<String, String> formData){
        System.out.println("Currently return true no matter what.");
        return true;
    }
}
