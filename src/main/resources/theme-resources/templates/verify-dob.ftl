<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle", realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml", realm.name)}
    <#elseif section = "form">
        <#--  Placeholder for secret question: used in secret-question.ftl  -->
        <#--  <label class="sr-only" for="otp"> Please Answer The Following Security Question </label>
        <input autofocus required aria-invalid="false" class="block border-secondary-200 mt-1 rounded-md w-full focus:border-primary-300 focus:ring focus:ring-primary-200 focus:ring-opacity-50 sm:text-sm" id="otp" name="secret_question" placeholder="What is my middle Name?" type="text" autocomplete="off">  -->

        <form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter answer  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Please Enter Date of Birth: </label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer" type="date" class="${properties.kcInputClass!}" placeholder="dd/MM/yyyy Example: 31-12-2023"/>
                </div>
            </div>

            <div>  <#--  This is for the Submit button  -->
                <#--  <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                        <label> Why is this label? </label>
                    </div>
                </div>  -->

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <div class="${properties.kcFormButtonsWrapperClass!}">
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="login" id="kc-login" type="submit" value="${msg("doSubmit")}"/>
                    </div>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>