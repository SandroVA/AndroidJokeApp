<?php

	require_once('db_connection.php');
    header('Content-type: application/json');


	if(isset($_GET['category']) && isset($_GET['subcategory']) && isset($_GET['description']) && isset($_GET['joketype'])) {



		$joketype = $_GET['joketype'];
		$subcategory = $_GET['subcategory'];
		$description = $_GET['description'];
		$category = $_GET['category'];

		// Build SQL query
		if ($category == "*") {
			$query = "SELECT _id,  s_description FROM jokes";
		} else {
			$query = sprintf("SELECT _id, s_description FROM jokes 
				WHERE category LIKE '%s' AND s_category LIKE '%s' AND joketype LIKE '%s' AND s_description LIKE '%s' ;", mysql_real_escape_string($category),mysql_real_escape_string($subcategory),mysql_real_escape_string($joketype),mysql_real_escape_string($description));
		}

		$result = mysql_query($query, $_db);

			

		// Array of data to be encoded through JSON
		$data = array();

		if (mysql_num_rows($result)) { // If there are results from the query
			while($joke = mysql_fetch_assoc($result)) {
				$jokes[] = array('joke'=>$joke);
			}	
		}
		echo json_encode(array('jokes'=>$jokes));
		
	} else echo json_encode(array('error'=>'not all the fields were set'));
		
	if(isset($_GET['searchById'])) {
		//echo "<p class='bad'>search by id</span>";
		
		// Build SQL query
		
		if (isset($_GET['id']) && $_GET['id'] != "") {
			//	echo "<p class='bad'>id is set</span>";
			
			$query = sprintf("SELECT _id, joketype, category, s_category, s_description,question_text,answer_text,monologue_text FROM jokes 
				WHERE _id = '%s' ;", mysql_real_escape_string(trim($_GET['id'])));
			
			$result = mysql_query($query, $_db);
			
			// Array of data to be encoded through JSON
			$data = array();

			if (mysql_num_rows($result))  // If there are results from the query
				$joke = mysql_fetch_assoc($result); 
			
			//	header('Content-type: application/json');
			echo json_encode(array('joke'=>$joke));
		}	
	}
		
?>