<%--
  Created by IntelliJ IDEA.
  User: FlyFireF
  Date: 2024-05-15
  Time: 08:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>哔哩哔哩每周必看抓取系统</title>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="statics/js/index.js"></script>
</head>
<body>
    <div align="center">
        <h1>欢迎来到哔哩哔哩每周必看抓取系统！</h1>
        <button id="get_all_weekly_num">获取每周必看总数</button>每周必看总数: <span id="all_weekly_num"></span><br>
        请输入爬取每周必看的页数：<input type="text" id="page_count" name="page_count"><br>
        <label for="is_reverse"><input type="checkbox" id="is_reverse" name="is_reverse">倒序爬取（需先获取总数）</label>
        <button id="start_crawl">开爬！</button><br>
        <a href="showVideos">查看爬取结果</a>
    </div>
</body>
</html>
