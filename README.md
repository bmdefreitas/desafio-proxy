# Proxy Reverso

Proxy HTTPS Reverso - JAVA com suporte SNI



# Funcionamento da Solução

![alt text](https://raw.githubusercontent.com/bmdefreitas/desafio-proxy/master/Funcionamento.jpg)

# KeyStore
```
Nome do alias: test1.localdomain
Data de criação: 29/09/2018
Tipo de entrada: PrivateKeyEntry
Comprimento da cadeia de certificados: 1
Certificado[1]:
Proprietário: CN=test1.localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Emissor: CN=test1.localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Número de série: 57d266ec
Válido de Sat Sep 29 01:07:56 BRT 2018 até Sun Sep 29 01:07:56 BRT 2019
Fingerprints do certificado:
         MD5:  68:24:B2:CD:09:41:F9:A3:12:2F:BA:81:22:2F:BA:EA
         SHA1: C8:43:7F:DA:18:DF:50:B4:8F:E6:6B:B9:84:AF:7D:A0:80:DF:B5:B5
         SHA256: 83:93:D0:8B:35:EE:90:3F:27:A6:E2:18:DA:E5:65:28:58:9C:75:99:33:E0:21:D0:26:11:B0:4E:AA:11:87:1E
Nome do algoritmo de assinatura: SHA256withRSA
Algoritmo de Chave Pública do Assunto: Chave RSA de 2048 bits
Versão: 3

Extensões: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 7F 83 AF 20 D3 50 E7 EA   7C 58 69 B4 1F FF C9 B4  ... .P...Xi.....
0010: 9C 70 CF 21                                        .p.!
]
]



*******************************************
*******************************************


Nome do alias: test2.localdomain
Data de criação: 29/09/2018
Tipo de entrada: PrivateKeyEntry
Comprimento da cadeia de certificados: 1
Certificado[1]:
Proprietário: CN=test2.localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Emissor: CN=test2.localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Número de série: 14beacb4
Válido de Sat Sep 29 01:08:50 BRT 2018 até Sun Sep 29 01:08:50 BRT 2019
Fingerprints do certificado:
         MD5:  33:A4:54:9A:47:2F:BD:C4:E7:E1:8A:AC:5A:CA:F1:32
         SHA1: D0:E9:FF:10:B2:2A:56:CA:30:3B:09:F9:BE:B3:68:D0:88:7E:70:FA
         SHA256: 96:F5:A2:38:78:92:AB:90:91:C4:61:F4:54:07:55:8C:4E:33:03:A9:45:45:A2:63:F1:5D:A9:25:4C:F7:4F:1A
Nome do algoritmo de assinatura: SHA256withRSA
Algoritmo de Chave Pública do Assunto: Chave RSA de 2048 bits
Versão: 3

Extensões: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: D4 A3 65 19 24 4A A9 79   1F C6 B0 52 13 C0 97 70  ..e.$J.y...R...p
0010: 54 77 87 98                                        Tw..
]
]



*******************************************
*******************************************


Nome do alias: localdomain
Data de criação: 29/09/2018
Tipo de entrada: PrivateKeyEntry
Comprimento da cadeia de certificados: 1
Certificado[1]:
Proprietário: CN=localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Emissor: CN=localdomain, OU=Unknown, O=bmdefreitas, L=Rio de Janeiro, ST=RJ, C=BR
Número de série: 796ca68a
Válido de Sat Sep 29 01:16:36 BRT 2018 até Sun Sep 29 01:16:36 BRT 2019
Fingerprints do certificado:
         MD5:  8F:63:98:66:C7:C9:B3:E4:80:9D:DB:96:CE:60:80:66
         SHA1: 38:B2:36:40:6C:03:0A:E4:13:A3:16:7B:97:81:1C:C8:D0:D5:89:6B
         SHA256: E2:4D:40:D4:FA:6F:A7:DC:77:4D:1E:CF:05:2F:8D:42:0A:54:B1:BD:C2:5B:A3:7A:29:EF:C3:E3:D2:3F:7F:5A
Nome do algoritmo de assinatura: SHA256withRSA
Algoritmo de Chave Pública do Assunto: Chave RSA de 2048 bits
Versão: 3

Extensões: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 18 6C FF 52 0A F3 60 0E   C5 DE 16 F2 A4 91 F6 BC  .l.R..`.........
0010: 90 2D 97 2E                                        .-..
]
]



*******************************************
*******************************************
```

# Ajustes no SO - Linux

Tendo em vista que o Proxy deverá atender mais de 10.000 conexões simultâneas e que todas as conexão se faz necessário acessar o Keystore para checar se o CN contém nele, alguns ajustes no SO serão necessários para melhorar a performance. Podemos levar em consideração ajustar os parametros do file descriptors e do número de processos abertos. Inicialmente o SO limita estes números por usuário. Então o usuário que rodará a aplicação deverá ter essas configurações ajustadas. Logo abaixo tentarei exemplificar.

*/etc/security/limits.conf* 

```
proxyuser soft nofile 294180 
proxuser hard nofile 294180 
proxyuser soft nproc 32768 
proxyuser hard nproc 32768
```


Como a configuração de fs.file-max tem precedência com as configurações do nofile, devemos ajustá-la com valor igual o superior. O superior neste caso é mais recomendado.

*/etc/sysctl.conf*

```
fs.file-max = 294180
```


Podemos considerar as configurações TCP do Kernel, tais como: 

*/etc/sysctl.conf*

```
net.ipv4.tcp_tw_reuse=1 
net.ipv4.tcp_tw_recycle=1 
net.ipv4.tcp_fin_timeout=60
```


# Rodando o Projeto com Docker

Configurar o arquivo hosts do cliente que irá acessar a solução, informando o IP para os hosts test1.localdomain, test2.localdomain e test3.localdomain, conforme abaixo:


```
10.0.0.7        localdomain
10.0.0.7        test1.localdomain
10.0.0.7        test2.localdomain
10.0.0.7        test3.localdomain
```

Faça o checkout do projeto:

```
git clone https://github.com/bmdefreitas/desafio-proxy.git
```


Considerando que o docker e docker-compose estejam instalado, execute os comandos abaixo para a criação e start dos containers:

```
sudo docker-compose build
sudo docker-compose up
```

Abra o browser e acesse a url: [https://test1.localdomain:8443/](https://test1.localdomain:8443/) verifique o certificado correspondente.

Em seguida acesse a url: [https://test2.localdomain:8443/](https://test2.localdomain:8443/) verifique o certificado correspondente.


# Rodando o Projeto com Ansible

Faça o checkout do projeto:

```
git clone https://github.com/bmdefreitas/desafio-proxy.git
```

Considerando que o ansible esteja instalado, execute os comandos abaixo:


```
ansible-playbook -i hosts provisioning.yml
``` 

