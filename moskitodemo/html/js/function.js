$(function() {
	$('.tabs a').click(function() {
		var tn = 't'+$(this).attr('id');
		$('.tabs a').each(function() {
			$(this).parent().removeClass('active');
		});
		$(this).parent().addClass('active');
		$('.tab').each(function() {
			$(this).hide();
			if ($(this).attr('id') == tn) {
				$(this).show();
			}
		});
		return false;
	});
});