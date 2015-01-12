<?php // index.php

	session_start();

	// Session doesn't exist
	if (!isset($_SESSION['username'])) {

		header('Location: login.php');

	} else {

		// Connect to db
		require_once 'db_connection.php';

		if ($_SERVER['REQUEST_METHOD'] == 'POST') { // POST return
		
			if(isset($_POST['searchbyid'])) {
				echo "<p class='bad'>search by id</span>";
				
				// Build SQL query
				
				if (isset($_POST['id']) && $_POST['id'] != "") {
					echo "<p class='bad'>id is set</span>";
					
					$query = sprintf("SELECT _id, joketype, category, s_category, s_description FROM jokes 
						WHERE _id = '%s' ;", mysql_real_escape_string(trim($_POST['id'])));
					
					$result = mysql_query($query, $_db);
					
					$data = array();
					
					if (mysql_num_rows($result)) { // If there are results from the query
						while ($r = mysql_fetch_assoc($result)) {
							$data[] = $r;
						}
					}
					
					// Remove when done
					echo "<p class='bad'>$query</span>";
					echo "<p class='debug'>" . count($data) . "</p>";
					
				}
				
			} else if(isset($_POST['category'])) {
			
				echo "<p class='bad'>search by category</span>";

				// Build SQL query
				if ($_POST['category'] == "*") {
					$query = "SELECT _id, joketype, category, s_category, s_description FROM jokes";
				} else {
					$query = sprintf("SELECT _id, joketype, category, s_category, s_description FROM jokes 
						WHERE category = '%s' ;", mysql_real_escape_string(trim($_POST['category'])));
				}

				$result = mysql_query($query, $_db);

				

				// Array of data to be encoded through JSON
				$data = array();

				if (mysql_num_rows($result)) { // If there are results from the query
					while ($r = mysql_fetch_assoc($result)) {
						$data[] = $r;
					}
				}
				
				// Remove when done
				echo "<p class='bad'>$query</span>";
				echo "<p class='debug'>" . count($data) . "</p>";
			}

		} else { // Initial page load

			$query = "SELECT _id, joketype, category, s_category, s_description FROM jokes";

			$result = mysql_query($query, $_db);

			// Array of data to be encoded through JSON
			$data = array();

			if (mysql_num_rows($result)) { // If there are results from the query
				while ($r = mysql_fetch_assoc($result)) {
					$data[] = $r;
				}
	
			}

			// Remove when done
			echo "<p class='bad'>$query</span>";
			echo "<p class='debug'>" . count($data) . "</p>";
		}
	}

?>

<title> Team Viking </title>
<link rel="stylesheet" type="text/css" href="style.css">

<div id="container">
	
	<div id="content">

		<div id="content-header">
			<form name="categories" method="post" action="index.php">
		
				<label for="category">Search by category:</label>
				<select name="category" onchange="this.form.submit()">

					<option value="*">*</option>

					<option value="Geek" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Geek") { echo "selected"; } } ?>>Geek</option>

					<option value="Holiday" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Holiday") { echo "selected"; } } ?>> Holiday </option>

					<option value="Kids" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Kids") { echo "selected"; } } ?>>Kids</option>

					<option value="Horror" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Horror") { echo "selected"; } } ?>>Horror</option>

					<option value="School" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "School") { echo "selected"; } } ?>>School</option>

				</select>
				
				<br />
				
				<label for="searchbyid">Search by ID:</label>
				<input type="text" name="id" />
				<input type="submit" value="Search" name="searchbyid" class="button" />

			</form>
		</div>

		<div id="content-body">
			<ul id="jokelist">
			
				<!-- Dynamically adds list elements in here through Javascript -->
				
			</ul>
		</div>
		
		<div id="content-footer">
			<a href="modifyjoke.php">
				<button type="button" class="button"> Add Joke </button>
			</a>
		</div>

	</div>

</div>

<script type="text/javascript">

	var data = <?php echo json_encode($data); ?>;

	function main() {
		// do stuff here

		for (var i=0; i<data.length; i++) {

			var li = document.createElement("li");
			document.getElementById("jokelist").appendChild(li);

			li.innerHTML =  data[i]['_id'].toString() + " - " 
							+ data[i]['joketype'].toString() + " - " 
							+ data[i]['category'].toString() + " ("
							+ data[i]['s_category'].toString() + "): "
							+ data[i]['s_description'].toString();
			
			li.onclick = function(e) { 
				var id = e.target.innerHTML.substring(0,e.target.innerHTML.indexOf(" "));
				
				var url = "modifyjoke.php?id=" + id;
				
				window.location.href = url;
				
			};
		}

	}

	window.onload=main();

</script>