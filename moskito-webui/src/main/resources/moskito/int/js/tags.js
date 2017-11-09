/**
 * @author strel
 */
$(function () {
    // Changing chevron sign orientation on collapse click
    $('.collapse-toggle').click(function () {
        var chevronIcon = $(this).find('i');
        chevronIcon.toggleClass('fa-chevron-up fa-chevron-down');
    });
});
