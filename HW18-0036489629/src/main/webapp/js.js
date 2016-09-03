$(function(){
	GALLERY = $('#gallery');

	$.getJSON("tag", function(r){
		$.each(r, addButton)
	})

	$('#overlay').click(function(){
		$('#overlay, #photo').fadeOut();
	})
});

function addButton(i, tag){
	var button = $("<div class='button' data-tag='"+tag+"'>"+tag+"</div>").appendTo($('#buttons'));

	button.click(function(){
		$("#gallery div").remove();
		$.getJSON("tag", {tag: tag}, function(r){
			$.each(r, addThumb);
		})
		$('#tag-title').html(tag);
	})
}

function addThumb(i, data){
	var tags = "";
	var path = data.path;
	$.each(data.tags, function(i, v){
		tags += v;
		if (i < data.tags.length-1){
			tags += ", ";
		}
	});

	var entry = $("<div class='entry' data-path='"+path+"' data-title='"+data.title+"' data-tags='"+tags+"'>"+
		"<img src='thumbnail?path="+path+"'>"+
		"</div>").appendTo(GALLERY);

	entry.click(function(){
		openPhoto($(this));
	})
}

function openPhoto(photo){
	var p = $('#photo img')
	p.attr('src', "photo?path="+photo.data('path'));
	$('#overlay').fadeIn();

	$('#photoData-title').html(photo.data('title'));
	$('#photoData-tags').html(photo.data('tags'));

	p.load(function(){
		center($('#photo').fadeIn());
	})
}

function center(element){
	element.css({'margin-left': -$('#photo').outerWidth()/2,
			'margin-top': -$('#photo').outerHeight()/2})
}