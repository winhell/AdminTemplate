/**
 * Created by Administrator on 14-7-5.
 */
var resourceMgr = function(){

    var resourceId;
    return {
        init:function(){
            TableAjax.init();
            $('#assignButton').click(function(){
                var roleids = [];
                $('.roleList').each(function(){
                    if($(this).attr('checked')==="checked")
                        roleids.push($(this).val());
                });
                var idList = roleids.join(",");
                $.getJSON("system/assignRoles.action",{resourceId:resourceId,idList:idList},function(jsonData){
                    bootbox.alert(jsonData.msg);
                    $('#assignRolesDiv').modal('hide');
                })
            });
            $.getJSON('system/getAllRoles.action',function(data){
                $('.checkbox-list').empty();
                $.each(data.data,function(index,item){
                    $('.checkbox-list').append('<label class="col-md-4"><input class="roleList" type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
                });
                Metronic.initUniform();
            });

        },
        assignRoles:function(id){
            resourceId = id;
            $('.roleList').each(function(){
                $(this).attr('checked',false).parent().removeClass('checked');
            });
            $.post('system/getResourceRoles.action',{resourceId:resourceId},function(resText){
                    var ids = resText.split(",");
                    $.each(ids,function(i,v){
                        $('.roleList').filter(function(index){
                            return $(this).val()==v;
                        }).attr('checked',true).parent().addClass('checked');
                    });
            });
            $('#assignRolesDiv').modal('show');
        },
        assignRender:function(data){
            return '<a href="javascript:resourceMgr.assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
        }
    }
}();
resourceMgr.init();