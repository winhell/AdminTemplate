/**
 * Created by Administrator on 14-7-10.
 */
var logMgr = function(){

    var theForm = $('#searchForm');
    var grid ;
    var toggleSearch = function(){
        $('#searchPanel').slideToggle();
        grid.getTableWrapper().slideToggle();
    };

    var handlerButton = function(){
        $('#searchButton').on('click',toggleSearch);
        $('#closeSearchForm').on('click',toggleSearch);
        $('#searchSubmit').on('click',function(){
            var params = theForm.formSerialize();
            var url = "system/logSearch.action?"+params;
            grid.getDataTable().ajax.url(url).load(toggleSearch);
            theForm.resetForm();

        });
    };
    return {
        init:function(){
            $('.date-picker').datepicker({ orientation: "left",
                autoclose: true,language:"zh-CN"});
            TableAjax.init();
            grid = TableAjax.getGrid();
            handlerButton();
        }
    }
}();

logMgr.init();