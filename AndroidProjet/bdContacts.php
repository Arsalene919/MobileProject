<?php
$db = "miniprojet";
    $ID = $_GET["ID"];
    $nom = $_GET["Name"];
    $adresse = $_GET["Address"];
    $phone1 = $_GET["Phone1"];
    $phone2 = $_GET["Phone2"];
    $entreprise = $_GET["Company"];


    $servername = "localhost";
$conn = mysqli_connect($servername,"root","",$db);

    

if ($conn)
{
	$q= "insert into contacts (ID, nom, adresse, phone1, phone2, entreprise)
    VALUES ('$ID', '$nom', '$adresse', '$phone1', '$phone2', '$entreprise')";
	if (mysqli_query($conn, $q)) {
		echo "succes insertion";
		
	} else {
		echo "echec insertion";
	}
	mysqli_close($conn);
	
} else{
	echo "probleme de connexion";
}

?>