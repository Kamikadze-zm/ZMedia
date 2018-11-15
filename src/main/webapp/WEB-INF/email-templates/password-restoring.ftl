<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Восстановление пароля</title>
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
                <p>Для восстановление пароля, пройдите, пожалуйста, по ссылке:<br>
                    <a href="https://zmedia.xyz/account/password-restoring/${code}">https://zmedia.xyz/account/password-restoring/${code}</a>
                </p>
                <p>Ссылка действительна в течении 1 часа</p>
                <p>Если вы не желаете восстанавливать пароль, проигнорируйте это письмо, пароль останется прежним.</p>
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