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
            <#--  The Question and the answer  -->
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select Question 1: <#--${custom_attribute} --></label>
                    <#--  Dropdown for fisrt Question  -->
                    <select id="dropdown1" name="secret_question_1" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list custom_attribute?keys as key> 
                            <option value="${key}">${custom_attribute[key]}</option>
                        </#list>
                    </select>
                </div>

                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_1" type="text" class="${properties.kcInputClass!}" />
                </div>

                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select Question 2: <#--${custom_attribute} --></label>
                    <#--  Dropdown for second question  -->
                    <select id="dropdown2" name="secret_question_2" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list custom_attribute?keys as key> 
                            <option value="${key}">${custom_attribute[key]}</option>
                        </#list>
                    </select>
                </div>

                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_2" type="text" class="${properties.kcInputClass!}" />
                </div>

                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select Question 3: <#--${custom_attribute} --></label>
                    <#--  Dropdown for third question  -->
                    <select id="dropdown3" name="secret_question_3" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list custom_attribute?keys as key> 
                            <option value="${key}">${custom_attribute[key]}</option>
                        </#list>
                    </select>
                </div>

                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_3" type="text" class="${properties.kcInputClass!}" />
                </div>
                
            </div>

            <#--  Submit button  -->
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

<script> 
    function updateDropdowns(selectElement){
        // Get all dropdowns
        var dropdowns = document.querySelectorAll("select")

        // Extract selected questions from other dropdowns
        var selectedValues = {}; 
        dropdowns.forEach(function(dropdown){
            // dropdown.value is the value attribute in the option tag
            if(dropdown.value !== ""){
                selectedValues[dropdown.value] = true; 
            }
        });

        // Update options for all dropdowns 
        dropdowns.forEach(function(dropdown){
            var options = dropdown.querySelectorAll("option"); 
            options.forEach(function(option){
                if(option.value !== ""){
                    option.disabled = selectedValues[option.value];
                }
            });
        });
    }
</script>
    </#if>
</@layout.registrationLayout>