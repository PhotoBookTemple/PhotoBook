<?php
$servername = "127.0.0.1";
$database = "Fa14_4340_Photobook";
$username = "tuc69409";
$password = "thokeija";

// Create connection
$mysqlconn = mysqli_connect($servername, $username, $password, $database);
/*
// Check connection
 if (mysqli_connect_errno()) {
     echo "Connection failed: " . mysqli_connect_error();
} 
else
{
	echo "Connection Success to:" . ' ' . $database . ' ' . "and host info:" . ' ' . $mysqlconn->host_info;
}
*/
?>