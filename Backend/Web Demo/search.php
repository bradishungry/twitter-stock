<?php
require 'DatabaseConnect.php';

$search = $_REQUEST['search'];
if($search == '') {
    exit();
}

$sql = "SELECT DISTINCT(`text`) FROM `hashtags` WHERE `text` LIKE '$search%' LIMIT 10";
$result = $conn->query($sql);

$myArray = null;
if ($result->num_rows > 0) {
    while($row = $result->fetch_array(MYSQLI_BOTH)) {
        $myArray[] = $row[0];
    }
    echo json_encode($myArray);
}
else {
    echo "0 results";
}
echo "\n";
$conn->close();