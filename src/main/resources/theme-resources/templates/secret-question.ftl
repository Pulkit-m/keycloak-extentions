<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle", realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml", realm.name)}
    <#elseif section = "form">
        <#--  <label class="sr-only" for="otp"> Please Answer The Following Security Question </label>
        <input autofocus required aria-invalid="false" class="block border-secondary-200 mt-1 rounded-md w-full focus:border-primary-300 focus:ring focus:ring-primary-200 focus:ring-opacity-50 sm:text-sm" id="otp" name="otp" placeholder="What is my middle Name?" type="text" autocomplete="off">  -->

        <form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Question: ${custom_attribute}</label>
                    <#--  <select name="secret_question">  -->
                        <#--  <option disabled selected> Select Question </option>  -->
                        <#--  <#list custom_attribute as item>
                            <option id="someId">${item}</option>
                        </#list>  -->
                        <#--  <#list custom_attribute?keys as key>   -->
                            <#--  <option value="${key}">${custom_attribute[key]}</option>  -->
                        <#--  </#list>  -->
                    <#--  </select>  -->
                </div>

                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer" type="text" class="${properties.kcInputClass!}" />
                </div>
            </div>

            <div>
                <#--  <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
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