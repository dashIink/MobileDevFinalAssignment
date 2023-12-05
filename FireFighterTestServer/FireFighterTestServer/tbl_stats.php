<?php
include_once("db.php");


class tbl_stats {
    public $conn;


    public function __construct($conn){
        $this->conn = $conn;

    }

    function AddStat($ID, $CallID, $status) {
        global $conn;
    
        $sql = "INSERT INTO `statistics` (FireFighterUserID, CallID, Status) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("iss", $ID, $CallID, $status);
    
        if ($stmt->execute()) {
            return true;  // Call created successfully
        } else {
            return false;  // Error in creating the call
        }
    }
    
    // Function to update an existing call
    function updateCall($callID, $callerUserID, $location, $timestamp, $status) {
        global $conn;
    
        $sql = "UPDATE `Call` SET CallerUserID=?, Location=?, Timestamp=?, Status=? WHERE CallID=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("issii", $callerUserID, $location, $timestamp, $status, $callID);
    
        if ($stmt->execute()) {
            return true;  // Call updated successfully
        } else {
            return false;  // Error in updating the call
        }
    }
    
    // Function to delete a call
    function deleteCall($callID) {
        global $conn;
    
        $sql = "DELETE FROM `Call` WHERE CallID=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $callID);
    
        if ($stmt->execute()) {
            return true;  // Call deleted successfully
        } else {
            return false;  // Error in deleting the call
        }
    }
    
    // Function to get call details by ID
    function getCallByID($callID) {
        global $conn;
    
        $sql = "SELECT * FROM `Call` WHERE CallID=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $callID);
        $stmt->execute();
        
        $result = $stmt->get_result();
        $call = $result->fetch_assoc();
    
        return array_values($call);
    }
    function getAllCalls() {
        $calls = array();

        $sql = "SELECT * FROM `Call`";
        $result = $this->conn->query($sql);

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $calls[] = array_values($row);
            }
        }

    return $calls;
    }
    
}
?>