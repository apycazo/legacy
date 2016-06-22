
jQuery(document).ready(function($) {
    console.log("Document ready");

    $('#jqueryMessage').html("JQuery loaded");

    $('#usercombo').change(function() {
        console.log("User selection has changed");
        // $('#updateValue').click();
    });
});

var getUserMessage = function() {
    
    $.ajax({
        url: "/spring_mvc/home/ajax/" + $('#usercombo').val(),
        success: function(result) {
            $("#ajaxMessage").html(result);
        }
    });
};

