<?php
require 'DatabaseConnect.php';

$search = $_REQUEST['search'];
//$search = "job";
$sql = "SELECT `hour`, `count` FROM hashtags WHERE text=\"maga".$search."\" ORDER BY `hour` ASC";
$result = $conn->query($sql);

$myArray = null;
if ($result->num_rows > 0) {
    while($row = $result->fetch_array(MYSQLI_BOTH)) {
        $myArray[] = array($row[0],$row[1]);
    }
    echo json_encode($myArray);
}
else {
    echo "0 results";
}
echo "\n";
$conn->close();
