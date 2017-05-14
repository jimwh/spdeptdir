$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();
    });
});

function fire_ajax_submit() {

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "GET",
        url: "/api/deptdir/search/" + $("#username").val(),
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Search Results</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);

            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Error Message</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });

}