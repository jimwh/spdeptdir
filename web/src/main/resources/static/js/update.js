var url = "/amend/";
var editModalTarget = url + "loadEntity/";
var tableTarget = url + "loadDeptDirectory/";

function showEditModal(index) {
    var editUrl = editModalTarget + index;
    loadEntity(editUrl);
}

function showDeleteModal(index) {
    $('#delete-id').val(index);
}

function loadEntity(url) {
    $.getJSON(url, {}, function (data) {
        populateModal(data, names);
    });
}

function populateModal(data) {
    $('#update-id').val(data.id);
    $('#update-name').val(data.directoryName);
    $('#update-address').val(data.address);
    $('#update-mailCode').val(data.mailCode);
    $('#update-phoneNumber').val(data.phoneNumber);
    $('#update-tieLine').val(data.tieLine);
    $('#update-parent').val(data.parent);
    $('#update-level').val(data.level);
}

function clearModal() {
    $('#update-id').val('');
    $('#update-name').val('');
    $('#update-address').val('');
    $('#update-maildCode').val('');
    $('#update-phoneNumber').val('');
    $('#update-tieLine').val('');
    $('#update-parent').val('');
    $('#update-level').val('');
}

function closeModal(name) {
    $(name).modal('toggle');
}

function clearAndCloseModal(name) {
    clearModal();
    closeModal(name);
}

function postEdit() {
    var prod = $('#edit-form').serialize();
    var editUrl = url + 'update';
    $.post(editUrl, prod, function (data) {
        updateTable(data);
    });
    clearAndCloseModal('#umodal');
}

function deleteEntity(entity) {
    var input = $('#delete-id');
    var url = '/' + entity + '/delete/' + input.val();
    $.post(url, function (data) {
        updateTable(data);
    });
    closeModal('#dmodal');
    input.val('');
}

function updateTable(data) {
    $.ajax({
        dataType: "json",
        url: tableTarget,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        success: function (response) {
            $('#table-body').empty();
            $.each(response, function (i, e) {
                var end = e.id + ");'";
                var edit = "'showEditModal(" + end;
                var del = "'showDeleteModal(" + end;
                var row = $('<tr>').append(
                    $('<td>').text(e.id),
                    $('<td>').text(e.directoryName),
                    $('<td>').text(e.address),
                    $('<td>').text(e.mailCode),
                    $('<td>').text(e.phoneNumber),
                    $('<td>').text(e.tieLine),
                    $('<td>').text(e.parent),
                    $('<td>').text(e.level),

                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#umodal' onclick=" +
                        edit + ">Edit</a>"
                    ),
                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#dmodal' onclick=" +
                        del + ">Delete</a>"
                    )
                );
                $('#something-table').append(row);
            });
        }
    });
}
