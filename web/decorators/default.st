<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>$title$</title>
    $templates/shared/stylesheet(href="static/theme/jquery-ui-1.8.custom.css")$
    $templates/shared/stylesheet(href="static/css/main.css")$
    $templates/shared/javascript(src="static/js/jquery-1.4.2.min.js")$
    $templates/shared/javascript(src="static/js/jquery-ui-1.8.custom.min.js")$
    $templates/shared/javascript(src="static/js/buttons.js")$
    $head;format="none"$
</head>
<body class="$decorators/bodyClass()$">
<div id="container">
    <div id="header">
        <h1>$title$</h1>
    </div>
    <div id="content">
        $body;format="none"$
    </div>
</div>
</body>
</html>
