<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${base}/static/plugins/layui/css/layui.css" media="all" />
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<div style="margin-bottom: 5px;">

    <!-- 示例-970 -->
    <#--<ins class="adsbygoogle" style="display:inline-block;width:970px;height:90px" data-ad-client="ca-pub-6111334333458862" data-ad-slot="3820120620"></ins>-->

    <span class="fr">
        <div class="layui-form-pane">
            <span class="layui-form-label">时间范围：</span>
            <div class="layui-input-inline">
                <#--  -->
                    <input type="text" autocomplete="off" name="startTime" id="startTime" lay-verify="date" placeholder="开始时间"  class="layui-input" />
            </div>
            ~
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" name="endTime" id="endTime" lay-verify="date" placeholder="结束时间"  class="layui-input" />
            </div>
            <button class="layui-btn mgl-20" id="btn-search">
                <i class="layui-icon">&#xe615;</i>&nbsp;查询
            </button>
        </div>
    </span>
</div>

<div class="layui-btn-group demoTable">
    <button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
    <button class="layui-btn" data-type="getCheckLength">获取选中数目</button>
    <button class="layui-btn" data-type="isAll">验证是否全选</button>
    <button class="layui-btn" data-type="export">导出EXCEL</button>
</div>

<table class="layui-table" lay-data="{ url:'${base}/test/page2', page:true, id:'idTest',limit:5,limits:[5,10,20,50]}" lay-filter="demo">
    <thead>
    <tr>
        <th lay-data="{checkbox:true, fixed: true}"></th>
        <th lay-data="{field:'id', width:80, sort: true, fixed: true}">ID</th>
        <th lay-data="{field:'userId', width:100}">用户ID</th>
        <th lay-data="{field:'loginTime', width:200, sort: true}">登录时间</th>
        <th lay-data="{field:'loginIp', width:200}">登录IP</th>
        <th lay-data="{field:'loginType', width:100,templet: '#loginType'}">登录方式</th>
        <th lay-data="{field:'loginDesc', width:250, sort: true,event: 'setSign',style:'cursor: pointer;'}"}">备注</th>

        <th lay-data="{fixed: 'right', width:160, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<script type="text/html" id="loginType">
    {{#  if(d.loginType === 1){ }}
    <span style="color: #F581B1;">WEB</span>
    {{#  } else { }}
     APP
    {{#  } }}
</script>


<script type="text/javascript" src="${base}/static/plugins/layui/layui.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['table','laydate'], function(){
        var table = layui.table;
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startTime'
            //,type: 'date' //默认，可不填
        });
        laydate.render({
            elem: '#endTime'
            //,type: 'date' //默认，可不填
        });
        //监听表格复选框选择
        table.on('checkbox(demo)', function(obj){
            console.log(obj)
        });
        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.alert('编辑行：<br>'+ JSON.stringify(data))
            }

            if(obj.event === 'setSign'){
                layer.prompt({
                    formType: 2
                    ,title: '修改 ID 为 ['+ data.id +'] 的用户签名'
                    ,value: data.loginDesc
                }, function(value, index) {
                    layer.close(index);

                    //这里一般是发送修改的Ajax请求

                    //同步更新表格和缓存对应的值
                    obj.update({
                        loginDesc: value
                    });
                });
            }
        });

        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('idTest')
                        ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
            ,getCheckLength: function(){ //获取选中数目
                var checkStatus = table.checkStatus('idTest')
                        ,data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
            }
            ,isAll: function(){ //验证是否全选
                var checkStatus = table.checkStatus('idTest');
                layer.msg(checkStatus.isAll ? '全选': '未全选')
            }
            ,export:function(){
                layer.msg("导出文件");
                window.location.href = '${base}/test/exportExcel';
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $(document).on('click','#btn-search', function(){
            var startTime = $.trim($("#startTime").val());

            var endTime = $.trim($("#endTime").val());

            if (null != startTime && null != endTime) {
                if (startTime > endTime) {
                    layer.msg('开始时间必须小于结束时间！！', {icon: 5});
                    return false;
                }
            }
            //搜索条件
            table.reload('idTest', {
                where: {
                    startTime: startTime,
                    endTime:endTime
                }
            });
        });
    });

   /* $(document).on('click','#btn-search', function(){
        var startTime = $.trim($("#startTime").val());

        var endTime = $.trim($("#endTime").val());

        if (null != startTime && null != endTime) {
            if (startTime > endTime) {
                layer.msg('开始时间必须小于结束时间！！', {icon: 5});
                return false;
            }
        }
        //搜索条件
        table.reload('idTest', {
            where: {
                startTime: startTime,
                endTime:endTime
            }
        });
    });*/

    /**
     * 开始你的表演
     */
    function action(){

    }
</script>

</body>
</html>