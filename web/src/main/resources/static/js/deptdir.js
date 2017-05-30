$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit($("#username").val());
    });
});

function getLevel1(data) {
    var bg = '<tr class="active"><td>';
    var row = "<strong>" + data.name + "</strong>";
    var address = "";
    if (data.address != "") {
        address = "</br>Address:&nbsp;&nbsp;" + data.address;
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
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
    var phone="";
    if( data.phoneNumber != "") {
        phone = "&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+data.phoneNumber + '&nbsp;&nbsp;';
    }
    var tieLine="";
    if( data.tieLine != "") {
        tieLine = "Tie Line: &nbsp;&nbsp;"+data.tieLine;
    }

    row = bg + row + address + mailCode + phone + '&nbsp;&nbsp;' + tieLine + '</td></tr>';
    return row;
}

function getLevel2(data) {
    var bg = '<tr class="info"><td>';
    // var row = "<strong>" + data.directoryName + "</strong>";
    var row = '&nbsp;&nbsp;' + data.name;

    var address = "";
    if (data.address != "") {
        address = "</br>&nbsp;&nbsp;Address:&nbsp;&nbsp;" + data.address;
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }else {
            mailCode = "<br/>&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }
    }else {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;";
        }else {
            mailCode = "<br/>";
        }
    }
    var phone="";
    if( data.phoneNumber != "") {
        phone = "&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+data.phoneNumber + '&nbsp;&nbsp;';
    }
    var tieLine="";
    if( data.tieLine != "") {
        tieLine = "Tie Line: &nbsp;&nbsp;"+data.tieLine;
    }

    row = bg + row + address + mailCode + phone + '&nbsp;&nbsp;' + tieLine + '</td></tr>';
    return row;
}

function getLevel3(data) {

    var bg = '<tr class="success"><td>';

    var row = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data.name;

    var address = "";
    if (data.address != "") {
        address = "</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address:&nbsp;&nbsp;" + data.address;
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }else {
            mailCode = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }
    }else {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }else {
            mailCode = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }
    }
    var phone="";
    if( data.phoneNumber != "") {
        phone = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+data.phoneNumber + '&nbsp;&nbsp;';
    }
    var tieLine="";
    if( data.tieLine != "") {
        tieLine = "Tie Line: &nbsp;&nbsp;"+data.tieLine;
    }

    row = bg + row + address + mailCode + phone + '&nbsp;&nbsp;' + tieLine + '</td></tr>';
    return row;
}

function getLevel4(data) {

    var bg = '<tr class="warning"><td>';
    var row = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data.name;

    var address = "";
    if (data.address != "") {
        address = "</br>&nbsp;&nbsp;Address:&nbsp;&nbsp;" + data.address;
    }
    var mailCode = "";
    if (data.mailCode != "") {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }else {
            mailCode = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail Code:&nbsp;&nbsp;" + data.mailCode;
        }
    }else {
        if(address!="") {
            mailCode = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }else {
            mailCode = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }
    }
    var phone="";
    if( data.phoneNumber != "") {
        phone = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Phone: &nbsp;&nbsp;"+data.phoneNumber + '&nbsp;&nbsp;';
    }
    var tieLine="";
    if( data.tieLine != "") {
        tieLine = "Tie Line: &nbsp;&nbsp;"+data.tieLine;
    }

    row = bg + row + address + mailCode + phone + '&nbsp;&nbsp;' + tieLine + '</td></tr>';
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
            $('#feedback').html('Your search for <strong>"' + searchTerm + '"</strong> returned ' + data.length + ' results.');

            var html = "";

            for (var i = 0; i < data.length; i++) {
                var row = "";

                if (data[i].level == "LEVEL1") {
                    row = getLevel1(data[i]);
                } else if (data[i].level == "LEVEL2") {
                    row = getLevel2(data[i]);
                } else if (data[i].level == "LEVEL3") {
                    row = getLevel3(data[i]);
                } else if (data[i].level == "LEVEL4") {
                    row = getLevel4(data[i]);
                }
                // $('#myTable').append(row);
                // $('#myTable').html(row);
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