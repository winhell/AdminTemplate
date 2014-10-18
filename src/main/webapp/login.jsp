<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎登录</title>
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="easyui/themes/metro/easyui.css"/>
</head>

<body>
<div id="loginWin" class="easyui-window" title="管理员登录" style="width:350px;height:240px;padding:5px;"
     minimizable="false" maximizable="false" resizable="false" collapsible="false">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding:5px;background:#fff;">
            <form id="loginForm" method="post" action="j_spring_security_check">
                <div style="padding:5px 0;">
                    <label for="j_username">帐号:</label>
                    <input id="j_username" type="text" name="j_username" style="width:260px;"/>
                </div>
                <div style="padding:5px 0;">
                    <label for="j_password">密码:</label>
                    <input type="password" name="j_password" id="j_password" style="width:260px;"/>
                </div>
                <div style="padding:5px 0;">
                    <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
                    <label for="_spring_security_remember_me">Remember Me?</label>
                </div>
                <div style="padding:5px 0;text-align: center;color: red;" id="showMsg">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
            </form>
        </div>
        <div region="south" border="false" style="text-align:right;padding:5px 0;">
            <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>
            <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
    document.onkeydown = function(e){
        var event = e || window.event;
        var code = event.keyCode || event.which || event.charCode;
        if (code == 13) {
            login();
        }
    }
    $(function(){
        $("input[name='j_username']").focus();
    });
    function cleardata(){
        $('#loginForm').form('clear');
    }
    function login(){
        if($("input[name='j_username']").val()=="" || $("input[name='j_password']").val()==""){
            $("#showMsg").html("用户名或密码为空，请输入");
            $("input[name='j_username']").focus();
        }else{
            //ajax异步提交
            $('#loginForm').submit();
        }
    }
</script>
</html>