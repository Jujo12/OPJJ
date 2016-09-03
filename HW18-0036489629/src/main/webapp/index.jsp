<!doctype html>
<html>
<head>
	<title>Sailboat gallery</title>
	<link rel="stylesheet" href="style.css">
	<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
</head>
<body>
	<div id="overlay"></div>
	<div id="photo">
		<img>
		<div id="photoData">
			<div id="photoData-title"></div>
			<div id="photoData-tags"></div>
		</div>
	</div>

	<div class="container" id="c-top">
		<h1>Galerija jedrilica - odaberite tag:</h1>
		<div id="buttons">
		</div>
	</div>
	<div class="container" id="c-bottom">
		<h2>Tag: <span id="tag-title"></span></h2>
		<div id="gallery">
		</div>
	</div>

	<script type="text/javascript" src="js.js"></script>
</body>
</html>