确认列表
1 .JDK 17.0.12
2. 能
3.能
4.是
5.API fox  curl-windows版请求接口代码
用户名与密码登录

普通用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"user_1\",    \"password\": \"user_1\",    \"loginType\": \"USER\"}"
{"code":200,"message":"success","data":{"userId":1,"username":"user_1","role":"COMMON","loginType":"USER","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3MDU0LCJleHAiOjE3NjgzNjQyNTQsImp0aSI6IjRmYjM5NWI2LTIxMmItNDZmOC1hNDMyLWM1MTZlNTFjOTVhNiIsInJvbGVzIjpbIkNPTU1PTiJdfQ.IfteNex2ISHgXxPStTgvB4e9anOl4J8003yF0ym_Afk","expiresIn":7200},"timestamp":1768357054276}
编辑用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"editor_1\",    \"password\": \"editor_1\",    \"loginType\": \"USER\"}"
{"code":200,"message":"success","data":{"userId":2,"username":"editor_1","role":"EDITOR","loginType":"USER","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3MjM4LCJleHAiOjE3NjgzNjQ0MzgsImp0aSI6IjUyMGIyMjRkLTM4NTYtNDQxNS1iMWRlLWUzZjUxOTk2ZDFhNCIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiJdfQ.s83pF7CKp1CUkT5jiFJX7zqURscq2DJkF6FgLhG3dck","expiresIn":7200},"timestamp":1768357238763}
管理用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"adm_1\",    \"password\": \"adm_1\",    \"loginType\": \"USER\"}"
{"code":200,"message":"success","data":{"userId":3,"username":"adm_1","role":"ADMIN","loginType":"USER","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3MjkwLCJleHAiOjE3NjgzNjQ0OTAsImp0aSI6Ijg5MmJiNDU4LTI2MGYtNGZhMy1iNjdmLTg4YzI1ZTg3NjVkZCIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.Q8Bh7hc7kV6K3G8vktNIrAGaL19m-yVJNtevz6FvaaU","expiresIn":7200},"timestamp":1768357290255}

GitHub登录  有多处重定向只能网址校验
可输入网址 http://localhost:7573/auth/oauth2/authorization/github  成功后会跳转到商品页面

LDAP登录

普通用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"ldap_user_1\",    \"password\": \"123456\",    \"loginType\": \"LDAP\"}"
{"code":200,"message":"success","data":{"userId":1,"username":"ldap_user_1","role":"COMMON","loginType":"LDAP","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NDYyLCJleHAiOjE3NjgzNjQ2NjIsImp0aSI6IjcwODcxZDE4LTI4NTktNDdjZi1iOGYyLTA4MTMzYzA2MDU3NCIsInJvbGVzIjpbIkNPTU1PTiJdfQ.1FOttE14x3UHym3AQTTEUeTfsSNmhmco4Q7nT0o9sSE","expiresIn":7200},"timestamp":1768357462996}
编辑用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"ldap_editor_1\",    \"password\": \"123456\",    \"loginType\": \"LDAP\"}"
{"code":200,"message":"success","data":{"userId":2,"username":"ldap_editor_1","role":"EDITOR","loginType":"LDAP","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTMyLCJleHAiOjE3NjgzNjQ3MzIsImp0aSI6ImFlM2MxNDgxLTM0MTQtNDJmMy1iYzIyLTNlMDlkMGYwN2Y4OCIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiJdfQ.T9OxD0A4eNdeFlJbLNBf7aYGZ_Ks-gic2jd9eQ3BwfU","expiresIn":7200},"timestamp":1768357532366}
管理用户
curl --location --request POST "http://localhost:7573/auth/login" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"username\": \"ldap_adm_1\",    \"password\": \"123456\",    \"loginType\": \"LDAP\"}"
{"code":200,"message":"success","data":{"userId":3,"username":"ldap_adm_1","role":"ADMIN","loginType":"LDAP","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTc1LCJleHAiOjE3NjgzNjQ3NzUsImp0aSI6ImMxZjE5MGI2LTk3ODItNGY1NC05ZmQxLTQ1ZWRmZWZmMGRkMiIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.R4NlnFkQfmOGOUvmfzQitOcgZ_5D6fS_OTU7xdk3VwA","expiresIn":7200},"timestamp":1768357575198}


