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

// function checkFileSize(fileInput) {
//     fileSize = fileInput.files[0].size;
//
//     if (fileSize > MAX_FILE_SIZE) {
//         fileInput.setCustomValidity("Изображение не должно превышать 1000KB");
//         fileInput.reportValidity();
//
//         return false;
//     } else {
//         fileInput.setCustomValidity("");
//
//         return true;
//     }
//
// }
//
// function showModalDialog(title, message){
//     $("#modalTitle").val(title);
//     $("#modalBody").text(message);
//     $("#modalDialog").modal();
//
// }
//
// function showErrorModal(message){
//     showModalDialog("Error", message);
// }
//
// function showWarningModal(message){
//     showModalDialog("Warning", message);
// }




