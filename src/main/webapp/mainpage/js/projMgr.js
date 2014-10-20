/**
 * Created by Administrator on 2014/9/29.
 */
var projMgr = function(){

    var uploadType;

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
        $('#gridtable').on('click','.btn',function(){
            var id = $(this).parents('tr').find('input:checkbox').val();
            alert('the id you click is '+id);
        });
    };
    return {
        init:function(){
            $('#theUpload').ajaxForm(uploadOpt);
            TableAjax.init();
            handleLink();
        }
    }
}();
projMgr.init();