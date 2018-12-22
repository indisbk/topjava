const mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax" : {
                "url" : mealAjaxUrl,
                "dataSrc" : ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render" : function (date, type, row) {
                        if (type === "display") {
                            return formatDate(date);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render" : {
                        "display" : renderEditBtn
                    }
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render" : {
                        "display" : renderDeleteBtn
                    }
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
});

const startDate = $('#startDate');
const endDate = $('#endDate');
const startTime = $('#startTime');
const endTime = $('#endTime');

startDate.datetimepicker({
   timepicker: false,
   format: 'Y-m-d',
   formatDate: 'Y-m-d'
});

endDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d'
});

$(startTime).datetimepicker({
    format: "H:i",
    datepicker: false
});
$(endTime).datetimepicker({
    format: "H:i",
    datepicker: false
});

$('#dateTime').datetimepicker({
    format: "Y-m-d H:i",
});
