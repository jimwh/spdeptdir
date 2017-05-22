$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit($("#username").val());
    });
});

function getLevel1(data) {
    var bg = '<tr class="active"><td>';
    var row = "<strong>" + data.directoryName + "</strong>";
    var address = "";
    if (data.address != "") {
        address = "</br>ADDRESS:&nbsp;&nbsp;" + data.address + ";";
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;MAILCODE:&nbsp;&nbsp;" + data.mailCode;
        }else {
            mailCode = "<br/>MAILCODE:&nbsp;&nbsp;" + data.mailCode;
        }
    }else {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;";
        }else {
            mailCode = "<br/>";
        }
    }

    row = bg + row + address + mailCode + data.phoneNumber + "&nbsp;&nbsp;" + data.tieLine + '</td></tr>';
    return row;
}


function fire_ajax_submit(searchTerm) {

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
            $('#feedback').html('Your search for "' + searchTerm + '" returned ' + data.length + ' results.');

            var html = "";

            for (var i = 0; i < data.length; i++) {
                var gap = "";
                var td = "";
                var bg = "";
                var address = "";
                var row = "";
                if (data[i].address != "") {
                    address = "&nbsp;&nbsp;ADDRESS:&nbsp;" + data[i].address + ";";
                }
                var mailCode = "";
                if (data[i].mailCode != "") {
                    mailCode = "&nbsp;&nbsp;MAILCODE:&nbsp;" + data[i].mailCode;
                }

                if (data[i].level == "LEVEL1") {
                    row = getLevel1(data[i]);
                } else if (data[i].level == "LEVEL2") {
                    gap = "&nbsp;&nbsp;&nbsp;" + data[i].directoryName + "<br/>" +
                        "&nbsp;&nbsp;&nbsp;" + address + mailCode +
                        "&nbsp;" + data[i].phoneNumber + "&nbsp;" + data[i].tieLine;
                    bg = '<tr class="info"><td>';
                    row = bg + gap + '</td></tr>';
                } else if (data[i].level == "LEVEL3") {
                    gap = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + data[i].directoryName +
                        "&nbsp;&nbsp;&nbsp;" +
                        address + mailCode +
                        "&nbsp;" + data[i].phoneNumber + "&nbsp;" + data[i].tieLine;
                    bg = '<tr class="success"><td>';
                    row = bg + gap + '</td></tr>';

                } else if (data[i].level == "LEVEL4") {
                    gap = "------------&nbsp;" + data[i].directoryName;
                    bg = '<tr class="warning"><td>';
                    row = bg + gap + '</td></tr>';
                }
                // var row = $('<tr class="active"><td>' + gap + data[i].directoryName + '</td></tr>');
                // var row = $('<tr class="active"><td>' + gap + '</td></tr>');
                // var row = $(bg + gap + '</td></tr>');
                // var row = bg + gap + '</td></tr>';
                // $('#myTable').append(row);
                //$('#myTable').html(row);
                html = html + row;

            }

            $('#myTable').html(html);

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