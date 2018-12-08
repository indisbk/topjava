const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#dataTableMeal").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function addMeal() {
    $("#detailsFormMeal").find(":input").val("");
    $("#editRowMeal").modal();
}

function saveMeal() {
    let form = $("#detailsFormMeal");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRowMeal").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}