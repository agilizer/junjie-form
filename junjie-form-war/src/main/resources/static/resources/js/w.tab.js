(function($){
    $.fn.tab = function(options){
        var defaults = {
            btnContainer:'btn',
            infoContainer:'index',
            infoList:'info',
            defaultBtn:'0'
        }
        var options = $.extend(defaults,options);
        var _this = this;

        if(this.find('#'+options.btnContainer).children('a').css('float') == 'left' || this.find('#'+options.btnContainer).children('a').css('float') == 'right'){
            this.find('#'+options.btnContainer).append('<div style="clear: both;"></div>');
        }
        this.find('#'+options.btnContainer).children('a').each(function(i){
            $(this).attr('data',i);
        })
        this.find('#'+options.infoContainer).find('.'+options.infoList).each(function(i){
            $(this).attr('data',i);
        })
        this.find('#'+options.btnContainer).children('a').bind('click',function(){
            _this.find('#'+options.btnContainer).children('a').removeClass('active');
            $(this).addClass('active');
            var _click = $(this).attr('data');
            _this.find('#'+options.infoContainer).find('.'+options.infoList).each(function(i){
                if($(this).attr('data') == _click){
                    $(this).show();
                }else{
                    $(this).hide();
                }
            })
        })
        var defaultBtn = function(){
            _this.find('#'+options.btnContainer).children('a').eq(options.defaultBtn).click();
        }
        $(function(){
            defaultBtn();
        })
    }
})(jQuery);