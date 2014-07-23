'use strict';


polstApp.controller("ePolstController", function($scope, polstService, $modal,   $location, $routeParams,  $timeout){
	var responseHandler = new ResponseHandler($scope);

	//POLST LIST (MEDCERT and MEDSTAFF view) JAVASCRIPT
    $scope.patientListForMed = [];
    $scope.message; //lets user know if zero records were found in their search
	$scope.quickSearchName;
	$scope.stillTyping = true;
	$scope.disablePrevious = true
	$scope.disableNext = true;
	$scope.currentPage=0;
	$scope.totalRecords=0;
	$scope.recordsPerPage=50;
	$scope.totalPages=0;
	$scope.searchBean={ 'currentPage':$scope.currentPage, 'recordsPerPage': $scope.recordsPerPage, 'quickSearchDob':'', 'quickSearchName':'' 	};
	
	//SET UP TOOL TIP FOR CURRENT USER 
	$scope.setUpToolTip=function(){
		//SEARCH GRID TOOL TIPS
		//View All/View Mine radio buttons
		$scope.toolTipAllEpolst="View All ePOLST forms";
		$scope.toolTipMyEpolst =  "View ePolst for Patients who are assigned to me.";
		$scope.status="Only ACTIVE status ePOLST forms will be viewable by EMT/EMS."
	 	 
	 	if('ROLE_MEDCERT' === $scope.role && $scope.searchBean['showAll'] == false  ){
	 		$scope.toolTipMyEpolst = "View ePolst for Patients who are assigned to me";
	 	} else if('ROLE_MEDSTAFF' === $scope.role && $scope.searchBean['showAll'] == false   ){
	 		$scope.toolTipMyEpolst= "View ePolst/Patients that were added by me.";
	 	}else if('ROLE_ADMIN' === $scope.role ){
	 		$scope.toolTipMyEpolst = "View All Patients with an ePOLST. ";
	 	} 	
 	 	
	 	//epolst status radio buttons
 		$scope.toolTipAll="View All ePOLST ";
	    $scope.toolTipActive="View Active ePOLST ";
	    $scope.toolTipInProcess="View In Process ePOLST ";
	    $scope.toolTipPending="View Pending Certification ePOLST " ;
	 	var suffix="";
     	if('ROLE_MEDCERT' === $scope.role && $scope.searchBean['showAll'] == false  ){
     		suffix = " assigned to me.";
     	} else if('ROLE_MEDSTAFF' === $scope.role && $scope.searchBean['showAll'] == false   ){
      	   suffix = "created by me.";
     	}else if('ROLE_ADMIN' === $scope.role ){
     		suffix = "in system.";
     	} 
     	$scope.toolTipAll  += suffix;
     	$scope.toolTipActive += suffix;
     	$scope.toolTipInProcess += suffix;
     	$scope.toolTipPending += suffix;
	};
	
	//DEFAULT VIEW
    $scope.initPhysicianList=function(){
  	
    	$scope.searchBean['polstStatus']="PENDING_CERTIFICATION"; 
    	$scope.searchBean['currentPage'] = 1;
    	$scope.searchBean['sortField'] = "patientLastName";
    	$scope.searchBean['showAll'] = false;
    	$scope.setUpToolTip();
    	
    	polstService.searchPolstListForMed($scope.searchBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
     	});
    };
     
   //RADIO BUTTONS or CHECK BOX on search grid
   $scope.changeView=function(){
    	$scope.searchBean['currentPage'] = 1;
    	$scope.searchBean['sortField'] = "patientLastName";
    	$scope.setUpToolTip();
       	polstService.searchPolstListForMed($scope.searchBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
     	});
    };

    $scope.onViewChange=function(message){
    	//lets user know if zero records were found in their search
    	$scope.message=message;
     };
 
    //when user enters name into Search Field
	//after they stop typing then an ajax call is sent.
	$scope.$watch('quickSearchName' , function(){
	  if (undefined !== $scope.quickSearchName){
			if (2 < $scope.quickSearchName.length){
				//reset currentPage
				$scope.searchBean['currentPage'] = 1;
				$scope.currentPage=0;
				$scope.startSearch();
			}else if (0 === $scope.quickSearchName.length){
				$scope.searchBean['quickSearchName']="";
				$scope.changeView();
			}	
		}
	});
 
	
	$scope.stopTyping=function(){
		console.log("Timeout has been hit.. check flag");
		$scope.stillTyping=false;
	};
    	
	$scope.startSearch=function(){
	 	console.log("Is user still typing? " + $scope.stillTyping);
	    $timeout( $scope.stopTyping, 500);
		console.log("Is user still typing after timeout? " + $scope.stillTyping); 
		if (!$scope.stillTyping){
		  	$scope.searchBean['quickSearchName'] = $scope.quickSearchName;	
	    	$scope.sendSearchRequest();
			$scope.stillTyping=true;
		}
	 };
	 
	 //calls service to send ajax call to EmsController.java
	 $scope.sendSearchRequest = function(){
		console.log("Send Search Request.");
		polstService.searchPolstListForMed($scope.searchBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
     	});
	  };
	 
	 
	  //call back -- after search is complete
	  $scope.onSearchComplete=function(searchBean){
		  $scope.setRecordNavButtons(searchBean);
	  };

	
      //control navigation buttons
	  $scope.setRecordNavButtons=function(searchBean){
    	
			if (1 < searchBean.currentPage){
	    		$scope.disablePrevious=false;
	    	}else{
	    		$scope.disablePrevious=true;
	    	}
			
	    	if ($scope.totalPages > $scope.currentPage ){
	    		$scope.disableNext=false;
	    	}else{
	    		$scope.disableNext=true;
	    	}
    	};
 
    
	  $scope.getPrevious=function(){
    	$scope.searchBean['currentPage']=$scope.currentPage -=1;
    	$scope.sendSearchRequest();
	  };
    
	  $scope.getNext=function(){
    	$scope.searchBean['currentPage']=$scope.currentPage +=1;
    	$scope.sendSearchRequest();
	  };

















	//JAVASCRIPT for POLST DETAIL (when user clicks grid)
    $scope.patient={};
    $scope.polstBean={};
    $scope.disableEpolstForm = false;
    $scope.disableInActivateButton = false;
    $scope.disablePrepareButton = false;
    $scope.resetRouteFlag=false;
    
    //control display of patient discussions
    $scope.showParentDisc=false;
    $scope.showSurrogateDisc=false;
    $scope.showOtherDisc=false;
    
    //control display of patient authorization
    $scope.showParentAuth=false; 
    $scope.showSurrogateAuth=false;    
    $scope.showOtherAuth=false;    
    
    //objects that hold new values for discussion and authorizations
    $scope.newParentDisc={};
  	$scope.newSurrogateDisc={};
    $scope.newOtherDisc={};
 	$scope.newSurrogateAuth={};
	$scope.newParentAuth={};


    //link from polst table/grid/list
	$scope.showPolstDetail = function(polstId, patientId ){
		$location.url('/polstDetail/'+  polstId +"/" + patientId ); 
	};
	
	//INITIALIZE polst detail
	$scope.initPolstForm = function(){
		//grab variables from the query string
		var ePolstId = $routeParams.polstId;
		var patientId = $routeParams.patientId;
	
		//load patient section
		polstService.getPatient( patientId).then(function(response) {
    		responseHandler.processCommands(response.commands);
      	});
		
      	if ('new'=== ePolstId){//load empty polstBean into ePOLST detail page.
      		console.log("Create New ePOLST.");
      		$scope.resetRouteFlag=true;
      		polstService.createNewEpolst(patientId).then(function(response) {
				responseHandler.processCommands(response.commands);
      		});	
      	 }else if('ems'=== ePolstId ){
      		 console.log("EMS ePOLST view, ACTIVE");
      		 polstService.getActivePolstByPatientId(patientId ).then(function(response) {
 				responseHandler.processCommands(response.commands);
 			 });
      	 }else{ 
      		console.log("Load existing ePOLST, PHYSICIAN or NURSE view") ;
	        polstService.getPolstByIdByPatient( ePolstId, patientId ).then(function(response) {
				responseHandler.processCommands(response.commands);
			 });
		 }
	 };
	
	//need this in case user reloads a new ePolst during the inital save. 
	$scope.resetRouteParams=function(bean){
		 var url= '/polstDetail/'+  bean.polstId +"/" + bean.patientId  ;  
		 $location.path(url);
 	};
 	
 	$scope.onPatientLoaded=function(bean){
 		console.log("Patient Data base been loaded.");
 	};
   
   //DEFAULTS set after the PolstBean has been retfrom server
   $scope.onLoadPolstForm = function(bean){
	 console.log("Polst Bean returned from server:" + bean.polstId + " Patient: " + bean.patientId);
	 if ($scope.resetRouteFlag){
		 $scope.resetRouteParams(bean);
		 console.log("Step-New - Reset Route Parameters for new ePOLST.");
	 }
	 $scope.enableFormLogic();
	 console.log("Step-1 - Disable/Enable form controls based on user role and ePOLST status.");
	// $scope.prepareSectionLogic();
	 console.log("Step-2 - Set Up prepare section logic.");
 	 $scope.setDiscussionDefaults();
 	 console.log("Step-3 - Set Up Discussion Section.");
 	 $scope.setAuthorizationDefaults(); 
 	 console.log("Step-4 - Set Up Authorization Section.");
   };

  //PAGE LOAD DEFAULTS
  //enable-disable logic based on status and logged in user -- controlled at fieldset
  $scope.enableFormLogic=function(){
	  
	 if (( 'ACTIVE' === $scope.polstBean.status || 'IN_ACTIVE' === $scope.polstBean.status )){
		  $scope.disableEpolstForm = true;
		  console.log("Active - disable form. "  );
		  if ($scope.role === 'ROLE_EMS' || 'IN_ACTIVE' === $scope.polstBean.status){
			$scope.disableInActivateButton=true;
			console.log("User is EMT or status is In-Active - disable inactivate button on menu. "  );
		  }
		
	  }else if (  $scope.role === 'ROLE_ADMIN'){
		 $scope.disableEpolstForm = true;
		 $scope.disableInActivateButton = true;
		 console.log("ROLE_ADMIN -- DISABLE" + $scope.role);
	  }else if(($scope.role === 'ROLE_MEDSTAFF' && $scope.currentUser !==  $scope.polstBean.createdByUserName ) || ($scope.role === 'ROLE_MEDCERT' && $scope.currentUser !==   $scope.polstBean.physicianUserName )){
		 $scope.disableEpolstForm = true;
		 $scope.disableInActivateButton = true;
		 console.log("Disable form -- current user doesn't match record owner.'");
	   }
	
	  
  
  };

  //HELPER method
  $scope.isPrepared=function(){
	   if ('PENDING_CERTIFICATION' === $scope.polstBean.status ){
		  return true;
	  }else{
		  return false;
	  }
  };
  
  //LOGIC to show/enable Prepare Section
