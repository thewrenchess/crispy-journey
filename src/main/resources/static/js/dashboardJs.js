$(document).ready(()=>{
	console.log("Ready!");
	
	$("#media").hide();
	
	$("#create").click(()=>{
		$("#media").slideToggle('slow');
	});
});
