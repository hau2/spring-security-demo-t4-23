package com.example.springsecuritydemo.template;

import lombok.Data;

@Data
public class RequestResetPasswordTemplate {

    public static String build(String username, String link) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <title></title>\n" +
                "    \n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Roboto:400,600\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <!-- Web Font / @font-face : BEGIN -->\n" +
                "    <!--[if mso]>\n" +
                "        <style>\n" +
                "            * {\n" +
                "                font-family: 'Roboto', sans-serif !important;\n" +
                "            }\n" +
                "        </style>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "    <!--[if !mso]>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Roboto:400,600\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <![endif]-->\n" +
                "\n" +
                "    <!-- Web Font / @font-face : END -->\n" +
                "\n" +
                "    <!-- CSS Reset : BEGIN -->\n" +
                "    \n" +
                "    \n" +
                "    <style>\n" +
                "        /* What it does: Remove spaces around the email design added by some email clients. */\n" +
                "        /* Beware: It can remove the padding / margin and add a background color to the compose a reply window. */\n" +
                "        html,\n" +
                "        body {\n" +
                "            margin: 0 auto !important;\n" +
                "            padding: 0 !important;\n" +
                "            height: 100% !important;\n" +
                "            width: 100% !important;\n" +
                "            font-family: 'Roboto', sans-serif !important;\n" +
                "            font-size: 14px;\n" +
                "            margin-bottom: 10px;\n" +
                "            line-height: 24px;\n" +
                "            color:#8094ae;\n" +
                "            font-weight: 400;\n" +
                "        }\n" +
                "        * {\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt !important;\n" +
                "            mso-table-rspace: 0pt !important;\n" +
                "        }\n" +
                "        table {\n" +
                "            border-spacing: 0 !important;\n" +
                "            border-collapse: collapse !important;\n" +
                "            table-layout: fixed !important;\n" +
                "            margin: 0 auto !important;\n" +
                "        }\n" +
                "        table table table {\n" +
                "            table-layout: auto;\n" +
                "        }\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        img {\n" +
                "            -ms-interpolation-mode:bicubic;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f5f6fa;\">\n" +
                "\t<center style=\"width: 100%; background-color: #f5f6fa;\">\n" +
                "        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#f5f6fa\">\n" +
                "            <tr>\n" +
                "               <td style=\"padding: 40px 0;\">\n" +
                "                    <table style=\"width:100%;max-width:620px;margin:0 auto;\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td style=\"text-align: center; padding-bottom:25px\">\n" +
                "                                    <a href=\"#\"><img style=\"height: 40px\" src=\"https://metiz.vn/static//assets/websites/images/logo_bottom.png\" alt=\"logo\"></a>\n" +
                "                                    <p style=\"font-size: 14px; color: #6576ff; padding-top: 12px;\">Tech Mind Dashboard Theme</p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                    <table style=\"width:100%;max-width:620px;margin:0 auto;background-color:#ffffff;\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td style=\"text-align:center;padding: 30px 30px 15px 30px;\">\n" +
                "                                    <h2 style=\"font-size: 18px; color: #6576ff; font-weight: 600; margin: 0;\" >Reset Password</h2>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"text-align:center;padding: 0 30px 20px\">\n" +
                "                                    <p style=\"margin-bottom: 10px;\">"+username+",</p>\n" +
                "                                    <p style=\"margin-bottom: 25px;\">Click On The link blow to reset tour password.</p>\n" +
                "                                    <a href=\""+link+"\" style=\"background-color:#6576ff;border-radius:4px;color:#ffffff;display:inline-block;font-size:13px;font-weight:600;line-height:44px;text-align:center;text-decoration:none;text-transform: uppercase; padding: 0 25px\">Reset Password</a>\n" +
                "                                    <p style=\"margin-top: 25px;\">"+link+"</p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"text-align:center;padding: 20px 30px 40px\">\n" +
                "                                    <p>If you did not make this request, please contact us or ignore this message.</p>\n" +
                "                                    <p style=\"margin: 0; font-size: 13px; line-height: 22px; color:#9ea8bb;\">This is an automatically generated email please do not reply to this email. If you face any issues, please contact us at  leconghau095@gmail.com</p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                    <table style=\"width:100%;max-width:620px;margin:0 auto;\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td style=\"text-align: center; padding:25px 20px 0;\">\n" +
                "                                    <p style=\"font-size: 13px;\">Copyright © 2021 Tech Mind. All rights reserved. <br> Template Made By <a style=\"color: #6576ff; text-decoration:none;\" href=\"\">Tech Mind</a>.</p>\n" +
                "                                    <ul style=\"margin: 10px -4px 0;padding: 0;\">\n" +
                "                                        <li style=\"display: inline-block; list-style: none; padding: 4px;\"><a style=\"display: inline-block; height: 30px; width:30px;border-radius: 50%; background-color: #ffffff\" href=\"fb.com/conghau6\"><img style=\"width: 30px\" src=\"https://raw.githubusercontent.com/hau2/E-mail-Templates/main/images/brand-b.png\" alt=\"brand\"></a></li>\n" +
                "                                        <li style=\"display: inline-block; list-style: none; padding: 4px;\"><a style=\"display: inline-block; height: 30px; width:30px;border-radius: 50%; background-color: #ffffff\" href=\"#\"><img style=\"width: 30px\" src=\"https://raw.githubusercontent.com/hau2/E-mail-Templates/main/images/brand-e.png\" alt=\"brand\"></a></li>\n" +
                "                                        <li style=\"display: inline-block; list-style: none; padding: 4px;\"><a style=\"display: inline-block; height: 30px; width:30px;border-radius: 50%; background-color: #ffffff\" href=\"#\"><img style=\"width: 30px\" src=\"https://raw.githubusercontent.com/hau2/E-mail-Templates/main/images/brand-d.png\" alt=\"brand\"></a></li>\n" +
                "                                        <li style=\"display: inline-block; list-style: none; padding: 4px;\"><a style=\"display: inline-block; height: 30px; width:30px;border-radius: 50%; background-color: #ffffff\" href=\"#\"><img style=\"width: 30px\" src=\"https://raw.githubusercontent.com/hau2/E-mail-Templates/main/images/brand-c.png\" alt=\"brand\"></a></li>\n" +
                "                                    </ul>\n" +
                "                                    <p style=\"padding-top: 15px; font-size: 12px;\">This email was sent to you as a registered user of <a style=\"color: #6576ff; text-decoration:none;\" href=\"\">techmindtech.com</a>. To update your emails preferences <a style=\"color: #6576ff; text-decoration:none;\" href=\"#\">click here</a>.</p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "               </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </center>\n" +
                "</body>\n" +
                "</html>";
    }
}
