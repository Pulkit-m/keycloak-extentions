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
                <#--  Box to enter question  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select a Secret Question: </label>
                    <#--  Dropdown for fisrt Question  -->
                    <select id="dropdown1" name="secret_question_1" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list available_questions?keys as key> 
                            <option value="${key}">${available_questions[key]}</option>
                        </#list>
                    </select>
                </div>
                <#--  <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_question_1" type="text" class="${properties.kcInputClass!}" placeholder="What was Harry Potter's Favorite Spell?"/>
                </div>  -->
            </div>

            
            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter answer  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Provide your answer for your question: </label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_1" type="text" class="${properties.kcInputClass!}" placeholder="type your answer"/>
                </div>
            </div>



            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter question  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select a Secret Question: </label>
                    <select id="dropdown2" name="secret_question_2" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list available_questions?keys as key> 
                            <option value="${key}">${available_questions[key]}</option>
                        </#list>
                    </select>
                </div>
                <#--  <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_question_2" type="text" class="${properties.kcInputClass!}" placeholder="What was Harry Potter's Favorite Spell?"/>
                </div>  -->
            </div>

            
            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter answer  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Provide your answer for your question: </label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_2" type="text" class="${properties.kcInputClass!}" placeholder="type your answer"/>
                </div>
            </div>



            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter question  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Select a Secret Question: </label>
                    <select id="dropdown3" name="secret_question_3" onchange="updateDropdowns(this)">
                        <option value="" disabled selected> Select Question </option>
                        <#list available_questions?keys as key> 
                            <option value="${key}">${available_questions[key]}</option>
                        </#list>
                    </select>
                </div>
                <#--  <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_question_3" type="text" class="${properties.kcInputClass!}" placeholder="What was Harry Potter's Favorite Spell?"/>
                </div>  -->
            </div>

            
            <div class="${properties.kcFormGroupClass!}">
                <#--  Box to enter answer  -->
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}"> Provide your answer for your question: </label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="secret_answer_3" type="text" class="${properties.kcInputClass!}" placeholder="type your answer"/>
                </div>
            </div>



            <div>  
                <#--  This is for the Submit button  -->
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

<script>
function updateDropdowns(selectElement){
        // Get all dropdowns
        var dropdowns = document.querySelectorAll("select")
        //todo
    }

</script>


    </#if>
</@layout.registrationLayout>