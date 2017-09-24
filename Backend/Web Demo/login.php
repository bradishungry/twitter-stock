<?php
require 'DatabaseConnect.php';

$username = $_REQUEST['username'];
$password = $_REQUEST['password'];

$sql = "SELECT * FROM `users` WHERE name=\"$username\" AND pass=MD5(\"$password\");";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "success";
}
else {
    echo "Username or password is incorrect";
}
$conn->close();
