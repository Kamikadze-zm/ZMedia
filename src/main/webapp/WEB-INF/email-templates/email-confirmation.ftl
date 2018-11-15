<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Подтверждение электронной почты</title>
    <style>
        body {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: 16px;
            color: #333333;
            background-color: #ffffff;
        }

        a {
            color: #0066cc;
            text-decoration: none;
        }
        a:hover,
        a:focus {
            color: #004080;
            text-decoration: underline;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 800px;">
        <tr>
            <td align="center" valign="middle">
                <p>Здравствуйте, ${user.name}!</p>
                <p>Для подтверждения электронной почты, пройдите, пожалуйста, по ссылке:<br>
                    <a href="https://zmedia.xyz/account/email-confirm/${code}">https://zmedia.xyz/account/email-confirm/${code}</a>
                </p>
                <p>Ссылка действительна в течении 7 дней. Запросить новую можно на странице 
                    <a href="https://zmedia.xyz/account">управления аккаунтом</a></p>
                <p>Если вы не осуществляли регистрацию на нашем сайте, проигнорируйте это письмо.</p>
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                <p style="color: #777777">&copy; ${.now?string('yyyy')} ZMedia</p>
            </td>
        </tr>
    </table>
</body>
</html>