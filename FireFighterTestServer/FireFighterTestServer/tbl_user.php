<?php
include_once("db.php");


class tbl_user {
    private $conn;

    public function __construct($conn) {
        $this->conn = $conn;
    }

    public function createUser($username, $password, $userType, $verificationStatus, $name, $stationNumber, $gpsLocation) {
        $stmt = $this->conn->prepare("INSERT INTO `User` (Username, Password, UserType, VerificationStatus, Name, StationNumber, GPSLocation) VALUES (?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("sssssss", $username, $password, $userType, $verificationStatus, $name, $stationNumber, $gpsLocation);
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

    public function getUserByID($userID) {
        $stmt = $this->conn->prepare("SELECT * FROM `User` WHERE UserID=?");
        $stmt->bind_param("i", $userID);
        $stmt->execute();
        $result = $stmt->get_result();
        $user = $result->fetch_assoc();
        $stmt->close();
        return $user;
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