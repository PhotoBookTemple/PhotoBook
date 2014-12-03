<?php
// array for JSON response
$response = array();
if (isset($_GET['userName']) && isset($_GET['userPwd'])) {
		
	$userName = $_GET['userName'];
	$userPwd = $_GET['userPwd'];

	// include db connect class
	require_once __DIR__ . '/connect_DB.php';
	
	// mysql inserting a new row
	$result = mysqli_query($mysqlconn, "SELECT userID FROM UserInfo WHERE userName = '$userName' AND userPwd = '$userPwd'");
	$count = mysqli_num_rows($result);

	if ($count >= 1)
	{		

		 $row = mysqli_fetch_assoc($result);
		 // successfully inserted into database
		 $response["success"] = 1;
		 $response["message"] = $row["userID"];
		 // echoing JSON response
		 echo json_encode($response);
	 
	} else {
		 // required field is missing
		 $response["success"] = 0;
		 $response["message"] = "Incorrect User Name/Password";
		 echo json_encode($response);
	}
} else {
		 // required field is missing
		 $response["success"] = 0;
		 $response["message"] = "User Name/Password Missing";
		 echo json_encode($response);
}

?>
