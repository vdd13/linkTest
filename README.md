Aplikacja do pobrania z https://hub.docker.com/r/vdd13/malwareverification
należy wykonać:
  sudo docker pull vdd13/malwareverification
i uruchomić dla portu 8080 (zmienić jeśli jest on zajęty): 
  sudo docker run -p 8080:8080 vdd13/malwareverification

Aplikacja obsługuje 2 metody POST (klasa w kodzie to: pl.dom.linkTest.controller.TextVerificationControler):

1. http://localhost:8080/messageVerification/numberToVerification
  Pozwala na dodanie numeru telefonu - do weryfikacji czy treść SMSów posiada linki phishing - do bazy danych (H2 embedded) poprzez wysłanie JSON
    {
      "number": "48700800999",
      "message": "START"
    }

  (Można wyłączyć sprawdzanie treści SMS dla danego numeru telefonu poprzez zamianę START na STOP wartości dla "message"

2. http://localhost:8080/messageVerification/textToVerification
  Pozwala na sprawdzenie treści przesłanej dla danego numeru poprzez przesłanie linków z treści JSON ("message") do https://webrisk.googleapis.com/v1eap1:evaluateUri
  zgodnie z podanym API https://cloud.google.com/web-risk/docs/reference/rest/v1eap1/TopLevel/evaluateUri
    {
      "sender": "234100200300",
      "recipient": "48700800999",
      "message": "Dzień dobry. W związku z audytem nadzór finansowy w naszym banku proszą o potwierdzanie danych pod adresem: https://www.m-bonk.pl.ng/personal-data"
    }

Korzystając z postman/talend API to sprawdzenia działania obu metod należy ustawić headers-  Content-Type : application/json
