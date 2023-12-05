<?php
include_once("db.php");


class tbl_messages {
    private $conn;

    public function __construct($conn) {
        $this->conn = $conn;
    }

    public function addMessage($ID, $message) {
        $stmt = $this->conn->prepare("INSERT INTO `messages` (UserID, Message) VALUES (?, ?)");
        $stmt->bind_param("is", $ID, $message);
        $stmt->execute();
        $stmt->close();
    }
    public function getMessage($ID) {
        $stmt = $this->conn->prepare("SELECT * FROM `messages` WHERE UserID = ?");
        $stmt->bind_param("i", $ID);
        $stmt->execute();
        $result = $stmt->get_result();
        $resultArray = [];

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $resultArray[] = $row['Message'];
            }
        }
        $stmt->close();

        
        return array_values($resultArray);
    }


    // Add more functions as needed
}
?>