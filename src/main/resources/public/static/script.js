window.addEventListener("load", function () {
   $("input").on("change", function () {
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
               console.log($("." + id).text());
               if (quantity == 0) {
                   $("tr#" + id).remove();
               } else {
                   $("." + id).text(response["value"] + " Ft");
               }
               $("#total").text("Total " + response["total"] + " Ft");
           }
       });
   });
});