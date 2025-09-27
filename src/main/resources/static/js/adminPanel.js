
function select(dataset) {
    const form = document.getElementById(dataset.form);
    const field = document.getElementById(dataset.field)
    field.value = dataset.value
    form.submit;
}