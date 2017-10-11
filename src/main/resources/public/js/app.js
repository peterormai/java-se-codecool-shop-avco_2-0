$(document).ready(function () {

    function filter() {
        var url = '/filter?name=' + $(this).data('name') + '&id=' + $(this).data('id') + '&type=' + $(this).data('type');
        $.get(url,
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
        })
    }


    function changeItemQuantity() {
        var id = $(this).attr("id");
        var quantity = $(this).val();
        $.ajax({
            url: "/review-cart",
            method: "PUT",
            dataType: "json",
            data: {
                quantity: quantity,
                id: id
            },
            success: function (response) {
                if (quantity === "0") {
                    $("tr#" + id).remove();
                    if ($('input').val() === undefined) {
                        $.get('/review-cart', function(resp) {
                            var tempDiv = $('<div>').append($.parseHTML(resp));
                            var content = $('.container', tempDiv);
                            $('.container').replaceWith(content);
                        })
                    }
                } else {
                    $("." + id).text("$" + response["value"].toLocaleString(undefined, {maximumFractionDigits:2}) + ".00");
                }
                $("#total").text("Total $" + response["total"].toLocaleString(undefined, {maximumFractionDigits:2}) + ".00");
            }
        });
    }

    function removeItem() {
        $.get('/review-cart?id=' + $(this).data('id'), function (response) {
            var tempDiv = $('<div>').append($.parseHTML(response));
            var content = $('.container', tempDiv);
            $('.container').replaceWith(content);
            addListeners();
        })
    }


    function addListeners() {
        $('.filter').on('click', filter);
        $('.addToCart-button').on('click', addToCart);
        $("input").on("change", changeItemQuantity);
        $('.removeButton').click(removeItem);
    }

    addListeners()

});
