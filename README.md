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
#### Deposit
 - 10_000 deposit limit per day 
 - 500 minimum deposit
 - 10_000 maximum deposit
-  2% of deposited amount will be deducted

#### Withdraw
 - 10_000 withdraw limit per day
 - 500 minimum withdraw
 - 10_000 maximum withdraw
 - 2% of withdrawn amount will be deducted
 
#### Peer to peer
 - 10_000 peer to peer limit per day
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
