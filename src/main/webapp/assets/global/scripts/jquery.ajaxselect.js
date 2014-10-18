/**
 * Created by Administrator on 14-7-2.
 */
;
(function($){
    var defaultSetting = {
        valueField:'id',
        textField:'name',
        defaultText:'',
        isMap:false
    };
//    var setting;
    var methods={
        init:function(options){
            return this.each(function(){
                var $this = $(this);
                var setting = {};
                $this.empty();
                var thisOptions;
                if($this.data('options')===undefined)
                    setting =  $.extend({},defaultSetting,options);
                else {
                    var htmlsetting = eval("({" + $this.data('options') + "})");
                    setting = $.extend({},defaultSetting, htmlsetting, options);
                }
                if(setting.url===undefined){
                    console.error('ajaxselect must has url option!');
                    return;
                }
                thisOptions = "<option></option>";
                $.getJSON(setting.url,function(jsondata){
                    if(setting.isMap){       //以Map方式传输数据
                        for(var p in jsondata){
                            thisOptions += "<option value='"+p+"'>"+jsondata[p]+"</option>";
                        }
                    }else{
                        $.each(jsondata,function(index,item){
//                options.push("<option value='",item[opts.valueField],"'>",item[opts.textField],"</option>");
                            thisOptions += "<option value='"+item[setting.valueField]+"'>"+item[setting.textField]+"</option>";
                        });
                    }
                    $this.append(thisOptions);
                    $this.select2({
                        placeholder:"请选择"+setting.defaultText
                    });
                });
            })
        }
    };

    $.fn.ajaxselect = function(){
        var method = arguments[0];
        var args;
        if (methods[method]) {
            method = methods[method];
            args = Array.prototype.slice.call(arguments, 1);
        } else if (typeof method === "object" || !method ) {
            method = methods.init;
            args = Array.prototype.slice.call(arguments, 0);
        } else {
            $.error("Method" + method + "does not exist on jQuery.ajaxselect");
            return this;
        }
        return method.apply(this, args);
    };
})(jQuery);