
function select(dataset) {
    const form = document.getElementById(dataset.form);
    const field = document.getElementById(dataset.field)
    field.value = dataset.value
    form.submit;
}

function selectAjax(dataset) {
    $.ajax({
        url: dataset.url,
        type: dataset.method,
        data: dataset.data,
        success(response){
            $('body').html(response);
        }
    });
}