/**
 * Created by Administrator on 2014/9/29.
 */
var projMgr = function(){

    var uploadType;

    var filterHandler = function(){
        $.getJSON("system/getCodeList.action?type=process",function(jsonData){
            var list = "<li><a href='javascript:projMgr.filter(0);'>全部</a> </li>";
            $.each(jsonData,function(index,item){
                list += "<li><a href='javascript:projMgr.filter("+ item.code +");'>"+ item.name +"</a></li>";
            });
            $('#filterList').append(list);
        })
    };

    var uploadOpt = {
        target:'#theUpload',
        dataType:'json',
        success:function(data){
            $('#uploadForm').slideToggle();
            $('.fileinput').fileinput('clear');
            if(data.status=="FAIL"){
                    Metronic.alert({
                        message:data.msg,
                        type:'danger',
                        icon:'warning',
                        container:'.portlet-body',
                        place:'prepend'
                    });
                return;
            }
            if(uploadType==="excel"){
                TableAjax.getGrid().getDataTable().ajax.reload();       
            }
        }
    };

    var handleLink = function(){
        $('#importExcel').on('click',function(){
            $('.Metronic-alerts').remove();
            $('#uploadForm').slideToggle();
            uploadOpt.url = 'projmgr/uploadExcel.action';
            uploadType = 'excel';
        });
        $('#importZip').on('click',function(){
            $('.Metronic-alerts').remove();
            uploadOpt.url = 'projmgr/uploadZip.action';
            $('#uploadForm').slideToggle();
            uploadType = 'zip';
        });

//     查找该行id
//        $('#gridtable').on('click','.btn',function(){
//            var id = $(this).parents('tr').find('input:checkbox').val();
//            alert('the id you click is '+id);
//        });
        $('#cancelUpload').on('click',function(){
            $('#uploadForm').slideToggle();
        })
    };
    return {
        init:function(){
            $('#theUpload').ajaxForm(uploadOpt);
            TableAjax.init();
            handleLink();
            filterHandler();
        },
        filter:function(index){
            var url = "projmgr/clientList.action?codeIndex="+index;
            TableAjax.getGrid().getDataTable().ajax.url(url).load();
        }
    }
}();
function downloadUrl(data, type, full){
    return '<a href="upload/doc/' +data+ '.doc" class="btn btn-xs default"><i class="fa fa-search"></i>查看论文</a>';
}
projMgr.init();