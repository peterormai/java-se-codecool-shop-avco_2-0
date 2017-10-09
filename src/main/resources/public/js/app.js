$(document).ready(function () {

    function filter() {
        var url = '/filter?name=' + $(this).data('name') + '&id=' + $(this).data('id') + '&type=' + $(this).data('type');
        $.get('/filter?name=' + $(this).data('name') + '&id=' + $(this).data('id') + '&type=' + $(this).data('type'),
            function (response) {
                window.history.pushState('', '', url);
                var tempDiv = $('<div>').append($.parseHTML(response));
                var content = $('.container', tempDiv);
                $('#products').replaceWith(content);
                addListeners();
            })
    }

    function addToCart() {
        $.get('/add-to-cart/' + $(this).data('id'), function (response) {
            $('#cartButton').parent().empty().append($.parseHTML(response));
            $('#cartButton').on("click", reviewCart);
        })
    }

    function reviewCart() {
        $.get('/review-cart', function () {
            window.location.href = '/review-cart';
        })
    }

    function addListeners() {
        $('.filter').on('click', filter);
        $('.addToCart-button').on('click', addToCart);
        $('#cartButton').on("click", reviewCart)
    }

    addListeners()

});