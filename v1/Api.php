<?php 

	//getting the dboperation class
	require_once '../Includes/DbOperation.php';

	//function validating all the paramters are available
	//we will pass the required parameters to this function 
	function isTheseParametersAvailable($params){
		//assuming all parameters are available 
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}
		
		//if parameters are missing 
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
			
			//displaying error
			echo json_encode($response);
			
			//stopping further execution
			die();
		}
	}
	
	//an array to display response
	$response = array();
	
	//if it is an api call 
	//that means a get parameter cnamed api call is set in the URL 
	//and with this parameter we are concluding that it is an api call
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
			//the CREATE operation
			//if the api call value is 'createRecord'
			//we will create a record in the database
			case 'createRecord':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('cname','fname','number'));
				
				//creating a new dboperation object
				$db = new DbOperation();
				
				//creating a new record in the database
				$result = $db->createRecord(
					$_POST['cname'],
					$_POST['fname'],
                    $_POST['number']
                   
				);
				

				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 

					//in message we have a success message
					$response['message'] = 'Record addedd successfully';

					//and we are getting all the records from the database in the response
					$response['Records'] = $db->getrecords();
				}else{

					//if record is not added that means there is an error 
					$response['error'] = true; 

					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 

			case 'login':

				isTheseParametersAvailable('username');
				$db = new DbOperation();
				$response["Authorized"] = $db->login($_POST['username']);
			break;
			
			//the READ operation
			//if the call is getrecords
			case 'getRecords':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['Records'] = $db->getrecords();
			break; 
			
			
			//the UPDATE operation
			case 'updateRecord':
				isTheseParametersAvailable(array('id','cname','fname','number'));
				$db = new DbOperation();
				$result = $db->updateRecord(
					$_POST['id'],
					$_POST['cname'],
					$_POST['fname'],
                    $_POST['number']
                   
				);
				
				if($result){
					$response['error'] = false; 
					$response['message'] = 'Client updated successfully';
					$response['Records'] = $db->getrecords();
				}else{
					$response['error'] = true; 
					$response['message'] = 'Some error occurred please try again';
				}
			break; 
			
			//the delete operation
			case 'deleteRecord':

				//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
				if(isset($_GET['id'])){
					$db = new DbOperation();
					if($db->deleteRecord($_GET['id'])){
						$response['error'] = false; 
						$response['message'] = 'Client deleted successfully';
						$response['Records'] = $db->getrecords();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an id please';
				}
			break; 
		}
        
        
    switch($_GET['apicall']){
			
			//the CREATE operation
			//if the api call value is 'createRecord'
			//we will create a record in the database
			case 'createTransaction':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('cname2','amount','cost','type'));
				
				//creating a new dboperation object
				$db = new DbOperation();
				
				//creating a new record in the database
				$result = $db->createTransaction(
					$_POST['cname2'],
					$_POST['amount'],
					$_POST['cost'],
                    $_POST['type']
                   
				);
				

				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 

					//in message we have a success message
					$response['message'] = 'Transaction addedd successfully';

					//and we are getting all the records from the database in the response
					$response['Transactions'] = $db->gettransactions();
				}else{

					//if record is not added that means there is an error 
					$response['error'] = true; 

					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			//the READ operation
			//if the call is getrecords
			case 'getTransactions':

				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['Transactions'] = $db->gettransactions();
			break; 
			
			
			//the UPDATE operation
			case 'updateTransaction':
				isTheseParametersAvailable(array('id','cname2','amount','cost','type'));
				$db = new DbOperation();
				$result = $db->updateTransaction(
					$_POST['id'],
					$_POST['cname2'],
					$_POST['amount'],
					$_POST['cost'],
                    $_POST['type']
                   
				);
				
				if($result){
					$response['error'] = false; 
					$response['message'] = 'Transaction updated successfully';
					$response['Transactions'] = $db->gettransactions();
				}else{
					$response['error'] = true; 
					$response['message'] = 'Some error occurred please try again';
				}
			break; 
			
			//the delete operation
			case 'deleteTransaction':

				//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
				if(isset($_GET['id'])){
					$db = new DbOperation();
					if($db->deleteRecord($_GET['id'])){
						$response['error'] = false; 
						$response['message'] = 'Client deleted successfully';
						$response['Records'] = $db->getrecords();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an id please';
				}
			break; 
		}
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);
?>