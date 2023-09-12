# atm-machine
REST API that has functionality of atm machine.

# Features
 - Send money peer to peer 
 - Deposit 
 - Withdraw
 - Record deposit transaction
 - Record withdraw transaction
 - Record sent money transaction

# Logics
 - 10_000 deposit limit
 - 10_000 withdraw limit
 - 10_000 peer to peer limit
 - 2% of deposited amount will be deducted
 - 2% of sent amount will be deducted
 - 2% of peer to peer amount will be deducted

# Technologies used
- Spring boot
- Spring mvc
- Spring data jpa
- Software used
  - MySQL
  - IntelliJ
  - Postman

 # Check API endpoints in Postman
 [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/26932885-b3659bf7-2dca-4df9-bd4e-92187201745d?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D26932885-b3659bf7-2dca-4df9-bd4e-92187201745d%26entityType%3Dcollection%26workspaceId%3D5d95d4a9-60d1-4437-8be3-7f9cd50e952e)
