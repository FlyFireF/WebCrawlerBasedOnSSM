<%--
  Created by IntelliJ IDEA.
  User: FlyFireF
  Date: 2024-05-15
  Time: 08:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>爬取结果</title>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <link href="statics/css/style.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="statics/js/videos.js"></script>
</head>
<body>
    <h1 align="center">爬取结果</h1>
    <!--视频列表-->
    <div id="item-table" align="center" style="margin-left:50px; margin-right: 50px">
        <table class="table table-hover" style="border: blue 1px solid">
            <thead>
                <tr class="active">
                    <td id="bvid"><b>BV号</b></td>
                    <td id="title"><b>标题</b></td>
                    <td id="uploader"><b>UP主</b></td>
                    <td id="v_view"><b>播放量</b></td>
                    <td id="v_like"><b>点赞数</b></td>
                    <td id="coin"><b>投币数</b></td>
                    <td id="fav"><b>收藏数</b></td>
                    <td id="share"><b>转发量</b></td>
                    <td id="danmaku"><b>弹幕数</b></td>
                    <td id="reply"><b>评论数</b></td>
                    <td id="edit"><b>操作</b></td>
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
    <!-- 详细信息的模态窗口 -->
    <div class="modal fade" id="detailVideoModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 75%;top:20%">
            <div class="modal-content">
                <!--窗口标题-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="detail">详情</h4>
                </div>
                <div class="modal-body" style="margin-left: 50px;margin-right: 50px">
                    <form class="form-horizontal" role="form" id="detail-video-form">
                        <!--每一级div就是一部分内容-->
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">BV号</div>
                                <input class="form-control" type="text" id="bvid-detail" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">标题</div>
                                <input class="form-control" type="text" id="title-detail" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">UP主</div>
                                <input class="form-control" type="text" id="uploader-detail" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">推荐原因</div>
                                <textarea class="form-control" id="rcmd_reason-detail" rows="3" disabled></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">视频简介</div>
                                <textarea class="form-control" id="description-detail" rows="3" disabled></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
