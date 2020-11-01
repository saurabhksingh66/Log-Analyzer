<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

<h2>LOG - ANALYZER </h2>

	<form method="POST" action="success"  enctype="multipart/form-data">
	
	 Upload your file please:
	 <input type="file" name="file" />
	 <input type="submit" value="upload" />
	</form>
</body>
</html>