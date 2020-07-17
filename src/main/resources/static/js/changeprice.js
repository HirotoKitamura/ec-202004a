/**
 * 
 */

$(function() {
	// var basePrice = $("input:radio[name='size']:checked").val();
	// var toppingCount = 0;
	// var count = 1;
	// //size change
	// var toppingPrice = 200;
	// //javascriptは
	//	
	var toppingPrice = 200;
	var basePrice = $("#price-m").val();
	var totalPrice = parseInt($("#price-m").text().replace(",", ""), 10);
	$("#total-price").text(totalPrice.toLocaleString());

	// when change size, change price
	$($(".radio-inline")).on("change", function() {
		printTotalPrice();
	})
	
	$("#allcheck").on('click', function() {
		$("input[type=checkbox]").prop('checked', true);
		printTotalPrice();
	})
	
	$("#alluncheck").on('click', function() {
		$("input[type=checkbox]").prop('checked', false);
		printTotalPrice();
	})

	$("#topping-check input[type='checkbox']").on("click", function() {
		printTotalPrice();
	})

	$("#curry-count").on("change", function() {
		printTotalPrice();
	})

	function printTotalPrice() {
		if ($("input:radio[name='size']:checked").val() == "M") {
			toppingPrice = 200;
			basePrice = parseInt($("#price-m").text().replace(",", ""), 10);
		} else if ($("input:radio[name='size']:checked").val() == "L") {
			toppingPrice = 300;
			basePrice = parseInt($("#price-l").text().replace(",", ""), 10);
		}
		console.log(basePrice);
		var toppingCount = $("#topping-check input[type='checkbox']:checked").length;
		var count = Number($("#curry-count").val());
		var totalToppingPrice = toppingPrice * toppingCount;
		var totalPrice = (basePrice + totalToppingPrice) * count;
		$("#total-price").text(totalPrice.toLocaleString());
		$("#form-total-price").val(totalPrice);
	}

})