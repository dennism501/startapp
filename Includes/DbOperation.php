<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createRecord($cname, $fname, $number){
		$stmt = $this->con->prepare("INSERT INTO Client (cname, fname, number,created_on) VALUES (?, ?, ?,NOW())");
		$stmt->bind_param("sss", $cname, $fname,$number);
		if($stmt->execute())
			return true; 
		return false; 
	}
    
    function createTransaction($cname2, $amount, $cost, $type){
		$stmt = $this->con->
		prepare("INSERT INTO Transaction (cname2, amount, cost, type,created_on) VALUES (?, ?,?,?,NOW())");
		$stmt->bind_param("ssss", $cname2, $amount, $cost, $type);
		if($stmt->execute())
			return true; 
		return false; 
	}
    
          
	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getRecords(){
		$stmt = $this->con->prepare("SELECT id, cname, fname, number FROM Client ORDER BY created_on DESC");
		$stmt->execute();
		$stmt->bind_result($id, $cname, $fname, $number);
		
		$Records = array(); 
		
		while($stmt->fetch()){
			$Record  = array();
			$Record['id'] = $id; 
			$Record['cname'] = $cname; 
			$Record['fname'] = $fname; 
            $Record['number'] = $number; 
            
			
			array_push($Records, $Record); 
		}
		
		return $Records; 
	}
	
    
function getTransactions(){
		$stmt = $this->con->prepare("SELECT id, cname2, amount, cost, type, created_on FROM Transaction ORDER BY created_on DESC");
		$stmt->execute();
		$stmt->bind_result($id, $cname2, $amount, $cost,$type,$date);
		
		$Transactions = array(); 
		
		while($stmt->fetch()){
			$Transaction  = array();
			$Transaction['id'] = $id; 
			$Transaction['cname2'] = $cname2; 
			$Transaction['amount'] = $amount; 
			$Transaction['cost'] = $cost;
            $Transaction['type'] = $type; 
            $Transaction['created_on'] = $date;
            
			array_push($Transactions, $Transaction); 
		}
		
		return $Transactions; 
	}

	function login($username){
		$stmt = $this->con->prepare("SELECT cname FROM Client WHERE cname = ?");
		$stmt->bind_param("s",$username);
		$stmt->execute();
		$stmt->bind_result($user);

		if (!empty($user)){

			return true;
		}else{

			return false;
		}
		
	}
    
    
    
    
	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	function updateRecord($id, $cname, $fname, $number){
		$stmt = $this->con->prepare("UPDATE Client SET cname = ?, fname = ?, number = ? WHERE id = ?");
		$stmt->bind_param("sssi", $cname, $fname, $number,$id);
		if($stmt->execute())
			return true; 
		return false; 
	}
    
    function updateTransaction($id, $cname, $fname, $number){
		$stmt = $this->con->prepare("UPDATE Transaction SET cname2 = ?, amount = ?, type = ? WHERE id = ?");
		$stmt->bind_param("sssi", $cname2, $amount, $type,$id);
		if($stmt->execute())
			return true; 
		return false; 
	}
	

	
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	function deleteRecord($id){
		$stmt = $this->con->prepare("DELETE FROM Client WHERE id = ? ");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
    
    function deleteTransaction($id){
		$stmt = $this->con->prepare("DELETE FROM Transaction WHERE id = ? ");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
    
    
}

?>