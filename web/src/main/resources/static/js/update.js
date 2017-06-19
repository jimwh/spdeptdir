var url = "/amend/";
var editModalTarget = "/amend/loadEntity/";
var tableTarget = "/amend/loadDirectory/";

function showAddTopModal() {
    var add_url = "/amend/addtop";
    load_add_top_entity(add_url);
}

function showAddModal(index) {
    var editUrl = editModalTarget + index;
    // loadEntity(editUrl);
    loadAddEntity(editUrl);
}

function showEditModal(index) {
    /* /amend/loadEntity/12345 */
    var editUrl = editModalTarget + index;
    loadEntity(editUrl);
}

function showDeleteModal(index) {
    $('#delete-id').val(index);
}

function load_add_top_entity(url) {
    $.getJSON(url, {}, function (data) {
        populate_top_modal(data, names);
    });
}

function loadAddEntity(url) {
    $.getJSON(url, {}, function (data) {
        populateAddModal(data, names);
    });
}

function loadEntity(url) {
    $.getJSON(url, {}, function (data) {
        populateModal(data, names);
    });
}

function populate_top_modal(data) {
    $('#top-id').val(data.id);
    $('#top-name').val(data.name);
    $('#top-address').val(data.address);
    $('#top-mailCode').val(data.mailCode);
    $('#top-phoneType').val(data.phoneType);
    $('#top-phoneNumber').val(data.phoneNumber);
    $('#top-tieLine').val(data.tieLine);
    $('#top-parent').val(data.parent);
    $('#top-level').val(data.level);
}

function populateAddModal(data) {
    $('#add-id').val(data.id);
    $('#add-name').val(data.name);
    $('#add-address').val(data.address);
    $('#add-mailCode').val(data.mailCode);
    $('#add-phoneType').val(data.phoneType);
    $('#add-phoneNumber').val(data.phoneNumber);
    $('#add-tieLine').val(data.tieLine);
    $('#add-parent').val(data.parent);
    $('#add-level').val(data.level);
}

function populateModal(data) {
    $('#update-id').val(data.id);
    $('#update-name').val(data.name);
    $('#update-address').val(data.address);
    $('#update-mailCode').val(data.mailCode);
    $('#update-phoneType').val(data.phoneType);
    $('#update-phoneNumber').val(data.phoneNumber);
    $('#update-tieLine').val(data.tieLine);
    $('#update-parent').val(data.parent);
    $('#update-level').val(data.level);
}

function clearModal() {
    $('#update-id').val('');
    $('#update-name').val('');
    $('#update-address').val('');
    $('#update-mailCode').val('');
    $('#update-phoneType').val('');
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
    console.log("postEdit ...");

    var directory = $('#edit-form').serialize();
    var editUrl = url + 'update';
    $.post(editUrl, directory, function (data) {
        updateTable(data);
    });
    clearAndCloseModal('#umodal');
}

function postAdd() {
    console.log("postAdd ...");
    var directory = $('#add-form').serialize();
    /* /amend/add */
    var editUrl = url + 'add';
    $.post(editUrl, directory, function (data) {
        updateTable(data);
    });
    clearAndCloseModal('#amodal');
}

function deleteEntity(entity) {
    console.log("delete entity by id ...");
    var input = $('#delete-id');
    var url = '/amend/directory/delete/' + input.val();
    $.get(url, function (data) {
        // updateTable(data);
    });
    closeModal('#dmodal');
    input.val('');
}


function updateTable(data) {
    console.log("update ...");

    $.ajax({
        dataType: "json",
        url: tableTarget + "/arts",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        success: function (response) {
            $('#table-body').empty();
            $.each(response, function (i, e) {
                var end = e.id + ");'";
                var add = "'showAddModal(" + end;
                var edit = "'showEditModal(" + end;
                var del = "'showDeleteModal(" + end;
                var row = $('<tr>').append(
                    $('<td>').text(e.id),
                    $('<td>').text(e.name),
                    $('<td>').text(e.address),
                    $('<td>').text(e.mailCode),
                    $('<td>').text(e.phoneType),
                    $('<td>').text(e.phoneNumber),
                    $('<td>').text(e.tieLine),
                    $('<td>').text(e.parent),
                    $('<td>').text(e.level),

                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#amodal' onclick=" +
                        add + ">Add</a>"
                    ),
                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#umodal' onclick=" +
                        edit + ">Edit</a>"
                    ),
                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#dmodal' onclick=" +
                        del + ">Delete</a>"
                    )
                );
                $('#another-table').append(row);
            });
        }
    });
}
