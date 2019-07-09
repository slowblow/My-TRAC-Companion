Secret variables to be defined:

[mytrac/app.conf]
MailSender = "xxxx@xxxxxx.xxx" //mailaddress (real email)
PassSender = "xxxxxxxxx" //password mail (real password)
SmtpServerHost = "xxxxxx" //host server for SMTP (real smtp host)
SmtpServerPort = "xxx"  //port for SMTP (real smtp port)

eventapps=XXXXXXXXXXXXXXXXXX,XXXXXXXXXXXXXXXXXX //eventbrite app key (here two keys coma separated) (real eventapps)

serverKeyFirebase = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" //Key Server FireBase (real key)

[mytrac/local.conf]

(client username and server username must be the same, mobile app must know that info)
ClientBasicAuthenticationHttpUsername=xxxxxxxxxxxxxx //client auth username for basic auth
ClientBasicAuthenticationHttpPassword=xxxxxxxxxxxxxx //client auth password for basic auth
(client pass and server pass must be the same, mobile app must know that info)
ServerBasicAuthenticationHttpUsername=xxxxxxxxxxxxxx //server auth username for basic auth
ServerBasicAuthenticationHttpPassword=xxxxxxxxxxxxxx //server auth password for basic auth

(otp for different locations must be created previously for an otp administrator)
otp.base.url.1=http://url1:port1/otp //otp server for Catalonia
otp.base.url.2=http://url2:port2/otp //otp server for Portugal
otp.base.url.3=http://url3:port3/otp //otp server for Belgium and Netherlands
otp.base.url.4=http://url4:port4/otp //otp server for Greece
