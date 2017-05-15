$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit($("#username").val());
    });
});

function fire_ajax_submit( searchTerm ) {

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "GET",
        url: "/api/deptdir/search/" + searchTerm,
        cache: false,
        timeout: 600000,
        success: function (data) {

            /*
            var json = "<h4>Search Results</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);
            */

            var html="";

            for (var i=0; i<data.length; i++) {
                var gap="";
                var td="";
                var bg="";
                var address = "";
                if( data[i].address != "") {
                    address = "&nbsp;&nbsp;ADDRESS:&nbsp;" + data[i].address + ";";
                }
                var mailCode = "";
                if( data[i].mailCode != "") {
                    mailCode = "&nbsp;&nbsp;MAILCODE:&nbsp;" + data[i].mailCode;
                }



                if(data[i].level=="LEVEL1") {
                    gap = "<strong>"+data[i].directoryName+"</strong><br/>" +
                    address + mailCode +
                        "&nbsp;" + data[i].phoneNumber +"&nbsp;" + data[i].tieLine;
                    bg = '<tr class="active"><td>';
                }else if(data[i].level=="LEVEL2") {
                    gap = "&nbsp;&nbsp;&nbsp;" + data[i].directoryName + "<br/>" +
                        "&nbsp;&nbsp;&nbsp;" + address + mailCode +
                    "&nbsp;" + data[i].phoneNumber +"&nbsp;" + data[i].tieLine;
                    bg = '<tr class="info"><td>';

                }else if( data[i].level=="LEVEL3") {
                    gap = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + data[i].directoryName +
                        "&nbsp;&nbsp;&nbsp;" +
                        address + mailCode +
                        "&nbsp;" + data[i].phoneNumber +"&nbsp;" + data[i].tieLine;
                    bg = '<tr class="success"><td>';

                }else if( data[i].level=="LEVEL4") {
                    gap = "------------&nbsp;" + data[i].directoryName;
                    bg = '<tr class="warning"><td>';

                }
                // var row = $('<tr class="active"><td>' + gap + data[i].directoryName + '</td></tr>');
                // var row = $('<tr class="active"><td>' + gap + '</td></tr>');
                var row = $(bg + gap + '</td></tr>');
                $('#myTable').append(row);
                //$('#myTable').html(row);
                // html = html + row;

            }

            // $('#myTable').html(html);

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