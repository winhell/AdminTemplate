/**
 * Created by Administrator on 14-7-7.
 */
/**
 * Created by Administrator on 14-6-26.
 */
var databaseUtil = function(){

    var reloadForm = function(data){

        if(data.status=='SUCCESS'){
            Metronic.alert({message:data.msg,icon:"check",closeInSeconds:5});
            $('#additemForm').resetForm();
            $('.ajaxselect').select2('val','');
            $("#gridtable").data("tableManager").initData();
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

    var opts = {
        param: {
            page: 1,
            rows: 10
        },
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 10
        }
    };
    var theForm = $('#additemForm');
    var funcPrefix = theForm.data('func');
    var path = theForm.data("path");

    var initTable = function(){
        var datatables = $('.datatable');
        datatables.each(function(){
            var datatable = $(this);
            opts.action = datatable.data('url');
            var fields = datatable.find('th');
            var aoColumns = [];
            fields.each(function(){
                var fieldOpt =  eval("({"+$(this).data('options')+"})");
                aoColumns.push(fieldOpt);
            });
            opts.aoColumns = aoColumns;
            datatable.tableManager(opts);
        })
    };

    var bindButtons = function(){
        $('#addLink').on('click',function(){                 //添加记录
            formOptions.url=path+'/add'+funcPrefix+'.action';
            $('#formTitle').html('增加项目');
            $('#additemFormDiv').modal('show');
        });

        $('#editLink').on('click',function(){               //编辑记录
            var rows = $('#gridtable').data('tableManager').getCheckedRows();
            if(rows.length!=1){
                bootbox.alert('请选取一条记录进行修改！');
                return;
            }

            $('#formTitle').html('项目修改');
            //装入表单数据
            var rowData = rows[0];
            for(var item in rowData){
                $('#'+item).val(rowData[item]).trigger('change');
            }

            formOptions.url=path+'/update'+funcPrefix+'.action?id='+rowData.id;
            $('#additemFormDiv').modal('show');
        });

        $('#deleteLink').on('click',function(){            //删除记录
            var delUrl = path+'/delete'+funcPrefix+'.action';
            var ids = $('#gridtable').data('tableManager').getCheckedList();
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

    var handleSearch = function() {

        var searchButton = $('#findButton');
        var searchIcon = $('.search-btn');
        if(searchButton===undefined){
            return;
        }
        searchButton.on('click',function(){
            var searchOpts = eval("({"+$(this).data('options')+"})");
            console.log(searchOpts);
            var searchUrl = path + '/search' + funcPrefix + '.action?field='+searchOpts.searchField+"&text="+encodeURI(encodeURI($('#searchText').val()));
            $('#gridtable').data('tableManager').initData({action:searchUrl},false);
            $('.search-box').slideUp();
        });

        searchIcon.click(function () {
            if($(this).hasClass('show-search-icon')){
                if ($(window).width()>767) {
                    $('.search-box').fadeOut(300);
                } else {
                    $('.search-box').fadeOut(0);
                }
                $('.search-btn').removeClass('show-search-icon');
            } else {
                if ($(window).width()>767) {
                    $('.search-box').fadeIn(300);
                } else {
                    $('.search-box').fadeIn(0);
                }
                $('.search-btn').addClass('show-search-icon');
            }
        });

        // close search box on body click
        if(searchIcon.size() != 0) {
            $('.search-box, .search-btn').on('click', function(e){
                e.stopPropagation();
            });

            $('body').on('click', function() {
                if (searchIcon.hasClass('show-search-icon')) {
                    searchIcon.removeClass("show-search-icon");
                    $('.search-box').fadeOut(300);
                }
            });
        }
    };

    var handleTemplate = function(){
        var selects = $('.ajaxselect');
        $.each(selects,function(){
            $(this).ajaxselect();
        });
    };
    return {
        init:function(){

            $('#additemForm').ajaxForm(formOptions);
            initTable();
            bindButtons();
            handleSearch();
            handleTemplate();
        },
        checkboxRender:function (data, type, full) {
            return "<input class='checkboxes' type='checkbox' value='" + data + "'/>";
        }
    };
}();

databaseUtil.init();