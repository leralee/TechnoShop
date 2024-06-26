var extraImagesCount = 0;
$(document).ready(function () {
    $("input[name='extraImage']").each(function (index) {
        extraImagesCount++;

        $(this).change(function(){
            if (!checkFileSize(this)) {
                return;
            }
            showExtraImageThumbnail(this, index);
        });
    });

    $("a[id='linkRemoveExtraImage']").each(function (index) {
        $(this).click(function() {
            removeExtraImage(index);
        })
    })
});

function showExtraImageThumbnail(fileInput, index){
    var file = fileInput.files[0];

    // этот фрагмент кода ищет id с элементов imageName и присваивает ему value равное имени файла
    // это нужно для когда изображение в контексте редактирования заменяется новым изображением, чтобы сохранилось
    // в базе данных именно название нового изображение, а не старого
    fileName = file.name;
    imageNameHiddenField = $("#imageName" + index);
    if (imageNameHiddenField.length) {
        imageNameHiddenField.val(fileName);
    }


    var reader = new FileReader();
    reader.onload = function(e) {
        $("#extraThumbnail" + index).attr("src", e.target.result);
    };
    reader.readAsDataURL(file);

    if (index >= extraImagesCount - 1) {
        addNextExtraImageSection(index + 1);
    }
}

//вызывает дополнительное окно для загрузки изображения
function addNextExtraImageSection(index){
    htmlExtraImage = `
    <div class="col border m-3 p-2" id="divExtraImage${index}">
            <div id="extraImageHeader${index}"><label>Дополнительное изображение #${index + 1}:</label></div>
            <div class="m-2">
                <img id="extraThumbnail${index}" alt="Дополнительное изображение #${index + 1}" class="img-fluid"
                     src="${defaultImageThumbnailSrc}"/>
            </div>
            <div>
                <input type="file" name="extraImage" 
                onchange="showExtraImageThumbnail(this, ${index})"
                accept="image/png, image/jpeg"
                       />
            </div>
    </div>

    `;

    htmlLinkRemove = `
        <a class="btn fas fa-times-circle fa-2x icon-grey float-right" 
        href="javascript:removeExtraImage(${index - 1})"
        title="Удалить изображение"></a>
`;

    $("#divProductImages").append(htmlExtraImage);

    $("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);

    extraImagesCount++;

}

function removeExtraImage(index) {
    $("#divExtraImage" + index).remove();
}




