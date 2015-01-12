<?php // modifyjoke.php
	
	session_start();

	// Session doesn't exist
	if (!isset($_SESSION['username'])) {

		header('Location: login.php');

	} else {
	
		// Connect to db
		require_once 'db_connection.php';

		if ($_SERVER['REQUEST_METHOD'] == 'POST') { // POST return
			
			// ---------------------------------------------------
			if(isset($_POST["add"])) {
			
				if(isset($_POST['category']) && $_POST['joketype'] == 'Image') {
					echo "<span class='debug'>Image joke for some reason...<br/></span>"; 
					
					// image upload
					
					$jokePictureName = $_FILES['image']['name']; 
					$photoData =  $_FILES['image']; 
				
					//check if there was no error during the file upload 
					if ($photoData['error']==0) { 
			  
						$location = "upload/".$jokePictureName; 
	   
						//get the last automatically generated ID 
				  
		  
						if(mysqli_query($_db,"INSERT INTO jokes ( category, s_category,joketype,s_description,question_text,answer_text,monologue_text)  
							VALUES('$_POST[category]','$_POST[s_category]','$_POST[joketype]','$_POST[s_description]','$location','$_POST[answer_text]','$_POST[monologue_text]');"))  
						{  
							echo "Record inserted with image location\n";  
						}  
						else
						{  
							echo("\nError description: " . mysqli_error($_db));  
						} 
	  
					
					  
						//move the temporarily stored file to a convenient location 
						if (move_uploaded_file($photoData['tmp_name'], $location)) { 
							//file moved, all good, generate thumbnail 
							//thumb("upload/".$IdPhoto.".jpg", 180); 
							print json_encode(array('successful'=>1)); 
						} else { 
							  
							$msg ='Upload on server problem .. error:' . $jokePictureName; 
							print json_encode(array('error'=>$msg)); 
						} 
	   
					} else { 
			   
						$msg ='Upload malfunction'; 
						print json_encode(array('error'=>$msg)); 
					} 
					
					
				} else {
					
					// non-image upload
					
					$query = sprintf("INSERT INTO jokes (joketype, category, s_category, 
								s_description, question_text, answer_text,monologue_text)
							 VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
							 mysql_real_escape_string(trim($_POST['joketype'])),
							 mysql_real_escape_string(trim($_POST['category'])),
							 mysql_real_escape_string(trim($_POST['s_category'])),
							 mysql_real_escape_string(trim($_POST['s_description'])),
							 mysql_real_escape_string(trim($_POST['question_text'])),
							 mysql_real_escape_string(trim($_POST['answer_text'])),
							 mysql_real_escape_string(trim($_POST['monologue_text'])));
							 
					
					if (mysql_query($query, $_db)) {
							
						echo "<span class='debug'>Joke successfully added<br/></span>";  
					} else {
						echo "<span class='debug'>Failed to add joke<br/></span>"; 
					}
								
					
				}
		
			} // end add
			
			// ---------------------------------------------------
			if (isset($_POST['update'])) {
			
				$query = sprintf("UPDATE jokes SET
									joketype='%s',
									category='%s',
									s_category='%s', 
									s_description='%s', 
									question_text='%s', 
									answer_text='%s', 
									monologue_text='%s'
								WHERE _id='%s';",
							 mysql_real_escape_string(trim($_POST['joketype'])),
							 mysql_real_escape_string(trim($_POST['category'])),
							 mysql_real_escape_string(trim($_POST['s_category'])),
							 mysql_real_escape_string(trim($_POST['s_description'])),
							 mysql_real_escape_string(trim($_POST['question_text'])),
							 mysql_real_escape_string(trim($_POST['answer_text'])),
							 mysql_real_escape_string(trim($_POST['monologue_text'])),
							 mysql_real_escape_string(trim($_POST['_id'])));
							 
							 
				echo"<span class='debug'>$query<br/></span>";
				if (mysql_query($query, $_db)) {
					echo "<span class='debug'>Joke successfully edited<br/></span>";  
				} else {
					echo "<span class='debug'>Failed to edit joke<br/></span>"; 
				}
				
			
			} // end update
			
			// ---------------------------------------------------
			if (isset($_POST['delete'])) {
			
				$query = sprintf("DELETE FROM jokes WHERE _id='%s';",
							 mysql_real_escape_string(trim($_POST['_id'])));
							 
				echo"<span class='debug'>$query<br/></span>";
				if (mysql_query($query, $_db)) {
					echo "<span class='debug'>Joke successfully deleted<br/></span>";  
				} else {
					echo "<span class='debug'>Failed to delete joke<br/></span>"; 
				}
				
			
			} // end delete
			// ---------------------------------------------------
			
		} else if ($_SERVER['REQUEST_METHOD'] == 'GET') {
				
				if (isset($_GET['id']) && $_GET['id'] != "") {
					
					// Build SQL query
					$query = sprintf("SELECT 
								_id, 
								category, 
								s_category, 
								joketype, 
								s_description,
								question_text,
								answer_text,
								monologue_text 
							FROM jokes WHERE _id = '%s' ;", 
						mysql_real_escape_string(trim($_GET['id'])));
					
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
					
		}
	
	}


?>


<title> Team Viking </title>
<link rel="stylesheet" type="text/css" href="style.css">

<div id="container">
	
	<div id="content">
	
		<div id="content-header">
			<h1>Team Viking presents:</h1>
		</div>
	
		
		<form name="jokeform" method="post" action="modifyjoke.php" enctype="multipart/form-data">
			
			<div id="content-body">
			
				<label for="_id">ID:</label>
				<input id="input_id" type="text" name="_id" value="<?php if(isset($_POST['id'])) { echo $_POST['id']; } ?>">
				
				<br/>
				
				
				<label for="category">Category:</label>
				<select id="input_category" name="category">
					<option value="Geek" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Geek") { echo "selected"; } } ?> >Geek</option>
					<option value="Holiday" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Holiday") { echo "selected"; } } ?> >Holiday</option>
					<option value="Kids" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Kids") { echo "selected"; } } ?> >Kids</option>
					<option value="Horror" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "Horror") { echo "selected"; } } ?> >Horror</option>
					<option value="School" <?php if(isset($_POST['category'])) {
					 if ($_POST['category'] == "School") { echo "selected"; } } ?> >School</option>
				</select>
				
				<br/>
								
				<label for="s_category">Subcategory:</label>
				<select id="input_s_category" name="s_category">
					<option value="" <?php if(isset($_POST['s_category'])) {
					 if ($_POST['s_category'] == "") { echo "selected"; } } ?> ></option>
					<option value="Science" <?php if(isset($_POST['s_category'])) {
					 if ($_POST['s_category'] == "Science") { echo "selected"; } } ?> >Science</option>
					<option value="Computer Science" <?php if(isset($_POST['s_category'])) {
					 if ($_POST['s_category'] == "Computer Science") { echo "selected"; } } ?> >Computer Science</option>
					<option value="Halloween" <?php if(isset($_POST['s_category'])) {
					 if ($_POST['s_category'] == "Halloween") { echo "selected"; } } ?> >Halloween</option>
					<option value="Thanksgiving" <?php if(isset($_POST['s_category'])) {
					 if ($_POST['s_category'] == "Thanksgiving") { echo "selected"; } } ?> >Thanksgiving</option>
				</select>
								
				<br/>
			
				<label for="joketype">Joke Type:</label>
				<select id="input_joketype" name="joketype">
					<option value="1" <?php if(isset($_POST['joketype'])) {
					 if ($_POST['joketype'] == "1") { echo "selected"; } } ?> >Question & Answer</option>
					<option value="2" <?php if(isset($_POST['joketype'])) {
					 if ($_POST['joketype'] == "2") { echo "selected"; } } ?> >Monologue</option>
					<option value="3" <?php if(isset($_POST['joketype'])) {
					 if ($_POST['joketype'] == "3") { echo "selected"; } } ?> >Image</option>
				</select>
					
				<br/>
				
			
				<label for="s_description">Joke Description:</label>
				<input id="input_s_description" type="text" name="s_description" maxlength="20" value="<?php if(isset($_POST['s_description'])) { echo $_POST['s_description']; } ?>">
				
				<br/>
				
				
				<label for="file">Joke picture file:</label>
				<input id="input_image" type="file" name="image" id="file">
				
				<br>
			
				<textarea id="input_question_text" name="question_text" placeholder="(Q&A OR IMAGE) Insert question OR IMAGE NAME here." maxlength="50" rows="4" cols="50"><?php if(isset($_POST['question_text'])) { echo $_POST['question_text']; } ?></textarea>
				
				<br/>
				
				<textarea id="input_answer_text" name="answer_text" placeholder="(Q&A ONLY) Insert answer here." maxlength="50" rows="4" cols="50"><?php if(isset($_POST['answer_text'])) { echo $_POST['answer_text']; } ?></textarea>
					
				<br/>
				
				<textarea id="input_monologue_text" name="monologue_text" placeholder="(MONOLOGUE ONLY)Insert monologue text here." maxlength="250" rows="6" cols="50"><?php if(isset($_POST['monologue_text'])) { echo $_POST['monologue_text']; } ?></textarea>
				
				<br/>
				
			</div>
				
			<div id="content-footer"> 
			
				<input type="submit" value="Add" name = "add" class="button">
				<input type="submit" value="Update" name ="update"  class="button">
				<input type="submit" value="Delete" name ="delete" class="button">
			</div>
			
		</form>
	</div>

</div>

<script type="text/javascript">

	
	var data = <?php echo json_encode($data); ?>;

	if (data.length == 1) {
		
		document.getElementById("input_id").value = data[0]['_id'].toString();
		document.getElementById("input_category").value = data[0]['category'].toString();
		document.getElementById("input_s_category").value = data[0]['s_category'].toString();
		document.getElementById("input_joketype").value = data[0]['joketype'].toString();
		document.getElementById("input_s_description").value = data[0]['s_description'].toString();
		document.getElementById("input_question_text").value = data[0]['question_text'].toString();
		document.getElementById("input_answer_text").value = data[0]['answer_text'].toString();
		document.getElementById("input_monologue_text").value = data[0]['monologue_text'].toString();
		
	}

	window.onload=main();

</script>