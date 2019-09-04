# AtmForServlet

> 利用Tomcat，Servlet，HTML来实现以一个银行系统（B/S模式）

## 结构

- V（HTML，JSP）
- C（Servlet）
- M（service，dao，domain三个层次和jdbc）
- DB（MySql）

## 思路

1. 浏览器发送请求，展示一个登录信息；每个人都一样，静态资源HTML
2. 浏览器发送请求，填写账号和密码；每个人不一样一样，可能是欢迎可能是重新登陆
