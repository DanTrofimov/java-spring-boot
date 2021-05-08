<#import "spring.ftl" as spring/>
<#import "tags/header.ftl" as header/>
<#import "tags/headerImports.ftl" as imports/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href='<@spring.url "/styles/main.css"/>' type="text/css">
    <link rel="stylesheet" href="<@spring.url '/styles/header.css' />" type="text/css">
    <link rel="icon" href='<@spring.url "/assets/favicon.ico"/>' type="image/x-icon" />
    <@imports.imports />
    <title>Todo - Oauth</title>
</head>
<body style="display: flex; flex-direction: column; margin: 0; padding: 0; box-sizing: border-box">
    <@header.header />
    <div style="height: 90vh; width: auto; text-align: center; display: grid; place-items: center; font-size: 20px; font-family: Consolas, sans-serif">
        <div>
            <form action="sign-in" method="POST">
                <#if email??>
                    <input type="hidden" name="email" value="${email}">
                </#if>
                    <input type="hidden" name="password" value=" ">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <input type="submit" value="Continue with VK account" class="send-button" style="padding: 10px; font-size: 17px; margin-bottom: 10px; cursor: pointer">
            </form>
            <a href='<@spring.url "/"/>'>back</a>
        </div>
    </div>
</body>
</html>