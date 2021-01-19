<?php
$db = "miniprojet";
$login = $_POST["Login"];
$password = $_POST["Password"];


$servername = "localhost";
$conn = mysqli_connect($servername,"root","",$db);
if($conn) {
    $sel ="select * from users where login like '$login' and password like '$password'" ;
    $resultat = mysqli_query($conn,$sel);
    if(mysqli_num_rows($resultat)>0){
        echo"succes";
    }else{
        echo "echec";
    }mysqli_close($conn);

}else{
    echo "prob de connexion";
}
?>