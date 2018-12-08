const ajaxUrl = "ajax/profile/meals/";
const ajaxUrlFilter = "ajax/profile/meals/filter"
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

function filterMeals() {
    let isEmpty = true;
    let form = $("#detailsFilter");
    form.find(":input").each(function () {
        if ($.trim($(this).val()) !== '') {
            isEmpty = false;
        }
    });
    $.ajax({
        type: "GET",
        url: (isEmpty) ? ajaxUrl : ajaxUrlFilter,
        data: (isEmpty) ? {} : form.serialize()
    }).done(function (data) {
        datatableApi.clear().rows.add(data).draw();
        successNoty("Meals filtered")
    });
}

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