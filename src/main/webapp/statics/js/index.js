$(function () {
    //获取每周必看总数
    $("#get_all_weekly_num").click(function (){
        $.ajax({
            url:'getAllWeeklyNum.do',
            type:'get',
            dataType:'json',
            success:function (data){
                $("#all_weekly_num").text(data)
            }
        })
    })
    //开始爬取
    $("#start_crawl").click(function (){
        //收集参数
        var pages=$("#page_count").val()//爬取页数
        var isReverse=$("#is_reverse").prop("checked")//是否倒序
        var allPages=$("#all_weekly_num").text()//每周必看总页数
        $.ajax({
            url:'startCrawl.do',
            data:{
                pages:pages,
                isReverse:isReverse,
                allPages:allPages
            },
            type:'post',
            dataType:'json',
            success:function (data){
                alert("后台正在爬取数据中……")
            }
        })
    })
});