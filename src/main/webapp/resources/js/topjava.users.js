const ajaxUrl = "ajax/admin/users/";
const ajaxUrlEnabled = "ajax/admin/users/enabled/";
let datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#dataTableUser").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function changeStatus(id, checkBox) {
    let flag = checkBox.is(":checked");
    $.ajax({
        type: "POST",
        url: ajaxUrlEnabled,
        data: {id: id,
                flag: flag}
    }).done(function () {
        updateTable();
        successNoty(flag ? "active" : "inactive");
    })
}

function addUser() {
    $("#detailsFormUser").find(":input").val("");
    $("#editRowUser").modal();
}

function saveUser() {
    let form = $("#detailsFormUser");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRowUser").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}