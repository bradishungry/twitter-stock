<?php

$servername = "mysql.cs.iastate.edu";
$username = "dbu309yt1";
$password = "ZWI4NjRhMjdl";

$conn = new mysqli($servername, $username, $password);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

mysqli_select_db($conn,"db309yt1");
