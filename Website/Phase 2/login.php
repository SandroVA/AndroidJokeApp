<?php // login.php

	session_start();

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		// Connect to db
		require_once 'db_connection.php';

		// Validation
		if (!isset($_POST['username']) || $_POST['username'] == "" ||
			!isset($_POST['password']) || $_POST['password'] == "" ) {

			// Destroy session 
			$_SESSION = array();
			session_destroy();
			setcookie ('PHPSESSID', "time()-3600, '/', ", 0, 0 );

			$errormsg = "Access denied! <br/> (invalid username/password)";

		} else {

			// Build SQL query
			$query = sprintf("SELECT _id, username FROM users 
				WHERE username = '%s' AND password = '%s';",
				mysql_real_escape_string(trim($_POST['username'])),
				//mysql_real_escape_string(crypt(trim($_POST['password']), $salt)));
				mysql_real_escape_string(trim($_POST['password'])));

			$result = mysql_query($query, $_db);

			if (mysql_num_rows($result) == 1) {

				// Fetch result
				$result = mysql_fetch_array($result);

				// Write session information (id & username)
				$_SESSION['_id'] = $result['_id'];
				$_SESSION['username'] = $result['username'];

				// Redirect page
				header('Location: index.php');

			} else {

				// Destroy session 
				$_SESSION = array();
				session_destroy();
				setcookie ('PHPSESSID', "time()-3600, '/', ", 0, 0 );
			
				// Display error message
				$errormsg = "Access denied! <br/> (invalid username/password)";
			}
		}
	}

?>

<title> Team Viking </title>
<link rel="stylesheet" type="text/css" href="style.css">

<div id="container">
	
	<div id="login">


		<h1 id="login_header"> Login </h1>

		<form name="login" method="post" action="login.php">
	
			<input type="text" name="username" class="loginfield" id="form_username"
				value="<?php if(isset($_POST['username'])) { echo $_POST['username']; } ?>" />

			<input type="password" name="password" class="loginfield" id="form_password" />
			
			<input type="submit" value="submit" id="form_submit">
		</form>
		
		<span id="errormsg"></span>

	</div>

</div>


<script type="text/javascript">
	var errormsg = <?php echo json_encode($errormsg); ?>;
	
	function main() {
		document.getElementById("errormsg").innerHTML = errormsg;
	}
	window.onload=main();
</script>