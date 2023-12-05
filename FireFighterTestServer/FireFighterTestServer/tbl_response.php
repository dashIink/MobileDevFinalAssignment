<?php
include_once("db.php");


class tbl_response {
    private $conn;

    public function __construct($conn) {
        $this->conn = $conn;
    }

    public function addResponse($ID, $CallID,  $Status) {
        $stmt = $this->conn->prepare("DELETE FROM `response` WHERE `CallID` = ? AND `ResponderUserID` = ?");
        $stmt->bind_param("ii", $CallID, $ID);
        $stmt->execute();
        $stmt->close();
        $stmt = $this->conn->prepare("INSERT INTO `response` ( CallID, ResponderUserID, Status) VALUES (?, ?, ?)");
        $stmt->bind_param("iis", $CallID, $ID, $Status);
        $stmt->execute();
        $stmt->close();
    }

    public function updateUser($userID, $username, $password, $userType, $verificationStatus, $name, $stationNumber, $gpsLocation) {
        // Implement the logic to update an existing user in the 'User' table
        // ...
    }

    public function deleteUser($userID) {
        // Implement the logic to delete a user from the 'User' table
        // ...
    }

    public function getResponders($CallID) {
        $calls = array();
        $stmt = $this->conn->prepare("SELECT * FROM `response` WHERE CallID=?");
        $stmt->bind_param("i", $CallID);
        $stmt->execute();
        $result = $stmt->get_result();
        $stmt->close();

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $calls[] = array_values($row);
            }
        }
        return $calls;
    }

    public function authorize($username, $password){
        $stmt = $this->conn->prepare("SELECT * FROM `User` WHERE Username LIKE ? AND Password LIKE ?");
        $stmt->bind_param("ss", $username, $password);
        $stmt->execute();
        $result = $stmt->get_result();
        $user = $result->fetch_assoc();
        
        if ($user != null){
            return $user;
        }
        return false;
    }

    // Add more functions as needed
}
?>