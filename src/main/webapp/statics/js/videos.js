$(function () {
    //加载页面时即查询视频
    queryVideos(1,10)
})
//根据标题查询视频，pN为page_no，页码；pS为page_size，页面显示记录数
function queryVideos(pN,pS) {
    $.ajax({
        url:'queryVideosWithCondition.do',
        data:{
            pageNo:pN,
            pageSize:pS,
        },
        type:'post',
        dataType:'json',
        success:function (data) {
            //拼接html，展示视频信息到表格
            var htmlStr="";
            $.each(data.videos,function (index,obj) {
                htmlStr+="<tr class=\"active\">";
                htmlStr+="<td>"+obj.bvid+"</td>";
                htmlStr+="<td>"+obj.title+"</td>";
                htmlStr+="<td>"+obj.uploader+"</td>";
                htmlStr+="<td>"+obj.v_view+"</td>";
                htmlStr+="<td>"+obj.v_like+"</td>";
                htmlStr+="<td>"+obj.coin+"</td>";
                htmlStr+="<td>"+obj.fav+"</td>";
                htmlStr+="<td>"+obj.share+"</td>";
                htmlStr+="<td>"+obj.danmaku+"</td>";
                htmlStr+="<td>"+obj.reply+"</td>";
                htmlStr+="<td style='text-align: center' ><button onclick='detailVideo("+'"'+obj.bvid+'"'+")' style='color:green'>详情</button><button onclick='viewComments("+'"'+obj.bvid+'"'+")' style='color:blue'>查看评论</button></td>";
                htmlStr+="</tr>";
            });
            $("#tBody").html(htmlStr);
            //分页插件的设置
            $("#demo_pag1").bs_pagination({
                currentPage:pN,//当前页号,相当于pageNo
                rowsPerPage:pS,//每页显示条数,相当于pageSize
                totalRows:data.count,//总条数
                totalPages: Math.ceil(data.count/pS),  //总页数,必填参数.
                visiblePageLinks:10,//最多可以显示的卡片数
                showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
                showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
                showRowsInfo:true,//是否显示记录的信息，默认true--显示
                //用户每次切换页号，都自动触发本函数;每次返回切换页号之后的pageNo和pageSize
                onChangePage: function(event,pageObj) {
                    queryVideos(pageObj.currentPage,pageObj.rowsPerPage);
                }
            });
        }
    })
}
//视频详情按钮的函数
function detailVideo(bvid) {
    $.ajax({
        url:"queryVideoByBvid.do",
        data:{
            bvid:bvid
        },
        type:'post',
        dataType:'json',
        success:function (data) {
            //模态窗口的相关位置显示信息
            $("#bvid-detail").val(bvid)
            $("#title-detail").val(data.title)
            $("#uploader-detail").val(data.uploader)
            $("#rcmd_reason-detail").val(data.rcmd_reason)
            $("#description-detail").val(data.description)
            $("#detailVideoModal").modal('show')
        }
    })
}
//查看评论
function viewComments(bvid) {
    location.href="viewComments/"+bvid;
}