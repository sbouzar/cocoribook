(function ($) {
    $(document).ready(function () {

        // search-form 
        $("#wrapper > header .search-btn, .close-search").on("click", function (event) {
            event.preventDefault();
            $("#search-form").toggleClass("closed");
        });


        // header scroll
        if ($(window).width() >= 500) {
            $(window).on("scroll", function () {
                if ($(window).scrollTop() > $("#wrapper > header").data("offset-top")) {
                    $("#wrapper > header").addClass('hidden-header');
                } else {
                    $("#wrapper > header").removeClass('hidden-header');
                }
            });
        }

        // egalize featured-product heights
        $('.products .row, .list-featured').each(function() {
            var max_height = 0;
            $(this).find('.featured').each(function() {
                if($(this).height() > max_height) {
                    max_height = $(this).height();
                }
            });
            $(this).find('.featured').css('height', max_height);
        });
        
        // totop
        $('a[href^="#top"]').click(
            function () {
                var the_id = $(this).attr("href");
                $('html,body').animate({
                    scrollTop: $(the_id).offset().top
                }, 'slow');
                return false;
            }
        );

        $(window).scroll(function () {
            if ($(this).scrollTop() !== 0) {
                $("#totop").fadeIn();
            } else {
                $("#totop").fadeOut();
            }
        });
               
        // tooltip
        $('[data-toggle="tooltip"]').tooltip();
        
        // rating
        $('.rating').on('change', 'input[type="radio"]', function () {
            var current = $(this).val();
            $('.rating input[type="radio"]').each(function() {
                if($(this).val() <= current){
                    $(this).next().removeClass('glyphicon-star-empty').addClass('glyphicon-star');
                } else {
                    $(this).next().removeClass('glyphicon-star').addClass('glyphicon-star-empty');
                }
            });            
        });
        
        // modify Delivery Address
        function getDaInfo() {
            $('.cart-table').removeClass("idChecked");
            $(this).parent().parent().addClass("idChecked");
            var datas = [];
            var ids = [
                "#da-id",
                "#da-lastName",
                "#da-firstName",
                "#da-addressLine1",
                "#da-addressLine2",
                "#da-digicode",
                "#da-zipcode",
                "#da-city",
                "#da-phone",
            ];
            $(".idChecked td, .idChecked td span").each(function (i) {
                var text = $(this).html();
                var reg = new RegExp("<span>(.*)</span>", "g");
                text = text.replace(reg, "");
                datas[i] = text;
                $(ids[i]).val(datas[i]);
            });
            //}
        }


        $(".modifyDelivAddress").on("click", getDaInfo);
        
        
        
    });
})(jQuery);
