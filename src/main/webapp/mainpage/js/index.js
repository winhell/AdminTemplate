var Index = function () {

    var initMenu = function(){
        $.ajax({
            url : "system/getUserMenus.action?="+Math.floor(Math.random() * (new Date()).getTime()),
            type : "POST",
            dataType : "json",
            success : function(_menus) {
                var menulist = "";
                $.each(_menus,function(i, n) {
                    if(n.parentId=='0'){
                        var iconHtml = "";
                        if(n.icon!=""){
                           iconHtml = "<i class='fa fa-"+ n.icon+"'></i>";
                        }
                        menulist += '<li>' + '<a href="javascript:;">'+iconHtml
                            +'<span class="title">'+ n.name + '</span> '
                            +'<span class="selected"></span> '
                            +'<span class="arrow"></span> '
                            +'</a> ';
                        menulist += '<ul class="sub-menu">';
                        $.each(_menus,function(index,item){
                            if(item.parentId==n.id){
                                var subIconHtml = "";
                                if(item.icon!=""){
                                    subIconHtml = "<i class='fa fa-"+ item.icon+"'></i>";
                                }
                                menulist += '<li><a class="ajaxify" href="'+ item.url + '">'+subIconHtml
                                    + item.name + '</a> </li>'
                            }
                        });
                        menulist += '</ul></li>';
                    }
                });
                $(".page-sidebar-menu").append(menulist);
            }
        });
    };
    return {

        //main function
        init: function () {
            initMenu();
            $.get('getLoginUser.action?='+Math.floor(Math.random() * (new Date()).getTime()),function(resText){
                $('.username').html(resText);
            });
        }
    }
}();