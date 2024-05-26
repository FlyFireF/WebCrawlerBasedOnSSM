<%--
  Created by IntelliJ IDEA.
  User: FlyFireF
  Date: 2024-05-26
  Time: 08:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>查看评论</title>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <link href="statics/css/style.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="statics/js/reply.js"></script>
</head>
<body>
    <h1 align="center">视频评论</h1>
    <!--手动获取评论-->
    <div align="center">
        <input type="text" name="bvid" id="bvid" value="${bvid}" hidden/>
        获取评论页数：<input type="text" name="pages" id="pages"/>
        <button id="get_comments">获取评论</button>
    </div>
    <!--评论列表-->
    <div id="item-table" align="center" style="margin-left:50px; margin-right: 50px">
        <table class="table table-hover" style="border: blue 1px solid">
            <thead>
                <tr class="active">
                    <td id="uname"><b>用户名</b></td>
                    <td id="content"><b>评论内容</b></td>
                    <td id="r_like"><b>点赞数</b></td>
                    <td id="ctime"><b>评论时间</b></td>
                </tr>
            </thead>
            <tbody id="tBody">
                <!--这里使用js代码动态追加数据-->
            </tbody>
        </table>
    </div>
    <!--翻页插件-->
    <div style="text-align: center;margin-left:50px; margin-right: 50px">
        <div id="demo_pag1" class="pagination-sm"></div>
    </div>
</body>
</html>
