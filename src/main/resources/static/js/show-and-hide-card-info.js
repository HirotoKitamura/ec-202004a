/**
 * 
 */
$(function() {
	$('input[name="paymentMethod"]').on("change", function() {
		if ($('input[name="paymentMethod"]:checked').val() == 1) {
			$(".card-info").hide();
		} else {
			$(".card-info").show();
		}
	})
})