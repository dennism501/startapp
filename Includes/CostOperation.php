<?php 
define('DB_HOST', 'localhost');
define('DB_USER', 'admin');
define('DB_PASS', 'D3nn15!t3ch');
define('DB_NAME', 'clients');

$con = mysqli_connect (DB_HOST, DB_USER, DB_PASS, DB_NAME);
mysqli_select_db($con,DB_NAME) or die("connection failed");

$result = mysqli_query($con, "SELECT SUM(cost) AS TotalCost FROM Transaction;");

while($row = mysqli_fetch_assoc($result)){
    
    $tmp[] = $row;
}

echo json_encode($tmp);
mysqli_close($con);

?>