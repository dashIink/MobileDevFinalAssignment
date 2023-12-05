<?php
include_once("tbl_user.php");
include_once("tbl_calls.php");
include_once("tbl_stats.php");
include_once("tbl_response.php");
include_once("tbl_messages.php");

$conn = connect();




$data = array();


$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$uri = explode( '/', $uri );

$function = $_GET['function'];

if($function == "auth"){
    $tbl_user = new tbl_user($conn);
    if(isset($_GET['user'])){
        if(isset($_GET['pass'])){
            $user = $_GET['user'];
            $pass = $_GET['pass'];
            $result = $tbl_user->authorize($user, $pass);

            if ($result != false){
                $data[] = 'Confirm';
                $data[] = $result["UserID"];
                $data[] = $result["UserType"];
            }
            else{
                $data[] = 'Failure';
            }
        }
        else{
            $data[] = 'Failure';
        }
    }
    else{
        $data[] = 'Failure';
    } 
}
if($function == "currentcalls"){
    $tbl_calls = new tbl_calls($conn);
    $return = $tbl_calls->getAllCalls();
    if ($return != []){
        $data[] = "Confirm";
        $data[] = $return;
    }
    else{
        $data[] = "Failure";
    }
    

}
if($function == "CallByID"){
    $tbl_calls = new tbl_calls($conn);
    $return = $tbl_calls->getCallByID($_GET['ID']);
    if ($return != []){
        $data[] = "Confirm";
        $data[] = $return;
    }
    else{
        $data[] = "Failure";
    }
    

}

if($function == "UpdateStat"){
    $id = $_GET['ID'];

    $CallID = $_GET['CallID'];
    $Status = $_GET['Status'];

    $tbl_stats = new tbl_stats($conn);
    if ($Status == "Accepted" || $Status == "Rejected"){
        $tbl_response = new tbl_response($conn);
        $tbl_response->addResponse($id, $CallID, $Status);
    }
    
    $return = $tbl_stats->AddStat($id, $CallID, $Status);
    if ($return == true){
        $data[] = "Confirm $Status";
        
    }
    else{
        $data[] = "Failure";
    }

}
if($function == "SendMessage"){
    $id = $_GET['ID'];
    $message = $_GET['Message'];

    $tbl_messages = new tbl_messages($conn);
    $tbl_messages->addMessage($id, $message);
    $data[] = "Confirm";
}
if($function == "GetMessage"){
    $id = $_GET['ID'];
    $tbl_messages = new tbl_messages($conn);
    $data = $tbl_messages->getMessage($id);
    
}

if($function == "GetFireFighters"){
    $CallID = $_GET['CallID'];
    $tbl_response = new tbl_response($conn);
    $tbl_user = new tbl_user($conn);
    $responders = $tbl_response->getResponders($CallID);
    foreach($responders as $responder){
        $data[] = array_values($tbl_user->getUserByID($responder[2]));
    }
    
}

if($function == "submitFireCall"){
    $lat = $_GET['lat'];
    $long = $_GET['long'];
    $type = $_GET['type'];
    $responding = $_GET['responding'];
    $tbl_calls = new tbl_calls($conn);
    $tbl_calls->createCall(1, $lat.", ".$long, 'Active', $type, $responding);
    
}




//$data = array("43.94516142522227", "-78.8969699544168", "Medical Emergency", "301");
$responseData = json_encode($data);




sendOutput( $responseData, array('Content-Type: application/json', 'HTTP/1.1 200 OK'));

//As a test, this is pre-set to a specific location

//



function sendOutput($data, $httpHeaders=array())
    {
        header_remove('Set-Cookie');
        if (is_array($httpHeaders) && count($httpHeaders)) {
            foreach ($httpHeaders as $httpHeader) {
                header($httpHeader);
            }
        }
        echo $data;
        exit;
    }



?>