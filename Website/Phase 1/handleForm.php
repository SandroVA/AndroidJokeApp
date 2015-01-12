<?php 
if(isset($_SESSION["sess_username"]))
{
//$con=mysqli_connect("waldo2.dawsoncollege.qc.ca","teamviking","grain8wine","teamviking"); 
$con=mysqli_connect("localhost","root","","jokes");
// Check connection 
//echo"test";
if (mysqli_connect_errno()) 
  { 
  echo "Failed to connect to MySQL: " . mysqli_connect_error(); 
  } 
 else echo"Connected to database <br />"; 

    
//insertJoke.php 
  
  
if(isset($_POST["insert"])) { 
  //User hit the save button, handle accordingly 
  
  echo"In the insert post if statement\n"; 
    
//if(mysqli_query($con,"INSERT INTO jokes (_id, category, s_category,joketype,s_description,question_text,answer_text,monologue_text) 
//VALUES (NULL, " . $_POST["category"] . " ,"  .  $_POST["s_category"] . "," .  $_POST["joketype"] . "," .  $_POST["s_description"] .  
//"," .  $_POST["question_text"] . "," . $_POST["answer_text"] . "," .  $_POST["monologue_text"] . ");")) 
  
  
  
if(mysqli_query($con,"INSERT INTO jokes ( category, s_category,joketype,s_description,question_text,answer_text,monologue_text) 
VALUES('$_POST[category]','$_POST[s_category]','$_POST[joketype]','$_POST[s_description]','$_POST[question_text]','$_POST[answer_text]','$_POST[monologue_text]');")) 
{ 
echo "Record inserted\n"; 
} 
else
{ 
echo("\nError description: " . mysqli_error($con)); 
} 
  
  
} 
//-------------------------------------------------------------------------- 
  
  
//2. updateJoke.php 
if(isset($_POST["update"])) { 
	if(mysqli_query($con,"UPDATE jokes  
	SET category = '$_POST[category]' 
	, s_category = '$_POST[s_category]' 
	, s_description = '$_POST[s_description]' 
	, question_text = '$_POST[question_text]'
	, answer_text = '$_POST[answer_text]'
	, monologue_text = '$_POST[monologue_text]'
	 WHERE _id = '$_POST[id]';")) 
	 { 
	echo "Record update\n"; 
	} 
	else
	{ 
	echo("\nError description: " . mysqli_error($con)); 
	} 
} 
  
// } 
// //-------------------------------------------------------------------------- 
  
  
  
if(isset($_POST["delete"])) { 
//3.deleteJoke.php 
  
if(mysqli_query($con,"DELETE FROM jokes 
WHERE _id= ' $_POST[id]' ;")) 
{ 
echo "Record '$_POST[id]' deleted\n"; 
} 
else
{ 
echo("\nError description: " . mysqli_error($con)); 
} 
} 
//-------------------------------------------------------------------------- 
//Display joke by ID is not needed. Talked to jeffs group he said Bonnie took that off when he went to talk to her. 
  
if(isset($_POST["displayJokesByCategory"])) { 
//4.displayJokesByCategory.php 
$result = mysqli_query($con,"SELECT question_text, answer_text , monologue_text FROM jokes WHERE category = ' $_POST[category] ';"); 
  if($result) {
	echo'Displaying category'; 
	while($row = mysqli_fetch_array($result,MYSQLI_BOTH)) {
		 echo"<br/>"; 
		 echo "  _id: " . $row['_id'] . "<br/>  category: " 
		 . $row['category'] . 
		 "<br/>  s_category: " . $row['s_category'] . 
		 "<br/>  joketype: " . $row['joketype'] . 
		 "<br/>  s_description: " . $row['s_description'] . 
		 "<br/>  question_text: " . $row['question_text'] . 
		 "<br/>  answer_text: " . $row['answer_text'] . 
		 "<br/>  monologue_text: " . $row['monologue_text'] . 
		 "<br/>  rating: " . $row['rating'] . 
		 "<br/>  comments: " . $row['comments'] . 
		 "<br/>  source : " . $row['source'] .  
		 "<br/>  release_status : " . $row['release_status'] . 
		 "<br/>  create_date : " . $row['create_date'] .  
		 "<br/>  modify date: " . $row['modify_date']; 
	} 
 } else echo("\nError description: " . mysqli_error($con)); 
  
 
  
//Free result set 
mysqli_free_result($result); 
    
mysqli_close($con); 
  
} 
//-------------------------------------------------------------------------- 
// //5.displayJokeDetailById 
  
  
 if(isset($_POST["displayJokeDetailById"])) { 
 $result = mysqli_query($con,"SELECT * FROM jokes WHERE _id = ' $_POST[id] ';"); 
 
 if ($result) {
 
	if ($row = mysqli_fetch_array($result,MYSQLI_BOTH)) {
		 echo"<br/>"; 
		 echo "  _id: " . $row['_id'] . "<br/>  category: " 
		 . $row['category'] . 
		 "<br/>  s_category: " . $row['s_category'] . 
		 "<br/>  joketype: " . $row['joketype'] . 
		 "<br/>  s_description: " . $row['s_description'] . 
		 "<br/>  question_text: " . $row['question_text'] . 
		 "<br/>  answer_text: " . $row['answer_text'] . 
		 "<br/>  monologue_text: " . $row['monologue_text'] . 
		 "<br/>  rating: " . $row['rating'] . 
		 "<br/>  comments: " . $row['comments'] . 
		 "<br/>  source : " . $row['source'] .  
		 "<br/>  release_status : " . $row['release_status'] . 
		 "<br/>  create_date : " . $row['create_date'] .  
		 "<br/>  modify date: " . $row['modify_date']; 
	} else {
		 echo "joke does not exist by that id";
	}
}
  
//  Free result set 
mysqli_free_result($result); 
    
mysqli_close($con); 
  
} 
 //-------------------------------------------------------------------------- 
  // } 
 }
 else header(login.html);
  
?>