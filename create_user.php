<?php
 
// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['userName']) && isset($_POST['userPwd'])) {

    $userName = $_POST['userName'];
    $userPwd = $_POST['userPwd'];
	
    // include db connect class
    require_once __DIR__ . '/connect_DB.php';

		
	// mysql inserting a new row
	$result = mysqli_query($mysqlconn, "SELECT userID FROM UserInfo WHERE userName = '$userName'");
	$count = mysqli_num_rows($result);

	if ($count == 1)
	{		
       	// failed to insert row
       	$response["success"] = 0;
		$response["message"] = "User already exists";
        
		// echoing JSON response
        echo json_encode($response);

    } else {

    	// mysql inserting a new row
    	$result = mysqli_query($mysqlconn, "INSERT INTO UserInfo(userName, userPwd) VALUES('$userName', '$userPwd')");
		$detailID = mysqli_insert_id($mysqlconn);
		// check if row inserted or not
    	if ($result) {
 	       // successfully inserted into database
        	$response["success"] = 1;
        	$response["message"] = $detailID;
 
 	       // echoing JSON response
 	       echo json_encode($response);
		} else {
 	       // failed to insert row
 	       $response["success"] = 0;
 	       $response["message"] = "Oops! An error occurred";
 	
        	// echoing JSON response
        	echo json_encode($response);
    	}
    }

} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

?>