商品服务  ---注意token是会过期的，两个小时，你直接用我下面的是不行的

商品查询
curl --location --request GET "http://localhost:7573/product?page=1&size=10" ^
--header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTc1LCJleHAiOjE3NjgzNjQ3NzUsImp0aSI6ImMxZjE5MGI2LTk3ODItNGY1NC05ZmQxLTQ1ZWRmZWZmMGRkMiIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.R4NlnFkQfmOGOUvmfzQitOcgZ_5D6fS_OTU7xdk3VwA" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)"
{"code":200,"message":"success","data":{"total":102,"page":1,"size":10,"records":[{"id":1,"name":"äº§å“1"},{"id":2,"name":"äº§å“2"},{"id":3,"name":"äº§å“3"},{"id":4,"name":"äº§å“4"},{"id":5,"name":"äº§å“5"},{"id":6,"name":"äº§å“6"},{"id":7,"name":"äº§å“7"},{"id":8,"name":"äº§å“8"},{"id":9,"name":"äº§å“9"},{"id":10,"name":"äº§å“10"}]},"timestamp":1768357909428}
商品新增
curl --location --request POST "http://localhost:7573/product" ^
--header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTc1LCJleHAiOjE3NjgzNjQ3NzUsImp0aSI6ImMxZjE5MGI2LTk3ODItNGY1NC05ZmQxLTQ1ZWRmZWZmMGRkMiIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.R4NlnFkQfmOGOUvmfzQitOcgZ_5D6fS_OTU7xdk3VwA" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"name\": \"商品102\"}"
{"code":200,"message":"success","data":{"id":103,"name":"商品102"},"timestamp":1768357966571}
商品修改
curl --location --request PUT "http://localhost:7573/product/101" ^
--header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTc1LCJleHAiOjE3NjgzNjQ3NzUsImp0aSI6ImMxZjE5MGI2LTk3ODItNGY1NC05ZmQxLTQ1ZWRmZWZmMGRkMiIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.R4NlnFkQfmOGOUvmfzQitOcgZ_5D6fS_OTU7xdk3VwA" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
--header "Content-Type: application/json" ^
--data-raw "{    \"name\": \"对大着火状使\"}"
{"code":200,"message":"success","data":{"id":101,"name":"对大着火状使"},"timestamp":1768358002028}
商品删除
curl --location --request DELETE "http://localhost:7573/product/102" ^
--header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibGhiIiwiaWF0IjoxNzY4MzU3NTc1LCJleHAiOjE3NjgzNjQ3NzUsImp0aSI6ImMxZjE5MGI2LTk3ODItNGY1NC05ZmQxLTQ1ZWRmZWZmMGRkMiIsInJvbGVzIjpbIkVESVRPUiIsIkNPTU1PTiIsIkFETUlOIl19.R4NlnFkQfmOGOUvmfzQitOcgZ_5D6fS_OTU7xdk3VwA" ^
--header "User-Agent: Apifox/1.0.0 (https://apifox.com)"
{"code":200,"message":"deleted","data":null,"timestamp":1768358057975}


注意事项
1.执行命令按顺序执行
docker-compose up -d
docker cp H:\Spring-Cloud-Demo\ldap-data\ldap-data.ldif ldap:/tmp/init.ldif
docker exec ldap ldapadd -x -D "cn=admin,dc=example,dc=com" -w admin -f /tmp/init.ldif
2.请确保在docker-compose所在目录下执行命令，请确保项目名是Spring-Cloud-Demo
3.GitHub登录需要修改auth下的配置文件application-docker.yaml
            client-id: 你的
            client-secret: 你的
            redirect-uri: "http://服务器ip:7771/login/oauth2/code/{registrationId}"
并创建oauth2 app


4.若过程中遇到问题无法解决可联系微信号ruyiruyilhb





















