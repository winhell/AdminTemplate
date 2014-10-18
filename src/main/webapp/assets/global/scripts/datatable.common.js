/**
 * Created by Administrator on 2014/10/11.
 */
var TableAjax = function () {
    var theForm = $('#additemForm');
    var funcPrefix = theForm.data('func');
    var path = theForm.data("path");
    var reloadForm = function(data){

        if(data.status=='SUCCESS'){
            Metronic.alert({message:data.msg,icon:"check",closeInSeconds:5});
            $('#additemForm').resetForm();
            $('.ajaxselect').select2('val','');
            grid.getDataTable().ajax.reload();
            $('#additemFormDiv').modal('hide');
        }else{
            Metronic.alert({message:data.msg,icon:"warning",type:'danger'});
            $('#additemFormDiv').modal('hide');
        }

    };

    var formOptions = {
        target:'#additemForm',
        dataType:'json',
        success:reloadForm
    };
    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: Metronic.isRTL(),
            autoclose: true
        });
    };

    var grid;
    var handleRecords = function () {

        grid = new Datatable();
        var gridtable = $("#gridtable");
        var gridUrl = gridtable.data('url');
        var fields = gridtable.find('th');
        var columns = [];
        fields.each(function(){
            var fieldOpt =  eval("({"+$(this).data('options')+"})");
            columns.push(fieldOpt);
        });
        grid.init({
            src:gridtable ,
            onSuccess: function (grid) {
                // execute some code after table records loaded
            },
            onError: function (grid) {
                // execute some code on network or other general error
            },
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options
                "lengthMenu": [
                    [10, 20, 50, 100, 150, -1],
                    [10, 20, 50, 100, 150, "All"] // change per page values here
                ],
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": gridUrl===undefined?'empty.json':gridUrl// ajax source
                },
                columns:columns
            }
        });
        // handle group actionsubmit button click
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
            e.preventDefault();
            var action = $(".table-group-action-input", grid.getTableWrapper());
            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
                grid.setAjaxParam("customActionType", "group_action");
                grid.setAjaxParam("customActionName", action.val());
                grid.setAjaxParam("id", grid.getSelectedRows());
                grid.getDataTable().ajax.reload();
                grid.clearAjaxParams();
            } else if (action.val() == "") {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'Please select an action',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            } else if (grid.getSelectedRowsCount() === 0) {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'No record selected',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            }
        });
    };

    var bindButtons = function(){
        $('#addLink').on('click',function(){                 //添加记录
            formOptions.url=path+'/add'+funcPrefix+'.action';
            $('#formTitle').html('增加项目');
            $('#additemFormDiv').modal('show');
        });

        $('#editLink').on('click',function(){               //编辑记录

            if(grid.getSelectedRowsCount()!=1){
                bootbox.alert('请选取单条记录进行修改！');
                return;
            }

            $('#formTitle').html('项目修改');
            //装入表单数据
            var rowData = grid.getSelectedRowData();
            for(var item in rowData){
                $('#'+item).val(rowData[item]).trigger('change');
            }

            formOptions.url=path+'/update'+funcPrefix+'.action?id='+rowData.id;
            $('#additemFormDiv').modal('show');
        });

        $('#deleteLink').on('click',function(){            //删除记录
            var delUrl = path+'/delete'+funcPrefix+'.action';
            var ids = grid.getSelectedRows();
            if(ids.length<1){
                bootbox.alert('请选取所要删除的记录！');
                return;
            }
            var idList = ids.join(",");

            bootbox.confirm("你确定要删除所选记录吗？",function(result){
                if(result){
                    $.getJSON(delUrl,{idList:idList},reloadForm);
                    console.log('record delete...');
                }
            });
        });
    };

    var handleTemplate = function(){
        var selects = $('.ajaxselect');
        $.each(selects,function(){
            $(this).ajaxselect();
        });
        grid.getTableWrapper().find('select').select2();
    };

    return {

        //main function to initiate the module
        init: function () {

            initPickers();
            handleRecords();
            bindButtons();
            handleTemplate();
        },
        getGrid:function(){
            return grid;
        }

    };

}();