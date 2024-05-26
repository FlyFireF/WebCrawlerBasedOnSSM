$(function () {
    var bvid=$("#bvid").val()
    queryReplies(1,10,bvid)
    //开始爬取
    $("#get_comments").click(function (){
        $.ajax({
            url:'getComments.do',
            data:{
                bvid:bvid,
                pages:$("#pages").val(),
            },
            type:'post',
            dataType:'json',
            success:function (data){
                alert("后台正在爬取评论中……")
            }
        })
    })
})
//根据bvid查询评论信息
function queryReplies(pN,pS,bvid) {
    $.ajax({
        url:'queryRepliesWithCondition.do',
        data:{
            pageNo:pN,
            pageSize:pS,
            bvid:bvid
        },
        type:'post',
        dataType:'json',
        success:function (data) {
            var htmlStr="";
            $.each(data.replyList,function (index,obj) {
                //拼接html显示数据
                htmlStr+="<tr class=\"active\">";
                htmlStr+="<td>"+obj.uname+"</td>";
                htmlStr+="<td>"+obj.content+"</td>";
                htmlStr+="<td>"+obj.r_like+"</td>";
                htmlStr+="<td>"+timestampToTime(obj.ctime)+"</td>";
                htmlStr+="</tr>";
            });
            $("#tBody").html(htmlStr);
            //分页插件设置
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
                    queryReplies(pageObj.currentPage,pageObj.rowsPerPage,bvid);
                }
            });
        }
    })
}
//一个工具函数，将时间戳转换为格式化时间
function timestampToTime(timestamp) {
    timestamp = timestamp ? timestamp : null;
    let date = new Date(timestamp*1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
    let h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    let m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
    return Y + M + D + h + m + s;
}