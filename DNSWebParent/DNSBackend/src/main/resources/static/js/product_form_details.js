$(document).ready(function () {
    $("a[id='linkRemoveDetail']").each(function (index) {
        $(this).click(function () {
            removeDetailSectionByIndex(index);
        })
    })
});

function addNextDetailSection(){
    allDivDetails = $("[id^='divDetail']");
    divDetailsCount = allDivDetails.length;

    htmlDetailSection = `    
        <div class="align-items-center row g-3 mb-2" id='divDetail${divDetailsCount}'>
        <input type="hidden" name="detailIDs" value="0"/>
        <label class="col-sm-1 col-form-label">Название: </label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="detailNames" maxlength="255"/>
        </div>
        <label class="col-sm-1 col-form-label">Значение: </label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="detailValues" maxlength="255"/>
        </div>
    </div>
    `;

    $("#divProductDetails").append(htmlDetailSection);

    previousDivDetailSection = allDivDetails.last();
    previousDivDetailID = previousDivDetailSection.attr("id");

    htmlLinkRemove = `
        <div class="col-sm-auto">
                <a href="javascript:removeDetailSectionById('${previousDivDetailID}')" class="fas fa-times-circle fa-2x icon-dark"
                   title="Удалить"></a>
            </div>
    `;

    previousDivDetailSection.append(htmlLinkRemove);

    $("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
    $("#" + id).remove();
}

function removeDetailSectionByIndex(index) {
    $("#divDetail" + index).remove();
}