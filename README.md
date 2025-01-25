## Bank Service project
### Władysław Farat s30135

### Project structure
1. Model:
* BaseEntity:  
** Client  
** Transaction -> Transfer, Deposit
2. Service:
* TransactionService: handles creation of transfers and deposits
* ClientSerivce: handles creation of clients, changing balance of clients and retrieving information about clients
3. Storage
* Storage  
** ClientStorage  
** TransactionStorage