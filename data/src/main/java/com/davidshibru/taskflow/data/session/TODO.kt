package com.davidshibru.taskflow.data.session

/*

  curl -X POST "http://localhost:8008/_matrix/client/v3/login" \
       -H "Content-Type: application/json" \
       -d '{
             "type": "m.login.password",
             "identifier": {
               "type": "m.id.user",
               "user": "admin"
             },
             "password": "admin"
           }'

  Error response
  {"errcode":"M_FORBIDDEN","error":"Invalid username or password"}

  Success Response

  {"user_id":"@admin:localhost","access_token":"syt_YWRtaW4_gGLZIuKOafdLsWATZvuG_3cvKru","home_server":"localhost","device_id":"IFAMTIRJUQ"}d

  */