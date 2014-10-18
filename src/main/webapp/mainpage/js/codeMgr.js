/**
 * Created by Administrator on 14-7-14.
 */
var codeMgr = function(){
    var grid;
    var filterHandler = function(){
        $.getJSON("system/getCodeTypeList.action",function(jsonData){
            var list = "<li><a href='javascript:codeMgr.filter(\"all\");'>全部</a> </li>";
            for(var p in jsonData){
                list += "<li><a href='javascript:codeMgr.filter(\""+p+"\");'>"+jsonData[p]+"</a></li>";
            }
            $('#filterList').append(list);
        })
    };
    return{
        init:function(){
//            $('#type').ajaxselect();
            TableAjax.init();
            grid = TableAjax.getGrid();
            filterHandler();
        },
        filter:function(index){
            var url = "system/getAllCode.action?codeIndex="+index;
            grid.getDataTable().ajax.url(url).load();
        }
    }
}();

codeMgr.init();