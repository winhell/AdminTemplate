var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                j_username: {
	                    required: true
	                },
	                j_password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                j_username: {
	                    required: "请输入用户名！"
	                },
	                j_password: {
	                    required: "请输入密码！"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    $('.login-form').submit();
	                }
	                return false;
	            }
	        });
	};

    var getParameter = function(name){
        var r = new RegExp("(\\?|#|&)"+name+"=([^&#]*)(&|#|$)");
        var m = location.href.match(r);
        //if ((!m || m=="") && !cancelBubble) m = top.location.href.match(r);
        return (!m?"":m[2]);
    };

    return {
        //main function to initiate the module
        init: function () {

            if(getParameter("error")=="true"){
                $.getJSON("getLoginError.action",function(jsonData){

                    $('.alert-danger', $('.login-form')).show().find('span').html(jsonData.msg);

                })
            }

            handleLogin();
	       
	       	$.backstretch([
		        "../../assets/admin/pages/media/bg/1.jpg",
    		    "../../assets/admin/pages/media/bg/2.jpg",
    		    "../../assets/admin/pages/media/bg/3.jpg",
    		    "../../assets/admin/pages/media/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		    });
        }

    };

}();