//  $scope.prepareSectionLogic=function(){
//	  if( $scope.isPrepared() ) {
//		  $scope.disablePrepareButton = true;
//	  }else{
//		  $scope.disablePrepareButton = false;
//	  }
//  };

  //DISCUSSION show list depending on value
  $scope.setDiscussionDefaults = function(){
	 if ( null !=  $scope.polstBean.parentDiscussions  &&  $scope.polstBean.parentDiscussions.length > 0){
		$scope.showParentDisc = true;
	 } 		 
	 if (null !==   $scope.polstBean.surrogateDiscussions &&  $scope.polstBean.surrogateDiscussions.length > 0){
		$scope.showSurrogateDisc = true;
	}
	 if (null !==  $scope.polstBean.otherDiscussions &&  $scope.polstBean.otherDiscussions.length > 0){
		$scope.showOtherDisc = true;
	}
	 $scope.newParentDisc={};
	 $scope.newSurrogateDisc={};
	 $scope.newOtherDisc={};
 };
 //AUTHORIZATION (sets checkboxes after page load)
  $scope.setAuthorizationDefaults=function(){
	 if (null !== $scope.polstBean.parentAuthorizations &&  $scope.polstBean.parentAuthorizations.length > 0){
 		$scope.showParentAuth = true;
 	 }
	if (null !== $scope.polstBean.surrogateAuthorizations &&  $scope.polstBean.surrogateAuthorizations.length > 0){
 		$scope.showSurrogateAuth = true;
 	}
	 $scope.newSurrogateAuth={};
	 $scope.newParentAuth={};
 };
  
  	
	//USER EVENT METHODS
	//DISCUSSION (hide/show discussion sections)
	$scope.toggleParentDiscussions=function(){
		if ($scope.showParentDisc === true){
			
			if(null != $scope.polstBean.parentDiscussions ){
				if ($scope.polstBean.parentDiscussions.length > 0){ 
					var deleteDiscussion = confirm("Confirm OK to remove Parent Discussion records.")
					if (deleteDiscussion ){
						$scope.showParentDisc = false;//hide data entry fields.
						var discussionBean={'ePolstId': $scope.polstBean.polstId, 'type':'PARENT_OF_MINOR'};
						//if Status PENDING -- warn user's of form status change
						if (  $scope.continuePendingCertificationStatus() ){
							polstService.deleteDiscussionsForType(discussionBean).then(function(response) {
								responseHandler.processCommands(response.commands);
							});
						}	
					 }
				}//length	
				else{
					$scope.showParentDisc = false;//hide data entry fields.
				}
		 	}else{
				$scope.showParentDisc = false;//hide data entry fields.
			}	
		}else{
			$scope.showParentDisc=true;
		}	
	};
	
	//SURROGATE checkbox -- user un=checks give them a warning that all will be removed.
	$scope.toggleSurrogateDiscussions=function(){
		if ($scope.showSurrogateDisc === true){
			
			if( null !== $scope.polstBean.surrogateDiscussions){
					if ( $scope.polstBean.surrogateDiscussions.length>0){
						var deleteDiscussion = confirm("Confirm OK to remove Surrogate Discussion records.");
						if (deleteDiscussion ){
							$scope.showSurrogateDisc = false;//hide data entry fields
							var discussionBean={'ePolstId': $scope.polstBean.polstId, 'type':'SURROGATE'};
							if (  $scope.continuePendingCertificationStatus() ){
								polstService.deleteDiscussionsForType(discussionBean).then(function(response) {
										responseHandler.processCommands(response.commands);
								});
							}	
						}
					}//length >0
					else{
						$scope.showSurrogateDisc = false;//hide data entry fields
					}
			}else{
				$scope.showSurrogateDisc = false;//hide data entry fields
			}
			
			
		}else{
			$scope.showSurrogateDisc=true;//show data entry fields
		}	
	};
	
	
	 //OTHER checkbox -- if user un-clicks confirm that they want all removed.
	 $scope.toggleOtherDiscussions=function(){
		if ($scope.showOtherDisc === true){
			
			if(null !== $scope.polstBean.otherDiscussions){
					if ($scope.polstBean.otherDiscussions.length>0){
						var deleteDiscussion = confirm("Confirm OK to remove Other Discussion records.");
						if (deleteDiscussion ){
							$scope.showOtherDisc = false;//hide data entry fields.
							var discussionBean={'ePolstId': $scope.polstBean.polstId, 'type':'OTHER'};
							//if user attempts to remove all discussions for status PENDING -- give them a warning message. 
							if (  $scope.continuePendingCertificationStatus() ){
								polstService.deleteDiscussionsForType(discussionBean).then(function(response) {
									responseHandler.processCommands(response.commands);
								});
							}	
						
						
						}
					}//length	
					else{
						$scope.showOtherDisc = false;//hide data entry fields.
					}
			}else{
				$scope.showOtherDisc = false;//hide data entry fields.
			}//unchecked box... 
			
		}else{
			$scope.showOtherDisc=true;//box is checked, show data entry fields.
		}	
	};
	
	//patient discussion check box either adds or deletes an discussion record.
	$scope.udpdatePatientDiscussion=function(){
		$scope.newPatientDisc={'type':'PATIENT', 'ePolstId':   $scope.polstBean.polstId,  'patientId': $scope.polstBean.patientId   };
		if (  $scope.continuePendingCertificationStatus() ){
			(true === $scope.polstBean.patientDiscussed) ? $scope.polstBean.patientDiscussed = false : $scope.polstBean.patientDiscussed = true;
		 	if ( $scope.polstBean.patientDiscussed ){
		 	    polstService.savePolstDiscussion($scope.newPatientDisc).then(function(response) {
		 			responseHandler.processCommands(response.commands);
		 		});
			}else{
				polstService.deletePolstDiscussion($scope.newPatientDisc).then(function(response) {
		 			responseHandler.processCommands(response.commands);
		 		});
			}
		}			
     };		
		 
     
    //PARENT DISCUSSIONS save/enable-disable
    $scope.disableParentDiscSave = true;
   	$scope.$watch('newParentDisc.phone' , function(){ 	
   	  if (typeof $scope.newParentDisc.discussorsRelationship !== 'undefined' && typeof $scope.newParentDisc.name !== 'undefined' && typeof $scope.newParentDisc.phone !== 'undefined'  ){
   		  $scope.disableParentDiscSave=false;
   	  }	
	});
   	//SURROGATE DISCUSSION save/enable-disable
	 $scope.disableSurrogateDiscSave = true;
	 $scope.$watch('newSurrogateDisc.phone' , function(){ 	
	
  	  if ( typeof $scope.newSurrogateDisc.discussorsRelationship !== 'undefined' && typeof $scope.newSurrogateDisc.name !== 'undefined' && typeof $scope.newSurrogateDisc.phone !== 'undefined'  ){
  		  $scope.disableSurrogateDiscSave = false;
  	  }	
	});
	 //OTHER DISCUSSIONS save/enable-disable
	 $scope.disableOtherDiscSave = true;
	 $scope.$watch('newOtherDisc.phone' , function(){ 	
	  if ( typeof $scope.newOtherDisc.discussorsRelationship !== 'undefined' && typeof $scope.newOtherDisc.name !== 'undefined' && typeof $scope.newOtherDisc.phone !== 'undefined'  ){
  		  $scope.disableOtherDiscSave = false;
  	  }	
	});	

	 

	//SAVE DISCUSSIONS
	$scope.saveDiscussion=function(newDisc){
		//these values need to be saved with the new*Disc object.
		//add them to the newDisc object before we send the ajax call.
		$scope.newParentDisc['ePolstId']= $scope.polstBean.polstId;
		$scope.newParentDisc['patientId']= $scope.polstBean.patientId;
		$scope.newParentDisc['type']='PARENT_OF_MINOR';
			
		$scope.newSurrogateDisc['ePolstId']=$scope.polstBean.polstId;
	    $scope.newSurrogateDisc['patientId']= $scope.polstBean.patientId;
	    $scope.newSurrogateDisc['type']='SURROGATE';
	    	
	    $scope.newOtherDisc['ePolstId']=  $scope.polstBean.polstId;
	    $scope.newOtherDisc['patientId']=  $scope.polstBean.patientId  ;	
	    $scope.newOtherDisc['type']='OTHER';
	    
	    $scope.disableParentDiscSave = true;
		$scope.disableSurrogateDiscSave = true;
		$scope.disableOtherDiscSave = true;
		 
	    
	    if (  $scope.continuePendingCertificationStatus() ){
		    polstService.savePolstDiscussion(newDisc).then(function(response) {
	    		responseHandler.processCommands(response.commands);
	      	});
		 }    
	};
	//deletes individual discussions
	$scope.deleteDiscussion=function(discussion){
		if (  $scope.continuePendingCertificationStatus() ){
			polstService.deletePolstDiscussion(discussion).then(function(response) {
	    		responseHandler.processCommands(response.commands);
	    	});
		}	
	};
	
	$scope.onDiscSaveErr=function(){
		console.log("An issue was encountered while saving ePOLST discustion.");
	};
	
	


	//AUTHORIZATION->PARENT checkbox -- warn users if they attempt to delete all
	 $scope.toggleParentAuthorizations=function(){
		if ($scope.showParentAuth === true){
			
			if( null !== $scope.polstBean.parentAuthorizations){
			  	if ( $scope.polstBean.parentAuthorizations.length > 0 ){
					var deleteAuthorizations = confirm("Confirm OK to remove Parent Authorization records.");
					if (deleteAuthorizations  ){
						$scope.showParentAuth = false;//hide data entry
						var authorizationBean={'ePolstId': $scope.polstBean.polstId, 'type':'PARENT_OF_MINOR'};
						//warn users status will be rolled back
						if (  $scope.continuePendingCertificationStatus() ){
							polstService.deleteAuthorizationsForType(authorizationBean).then(function(response) {
					 			responseHandler.processCommands(response.commands);
					 		});
						}	
					}	
				}//length	
			} 
			//hide data entry
			$scope.showParentAuth = false;
		}else{
			$scope.showParentAuth=true;
		}	
	};
	
	//AUTHORIZATION->SURROGATE checkbox -- warn users if they attempt to delete all
	$scope.toggleSurrogateAuthorizations=function(){
		if ($scope.showSurrogateAuth === true){
			
			if( null !== $scope.polstBean.surrogateAuthorizations){
				if ( $scope.polstBean.surrogateAuthorizations.length>0){
					var deleteAuthorizations = confirm("Confirm OK to remove Surrogate Authorization records.");
					if (deleteAuthorizations  ){
						//hide authorization.surrogate section
						$scope.showSurrogateAuth = false;
						var authorizationBean={'ePolstId': $scope.polstBean.polstId, 'type':'SURROGATE'};
						//warn users that status will be rolled back to pending.
						if (  $scope.continuePendingCertificationStatus() ){
							polstService.deleteAuthorizationsForType(authorizationBean).then(function(response) {
					 			responseHandler.processCommands(response.commands);
					 		});
						 }	
					}	
				 }	
			} 
		    $scope.showSurrogateAuth = false;//hide data entry
 		
		}else{
			$scope.showSurrogateAuth=true;
		}	
	};

	
    //Patient Authorization check box either adds or removes a patient authorization record.
	$scope.udpdatePatientAuthorization=function(){
		if (  $scope.continuePendingCertificationStatus() ){
	
				//set Default Authorization Values
			    $scope.newPatientAuth={'type':'PATIENT', 'ePolstId':  $scope.polstBean.polstId, 'appointedAgent' :'',  'patientId': $scope.polstBean.patientId  };
			 	(true === $scope.polstBean.patientAuthorized) ? $scope.polstBean.patientAuthorized= false : $scope.polstBean.patientAuthorized = true;
			 	if ( $scope.polstBean.patientAuthorized ){
					polstService.savePolstAuthorization($scope.newPatientAuth).then(function(response) {
			 			responseHandler.processCommands(response.commands);
			 		});
				}else{
				 	polstService.deletePolstAuthorization($scope.newPatientAuth).then(function(response) {
			 			responseHandler.processCommands(response.commands);
			 		});
				}
		    }	
     };	

    //Enable-Disable Authorized Save Button
    $scope.disableParentAuthSave = true;
   	$scope.$watch('newParentAuth.authorizedDate' , function(){ 	
   		if (typeof $scope.newParentAuth.authorizerName !== 'undefined' && typeof $scope.newParentAuth.authorizerRelationship !== 'undefined' && typeof $scope.newParentAuth.authorizerPhone !== 'undefined' && typeof $scope.newParentAuth.authorizedDate !== 'undefined'){
   			$scope.disableParentAuthSave=false;
   		}	
	});
	
	//Enable-Disable Authorized Save Button
	$scope.disableSurrogateAuthSave = true;
	$scope.$watch('newSurrogateAuth.authorizedDate' , function(){ 	
		if (typeof $scope.newSurrogateAuth.authorizerName !== 'undefined' && typeof $scope.newSurrogateAuth.appointedAgent !== 'undefined' && typeof $scope.newSurrogateAuth.authorizerPhone !== 'undefined' && typeof $scope.newSurrogateAuth.authorizedDate !== 'undefined'){
	   			$scope.disableSurrogateAuthSave=false;
	   		}	
	});	
 
	//SAVE AUTHORIZATION
	$scope.saveAuthorization=function(authorization){
		//these values need to be saved on the authorization object.
	    $scope.newParentAuth['type']='PARENT_OF_MINOR';
	    $scope.newParentAuth['ePolstId']=$scope.polstBean.polstId;
	    $scope.newParentAuth['patientId']=$scope.polstBean.patientId; 
	    $scope.newSurrogateAuth['type']='SURROGATE';
	    $scope.newSurrogateAuth['ePolstId']=$scope.polstBean.polstId;
	    $scope.newSurrogateAuth['patientId']=$scope.polstBean.patientId;
	    
	    $scope.disableParentAuthSave = true;
	    $scope.disableSurrogateAuthSave = true;
	    if (  $scope.continuePendingCertificationStatus() ){
			polstService.savePolstAuthorization(authorization).then(function(response) {
				responseHandler.processCommands(response.commands);
			});
		}	
	};
    $scope.onAuthSaveErr=function(){
		console.log("An issue was encountered while saving ePOLST discustion.");
	};
	
   $scope.deleteAuthorization=function(authorization){
	   if (  $scope.continuePendingCertificationStatus() ){
			polstService.deletePolstAuthorization(authorization).then(function(response) {
				responseHandler.processCommands(response.commands);
			});
		}	
	};
	 

	



    //GENERAL POLST JAVASCRIPT METHODS
	$scope.savePolst = function( ){
	  	if (  $scope.continuePendingCertificationStatus() ){
			polstService.savePolst($scope.polstBean ).then(function(response) {
			   responseHandler.processCommands(response.commands);
		   });	
		}	   
	};
 
	$scope.onPolstSaveError = function(){
		console.log("An Issue was encountered saving ePOLST.");
	};

	$scope.prepareEpolst=function(){
	  polstService.preparePolst($scope.polstBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
      	});
	  $scope.disablePrepareButton = true;
	};
	
	$scope.printEpolst=function(){
		   window.open( 'printEPolst/'+$scope.polstBean.polstId );
	};
	 
	//WARN users if they attempt to update an ePOLST bean.status=PENDING_CERTIFCATION
	$scope.continuePendingCertificationStatus=function(){
		var continueToSave = true;
		if ( $scope.isPrepared()){
			continueToSave = confirm("WARNING -- ePOLST status  PENDING CERTIFICATION will revert back to IN PROCESS.  OK to Confirm or CANCEL to roll back.");
		   
			if (continueToSave){
				$scope.polstBean.status = 'IN_PROCESS';
				$scope.polstBean.preparedBy='';
				$scope.polstBean.preparedDate='';
				$scope.polstBean.sectionStatus.preparerComplete=false;
				$scope.prepareEpolst();
			}else{
				continueToSave = false;
				//reset form changes
				polstService.getPolstByIdByPatient( $scope.polstBean.polstId, $scope.polstBean.patientId ).then(function(response) {
					responseHandler.processCommands(response.commands);
				 });
			}
		}
		 
		return continueToSave;
	};
 
	$scope.certifyEpolst=function(){
	  polstService.certifyPolst($scope.polstBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
      });
	};



	
