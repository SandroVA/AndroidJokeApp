<?php 

	$_db = @mysql_connect('waldo2.dawsoncollege.qc.ca', 'teamviking', 'grain8wine');
	if (!$_db) die ("Unable to connect to MySQL" . mysql_error());

	mysql_select_db('teamviking', $_db);

?>