1. Clone repo
2. Build the project
3. Start ChatTestApplication
4. For databese I am using in-memory H2 db. For connecting to DB use /h2-console endpoint. user/pass = sa/sa
5. App supports few endpoints for(only rest implementation)
  - user register/auth
  - join the chat room
  - send/fetch messagesg to/from chat room


List of available endpoints with requests

**register user**
POST api/v1/auth/register
```
{
    "userName": "userName",
    "password": "password"
}
```
**authenticate user**
POSTa api/v1/auth/authenticate 
```
{
    "userName": "userName",
    "password": "password"
}
```
**join the chat room**
POST api/v1/room
```
{
    "chatRoomId": 1,
    "userName": "userName"
}
```
**create message**
POST api/v1/message
```
{
    "chatRoomId":1,
    "userName":"userName",
    "text":"text"
}
```
fetch message
GET api/v1/message
```
{
    "chatRoomId":1,
    "userName":"userName"
}
```
