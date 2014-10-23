/**
 * Created by Administrator on 14-7-2.
 */
var userMgr = function () {

    var userID;
    var grid;
    var handleLink = function(){
        $('#assignButton').click(function(){
            var roleids = [];
            $('.roleList').each(function(){
                if($(this).attr('checked')==="checked")
                    roleids.push($(this).val());
            });
            var idList = roleids.join(",");
            $.getJSON("system/setUserRoles.action",{userID:userID,idList:idList},function(jsonData){
                bootbox.alert(jsonData.msg);
                $('#assignRolesDiv').modal('hide');
                grid.getDataTable().ajax.reload();
            })
        });
        $('#editLink').on('click',function(){
            $('input:password').val("");
        });
        $('#gridtable').on('click','.changelock',function(){
            var id = $(this).parents('tr').find('input:checkbox').val();
            bootbox.confirm("确定要改变该用户的锁定状态吗？",function(yes){
                    if(yes){
                        $.get('system/changeUserlock.action',{id:id},function(){
                            grid.getDataTable().ajax.reload();
                        });
                    }
            });
        });
    };
    return {
        init:function(){
            TableAjax.init();
            grid = TableAjax.getGrid();
            handleLink();
            $('#additemForm').validate({
                rules:{name:'required',
                    password:'required',
                    repassword:{
                        equalTo:'#password',
                        required:true
                    }
                }
            });
//            $('.changelock').on('click',function(){
//                var id = $(this).parents('tr').find('input:checkbox').val();
//                bootbox.confirm("确定要改变用户"+id+"的锁定状态吗？",function(yes){
//                    if(yes){
//                        $.get('system/changeUserlock.action',{id:id},function(){
//                            grid.getDataTable().ajax.reload();
//                        });
//                    }
//                });
//            });

            $.getJSON('system/getAllRoles.action',function(data){
                $('.checkbox-list').empty();
                $.each(data.data,function(index,item){
                    $('.checkbox-list').append('<label class="col-md-4"><input class="roleList" type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
                });
                Metronic.initUniform();

            });
        },
        assignRoles:function(id){
            userID = id;
            $('.roleList').each(function(){
                $(this).attr('checked',false).parent().removeClass('checked');
            });
            $.post('system/getUserRoles.action',{userID:id},function(resText){
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
            return '<a href="javascript:userMgr.assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
        },
        lockRender:function(data){
            if(data){
                return '<a href="javascript:;" class="btn btn-xs red changelock"><i class="fa fa-lock"></i>已被锁定</a>';
            }else{
                return '<a href="javascript:;" class="btn btn-xs green changelock"><i class="fa fa-unlock"></i>正常</a>';
            }
        }
    }
}();
userMgr.init();