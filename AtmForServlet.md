# AtmForServlet

> 利用Tomcat，Servlet，HTML来实现以一个银行系统（B/S模式）

## 结构

- V（HTML，JSP）
- C（Servlet）
- M（service，dao，domain三个层次和jdbc）
- DB（MySql）

## 思路

1. 浏览器发送请求，展示一个登录信息；每个人都一样，静态资源HTML（登录页面）
2. 用户点击登录按钮，浏览器发送请求，填写账号和密码；每个人不一定一样，如果用户输入的账号密码是正确的就会进入欢迎界面，否则要重新输入

> 一个小知识点的补充：发送请求的时候，如果只写工程名，不写资源名；Tomcat需要参考web.xml文件（首先参考的是当前工程的web文件夹下WEB-INF文件夹内的web.xml，发现没有相关配置，然后去Tomcat文件夹下conf文件夹中的web.xml查看）下面5行代码：
```
    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.htm</welcome-file>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    
//welcome-file-list是请求的默认列表，如果没有发送任何的请求资源名，就会依次找寻中间三行显示的文件
//找到了就返回这个文件的信息，否则404    
```

