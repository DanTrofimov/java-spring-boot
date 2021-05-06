<#import "spring.ftl" as spring/>
<#import "tags/header.ftl" as header/>
<#import "tags/sign-in-form.ftl" as signForm/>
<#import "tags/headerImports.ftl" as imports/>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In</title>
    <link rel="stylesheet" href='<@spring.url "/styles/auth.css"/>' type="text/css">
    <link rel="icon" href='<@spring.url "/assets/favicon.ico"/>' type="image/x-icon" />
    <@imports.imports />
</head>
<body>
    <@header.header />

    <div class="sign-in-content">
        <div>
            <@signForm.signInForm/>

            <p class="link-container">
                <a href='<@spring.url "/registration"/>'><@spring.message 'sign_in_page.placeholder.registration'/></a>
            </p>
            <p class="link-container">
                <a href="https://oauth.vk.com/authorize?client_id=7810780&redirect_uri=http://localhost:8098/sign-in&response_type=code&scope=email">VK-Oauth login</a>
<#--                <a href="https://oauth.vk.com/access_token?client_id=7810780&client_secret=NrTByxV7tSqghYGYeNHx&redirect_uri=http://localhost:8098/sign-in&code=262659c29cb26de435" target="_blank">VK-Oauth login</a>-->
<#--                520562571 - userID-->
            </p>
            <#if code??>
                <p class="link-container error-message">
                    ${code}
                </p>
            </#if>
            <#if signInError??>
                <p class="link-container error-message">
                    ${signInError}
                </p>
            </#if>
            <#if confirmMessage??>
                <p class="link-container error-message" style="color: green">
                    ${confirmMessage}
                </p>
            </#if>
        </div>
        <div>
            <img src="${ springMacroRequestContext.contextPath }/static/assets/welcome-image.png" alt="welcome-image" class="welcome-image">
        </div>
    </div>
</body>
</html>
