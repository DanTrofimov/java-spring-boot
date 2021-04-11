<#import "spring.ftl" as spring/>
<#import "tags/header.ftl" as header/>
<#import "tags/registration-form.ftl" as regForm/>
<#import "tags/headerImports.ftl" as imports/>
<#import "tags/footerImports.ftl" as footerImports/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href='<@spring.url "/styles/auth.css"/>' type="text/css">
    <link rel="icon" href='<@spring.url "/assets/favicon.ico"/>' type="image/x-icon" />
    <@imports.imports />
</head>
<body>
    <@header.header />
    <div class="registration-content" style="text-align: center">
        <div>
            <h3>${ showError }</h3>
            <br><a href="<@spring.url "/sign-in"/>">back</a>
        </div>
    </div>
    <@footerImports.footerImports />
</body>
</html>