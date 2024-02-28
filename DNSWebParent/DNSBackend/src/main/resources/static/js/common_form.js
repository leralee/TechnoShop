$(document).ready(function () {
    $("#buttonCancel").click(function () {
        window.location = moduleURL;
    });

    $("#fileImage").change(function (){
        let fileSize = this.files[0].size;
        if (fileSize > 10485760) {
            this.setCustomValidity("Файл должен быть менее 10MB!");
            this.reportValidity();
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    });
});

function showImageThumbnail(fileInput) {
    let file = fileInput.files[0];
    let reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };
    reader.readAsDataURL(file);
}

function showModalDialog(title, message) {
    // способ обращения с помощью jquery
    $("#modalTitle").text(title);
    $("#modalBody").text(message);

    // другой способ обращаться к идентификатору с помощью нативного js, а не jquery
    const myModal = new bootstrap.Modal(document.getElementById('modalDialog'));
    myModal.show(this);
}

function showErrorModal(message) {
    showModalDialog("Ошибка", message);
}
function showWarningModal(message) {
    showModalDialog("Предупреждение", message);
}




