window.addEventListener("load", function () {
   $("input").click(function () {
       $.ajax({
           url: "/review-cart",
           method: "PUT",
           data: {
               quantity: $(this).val(),
               id: $(this).attr("id")
           },
           success: function (response) {
                $("#subtotal").text(response);
                console.log(response);
           }
       });
   });
});