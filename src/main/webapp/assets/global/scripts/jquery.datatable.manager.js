/**
 * 对填充表格数据以及表格的一些通用操作的封装
 * 依赖JS：assets/plugins/jquery-bootpag/jquery.bootpag.min.js、assets/plugins/data-tables/jquery.dataTables.js
 */
;
(function ($) {
    var tableManager = function () {
        var _ = this;
        var rowsData ;
        /*_.opts=opts;
         _.ele=$ele;*/
        /*_.opts={
         action:"",//取表格数据Action
         param:{//两个基本参数
         page:1,//当前页
         },//action参数
         aoColumns:null,//表格列映射关系
         dataProp:"rows",//返回的集合数据的属性名
         pageProp:"total",//返回的集合总页数的属性名
         bootpag:false,//值要么为false(不显示分页),要不为bootpag选项
         pageTotal:null

         }*/
        _.initializedPager = false;
        _.init = function (ele, opts) {
            _.opts = $.extend({}, $.fn.tableManager.defaults, opts);
            _.ele = ele;
            _.initData();
            return _;
        };
        _.initData = function (opts, flag) {
            //alert("发送请求");
            _.opts = $.extend({}, _.opts, opts);
            if(flag===false){
                _.initializedPager = false;
            }
            $.post(_.opts.action, _.opts.param, function (data) {
                //alert("执行了回调函数");
                if (data.status == "SUCCESS") {
                    if (_.opts.bootpag) {
                        rowsData = data.rows;
                        _.initPage(data);
                    }
                    _.initTable(data);
                }
                else {
                    alert("出现异常：" + data.reason);
                }
            }, "json")

        };
        _.initTable = function (data) {
            //_.ele
            $("#" + _.ele.attr("id")).dataTable({
                "oLanguage": {
                    "sEmptyTable": "未查询到相关记录!"
                },
                "bProcessing": true,
                "bPaginate": false,
                "bLengthChange": false,
                "bDestroy": true,
                "bFilter": false,
                "bSort": false,
                "bInfo": false,
                "bAutoWidth": false,
                "aaData": data[_.opts.dataProp],
                "aoColumns": _.opts.aoColumns,
                "fnInitComplete": function () {
                    Metronic && Metronic.initUniform();
                    $('.dataTables_wrapper').addClass("table-responsive");
                }

            })
        };
        _.getCheckedList = function () {
            var checked = _.ele.find("tbody span.checked");
            var idList = [];
            $.each(checked, function (index, element) {
                var checker = $(element).find(":checked");
                if (checker) {
                    idList.push(checker.val());
                }
            });
            return idList;
        };

        _.getCheckedRows = function(){
            var checkboxes = $(".checkboxes");
            var result = [];
            $.each(checkboxes,function(index,item){
                if($(item).attr("checked")=='checked'){
                    result.push(rowsData[index]);
                }
            });
            return result;
        };

        _.initPage = function (data) {
            var total = Math.ceil(data[_.opts.pageProp] / _.opts.param.rows);
            if (total == _.opts.pageTotal) return;
            _.opts.pageTotal = total;
            _.opts.bootpag.total = total;
            var id = _.ele.attr("id") + "-page-nav";
            var $pageSelector = ($("#" + id).length > 0 && $("#" + id)) || $("<div data-target='#" + _.ele.attr("id") + "' id='" + id + "'>").insertAfter(_.ele);
            /*$pageSelector.off();//清除之前绑定事件，否则会造成重复发送请求*/
            if (!_.initializedPager) {
                _.initializedPager = true;
                $pageSelector.bootpag(_.opts.bootpag).on("page", function (e, num) {
                    var tableManager = $($(e.target).attr("data-target")).data("tableManager");
                    var opts = tableManager.opts;
                    opts.param.page = num;
                    tableManager.initData(opts);
                })
            }
        }
    };
    $.fn.tableManager = function (opts) {
        //var opts=$.extend({},$.fn.tableManager.defaluts,opts);
        return this.each(function () {
            var that = $(this);
            var manager = (new tableManager()).init(that, opts);
            that.data("tableManager", manager);
        })

    };
    $.fn.tableManager.defaults = {
        action: "",//取表格数据Action
        param: {//两个基本参数
            page: 1//当前页
        },//action参数
        aoColumns: null,//表格列映射关系
        dataProp: "rows",//返回的集合数据的属性名
        pageProp: "total",//返回的集合总页数的属性名
        bootpag: false,//值要么为false(不显示分页),要不为bootpag选项
        pageTotal: null
    };
    $(document).on("change", ".group-checkable", function () {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        jQuery(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
                $(this).parent().addClass("checked");
                $(this).parents('tr').addClass("active");
            } else {
                $(this).attr("checked", false);
                $(this).parent().removeClass("checked");
                $(this).parents('tr').removeClass("active");
            }
        });
        jQuery.uniform.update(set);
    }).on("change", "tbody tr .checkboxes", function () {
        $(this).parents('tr').toggleClass("active");
    });
})(jQuery);