//CANCEL EPOLST
$scope.cancelEpolst=function(){
	var modalInstance = $modal.open({
	      templateUrl: 'resources/js/partials/cancelModal.html',
	      controller: CancelPolstCtrl,
	      windowClass:  'polst-modal-window', 
	      resolve: {
	       patient: function () {
		          return $scope.patient;
		     },
		    polst: function () {
		          return $scope.polstBean;
		     }
	       }
	    });
};
 
	
var CancelPolstCtrl = function ($scope, $modalInstance, patient, polst ) {
  
	$scope.patient = patient;
	$scope.polst =  polst;
	
	$scope.cancelPolst=function(  ){
	   polstService.cancelPolst(polst ).then(function(response) {
		 responseHandler.processCommands(response.commands);
	 	});
	  $modalInstance.close();
  };
   
   $scope.closeModal = function () {
	    $modalInstance.dismiss('cancel');
   };
};












	$scope.checkEmailHistory = function(){
		polstService.getEmailHistory($scope.polstBean.polstId ).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	};

    //modal pop-up when user selects sendEmail button from menu
	$scope.onHistoryCheck=function( emailHistory ){
		var modalInstance = $modal.open({
		      templateUrl: 'resources/js/partials/emailHistoryModal.html',
		      windowClass:  'polst-modal-window', 
		      controller: EmailHistoryCtrl,
		      resolve: {
		        items: function () {
		          return emailHistory;
		        }
		      }
		    });
	  	 
  
           modalInstance.result.then(function (selectedItem) {
        	   $scope.selected = selectedItem;
           }, function () {
        	   console.log('Modal dismissed at: ' + new Date());
           });
 };
	
	//controller for EmailHistoryModal
	var EmailHistoryCtrl = function ($scope, $modalInstance, items ) {
	  $scope.items=items;
	  $scope.sendReminder = function ( ) {
		  polstService.sendReminderEmail(items[0].polstId).then(function(response) {
		 		responseHandler.processCommands(response.commands);
		  });
		  $modalInstance.close();
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
 };





});